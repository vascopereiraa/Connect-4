package pt.isec.a2019134744.jogo.ui.grafico;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import pt.isec.a2019134744.jogo.logica.GestorDeJogo;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.logica.estados.Situacao;
import pt.isec.a2019134744.jogo.ui.grafico.estados.*;

import java.io.File;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.*;

public class PaneOrganizer extends BorderPane {

    private GestorDeJogoObs gestorDeJogoObs;
    private MenuBar menuBar;

    // Vistas dos Estados
    private StackPane vistas;
    private AguardaJogadaUI aguardaJogadaUI;

    private MenuItem saveGame;
    private Menu view;
    private CheckMenuItem ativarIcons;

    private int visibleColors;

    public PaneOrganizer(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        visibleColors = 0;
        createMenu();
        createView();
        registerListeners();
        registerObservers();
    }

    private void createView() {
        aguardaJogadaUI = new AguardaJogadaUI(gestorDeJogoObs);
        AguardaJogadoresUI aguardaJogadoresUI = new AguardaJogadoresUI(gestorDeJogoObs);
        DecisaoMinijogoUI decisaoMinijogoUI = new DecisaoMinijogoUI(gestorDeJogoObs);
        FimJogoUI fimJogoUI = new FimJogoUI(gestorDeJogoObs);
        InicioUI inicioUI = new InicioUI(gestorDeJogoObs);
        JogaMinijogoUI jogaMinijogoUI = new JogaMinijogoUI(gestorDeJogoObs);
        VerReplayUI verReplayUI = new VerReplayUI(gestorDeJogoObs);

        this.vistas = new StackPane();
        vistas.getChildren().addAll(inicioUI,aguardaJogadaUI, decisaoMinijogoUI,
                fimJogoUI, aguardaJogadoresUI, jogaMinijogoUI, verReplayUI);

        this.setCenter(vistas);
    }

    private void createMenu() {
        this.menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);

        // ------ FILE -------------
        Menu file = new Menu("_File");
        saveGame = new MenuItem("_Save Game");
        saveGame.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveGame.setVisible(false);

        // ----- VIEW -----------
        view = new Menu("Colors");
        ativarIcons = new CheckMenuItem("Preto & Branco");
        ativarIcons.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
        ativarIcons.setSelected(false);
        view.setVisible(false);
        view.getItems().add(ativarIcons);

        file.getItems().addAll(saveGame);
        menuBar.getMenus().addAll(file, view);
        this.setTop(menuBar);

    }

    private void registerListeners() {
        saveGame.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File saveDir = new File(GestorDeJogo.SAVES_PATH);
            if(!saveDir.exists())
                fileChooser.setInitialDirectory(new File(GestorDeJogo.RES_PATH));
            else
                fileChooser.setInitialDirectory(saveDir);
            fileChooser.setTitle("Save Currente Game");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Game(*.dat)", "*.dat")
            );
            File resultFile = fileChooser.showSaveDialog(PaneOrganizer.this.getScene().getWindow());
            if(resultFile != null) {
                System.out.println(resultFile.getName());
                String res = gestorDeJogoObs.gravaJogo(resultFile);
                if(res.charAt(0) == 'N') {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Save Error");
                    alert.setHeaderText(null);
                    alert.setContentText(res);
                    alert.showAndWait();
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Saved Successfully");
                alert.setHeaderText(null);
                alert.setContentText(res);
                alert.showAndWait();
            }
        });

        ativarIcons.setOnAction(e -> {
            if(ativarIcons.isSelected())
                aguardaJogadaUI.switchIcons(1);
            else
                aguardaJogadaUI.switchIcons(0);
        });
    }

    private void registerObservers() {
        gestorDeJogoObs.addPropertyChangeListener(ALTERA_ESTADO, e -> {
            System.out.println("\n\n" + gestorDeJogoObs.getSituacao().toString() + "\n\n");
            if(gestorDeJogoObs.getSituacao() == Situacao.AguardaJogada) {
                saveGame.setVisible(true);
                view.setVisible(true);
                return;
            }
            else {
                saveGame.setVisible(false);
                view.setVisible(false);
            }
            view.setVisible(visibleColors == 1);
        });

        gestorDeJogoObs.addPropertyChangeListener(REPLAY, e -> {
            visibleColors = 1;
            view.setVisible(true);
        });

        gestorDeJogoObs.addPropertyChangeListener(REPLAY_END, e -> {
            visibleColors = 0;
            view.setVisible(false);
        });
    }


}
