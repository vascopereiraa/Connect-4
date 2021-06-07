package pt.isec.a2019134744.jogo.ui.grafico.dados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.dados.Tabuleiro;

import java.util.ArrayList;
import java.util.List;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class VistaTabuleiro extends BorderPane {

    private GestorDeJogoObs gestorDeJogoObs;

    private GridPane tab;
    private List<PecaTabuleiro> pecas;
    private double radiusCircle = 25.0;
    private final double verticalGap = 5.0;
    private final double horizontalGap = 5.0;
    private final double paddingCircle = 12.0;

    public VistaTabuleiro(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        this.pecas = new ArrayList<>();
        createView();
        registerListeners();
        registaObservers();
        atualiza();
    }

    public void atualiza() {
        /*int x = 8;
        System.out.println((x-1) / Tabuleiro.NR_COLUNAS);
        System.out.println((x-1) % Tabuleiro.NR_COLUNAS);*/

        String tabuleiro = gestorDeJogoObs.getTabuleiro().trim();
        System.out.println(tabuleiro);
        tabuleiro = tabuleiro.replace("|", "");
        tabuleiro = tabuleiro.replace("   ", "_");
        tabuleiro = tabuleiro.replace("$ 1 $ 2 $ 3 $ 4 $ 5 $ 6 $ 7 $", "");
        tabuleiro = tabuleiro.replace(" ", "");
        tabuleiro = tabuleiro.replace("\n", "");

        for(int i = 0; i < tabuleiro.length(); ++i)
            switch (tabuleiro.charAt(i)) {
                case 'Y' -> {
                    PecaTabuleiro peca = pecas.get(i);
                    peca.setFill(Color.YELLOW);
                    peca.setCor(Color.YELLOW);
                }
                case 'R' -> {
                    PecaTabuleiro peca = pecas.get(i);
                    peca.setFill(Color.RED);
                    peca.setCor(Color.RED);
                }
                default -> {
                    PecaTabuleiro peca = pecas.get(i);
                    peca.setFill(Color.LIGHTGRAY);
                    peca.setCor(Color.LIGHTGRAY);
                }
            }
    }

    public void createView() {
        tab = new GridPane();
        for(int i = 0; i < Tabuleiro.NR_LINHAS; ++i)
            for(int j = 0; j < Tabuleiro.NR_COLUNAS; ++j) {
                PecaTabuleiro peca = new PecaTabuleiro(radiusCircle, j);
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
                if(gestorDeJogoObs.isHumano()) {
                    int coluna = (int) e.getNewValue();
                    for (int i = 0; i < Tabuleiro.NR_LINHAS; ++i) {
                        ((PecaTabuleiro) tab.getChildren().get(coluna + i * Tabuleiro.NR_COLUNAS)).alteraCor();
                    }
                }
            });

            peca.addObserver(SAI_RATO, e -> {
                if(gestorDeJogoObs.isHumano()) {
                    int coluna = (int) e.getNewValue();
                    for (int i = 0; i < Tabuleiro.NR_LINHAS; ++i) {
                        ((PecaTabuleiro) tab.getChildren().get(coluna + i * Tabuleiro.NR_COLUNAS)).resetCor();
                    }
                }
            });

            peca.addObserver(JOGA_PECA, e -> {
                int coluna = (int) e.getNewValue();
                if(gestorDeJogoObs.isHumano())
                    gestorDeJogoObs.joga(coluna + 1);
            });
        }

        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> atualiza());

        gestorDeJogoObs.addPropertyChangeListener(UNDO_JOGADA, e -> {
            atualiza();
        });
    }

    public void alteraDims(double width, double height) {
        setWidth(width);
        setHeight(height);
    }
}
