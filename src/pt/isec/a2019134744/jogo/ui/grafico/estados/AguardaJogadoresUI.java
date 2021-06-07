package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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

        jogador1 = new TextField("Nome do jogador 1");
        jogador1.setMaxWidth(230);
        jogador1.setFont(fonte);

        jogador2 = new TextField("Nome do jogador 2");
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
                return;
            }

            gestorDeJogoObs.recomeca();
        });

        btnJog2.setOnAction(e -> {
            jogadores.setVisible(false);
            nomes.setVisible(true);
            jogador1.setVisible(true);
            jogador2.setVisible(true);
            nJog = 2;
        });

        btnJog1.setOnAction(e -> {
            jogadores.setVisible(false);
            nomes.setVisible(true);
            jogador1.setVisible(true);
            jogador2.setVisible(false);
            nJog = 1;
        });

        btnJog0.setOnAction(e -> {
            gestorDeJogoObs.comeca("");
        });

        btnComeca.setOnAction(e -> {
            switch (nJog) {
                case 1 -> {
                    System.out.println(jogador1.getText());
                    gestorDeJogoObs.comeca(jogador1.getText());
                }
                case 2 -> {
                    String [] nomes = new String[nJog];
                    nomes[0] = jogador1.getText();
                    nomes[1] = jogador2.getText();
                    Arrays.stream(nomes).toList().forEach(t -> {
                        System.out.println(t);
                    });
                    gestorDeJogoObs.comeca(nomes);
                }
            }
        });
    }

    public void registaObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });
    }

    public void atualiza() {
        setVisible(gestorDeJogoObs.getSituacao() == Situacao.AguardaJogadores);
        jogadores.setVisible(true);
        nomes.setVisible(false);
        nJog = -1;
    }
}
