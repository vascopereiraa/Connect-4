package pt.isec.a2019134744.jogo.logica.dados.jogadores;

import pt.isec.a2019134744.jogo.logica.dados.Peca;

public abstract class Player {
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

    public abstract boolean jogaPecaEspecial();
}
