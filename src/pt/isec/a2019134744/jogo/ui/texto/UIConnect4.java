package pt.isec.a2019134744.jogo.ui.texto;

import pt.isec.a2019134744.jogo.logica.GestorDeJogo;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;
import pt.isec.a2019134744.jogo.utils.Util;

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
        System.out.println(ConsoleColors.BLUE + "Fim de Jogo" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Recomecar", "Sair")) {
            case 1 -> gestorDeJogo.recomeca();
            case 0 -> sair = true;
        }
    }

    private void uiJogaMinijogo() {
        System.out.println(gestorDeJogo.getPerguntaMinijogo());
        System.out.println(gestorDeJogo.setRespostaMinijogo(sc));
        gestorDeJogo.terminaMinijogo();
    }

    private void uiDecisaoMinijogo() {
        System.out.println(ConsoleColors.BLUE + "Decisao do Minijogo" + ConsoleColors.RESET);
        System.out.println(gestorDeJogo);
        switch (Util.escolheOpcao("Jogar o minijogo", "Desistir do minijogo")) {
            case 1 -> gestorDeJogo.jogaMinijogo();
            case 0 -> gestorDeJogo.desisteMinijogo();
        }
    }

    private void uiAguardaJogada() {
        System.out.println(ConsoleColors.BLUE + "Aguarda Jogada" + ConsoleColors.RESET);
        // System.out.println(gestorDeJogo.getInfoJogo());
        System.out.println(gestorDeJogo);
        System.out.println(gestorDeJogo.getTabuleiro());
        System.out.println(" ");
        switch (Util.escolheOpcao("Jogar peça", "Jogar peça especial", "Voltar a trás", "Desistir")) {
            case 1 -> gestorDeJogo.joga(Util.pedeInteiro("Numero da Coluna: "));
            case 2 -> gestorDeJogo.jogaEspecial(Util.pedeInteiro("Numero da Coluna: "));
            case 3 -> gestorDeJogo.undo(Util.pedeInteiro("Numero de jogadas a reverter: "));
            case 0 -> gestorDeJogo.termina();
        }
    }

    private void uiAguardaJogadores() {
        System.out.println(ConsoleColors.BLUE + "Aguarda Jogadores" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Comecar Jogo", "Recomecar")) {
            case 1 -> {
                System.out.println("Introduza os nomes dos jogadores: ");
                gestorDeJogo.comeca(sc.nextLine().split("\\s"));
            }
            case 0 -> gestorDeJogo.recomeca();
        }
    }

    private void uiInicio() {
        System.out.println(ConsoleColors.BLUE + "Inicio" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Começar", "Sair")) {
            case 1 -> gestorDeJogo.comeca();
            case 0 -> sair = true;
        }
    }

    private void jogaVirtual() {
        System.out.println(gestorDeJogo.getSituacao());
        System.out.println(ConsoleColors.BLUE + "Jogador Virtual" + ConsoleColors.RESET);
        System.out.println(gestorDeJogo);
        int random = (int) (Math.random() * 7) + 1;
        System.out.println(gestorDeJogo.getNomeJogador() + ": Vou jogar uma peça na coluna " + random);
        gestorDeJogo.joga(random);
        System.out.println(gestorDeJogo.getTabuleiro());
        System.out.println("..Presssione [ENTER] para continuar..");
        sc.nextLine();
    }
}
