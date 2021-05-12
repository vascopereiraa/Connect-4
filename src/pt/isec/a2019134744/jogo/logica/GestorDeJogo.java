package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.logica.memento.CareTaker;

import java.util.Scanner;

public class GestorDeJogo {
    private MaquinaEstados maquinaEstados;
    private CareTaker careTaker;

    public GestorDeJogo() {
        this.maquinaEstados = new MaquinaEstados();
        this.careTaker = new CareTaker(maquinaEstados);
    }

    /* FUNCOES DO CARETAKER */
    public void undo() {
        careTaker.undo();
    }

    /* FUNCOES DA MAQUINA DE ESTADOS */
    public void comeca() {
        maquinaEstados.comeca();
    }

    public void comeca(String... jogadores) {
        maquinaEstados.comeca(jogadores);
    }

    public void termina() {
        maquinaEstados.termina();
    }

    public void joga(int nColuna) {
        if(isHumano())
            careTaker.gravaMemento();
        maquinaEstados.joga(nColuna);
    }

    public void jogaEspecial(int nColuna) {
        if(isHumano())
            careTaker.gravaMemento();
        maquinaEstados.jogaEspecial(nColuna);
    }

    public void desisteMinijogo() {
        maquinaEstados.desisteMinijogo();
    }

    public void jogaMinijogo() {
        maquinaEstados.jogaMinijogo();
    }

    public void terminaMinijogo() {
        maquinaEstados.terminaMinijogo();
    }

    public void recomeca() {
        maquinaEstados.recomeca();
    }

    public Situacao getSituacao() {
        return maquinaEstados.getSituacao();
    }

    /* FUNCOES DO JOGO */
    public String getInfoJogo() {
        return maquinaEstados.getInfoJogo();
    }

    public boolean isHumano() { return maquinaEstados.isHumano(); }

    public String getTabuleiro() {
        return maquinaEstados.getTabuleiro();
    }

    public String getNomeJogador() { return maquinaEstados.getNomeJogador(); }

    /* FUNCOES DOS MINIJOGOS */
    public String getPerguntaMinijogo() {
        return maquinaEstados.getPerguntaMinijogo();
    }

    public String setRespostaMinijogo(Scanner sc) {
        return maquinaEstados.setRespostaMinijogo(sc);
    }
}
