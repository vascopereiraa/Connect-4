package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;
import pt.isec.a2019134744.jogo.ui.grafico.resources.ImageLoader;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class InicioUI extends BorderPane {

    private final GestorDeJogoObs gestorDeJogoObs;

    VBox capa;
    Button btnJogar;
    Button btnReplay;
    Button btnLoadSave;

    Font fonte;

    public InicioUI(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        setStyle("-fx-background-color: #a1ddff");

        createView();
        registerListeners();

        registaObservers();
        atualiza();
    }

    private void createView() {
        capa = new VBox();
        capa.setAlignment(Pos.CENTER);
        capa.setSpacing(15.0);

        ImageView imageView = new ImageView(ImageLoader.getImage(ICON_MAINMENU));
        Label title = new Label("CONNECT-4");
        title.setFont(FontLoader.loadFont(TITLE_FONT, 112));
        title.setTextFill(Color.DARKCYAN);

        this.fonte = FontLoader.loadFont(APP_FONT, 18);
        btnJogar = new Button("Começar jogo");
        btnJogar.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnJogar.setFont(fonte);

        btnLoadSave = new Button("Carregar jogo");
        btnLoadSave.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnLoadSave.setFont(fonte);

        btnReplay = new Button("Ver Replay");
        btnReplay.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnReplay.setFont(fonte);

        this.capa.getChildren().addAll(imageView, title,
                new Rectangle(1.0, 20.0, Color.TRANSPARENT),
                btnJogar, btnLoadSave, btnReplay);
        this.setCenter(capa);
    }

    private void registerListeners() {
        btnJogar.setOnAction(e -> {
            gestorDeJogoObs.inicia();
        });

        btnReplay.setOnAction(e -> {
            System.out.println("Replay");
        });

        btnLoadSave.setOnAction(e -> {
            System.out.println("Load");
        });
    }

    public void registaObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });
    }

    public void atualiza() {
        setVisible(gestorDeJogoObs.getSituacao() == Situacao.Inicio);
    }

}
