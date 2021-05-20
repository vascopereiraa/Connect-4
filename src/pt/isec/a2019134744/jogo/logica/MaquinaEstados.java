package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;
import pt.isec.a2019134744.jogo.logica.estados.IEstado;
import pt.isec.a2019134744.jogo.logica.estados.Inicio;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.logica.memento.IMementoOriginator;
import pt.isec.a2019134744.jogo.logica.memento.Memento;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class MaquinaEstados implements IMementoOriginator, Serializable {

    private IEstado atual;
    private JogoConnect4 jogoConnect4;

    public MaquinaEstados() {
        this.jogoConnect4 = new JogoConnect4();
        this.atual = new Inicio(jogoConnect4);
    }

    /* FUNCOES DA MAQUINA DE ESTADOS */
    public void comeca() {
        atual = atual.comeca();
    }

    public void comeca(String... jogadores) {
        atual = atual.comeca(jogadores);
    }

    public void termina() {
        atual = atual.termina();
    }

    public boolean joga(int nColuna) {
        IEstado anterior = atual;
        atual = atual.joga(nColuna);
        return atual != anterior;
    }

    public boolean jogaEspecial(int nColuna) {
        IEstado anterior = atual;
        atual = atual.jogaEspecial(nColuna);
        return atual != anterior;
    }

    public void desisteMinijogo() {
        atual = atual.desisteMinijogo();
    }

    public void jogaMinijogo() {
        atual = atual.jogaMinijogo();
    }

    public void respondeMinijogo(Scanner sc) {
        atual = atual.respondeMinijogo(sc);
    }

    public void recomeca() {
        atual = atual.recomeca();
    }

    public Situacao getSituacao() {
        return atual.getSituacao();
    }

    /* FUNCOES DO JOGO */
    public String getInfoJogo() {
        return jogoConnect4.toString();
    }

    public String getResultadoJogo() { return jogoConnect4.getResultadoJogo(); }

    public boolean isHumano() { return jogoConnect4.isHumano(); }

    public String getTabuleiro() {
        return jogoConnect4.imprimeTabuleiroJogo();
    }

    public String getNomeJogador() { return jogoConnect4.getNomeJogador(); }

    public List<String> getReplay() { return jogoConnect4.getReplay(); }

    /* FUNCOES DOS MINIJOGOS */
    public String getPerguntaMinijogo() {
        return jogoConnect4.getPerguntaMinijogo();
    }


    /* FUNCOES PARA O UNDO */
    @Override
    public Memento getMemento() {
        return jogoConnect4.getMemento();
    }

    @Override
    public boolean setMemento(Memento m, int nJogadas) {
        return jogoConnect4.setMemento(m, nJogadas);
    }
}
