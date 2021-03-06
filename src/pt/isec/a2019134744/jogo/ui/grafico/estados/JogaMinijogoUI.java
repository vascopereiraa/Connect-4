package pt.isec.a2019134744.jogo.ui.grafico.estados;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.ui.grafico.resources.FontLoader;

import java.util.Timer;
import java.util.TimerTask;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class JogaMinijogoUI extends VBox {

    private final GestorDeJogoObs gestorDeJogoObs;

    private Label title;
    private Text regras;
    private Text pergunta;
    private Text tempo;
    private TextField jogoPalavras;
    private TextField jogoCalculos;
    private Button btnOk;

    private int segundos;
    private int jogoAtivo;

    private Timer temporizador;

    public JogaMinijogoUI(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        segundos = -1;
        temporizador = null;
        setStyle("-fx-background-color: orange");
        createView();
        registerListeners();
        registaObservers();
        atualiza();
    }

    private void createView() {
        title = new Label("");
        title.setFont(FontLoader.loadFont(TITLE_FONT, 112));
        title.setTextFill(Color.DARKCYAN);

        Font fonte = FontLoader.loadFont(APP_FONT, 18);

        regras = new Text("");
        regras.setFont(FontLoader.loadFont(APP_FONT, 24));
        regras.setTextAlignment(TextAlignment.CENTER);

        pergunta = new Text("");
        pergunta.setFont(fonte);
        pergunta.setTextAlignment(TextAlignment.CENTER);

        tempo = new Text("");
        tempo.setFont(fonte);
        tempo.setTextAlignment(TextAlignment.CENTER);

        StackPane tipoCaixa = new StackPane();

        jogoPalavras = new TextField("Palavras");
        jogoPalavras.setFont(fonte);
        jogoPalavras.setMaxWidth(BTN_WIDTH * 3);
        jogoPalavras.setPrefWidth(BTN_WIDTH * 3);

        jogoCalculos = new TextField("1234");
        jogoCalculos.setFont(fonte);
        jogoCalculos.setMaxWidth(BTN_WIDTH * 2);

        tipoCaixa.getChildren().addAll(jogoCalculos, jogoPalavras);

        btnOk = new Button("Submeter");
        btnOk.setPrefSize(BTN_WIDTH / 2, BTN_HEIGHT);
        btnOk.setFont(fonte);

        HBox hbox = new HBox(tipoCaixa, btnOk);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10.0);

        this.setSpacing(15.0);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(title, tempo, regras, pergunta, hbox);
    }

    private void registerListeners() {
        btnOk.setOnAction(e -> {
            if(jogoAtivo == 1) {
                gestorDeJogoObs.respondeMinijogo(jogoPalavras.getText());
                jogoPalavras.clear();
                jogoPalavras.requestFocus();
            }
            else {
                gestorDeJogoObs.respondeMinijogo(jogoCalculos.getText());
                jogoCalculos.clear();
                jogoCalculos.requestFocus();
            }
        });

        // Transforma text field para apenas aceitar numeros
        jogoCalculos.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("-?\\d+")) {
                    jogoCalculos.setText(newValue.replaceAll("[^-?\\d+]", ""));
                }
            }
        });

        jogoPalavras.setOnMouseClicked(e -> {
            if(jogoPalavras.getText().equalsIgnoreCase("Palavras"))
                jogoPalavras.clear();
        });

        jogoCalculos.setOnMouseClicked(e -> {
            if(jogoCalculos.getText().equalsIgnoreCase("1234"))
                jogoCalculos.clear();
        });
    }

    public void registaObservers() {

        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            atualiza();
        });

        gestorDeJogoObs.addPropertyChangeListener(GANHA_PECA, e -> {
            Alert notificacao = new Alert(Alert.AlertType.INFORMATION);
            notificacao.setHeaderText(null);
            notificacao.setContentText("Ganhou uma pe??a especial!\n" +
                    "Antes tinha " + e.getOldValue() + " pecas, fica agora com " + e.getNewValue() +
                    " pecas especiais!");
            notificacao.initModality(Modality.APPLICATION_MODAL);
            notificacao.showAndWait();
        });

        gestorDeJogoObs.addPropertyChangeListener(FINAL_MINIJOGO, e -> {
            Alert notificacao = new Alert(Alert.AlertType.INFORMATION);
            notificacao.setHeaderText(null);
            notificacao.setContentText("Infelizmente n??o foi desta que levou a melhor!\n" +
                    "Melhor sorte para a pr??xima");
            notificacao.initModality(Modality.APPLICATION_MODAL);
            notificacao.showAndWait();
        });
    }

    public void atualiza() {
        if (gestorDeJogoObs.getSituacao() == Situacao.JogaMinijogo) {
            setVisible(true);
            title.setText(gestorDeJogoObs.getNomeMinijogo());
            if (gestorDeJogoObs.getNomeMinijogo().equalsIgnoreCase("Jogo das Palavras")) {
                jogoPalavras.setVisible(true);
                jogoAtivo = 1;
                btnOk.setTranslateX(0);
            } else {
                jogoPalavras.setVisible(false);
                jogoAtivo = 2;
                btnOk.setTranslateX(-120);
            }
            String infoJogo = gestorDeJogoObs.getInfoJogo();
            int index = infoJogo.indexOf("Minijogo");
            if (index != -1) {
                infoJogo = infoJogo.substring(index);
                infoJogo = infoJogo.substring(infoJogo.indexOf('\n') + 1);
            }
            regras.setText(infoJogo);
            String a = gestorDeJogoObs.getPerguntaMinijogo();
            System.out.println(a);
            pergunta.setText(a);
            System.out.println(gestorDeJogoObs.getContexto());
            if(segundos == -1) {
                pararTimer();
                segundos = gestorDeJogoObs.getSegundos();
                System.out.println(segundos);
                tempo.setText(String.valueOf(segundos));
                temporizador = new Timer(true);
                temporizador.scheduleAtFixedRate(new Tarefa(),
                        1000, 1000);
            }
        }
        else {
            segundos = -1;
            setVisible(false);
        }
    }

    private void pararTimer() {
        if(temporizador != null) {
            temporizador.purge();
            temporizador.cancel();
            temporizador = null;
        }
    }

    private class Tarefa extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() -> {
                segundos -= 1;
                if(segundos >= 0) {
                    tempo.setText("Tempo restante: " + segundos);
                    System.out.println(segundos);
                }
                else {
                    gestorDeJogoObs.respondeMinijogo("0");
                    pararTimer();
                }
            });
        }
    }
}
