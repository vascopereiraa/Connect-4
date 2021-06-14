package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;

import java.util.Arrays;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class AguardaJogadoresUI extends BorderPane {

    private final GestorDeJogoObs gestorDeJogoObs;

    Button btnVoltar;

    StackPane opcoes;

    VBox jogadores;
    Button btnJog2;        // Players vs Player
    Button btnJog1;        // Players vs Computer
    Button btnJog0;        // Computer vs Computer
    int nJog;

    VBox nomes;
    TextField jogador1;
    TextField jogador2;
    Button btnComeca;

    Font fonte;

    public AguardaJogadoresUI(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        nJog = -1;
        setStyle("-fx-background-color: #a1ddff");
        createView();
        registerListeners();
        registaObservers();
        atualiza();
    }

    private void createView() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15.0);

        this.fonte = FontLoader.loadFont(APP_FONT, 18);

        Label title = new Label("Modo de Jogo");
        title.setFont(FontLoader.loadFont(TITLE_FONT, 112));
        title.setTextFill(Color.DARKCYAN);

        btnVoltar = new Button("Voltar");
        btnVoltar.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnVoltar.setFont(fonte);

        jogadores = new VBox();
        jogadores.setAlignment(Pos.CENTER);
        jogadores.setSpacing(15.0);

        btnJog2 = new Button("Jogador vs Jogador");
        btnJog2.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnJog2.setFont(fonte);

        btnJog1 = new Button("Jogador vs Computador");
        btnJog1.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnJog1.setFont(fonte);

        btnJog0 = new Button("Computador vs Computador");
        btnJog0.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnJog0.setFont(fonte);

        jogadores.getChildren().addAll(btnJog2, btnJog1, btnJog0);

        nomes = new VBox();
        nomes.setSpacing(15.0);
        nomes.setAlignment(Pos.CENTER);

        jogador1 = new TextField("");
        jogador1.setPromptText("Nome do Jogador 1");
        jogador1.setMaxWidth(230);
        jogador1.setFont(fonte);

        jogador2 = new TextField("");
        jogador2.setPromptText("Nome do Jogador 2");
        jogador2.setMaxWidth(230);
        jogador2.setFont(fonte);

        btnComeca = new Button("Comecar jogo");
        btnComeca.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        btnComeca.setFont(fonte);

        nomes.getChildren().addAll(jogador1, jogador2, btnComeca);

        opcoes = new StackPane();
        jogadores.setVisible(false);
        nomes.setVisible(false);
        opcoes.getChildren().addAll(jogadores, nomes);

        vbox.getChildren().addAll(title, new Rectangle(1, 25, Color.TRANSPARENT),
                opcoes, btnVoltar);
        setCenter(vbox);

    }

    private void registerListeners() {

        btnVoltar.setOnAction(e -> {
            if(nomes.isVisible()) {
                nomes.setVisible(false);
                jogadores.setVisible(true);
                jogador1.setTranslateY(0);
                return;
            }
            gestorDeJogoObs.recomeca();
        });

        btnJog2.setOnAction(e -> {
            jogadores.setVisible(false);
            nomes.setVisible(true);
            jogador1.setVisible(true);
            // jogador1.setText("Nome do Jogador 1");
            jogador2.setVisible(true);
            // jogador2.setText("Nome do Jogador 2");
            nJog = 2;
        });

        btnJog1.setOnAction(e -> {
            jogadores.setVisible(false);
            nomes.setVisible(true);
            jogador1.setVisible(true);
            // jogador1.setText("Nome do Jogador Humano");
            jogador1.setPromptText("Nome do Jogador Humano");
            jogador2.setVisible(false);
            nJog = 1;
            jogador1.setTranslateY(50);
        });

        btnJog0.setOnAction(e -> {
            gestorDeJogoObs.comeca("");
        });

        btnComeca.setOnAction(e -> {
            switch (nJog) {
                case 1 -> {
                    if(!jogador1.getText().equalsIgnoreCase("")) {
                        System.out.println(jogador1.getText());
                        gestorDeJogoObs.comeca(jogador1.getText());
                    }
                }
                case 2 -> {
                    if(!jogador1.getText().equalsIgnoreCase("") && !jogador2.getText().equalsIgnoreCase("")) {
                        String[] nomes = new String[nJog];
                        nomes[0] = jogador1.getText();
                        nomes[1] = jogador2.getText();
                        Arrays.stream(nomes).toList().forEach(System.out::println);
                        gestorDeJogoObs.comeca(nomes);
                        jogador1.setTranslateY(0);
                    }
                }
            }
        });

        /*jogador1.setOnMouseClicked(e -> {
            if(jogador1.getText().contains("Nome do Jogador"))
                jogador1.clear();
        });

        jogador2.setOnMouseClicked(e -> {
            if(jogador2.getText().equalsIgnoreCase("Nome do Jogador"))
                jogador2.clear();
        });*/
    }

    private void registaObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });

        gestorDeJogoObs.addPropertyChangeListener(USERNAME_INVALIDO, e -> {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("Os nomes introduzidos não são válidos, não podem ser iguais!");
            alerta.initModality(Modality.APPLICATION_MODAL);
            alerta.showAndWait();
        });
    }

    public void atualiza() {
        setVisible(gestorDeJogoObs.getSituacao() == Situacao.AguardaJogadores);
        jogadores.setVisible(true);
        nomes.setVisible(false);
        jogador1.clear();
        jogador2.clear();
        nJog = -1;
    }
}
