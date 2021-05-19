package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.logica.memento.CareTaker;

import java.util.Scanner;

public class GestorDeJogo {
    private MaquinaEstados maquinaEstados;
    private final CareTaker careTaker;

    public GestorDeJogo() {
        this.maquinaEstados = new MaquinaEstados();
        this.careTaker = new CareTaker(maquinaEstados);
    }

    /* FUNCOES DO CARETAKER */
    public void undo(int nJogadas) {
        careTaker.undo(nJogadas);
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
        if(maquinaEstados.joga(nColuna))
            careTaker.gravaMemento();
    }

    public void jogaEspecial(int nColuna) {
        if(maquinaEstados.jogaEspecial(nColuna))
            careTaker.gravaMemento();
    }

    public void desisteMinijogo() {
        maquinaEstados.desisteMinijogo();
    }

    public void jogaMinijogo() {
        maquinaEstados.jogaMinijogo();
    }

    public void respondeMinijogo(Scanner sc) {
        maquinaEstados.respondeMinijogo(sc);
    }

    public void recomeca() {
        careTaker.reset();
        maquinaEstados.recomeca();
    }

    public Situacao getSituacao() {
        return maquinaEstados.getSituacao();
    }

    /* FUNCOES DO JOGO */
    public String getInfoJogo() {
        return maquinaEstados.getInfoJogo();
    }

    public String getResultadoJogo() { return maquinaEstados.getResultadoJogo(); }

    public boolean isHumano() { return maquinaEstados.isHumano(); }

    public String getTabuleiro() {
        return maquinaEstados.getTabuleiro();
    }

    public String getNomeJogador() { return maquinaEstados.getNomeJogador(); }

    /* FUNCOES DOS MINIJOGOS */
    public String getPerguntaMinijogo() {
        return maquinaEstados.getPerguntaMinijogo();
    }

    /* FUNCOES DE SAVES */
    public String gravaJogo(String fich) {
        
        return "Aguarde!";
    }
}
