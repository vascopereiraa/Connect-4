package pt.isec.a2019134744.jogo.logica.dados;

import pt.isec.a2019134744.jogo.utils.ConsoleColors;

public enum Peca {
    Vermelha(ConsoleColors.RED, "Vermelha"),
    Amarela(ConsoleColors.YELLOW,  "Amarela"),
    none(ConsoleColors.BLACK, "  ");

    private final String stringPeca;
    private final String cor;

    Peca(String cor, String peca) {
        this.cor = cor;
        this.stringPeca = this.cor + peca + ConsoleColors.RESET;
    }

    public String getString() {
        return stringPeca;
    }

    public String getCor(String texto) {
        return cor + texto + ConsoleColors.RESET;
    }
}
