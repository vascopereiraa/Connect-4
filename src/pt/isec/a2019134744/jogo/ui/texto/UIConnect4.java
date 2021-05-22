package pt.isec.a2019134744.jogo.ui.texto;

import pt.isec.a2019134744.jogo.logica.GestorDeJogo;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;
import pt.isec.a2019134744.jogo.utils.Util;

import java.util.List;
import java.util.Scanner;

public class UIConnect4 {

    private final GestorDeJogo gestorDeJogo;
    private boolean sair;
    private final Scanner sc;

    public UIConnect4(GestorDeJogo gestorDeJogo) {
        this.gestorDeJogo = gestorDeJogo;
        this.sair = false;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        while(!sair) {
            switch (gestorDeJogo.getSituacao()) {
                case Inicio -> uiInicio();
                case AguardaJogadores -> uiAguardaJogadores();
                case AguardaJogada -> {
                    if (gestorDeJogo.isHumano()) uiAguardaJogada();
                    else jogaVirtual();
                }
                case DecisaoMinijogo -> uiDecisaoMinijogo();
                case JogaMinijogo -> uiJogaMinijogo();
                case FimJogo -> uiFimJogo();
            }
        }
    }

    private void uiFimJogo() {
        System.out.println("\n" + ConsoleColors.BLUE + "Fim de Jogo" + ConsoleColors.RESET);
        System.out.println(gestorDeJogo.getInfoJogo());
        switch (Util.escolheOpcao("Recomecar", "Sair")) {
            case 1 -> gestorDeJogo.recomeca();
            case 0 -> sair = true;
        }
    }

    private void uiJogaMinijogo() {
        System.out.println("\n" + gestorDeJogo.getPerguntaMinijogo());
        gestorDeJogo.respondeMinijogo(sc);
        System.out.println(gestorDeJogo.getResultadoJogo());
    }

    private void uiDecisaoMinijogo() {
        System.out.println("\n" + ConsoleColors.BLUE + "Decisao do Minijogo" + ConsoleColors.RESET);
        System.out.println(gestorDeJogo.getInfoJogo());
        switch (Util.escolheOpcao("Jogar o minijogo", "Desistir do minijogo")) {
            case 1 -> gestorDeJogo.jogaMinijogo();
            case 0 -> gestorDeJogo.desisteMinijogo();
        }
    }

    private void uiAguardaJogada() {
        System.out.println("\n" + ConsoleColors.BLUE + "Aguarda Jogada" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN + "Jogador Humano" + ConsoleColors.RESET);
        System.out.print(gestorDeJogo.getInfoJogo());
        System.out.println(gestorDeJogo.getTabuleiro());
        boolean keep = true;
        do {
            switch (Util.escolheOpcao("Jogar peça", "Jogar peça especial", "Voltar a trás", "Gravar jogo", "Desistir")) {
                case 1 -> gestorDeJogo.joga(Util.pedeInteiro("Numero da Coluna: "));
                case 2 -> gestorDeJogo.jogaEspecial(Util.pedeInteiro("Numero da Coluna: "));
                case 3 -> gestorDeJogo.undo(Util.pedeInteiro("Numero de jogadas a reverter: "));
                case 4 -> {
                    System.out.println(gestorDeJogo.gravaJogo(Util.pedeString("Nome do ficheiro: ")) + "\n");
                    keep = false;
                }
                case 0 -> gestorDeJogo.termina();
            }
        } while(!keep);
        System.out.println("\n" + gestorDeJogo.getResultadoJogo());
        System.out.println("..Presssione [ENTER] para continuar..");
        sc.nextLine();
    }

    private void uiAguardaJogadores() {
        System.out.println("\n" + ConsoleColors.BLUE + "Aguarda Jogadores" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Comecar Jogo", "Recomecar")) {
            case 1 -> {
                System.out.print("""
                
                Modos de jogo:
                 - 1 vs 1
                 - 1 vs Computador
                 - Computador vs Computador
                
                Nota: Apenas introduza o nome dos jogadores, não do computador!
                Nomes:""" + " ");
                gestorDeJogo.comeca(sc.nextLine().split("\\s"));
            }
            case 0 -> gestorDeJogo.recomeca();
        }
        String contexto = gestorDeJogo.getResultadoJogo();
        if(contexto.charAt(0) != ' ') {
            System.out.println("\n" + contexto);
            System.out.println("..Presssione [ENTER] para iniciar o jogo..");
            sc.nextLine();
        }
        else
            System.out.println("\n" + contexto.substring(1));

    }

    private void uiInicio() {
        System.out.println(ConsoleColors.BLUE + "Inicio" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Começar", "Ver o replay de um jogo anterior", "Carregar um jogo", "Sair")) {
            case 1 -> gestorDeJogo.inicia();
            case 2 -> verReplay();
            case 3 -> System.out.println(gestorDeJogo.carregaJogo(Util.pedeString("Nome do ficheiro: ")) + "\n");
            case 0 -> sair = true;
        }
    }

    private void jogaVirtual() {
        System.out.println("\n" + ConsoleColors.BLUE + gestorDeJogo.getSituacao() + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN + "Jogador Virtual" + ConsoleColors.RESET);
        System.out.println(gestorDeJogo.getInfoJogo());
        int random = (int) (Math.random() * 7) + 1;
        System.out.println(gestorDeJogo.getNomeJogador() + ": Vou jogar uma peça na coluna " + random);
        gestorDeJogo.joga(random);
        System.out.println("\n" + gestorDeJogo.getResultadoJogo());
        System.out.println("..Presssione [ENTER] para continuar..");
        sc.nextLine();
    }

    private void verReplay() {
        System.out.println("\n" + ConsoleColors.BLUE + "Ver Replay de um Jogo" + ConsoleColors.RESET);
        System.out.println("Lista de replays guardados: ");
        List<String> ficheiros = gestorDeJogo.getListaReplays();

        if(ficheiros.size() == 0) {
            System.out.println("""
            Não existe nenhum replay guardado!
            ..Presssione [ENTER] para continuar..
            """);
            sc.nextLine();
            return ;
        }

        // Remove item mais antigo da pasta _> Este está sempre na ult. pos.
        ficheiros.remove(ficheiros.size() - 1);

        for(int i = 0; i < ficheiros.size(); ++i)
            System.out.println((i + 1) + " - " + ficheiros.get(i));

        int num;
        do {
            num = Util.pedeInteiro("Introduza o numero do ficheiro que prentende carregar: ");
        } while(num < 1 || num > ficheiros.size());

        
        List<String> replay = gestorDeJogo.verReplay(ficheiros.get(num - 1));
        if(replay == null)
            return;
        int i = 0;
        while(i < replay.size()) {
            String frase = replay.get(i);
            if ("espera".equalsIgnoreCase(frase)) {
                System.out.println( """
                        ..Presssione [ENTER] para continuar..
                          -> Insira 'sair' para terminar o replay
                        """);
                if(sc.nextLine().equalsIgnoreCase("sair"))
                    break;
            } else {
                System.out.println(frase);
            }
            ++i;
        }

        System.out.println("""
                        
                        
                        FIM DO REPLAY
            ..Presssione [ENTER] para continuar..
            """);
        sc.nextLine();
    }
}
