package pt.isec.a2019134744.jogo.logica.dados;

import pt.isec.a2019134744.jogo.utils.ConsoleColors;

public enum Peca {
    Vermelha(ConsoleColors.RED, "Vermelha", 'R'),
    Amarela(ConsoleColors.YELLOW,  "Amarela", 'Y'),
    none(ConsoleColors.BLACK, "  ", ' ');

    private final String cor;
    private final String stringPeca;
    private final char carater;

    Peca(String cor, String peca, char c) {
        this.cor = cor;
        this.stringPeca = this.cor + peca + ConsoleColors.RESET;
        this.carater = c;
    }

    public String getString() {
        return stringPeca;
    }

    public String getCor(String texto) {
        return cor + texto + ConsoleColors.RESET;
    }

    public String placePeca() {
        return " " + carater + " ";
    }
}
