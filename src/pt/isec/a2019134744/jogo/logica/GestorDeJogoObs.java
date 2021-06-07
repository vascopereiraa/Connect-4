package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.ALTERA_ESTADO;
import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.UNDO_JOGADA;

public class GestorDeJogoObs {

    private GestorDeJogo gestorDeJogo;
    private PropertyChangeSupport pcs;

    public GestorDeJogoObs() {
        this.gestorDeJogo = new GestorDeJogo();
        this.pcs = new PropertyChangeSupport(gestorDeJogo);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    /* FUNCOES DO CARETAKER */
    public void undo(int nJogadas) {
        gestorDeJogo.undo(nJogadas);
        pcs.firePropertyChange(UNDO_JOGADA, null, null);
    }

    /* FUNCOES DA MAQUINA DE ESTADOS */
    public void inicia() {
        gestorDeJogo.inicia();
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void comeca(String... jogadores) {
        gestorDeJogo.comeca(jogadores);
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void termina() {
        gestorDeJogo.termina();
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void joga(int nColuna) {
        gestorDeJogo.joga(nColuna);
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void jogaEspecial(int nColuna) {
        gestorDeJogo.jogaEspecial(nColuna);
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void desiste() {
        gestorDeJogo.desiste();
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void jogaMinijogo() {
        gestorDeJogo.jogaMinijogo();
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void respondeMinijogo(String resposta) {
        gestorDeJogo.respondeMinijogo(resposta);
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void recomeca() {
        gestorDeJogo.recomeca();
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public Situacao getSituacao() {
        return gestorDeJogo.getSituacao();
    }

    /* FUNCOES DO JOGO */
    public String getInfoJogo() {
        return gestorDeJogo.getInfoJogo();
    }

    public String getContexto() {
        return gestorDeJogo.getContexto();
    }

    public boolean isHumano() {
        return gestorDeJogo.isHumano();
    }

    public String getTabuleiro() {
        return gestorDeJogo.getTabuleiro();
    }

    public String getNomeJogador() {
        return gestorDeJogo.getNomeJogador();
    }

    public String getDadosJogador() {
        return gestorDeJogo.getDadosJogador();
    }

    public int getCreditosJogAtivo() {
        return gestorDeJogo.getCreditosJogAtivo();
    }

    /* FUNCOES DOS MINIJOGOS */
    public String getPerguntaMinijogo() {
        return gestorDeJogo.getPerguntaMinijogo();
    }

    /* FUNCOES DE SAVES */
    public List<String> getListaReplays() {
        return gestorDeJogo.getListaReplays();
    }

    public void gravaReplay() {
        gestorDeJogo.gravaReplay();
    }

    public String verReplay(String ficheiro) {
        return gestorDeJogo.verReplay(ficheiro);
    }

    public void resetReplays() {
        gestorDeJogo.resetReplays();
    }

    public String gravaJogo(String nomeSave) {
        return gestorDeJogo.gravaJogo(nomeSave);
    }

    public List<String> getListaSaves() {
        return gestorDeJogo.getListaSaves();
    }

    public String carregaJogo(String nomeSave) {
        return gestorDeJogo.carregaJogo(nomeSave);
    }
}
