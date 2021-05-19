package pt.isec.a2019134744.jogo.logica.dados.jogadores;

import pt.isec.a2019134744.jogo.logica.dados.Peca;

import java.io.Serializable;

public abstract class Player implements Serializable {
    private final String nome;
    private final Peca peca;

    public Player(String nome, Peca peca) {
        this.nome = nome;
        this.peca = peca;
    }

    public String getNome() {
        return nome;
    }

    public Peca getPeca() { return peca; }

    public boolean jogaPecaEspecial() { return false; }

    public boolean usaCreditos(int creditos) { return false; }

    public void setCreditos(int creditos) { }

    public int getCreditos() { return 0; }

    public void ganhaPecaEspecial() { }

    @Override
    public String toString() {
        return "Jogador: " + nome +
                "\nPeça: " + peca.toString();
    }
}

