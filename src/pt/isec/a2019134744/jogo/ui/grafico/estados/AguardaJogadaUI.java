package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.scene.layout.*;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.ui.grafico.dados.VistaDadosHumano;
import pt.isec.a2019134744.jogo.ui.grafico.dados.VistaDadosVirtual;
import pt.isec.a2019134744.jogo.ui.grafico.dados.VistaTabuleiro;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.ALTERA_ESTADO;

public class AguardaJogadaUI extends HBox {

    private final GestorDeJogoObs gestorDeJogoObs;
    private VistaTabuleiro vistaTabuleiro;
    private StackPane jogadores;
    private VistaDadosHumano vistaDadosHumano;
    private VistaDadosVirtual vistaDadosVirtual;

    public AguardaJogadaUI(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        createView();
        registerObservers();
        atualiza();
    }

    private void createView() {
        this.jogadores = new StackPane();
        this.vistaDadosHumano = new VistaDadosHumano(gestorDeJogoObs);
        this.vistaDadosVirtual = new VistaDadosVirtual(gestorDeJogoObs);
        this.vistaTabuleiro = new VistaTabuleiro(gestorDeJogoObs, vistaDadosHumano);
        this.getChildren().add(vistaTabuleiro);
        jogadores.getChildren().addAll(vistaDadosHumano, vistaDadosVirtual);

        this.getChildren().addAll(jogadores);
        HBox.setHgrow(jogadores, Priority.ALWAYS);
        HBox.setHgrow(vistaDadosHumano, Priority.ALWAYS);
        HBox.setHgrow(vistaDadosVirtual, Priority.ALWAYS);
    }

    public void registerObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> atualiza());
    }

    public void atualiza() {
        setVisible(gestorDeJogoObs.getSituacao() == Situacao.AguardaJogada);
    }
}

