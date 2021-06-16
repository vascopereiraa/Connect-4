package pt.isec.a2019134744.jogo.ui.grafico.dados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.dados.Tabuleiro;
import pt.isec.a2019134744.jogo.ui.grafico.Colors;

import java.util.ArrayList;
import java.util.List;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class VistaTabuleiro extends BorderPane {

    private GestorDeJogoObs gestorDeJogoObs;
    private VistaDadosHumano jogadas;

    private GridPane tab;
    private List<PecaTabuleiro> pecas;
    public static double radiusCircle = 25.0;
    private final double verticalGap = 5.0;
    private final double horizontalGap = 5.0;
    private final double paddingCircle = 12.0;

    private int indiceCorPeca;

    private boolean isReplay;

    public VistaTabuleiro(GestorDeJogoObs gestorDeJogoObs, VistaDadosHumano jogadas) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        this.pecas = new ArrayList<>();
        this.jogadas = jogadas;
        this.isReplay = false;
        indiceCorPeca = 0;
        createView();
        registerListeners();
        registaObservers();
        atualiza();
    }

    public void atualiza(String tabuleiro) {
        System.out.println(tabuleiro);
        tabuleiro = tabuleiro.replace("|", "");
        tabuleiro = tabuleiro.replace("   ", "_");
        tabuleiro = tabuleiro.replace("$ 1 $ 2 $ 3 $ 4 $ 5 $ 6 $ 7 $", "");
        tabuleiro = tabuleiro.replace(" ", "");
        tabuleiro = tabuleiro.replace("\n", "");

        for(int i = 0; i < tabuleiro.length(); ++i) {
            PecaTabuleiro peca = pecas.get(i);
            switch (tabuleiro.charAt(i)) {
                case 'Y' -> peca.setCor(Colors.getColor1());
                case 'R' -> peca.setCor(Colors.getColor2());
                default -> peca.setCor(Colors.getDefaultColor());
            }
        }
    }

    public void atualiza() {
        atualiza(gestorDeJogoObs.getTabuleiro().trim());
    }

    public void createView() {
        tab = new GridPane();
        for(int i = 0; i < Tabuleiro.NR_LINHAS; ++i)
            for(int j = 0; j < Tabuleiro.NR_COLUNAS; ++j) {
                PecaTabuleiro peca = new PecaTabuleiro(radiusCircle, j, i == 0 ? String.valueOf(j + 1) : "");
                pecas.add(peca);
                tab.add(peca, j, i);
            }
        tab.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE.darker(), new CornerRadii(25), Insets.EMPTY)));
        tab.setAlignment(Pos.CENTER);
        tab.setHgap(horizontalGap);
        tab.setVgap(verticalGap);
        tab.setPadding(new Insets(paddingCircle));
        tab.setMaxHeight(tab.getColumnCount() * radiusCircle + 9 * 2 * horizontalGap + 7 * paddingCircle);
        tab.setMinHeight(tab.getColumnCount() * radiusCircle + 9 * 2 * horizontalGap + 7 * paddingCircle);

        this.setCenter(tab);
        this.setPadding(new Insets(15));
    }

    public void registerListeners() {

    }

    public void registaObservers() {
        for(PecaTabuleiro peca : pecas) {
            peca.addObserver(ENTRA_RATO, e -> {
                if(gestorDeJogoObs.isHumano() && !isReplay) {
                    int coluna = (int) e.getNewValue();
                    for (int i = 0; i < Tabuleiro.NR_LINHAS; ++i) {
                        ((PecaTabuleiro) tab.getChildren().get(coluna + i * Tabuleiro.NR_COLUNAS)).alteraCor();
                    }
                    for(int j = 0; j < Tabuleiro.NR_COLUNAS; ++j)
                        ((PecaTabuleiro) tab.getChildren().get(j)).mostraColuna();
                }
            });

            peca.addObserver(SAI_RATO, e -> {
                if(gestorDeJogoObs.isHumano() && !isReplay) {
                    int coluna = (int) e.getNewValue();
                    for (int i = 0; i < Tabuleiro.NR_LINHAS; ++i) {
                        ((PecaTabuleiro) tab.getChildren().get(coluna + i * Tabuleiro.NR_COLUNAS)).resetCor();
                    }
                    for(int j = 0; j < Tabuleiro.NR_COLUNAS; ++j)
                        ((PecaTabuleiro) tab.getChildren().get(j)).escondeColuna();
                }
            });

            peca.addObserver(JOGA_PECA, e -> {
                int coluna = (int) e.getNewValue();
                if(gestorDeJogoObs.isHumano() && !isReplay) {
                    if(jogadas != null) {
                        if (jogadas.jogaPeca())
                            gestorDeJogoObs.joga(coluna + 1);
                        else
                            gestorDeJogoObs.jogaEspecial(coluna + 1);
                    }
                }
            });

            gestorDeJogoObs.addPropertyChangeListener(REPLAY, e -> {
                isReplay = true;
            });

            gestorDeJogoObs.addPropertyChangeListener(REPLAY_END, e -> {
                isReplay = false;
            });
        }

        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> atualiza());

        gestorDeJogoObs.addPropertyChangeListener(UNDO_JOGADA, e -> {
            atualiza();
        });

        gestorDeJogoObs.addPropertyChangeListener(REFRESH_VIEW, e -> atualiza());
    }

    public void switchIcons(int value) {
        Colors.setValue(value);
        gestorDeJogoObs.refreshView();
    }
}

