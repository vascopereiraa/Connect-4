package pt.isec.a2019134744.jogo.ui.grafico.dados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class ConfirmDesiste extends Stage {

    private GestorDeJogoObs gestorDeJogoObs;

    private VBox disp;
    private Font fonte;
    private Button btnConfirmar;
    private Button btnCancelar;

    public ConfirmDesiste(Window window, GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;

        createView();
        registerListeners();

        setScene(new Scene(disp, 300, 150));
        setResizable(false);
        setTitle("Conforma Desistência?");
        initModality(Modality.APPLICATION_MODAL);
        initOwner(window);
        btnCancelar.requestFocus();
        showAndWait();
    }

    private void createView() {
        disp = new VBox();
        disp.setAlignment(Pos.CENTER);
        disp.setSpacing(15.0);

        fonte = FontLoader.loadFont(APP_FONT, 16);

        Label prompt = new Label("Tem a certeza que pretende desistir?");
        prompt.setFont(FontLoader.loadFont(APP_FONT_BOLD, 16));

        HBox hbox = new HBox();
        btnCancelar = new Button("Cancelar");
        btnCancelar.setFont(fonte);
        btnCancelar.setPrefSize(BTN_WIDTH / 2, BTN_HEIGHT);

        btnConfirmar = new Button("Confirmar");
        btnConfirmar.setPrefSize(BTN_WIDTH / 2, BTN_HEIGHT);
        btnConfirmar.setFont(fonte);

        hbox.setSpacing(5.0);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(btnConfirmar, btnCancelar);

        disp.getChildren().addAll(prompt, hbox);
    }

    private void registerListeners() {
        btnCancelar.setOnMouseEntered(e -> {
            btnCancelar.setBackground(new Background(new BackgroundFill(Color.rgb(255, 128, 128),
                    new CornerRadii(5.0), Insets.EMPTY)));
        });

        btnCancelar.setOnMouseExited(e -> {
            btnCancelar.setBackground(btnConfirmar.getBackground());
        });

        btnCancelar.setOnAction(e -> {
            close();
        });

        btnConfirmar.setOnAction(e -> {
            gestorDeJogoObs.desiste();
            close();
        });
    }
}
