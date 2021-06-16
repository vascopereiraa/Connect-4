package pt.isec.a2019134744.jogo.logica;

import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

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

    public int getJogadasGravadas() {
        return gestorDeJogo.getJogadasGravadas();
    }

    /* FUNCOES DA MAQUINA DE ESTADOS */
    public void inicia() {
        gestorDeJogo.inicia();
        pcs.firePropertyChange(ALTERA_ESTADO, null, null);
    }

    public void comeca(String... jogadores) {
        if(gestorDeJogo.comeca(jogadores))
            pcs.firePropertyChange(USERNAME_INVALIDO, null, null);
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
        int nPecasEspeciais = gestorDeJogo.getNPecasEspeciais();
        Situacao s = gestorDeJogo.getSituacao();
        gestorDeJogo.respondeMinijogo(resposta);
        int newNPecasEspeciais = gestorDeJogo.getNPecasEspeciais();
        if(s != gestorDeJogo.getSituacao()) {
            if (nPecasEspeciais < newNPecasEspeciais)
                pcs.firePropertyChange(GANHA_PECA, nPecasEspeciais, newNPecasEspeciais);
            else
                pcs.firePropertyChange(FINAL_MINIJOGO, null, null);
        }
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

    public String getNomeMinijogo() {
        return gestorDeJogo.getNomeMinijogo();
    }

    public int getNPecasEspeciais() {
        return gestorDeJogo.getNPecasEspeciais();
    }

    public int getSegundos() {
        return gestorDeJogo.getSegundos();
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

    public boolean carregaReplay(File hFile) {
        boolean result = gestorDeJogo.carregaReplay(hFile);
        if(result)
            pcs.firePropertyChange(REPLAY, null, null);
        return result;
    }

    public List<Object> getReplay() {
        List<Object> dados = gestorDeJogo.getReplay();
        if(dados == null)
            pcs.firePropertyChange(REPLAY_END, null, null);
        return dados;
    }

    public boolean getLeitorReplaysEstado() {
        return gestorDeJogo.getLeitorReplaysEstado();
    }

    public void resetReplays() {
        gestorDeJogo.resetReplays();
        pcs.firePropertyChange(REPLAY_END, null, null);
    }

    public String gravaJogo(String nomeSave) {
        return gestorDeJogo.gravaJogo(nomeSave);
    }

    public String gravaJogo(File file) {
        return gestorDeJogo.gravajogo(file);
    }

    public List<String> getListaSaves() {
        return gestorDeJogo.getListaSaves();
    }

    public String carregaJogo(String nomeSave) {
        return gestorDeJogo.carregaJogo(nomeSave);
    }

    public String carregaJogo(File file) {
        String res = gestorDeJogo.carregaJogo(file);
        if(res.equalsIgnoreCase("")) {
            pcs.firePropertyChange(ALTERA_ESTADO, null, null);
            gestorDeJogo.iniciaMinijogos();
        }
        return res;
    }

    public void refreshView() {
        pcs.firePropertyChange(REFRESH_VIEW, null, null);
    }
}
