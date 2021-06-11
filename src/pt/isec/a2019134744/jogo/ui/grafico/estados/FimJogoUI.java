package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;
import pt.isec.a2019134744.jogo.ui.grafico.resources.ImageLoader;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class FimJogoUI extends VBox {

    private final GestorDeJogoObs gestorDeJogoObs;

    private Text texto;
    private Button btnReturn;

    public FimJogoUI(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        setStyle("-fx-background-color: yellow");
        createView();
        registerListeners();
        registaObservers();
        atualiza();
    }

    private void createView() {
        Label title = new Label("Fim de Jogo");
        title.setFont(FontLoader.loadFont(TITLE_FONT, 112));
        title.setTextFill(Color.DARKCYAN);

        texto = new Text("");
        texto.setFont(FontLoader.loadFont(APP_FONT, 24));
        texto.setTextAlignment(TextAlignment.CENTER);

        ImageView img = new ImageView(ImageLoader.getImage(ICON_TROPHY));

        btnReturn = new Button("Voltar ao Inicio");
        btnReturn.setPrefSize(BTN_WIDTH * 2 , BTN_HEIGHT);
        btnReturn.setFont(FontLoader.loadFont(APP_FONT, 18));

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10.0);
        this.getChildren().addAll(title, img, texto, btnReturn);
    }

    private void registerListeners() {
        btnReturn.setOnAction(e -> {
            gestorDeJogoObs.recomeca();
        });
    }

    public void registaObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });
    }

    public void atualiza() {
        setVisible(gestorDeJogoObs.getSituacao() == Situacao.FimJogo);
        String finale = ConsoleColors.removeConsoleColors(gestorDeJogoObs.getInfoJogo());
        texto.setText(finale.substring(0, finale.indexOf('\n')));

    }

}
