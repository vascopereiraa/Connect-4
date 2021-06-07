package pt.isec.a2019134744.jogo.ui.grafico.dados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class VistaDadosVirtual extends VBox {

    private GestorDeJogoObs gestorDeJogoObs;

    private Font fonte;

    private Label title;
    private Text descricao;
    private Button btnJogar;

    public VistaDadosVirtual(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        setStyle("-fx-background-color: #a1ddff");
        createView();
        registerListeners();
        registerObservers();
        atualiza();
    }

    private void createView() {
        this.setSpacing(20.0);
        this.setAlignment(Pos.CENTER);

        title = new Label("");
        title.setFont(FontLoader.loadFont(TITLE_FONT, 56));
        title.setTextFill(Color.DARKCYAN);

        fonte = FontLoader.loadFont(APP_FONT, 18);

        descricao = new Text("");
        descricao.setFont(fonte);

        btnJogar = new Button("Jogar");
        btnJogar.setFont(fonte);
        btnJogar.setPrefSize(BTN_WIDTH, BTN_HEIGHT);

        this.getChildren().addAll(title, descricao, btnJogar);
    }

    private void registerListeners() {
        btnJogar.setOnAction(e -> {
            int random = (int) (Math.random() * 7) + 1;
            gestorDeJogoObs.joga(random);
        });
    }

    private void registerObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });
    }

    private void atualiza() {
        setVisible(!gestorDeJogoObs.isHumano());
        String descVirt = ConsoleColors.removeConsoleColors(gestorDeJogoObs.getDadosJogador());
        descricao.setText(descVirt.substring(descVirt.indexOf("\n") + 1));
        title.setText(ConsoleColors.removeConsoleColors(gestorDeJogoObs.getNomeJogador()));

    }
}
