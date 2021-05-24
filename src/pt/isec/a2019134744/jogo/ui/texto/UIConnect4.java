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

        System.out.println("\nO JOGO FOI ENCERRADO!\n");
    }

    private void uiFimJogo() {
        System.out.println("\n" + ConsoleColors.BLUE + "Fim de Jogo" + ConsoleColors.RESET);
        System.out.println(gestorDeJogo.getInfoJogo());
        switch (Util.escolheOpcao("Voltar ao Inicio", "Sair")) {
            case 1 -> gestorDeJogo.recomeca();
            case 0 -> sair = true;
        }
    }

    private void uiJogaMinijogo() {
        System.out.println("\n" + gestorDeJogo.getPerguntaMinijogo());
        gestorDeJogo.respondeMinijogo(sc);
        System.out.println(gestorDeJogo.getContexto());

    }

    private void uiDecisaoMinijogo() {
        System.out.println("\n" + ConsoleColors.BLUE + "Decisao do Minijogo" + ConsoleColors.RESET);
        System.out.println(gestorDeJogo.getInfoJogo());
        switch (Util.escolheOpcao("Jogar o minijogo", "Desistir do minijogo")) {
            case 1 -> gestorDeJogo.jogaMinijogo();
            case 0 -> gestorDeJogo.desiste();
        }
    }

    private void uiAguardaJogada() {
        System.out.println("\n" + ConsoleColors.BLUE + "Aguarda Jogada" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN + "Jogador Humano" + ConsoleColors.RESET);
        System.out.print(gestorDeJogo.getInfoJogo());
        boolean keep;
        do {
            keep = true;
            System.out.println(gestorDeJogo.getTabuleiro());
            System.out.println("""
                    1 - Jogar peça
                    2 - Jogar peça especial
                    3 - Voltar a trás
                    4 - Gravar jogo
                                        
                    9 - Desiste
                    0 - Sair
                    """);
            System.out.print("> ");
            if (sc.hasNextInt()) {
                switch (sc.nextInt()) {
                    case 1 -> gestorDeJogo.joga(Util.pedeInteiro("Numero da Coluna: "));
                    case 2 -> gestorDeJogo.jogaEspecial(Util.pedeInteiro("Numero da Coluna: "));
                    case 3 -> {
                        int nJogadas = Util.pedeInteiro("Numero de jogadas a reverter: ");
                        if (nJogadas > 0)
                            gestorDeJogo.undo(nJogadas);
                    }
                    case 4 -> {
                        System.out.println("Para cancelar escreva 'cancel'");
                        String ficheiro = Util.pedeString( "Nome do ficheiro: ");
                        if(!ficheiro.equalsIgnoreCase("cancel"))
                            System.out.println(gestorDeJogo.gravaJogo(ficheiro) + "\n");
                        keep = false;
                    }
                    case 9 -> gestorDeJogo.desiste();
                    
                    case 0 -> {
                        System.out.println("Tem a certeza que pretende sair?");
                        if(Util.escolheOpcao("Sim", "Nao") == 1)
                            gestorDeJogo.termina();
                        else
                            keep = false;
                    }
                    default -> {
                        System.out.println("O numero introduzido nao corresponde a nenhuma funcao!\n");
                        keep = false;
                    }
                }
                if (keep) {
                    System.out.println("\n" + gestorDeJogo.getContexto());
                    System.out.println("..Presssione [ENTER] para continuar..");
                    sc.nextLine();
                    sc.nextLine();
                }
            }
        } while (!keep);
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
        String contexto = gestorDeJogo.getContexto();
        if(contexto.charAt(0) != ' ') {
            System.out.println("\n" + contexto);
            System.out.println("..Presssione [ENTER] para iniciar o jogo..");
            sc.nextLine();
        }
        else
            System.out.println("\n" + contexto.substring(1));

    }

    private void uiInicio() {
        System.out.println("\n" + ConsoleColors.BLUE + "Inicio" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Começar", "Ver o replay de um jogo anterior", "Carregar um jogo", "Sair")) {
            case 1 -> gestorDeJogo.inicia();
            case 2 -> verReplay();
            case 3 -> carregaJogo();
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
        System.out.println("\n" + gestorDeJogo.getContexto());
        System.out.println("..Presssione [ENTER] para continuar..");
        sc.nextLine();
    }

    private void carregaJogo() {

        List<String> ficheiros =  gestorDeJogo.getListaSaves();

        if(ficheiros.size() == 0) {
            System.out.println("""
            Não existe nenhum save guardado!
            ..Presssione [ENTER] para continuar..
            """);
            sc.nextLine();
            return ;
        }

        System.out.println("0 - Sair");
        for(int i = 0; i < ficheiros.size(); ++i)
            System.out.println((i + 1) + " - " + ficheiros.get(i));

        int num;
        do {
            num = Util.pedeInteiro("Numero do ficheiro: ");
        } while(num < 0 || num > ficheiros.size());

        if(num == 0)
            return;

        System.out.println(gestorDeJogo.carregaJogo(ficheiros.get(num - 1)) + "\n");
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

        System.out.println("0 - sair");
        for(int i = 0; i < ficheiros.size(); ++i)
            System.out.println((i + 1) + " - " + ficheiros.get(i));

        int num;
        do {
            num = Util.pedeInteiro("Introduza o numero do ficheiro que prentende carregar: ");
        } while(num < 0 || num > ficheiros.size());

        if(num == 0)
            return;
        
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
