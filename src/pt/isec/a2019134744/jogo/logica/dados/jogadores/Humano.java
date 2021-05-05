package pt.isec.a2019134744.jogo.logica.dados.jogadores;

import pt.isec.a2019134744.jogo.logica.dados.Peca;

public class Humano extends Player {

    private int nJogada;
    private int nPecasEspeciais;

    public Humano(String nome, Peca peca) {
        super(nome, peca);
        this.nJogada = 0;
        this.nPecasEspeciais = 0;
    }

    public int getnJogada() {
        return nJogada;
    }

    public void incNJogada() {
        ++nJogada;
    }

    public boolean jogaPecaEspecial() {
        if(nPecasEspeciais > 0) {
            --nPecasEspeciais;
            return true;
        }
        return false;
    }

    public void ganhaPecaEspecial() {
        ++nPecasEspeciais;
    }
}
