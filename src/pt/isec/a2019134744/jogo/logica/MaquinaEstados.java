package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;
import pt.isec.a2019134744.jogo.logica.estados.IEstado;
import pt.isec.a2019134744.jogo.logica.estados.Inicio;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.logica.memento.IMementoOriginator;
import pt.isec.a2019134744.jogo.logica.memento.Memento;

import java.io.Serializable;
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

    public void joga(int nColuna) {
        atual = atual.joga(nColuna);
    }

    public void jogaEspecial(int nColuna) {
        atual = atual.jogaEspecial(nColuna);
    }

    public void desisteMinijogo() {
        atual = atual.desisteMinijogo();
    }

    public void jogaMinijogo() {
        atual = atual.jogaMinijogo();
    }

    public void terminaMinijogo() {
        atual = atual.terminaMinijogo();
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

    public boolean isHumano() { return jogoConnect4.isHumano(); }

    public String getTabuleiro() {
        return jogoConnect4.imprimeTabuleiroJogo();
    }

    public String getNomeJogador() { return jogoConnect4.getNomeJogador(); }

    /* FUNCOES DOS MINIJOGOS */
    public String getPerguntaMinijogo() {
        return jogoConnect4.getPerguntaMinijogo();
    }

    public String setRespostaMinijogo(Scanner sc) {
        return jogoConnect4.setRespostaMinijogo(sc);
    }

    // todo;
    @Override
    public Memento getMemento() {
        return jogoConnect4.getMemento();
    }

    @Override
    public void setMemento(Memento m) {
        jogoConnect4.setMemento(m);
    }
}
