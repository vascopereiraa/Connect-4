package pt.isec.a2019134744.jogo.logica.dados.jogadores;

import pt.isec.a2019134744.jogo.logica.dados.Peca;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;

import java.io.Serializable;

public abstract class Player implements Serializable {
    private final String nome;
    private final Peca peca;

    public Player(String nome, Peca peca) {
        this.nome = nome;
        this.peca = peca;
    }

    public String getNome() {
        return ConsoleColors.CYAN + nome + ConsoleColors.RESET;
    }

    public Peca getPeca() { return peca; }

    public int getJogada() {
        return 0;
    }

    public void incJogada() {

    }

    public boolean jogaPecaEspecial() { return false; }

    public void ganhaPecaEspecial() {

    }

    public int getCreditos() {
        return 0;
    }

    public void setCreditos(int creditos) {

    }

    public boolean usaCreditos(int creditos) {
        return false;
    }

    @Override
    public String toString() {
        return "Jogador: " + ConsoleColors.CYAN + nome + ConsoleColors.RESET +
                "\nPeça: " + peca.getString() +
                "\n";
    }
}

