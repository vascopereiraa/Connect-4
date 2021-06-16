package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.dados.Tabuleiro;
import pt.isec.a2019134744.jogo.logica.dados.jogadores.Player;
import pt.isec.a2019134744.jogo.ui.grafico.dados.VistaTabuleiro;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;

import java.util.List;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class VerReplayUI extends HBox {

    private GestorDeJogoObs gestorDeJogoObs;

    private VistaTabuleiro tabuleiro;
    private Button btnAvanca;
    private Button btnVoltar;
    private Label jogador;
    private Text dados;

    private List<Object> info;
    private Tabuleiro tab;

    public VerReplayUI(GestorDeJogoObs gestorDeJogo) {
        this.gestorDeJogoObs = gestorDeJogo;
        setStyle("-fx-background-color: #a1ddff");
        setVisible(false);

        createView();
        registerListeners();
        registerObservers();

    }

    private void createView() {
        this.setSpacing(10.0);
        this.setAlignment(Pos.CENTER);

        tabuleiro = new VistaTabuleiro(gestorDeJogoObs, null);

        Font fonte = FontLoader.loadFont(APP_FONT, 18);

        btnAvanca = new Button("Avançar Jogada");
        btnAvanca.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnAvanca.setFont(fonte);

        btnVoltar = new Button("Voltar ao Inicio");
        btnVoltar.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnVoltar.setFont(fonte);

        VBox vbox = new VBox();
        vbox.setSpacing(10.0);
        vbox.setAlignment(Pos.CENTER);

        jogador = new Label("");
        jogador.setFont(FontLoader.loadFont(TITLE_FONT, 56));
        jogador.setTextFill(Color.DARKCYAN);

        dados = new Text("");
        dados.setFont(fonte);

        vbox.getChildren().addAll(jogador, dados, btnAvanca, btnVoltar);

        this.getChildren().addAll(tabuleiro, vbox);
    }

    private void registerListeners() {
        btnVoltar.setOnAction(e -> {
            gestorDeJogoObs.resetReplays();
        });

        btnAvanca.setOnAction(e -> {
            atualiza();
        });
    }

    private void registerObservers() {
        gestorDeJogoObs.addPropertyChangeListener(REPLAY, e -> {
            setVisible(true);
            btnAvanca.setDisable(false);
            atualiza();
        });

        gestorDeJogoObs.addPropertyChangeListener(REPLAY_END, e -> {
            setVisible(false);
        });

        gestorDeJogoObs.addPropertyChangeListener(REFRESH_VIEW, e -> {
            if(isVisible())
                tabuleiro.atualiza(tab.imprimeTab());
        });
    }

    private void atualiza() {
        info = gestorDeJogoObs.getReplay();
        if(info != null) {
            tab = (Tabuleiro) info.get(0);
            Player jog = (Player) info.get(1);

            jogador.setText(ConsoleColors.removeConsoleColors(jog.getNome()));
            dados.setText(ConsoleColors.removeConsoleColors(jog.toString().substring(jog.toString().indexOf('\n') + 1)));

            tabuleiro.atualiza(tab.imprimeTab());

            if(gestorDeJogoObs.getLeitorReplaysEstado()) {
                btnAvanca.setDisable(true);
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Replay End");
                alerta.setHeaderText(null);
                alerta.setContentText("Chegou ao final do Replay");
                alerta.initModality(Modality.APPLICATION_MODAL);
                alerta.showAndWait();
            }
        }
    }
}
