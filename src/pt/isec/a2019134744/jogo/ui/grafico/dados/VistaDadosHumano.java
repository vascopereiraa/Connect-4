package pt.isec.a2019134744.jogo.ui.grafico.dados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;
import pt.isec.a2019134744.jogo.ui.grafico.resources.ImageLoader;
import pt.isec.a2019134744.jogo.utils.ConsoleColors;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class VistaDadosHumano extends VBox {

    private final GestorDeJogoObs gestorDeJogoObs;

    private Label title;

    private Font fonte;

    private Text descricao;

    private HBox radioButtons;
    private Label lblTipoPeca;
    private ToggleGroup tipoPeca;
    private RadioButton pecaNormal;
    private RadioButton pecaEspecial;

    private HBox comandos;
    private Button btnUndo;
    private Button btnDesistir;

    public VistaDadosHumano(GestorDeJogoObs gestorDeJogoObs) {
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

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(10.0));
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setBorder(new Border(new BorderStroke(Color.CORNFLOWERBLUE.darker(), BorderStrokeStyle.SOLID,
                new CornerRadii(25.0), BorderWidths.DEFAULT)));
        stackPane.setMaxWidth(400);

        radioButtons = new HBox();
        tipoPeca = new ToggleGroup();
        lblTipoPeca = new Label("Tipo de Peça");
        lblTipoPeca.setFont(FontLoader.loadFont(APP_FONT_BOLD, 20));
        lblTipoPeca.setTranslateY(-35.0);
        lblTipoPeca.setTranslateX(15.0);
        lblTipoPeca.setStyle("-fx-background-color: #a1ddff");
        lblTipoPeca.setPadding(new Insets(10.0));

        pecaNormal = new RadioButton("Peça Normal");
        pecaNormal.setSelected(true);
        pecaNormal.setFont(fonte);
        pecaNormal.setToggleGroup(tipoPeca);

        pecaEspecial = new RadioButton("Peça Especial");
        pecaEspecial.setSelected(false);
        pecaEspecial.setDisable(true);
        pecaEspecial.setFont(fonte);
        pecaEspecial.setToggleGroup(tipoPeca);

        radioButtons.getChildren().addAll(pecaNormal, pecaEspecial);
        radioButtons.setAlignment(Pos.CENTER);
        radioButtons.setSpacing(15.0);
        stackPane.getChildren().addAll(lblTipoPeca, radioButtons);
        StackPane.setAlignment(lblTipoPeca, Pos.TOP_LEFT);

        comandos = new HBox();
        comandos.setSpacing(15.0);
        comandos.setAlignment(Pos.CENTER);

        btnUndo = new Button("Undo", new ImageView(ImageLoader.getImage(ICON_UNDO)));
        btnUndo.setPrefSize(BTN_WIDTH / 2, BTN_HEIGHT);
        btnUndo.setFont(fonte);

        btnDesistir = new Button("  Desistir", new ImageView(ImageLoader.getImage(ICON_FORFEIT)));
        btnDesistir.setCancelButton(true);
        btnDesistir.setPrefSize(BTN_WIDTH / 2, BTN_HEIGHT);
        btnDesistir.setFont(fonte);

        comandos.getChildren().addAll(btnUndo, btnDesistir);

        this.getChildren().addAll(title, descricao, stackPane, comandos);
    }

    private void registerListeners() {
        btnUndo.setOnAction(e -> {
            if(gestorDeJogoObs.getCreditosJogAtivo() > 0)
                new UndoCount(getScene().getWindow(), gestorDeJogoObs);
        });

        btnDesistir.setOnAction(e -> {
            new ConfirmDesiste(getScene().getWindow(), gestorDeJogoObs);
        });

        btnDesistir.setOnMouseEntered(e -> {
            btnDesistir.setBackground(new Background(new BackgroundFill(Color.rgb(255, 128, 128),
                    new CornerRadii(5.0), Insets.EMPTY)));
        });

        btnDesistir.setOnMouseExited(e -> {
            btnDesistir.setBackground(btnUndo.getBackground());
        });
    }

    private void registerObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });

        gestorDeJogoObs.addPropertyChangeListener(UNDO_JOGADA, e -> {
            atualiza();
        });
    }

    public boolean jogaPeca() {
        if(pecaNormal.isSelected())
            return true;
        return !pecaEspecial.isSelected();
    }

    private void atualiza() {
        setVisible(gestorDeJogoObs.isHumano());
        String descJog = ConsoleColors.removeConsoleColors(gestorDeJogoObs.getDadosJogador());
        descricao.setText(descJog.substring(descJog.indexOf('\n') + 1));
        title.setText(ConsoleColors.removeConsoleColors(gestorDeJogoObs.getNomeJogador()));
        pecaEspecial.setDisable(!(gestorDeJogoObs.getNPecasEspeciais() > 0));
        pecaNormal.setSelected(true);
        btnUndo.setDisable(gestorDeJogoObs.getCreditosJogAtivo() <= 0);
    }


}
