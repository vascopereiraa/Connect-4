package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class DecisaoMinijogoUI extends VBox {

    private final GestorDeJogoObs gestorDeJogoObs;

    private Text prompt;
    private Button btnJogar;
    private Button btnVoltar;

    public DecisaoMinijogoUI(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        setStyle("-fx-background-color: #a1ddff");
        createView();
        registerListeners();
        registaObservers();
        atualiza();
    }

    private void createView() {
        Label title = new Label("Decisao de Minijogo");
        title.setFont(FontLoader.loadFont(TITLE_FONT, 112));
        title.setTextFill(Color.DARKCYAN);

        Font fonte = FontLoader.loadFont(APP_FONT, 18);
        prompt = new Text();
        prompt.setFont(fonte);
        prompt.setTextAlignment(TextAlignment.CENTER);

        btnJogar = new Button("Jogar Minijogo");
        btnJogar.setFont(fonte);
        btnJogar.setPrefSize(BTN_WIDTH, BTN_HEIGHT);

        btnVoltar = new Button("Voltar");
        btnVoltar.setFont(fonte);
        btnVoltar.setPrefSize(BTN_WIDTH, BTN_HEIGHT);

        HBox hbox = new HBox();
        hbox.setSpacing(10.0);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(btnJogar, btnVoltar);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(15.0);
        this.getChildren().addAll(title, prompt, new Rectangle(300, 25, Color.TRANSPARENT), hbox);
    }

    private void registerListeners() {

        btnJogar.setOnAction(e -> {
            gestorDeJogoObs.jogaMinijogo();
        });

        btnVoltar.setOnAction(e -> {
            gestorDeJogoObs.desiste();
        });

    }

    public void registaObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });
    }

    public void atualiza() {
        setVisible(gestorDeJogoObs.getSituacao() == Situacao.DecisaoMinijogo);
        prompt.setText("O jogador " + ConsoleColors.removeConsoleColors(gestorDeJogoObs.getNomeJogador()) +
                " chegou à fase dos minijogos\nPretende jogar o " + gestorDeJogoObs.getNomeMinijogo() + " ?");
    }

}
