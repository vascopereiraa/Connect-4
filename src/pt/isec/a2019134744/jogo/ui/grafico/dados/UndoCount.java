package pt.isec.a2019134744.jogo.ui.grafico.dados;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class UndoCount extends Stage {

    private GestorDeJogoObs gestorDeJogoObs;

    private Font fonte;
    private Slider sliderCount;
    private TextField undoCount;
    private Button btnOk;
    private Button btnCancel;

    public UndoCount(Window parent, GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        createView();
        registerListeners();

        initOwner(parent);
        initModality(Modality.APPLICATION_MODAL);
        showAndWait();
    }

    private void createView() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10.0));
        vbox.setSpacing(8.0);
        vbox.setAlignment(Pos.CENTER);

        fonte = FontLoader.loadFont(APP_FONT, 18);

        Label lbl = new Label("Nr. de vezes a voltar a trás:");
        lbl.setFont(fonte);

        HBox controlos = new HBox();
        controlos.setAlignment(Pos.CENTER);
        controlos.setSpacing(10.0);

        sliderCount = new Slider();
        sliderCount.setMin(1);
        sliderCount.setMax(Math.min(gestorDeJogoObs.getCreditosJogAtivo(), gestorDeJogoObs.getJogadasGravadas() - 1));
        sliderCount.setValue(1);
        sliderCount.setBlockIncrement(1);
        sliderCount.setMajorTickUnit(1);
        sliderCount.setMinorTickCount(0);
        sliderCount.setSnapToTicks(true);
        sliderCount.setShowTickLabels(true);
        sliderCount.setShowTickMarks(true);

        undoCount = new TextField();
        undoCount.setMaxWidth(50);
        undoCount.setFont(fonte);
        undoCount.setText("1");
        undoCount.setEditable(false);

        controlos.getChildren().addAll(sliderCount, undoCount);


        btnOk = new Button("Submeter");
        btnOk.setFont(fonte);
        btnOk.setPrefSize(BTN_WIDTH / 2, BTN_HEIGHT);

        btnCancel = new Button("Cancelar");
        btnCancel.setFont(fonte);
        btnCancel.setPrefSize(BTN_WIDTH / 2, BTN_HEIGHT);
        HBox hbox = new HBox();
        hbox.setSpacing(5.0);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(btnOk, btnCancel);

        vbox.getChildren().addAll(lbl, controlos, hbox);
        this.setTitle(" -- UNDO -- ");
        this.setScene(new Scene(vbox, 300, 150));
        this.setResizable(false);
    }

    private void registerListeners() {
        // Transforma text field para apenas aceitar numeros
        undoCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    undoCount.setText(newValue.replaceAll("[^\\d]", ""));
                    return;
                }
                sliderCount.setValue(Double.parseDouble(newValue));
            }
        });

        undoCount.setOnMousePressed(e -> {
            undoCount.setText(String.valueOf(1));
        });

        sliderCount.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                undoCount.textProperty().setValue(String.valueOf(t1.intValue()));
            }
        });

        btnOk.setOnAction(e -> {
            gestorDeJogoObs.undo(Integer.parseInt(undoCount.getText()));
            UndoCount.this.close();
        });

        btnCancel.setOnMouseEntered(e -> {
            btnCancel.setBackground(new Background(new BackgroundFill(Color.rgb(255, 128, 128),
                    new CornerRadii(5.0), Insets.EMPTY)));
        });

        btnCancel.setOnMouseExited(e -> {
            btnCancel.setBackground(btnOk.getBackground());
        });

        btnCancel.setOnAction(e -> {
            UndoCount.this.close();
        });
    }


}
