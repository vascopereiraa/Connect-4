package pt.isec.a2019134744.jogo.ui.texto;

import pt.isec.a2019134744.jogo.logica.MaquinaEstados;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;
import pt.isec.a2019134744.jogo.utils.Util;

import java.util.Scanner;

public class UIConnect4 {

    private final MaquinaEstados maquinaEstados;
    private boolean sair;
    private final Scanner sc;

    public UIConnect4(MaquinaEstados maquinaEstados) {
        this.maquinaEstados = maquinaEstados;
        this.sair = false;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        while(!sair) {
            switch (maquinaEstados.getSituacao()) {
                case Inicio -> uiInicio();
                case AguardaJogadores -> uiAguardaJogadores();
                case AguardaJogada -> uiAguardaJogada();
                case DecisaoMinijogo -> uiDecisaoMinijogo();
                case JogaMinijogo -> uiJogaMinijogo();
                case FimJogo -> uiFimJogo();
            }
        }
    }

    private void uiFimJogo() {
        System.out.println(ConsoleColors.BLUE + "Fim de Jogo" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Recomecar", "Sair")) {
            case 1 -> maquinaEstados.recomeca();
            case 0 -> sair = true;
        }
    }

    private void uiJogaMinijogo() {
        System.out.println(maquinaEstados.getPerguntaMinijogo());
        System.out.println(maquinaEstados.setRespostaMinijogo(sc));
        maquinaEstados.terminaMinijogo();
    }

    private void uiDecisaoMinijogo() {
        System.out.println(ConsoleColors.BLUE + "Decisao do Minijogo" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Jogar o minijogo", "Desistir do minijogo")) {
            case 1 -> maquinaEstados.jogaMinijogo();
            case 0 -> maquinaEstados.desisteMinijogo();
        }
    }

    private void uiAguardaJogada() {
        System.out.println(ConsoleColors.BLUE + "Aguarda Jogada" + ConsoleColors.RESET);
        System.out.println(maquinaEstados.getInfoJogo());
        System.out.println(maquinaEstados.getTabuleiro());
        System.out.println(" ");
        switch (Util.escolheOpcao("Jogar peça", "Jogar peça especial", "Desistir")) {
            case 1 -> maquinaEstados.joga(Util.pedeInteiro("Numero da Coluna: "));
            case 2 -> maquinaEstados.jogaEspecial(Util.pedeInteiro("Numero da Coluna: "));
            case 0 -> maquinaEstados.termina();
        }
    }

    private void uiAguardaJogadores() {
        System.out.println(ConsoleColors.BLUE + "Aguarda Jogadores" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Comecar Jogo", "Recomecar")) {
            case 1 -> {
                System.out.println("Introduza os nomes dos jogadores: ");
                maquinaEstados.comeca(sc.nextLine().split("\\s"));
            }
            case 0 -> maquinaEstados.recomeca();
        }
    }

    private void uiInicio() {
        System.out.println(ConsoleColors.BLUE + "Inicio" + ConsoleColors.RESET);
        switch (Util.escolheOpcao("Começar", "Sair")) {
            case 1 -> maquinaEstados.comeca();
            case 0 -> sair = true;
        }
    }
}
