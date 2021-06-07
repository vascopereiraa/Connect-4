package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.scene.layout.Pane;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.ALTERA_ESTADO;

public class JogaMinijogoUI extends Pane {

    private GestorDeJogoObs gestorDeJogoObs;

    public JogaMinijogoUI(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        setStyle("-fx-background-color: orange");
        registaObservers();
        atualiza();
    }

    public void registaObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });
    }

    public void atualiza() {
        setVisible(gestorDeJogoObs.getSituacao() == Situacao.JogaMinijogo);
    }
}
