package pt.isec.a2019134744.jogo.logica.dados.jogadores;

import pt.isec.a2019134744.jogo.logica.dados.Peca;

public class Humano extends Player {

    private int nJogada;
    private int nPecasEspeciais;

    private int creditos;       // Creditos para o undo

    public Humano(String nome, Peca peca) {
        super(nome, peca);
        this.nJogada = 0;
        this.nPecasEspeciais = 0;
        this.creditos = 5;
    }

    public int getJogada() {
        return nJogada;
    }

    public void incJogada() {
        ++nJogada;
    }

    public void resetJogada() {
        nJogada = 1;
    }

    public void setNJogada(int nJogada) {
        this.nJogada = nJogada;
    }

    public int getnPecasEspeciais() {
        return nPecasEspeciais;
    }

    public void setnPecasEspeciais(int nPecasEspeciais) {
        this.nPecasEspeciais = nPecasEspeciais;
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

    public int getCreditos() { return creditos; }

    public void setCreditos(int creditos) { this.creditos = creditos; }

    public boolean usaCreditos(int creditos) {
        if(this.creditos < creditos)
            return false;
        this.creditos -= creditos;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Jogada: " + nJogada +
                "\nCreditos: " + creditos +
                "\nPecas Especiais: " + nPecasEspeciais + "\n";
    }
}
