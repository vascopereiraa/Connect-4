package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.dados.JogoConnect4;
import pt.isec.a2019134744.jogo.logica.estados.IEstado;
import pt.isec.a2019134744.jogo.logica.estados.Inicio;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.logica.memento.IMementoOriginator;
import pt.isec.a2019134744.jogo.logica.memento.Memento;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class MaquinaEstados implements IMementoOriginator, Serializable {

    // Versao da Serializacao
    @Serial
    private static final long serialVersionUID = 1;

    private IEstado atual;
    private final JogoConnect4 jogoConnect4;

    public MaquinaEstados() {
        this.jogoConnect4 = new JogoConnect4();
        this.atual = new Inicio(jogoConnect4);
    }

    /* FUNCOES DA MAQUINA DE ESTADOS */
    public void inicia() {
        atual = atual.inicia();
    }

    public boolean comeca(String... jogadores) {
        IEstado anterior = atual;
        atual = atual.comeca(jogadores);
        return atual == anterior;
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

    public void desiste() {
        atual = atual.desiste();
    }

    public void jogaMinijogo() {
        atual = atual.jogaMinijogo();
    }

    public void respondeMinijogo(String resposta) {
        atual = atual.respondeMinijogo(resposta);
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

    public String getContexto() { return jogoConnect4.getContexto(); }

    public boolean isHumano() { return jogoConnect4.isHumano(); }

    public String getTabuleiro() {
        return jogoConnect4.imprimeTabuleiroJogo();
    }

    public String getNomeJogador() { return jogoConnect4.getNomeJogador(); }

    public List<String> getReplay() { return jogoConnect4.getReplay(); }

    public String getDadosJogador() {
        return jogoConnect4.getDadosJogador();
    }

    public int getCreditosJogAtivo() {
        return jogoConnect4.getCreditosJogAtivo();
    }

    public String getNomeMinijogo() {
        return jogoConnect4.getNomeMinijogo();
    }

    public int getNPecasEspeciais() {
        return jogoConnect4.getNPecasEspeciais();
    }

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
