package pt.isec.a2019134744.jogo.ui.grafico;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.ui.grafico.estados.*;
import pt.isec.a2019134744.jogo.ui.grafico.resources.CSSLoader;

public class PaneOrganizer extends BorderPane {

    private GestorDeJogoObs gestorDeJogoObs;
    private MenuBar menuBar;

    // Vistas dos Estados
    private StackPane vistas;

    public PaneOrganizer(GestorDeJogoObs gestorDeJogoObs) {
        this.gestorDeJogoObs = gestorDeJogoObs;
        createMenu();
        createView();
        registerListeners();
    }

    private void createView() {
        CSSLoader.setCSS(this, "styles.css");
        AguardaJogadaUI aguardaJogadaUI = new AguardaJogadaUI(gestorDeJogoObs);
        AguardaJogadoresUI aguardaJogadoresUI = new AguardaJogadoresUI(gestorDeJogoObs);
        DecisaoMinijogoUI decisaoMinijogoUI = new DecisaoMinijogoUI(gestorDeJogoObs);
        FimJogoUI fimJogoUI = new FimJogoUI(gestorDeJogoObs);
        InicioUI inicioUI = new InicioUI(gestorDeJogoObs);
        JogaMinijogoUI jogaMinijogoUI = new JogaMinijogoUI(gestorDeJogoObs);

        this.vistas = new StackPane();
        vistas.getChildren().addAll(inicioUI,aguardaJogadaUI, decisaoMinijogoUI,
                fimJogoUI, aguardaJogadoresUI, jogaMinijogoUI);

        this.setCenter(vistas);
    }

    private void createMenu() {
        this.menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);

        // ------ FILE -------------
        Menu file = new Menu("File");

        // ----- VIEW -----------
        Menu view = new Menu("View");
        view.setVisible(false);


        menuBar.getMenus().addAll(file, view);
        this.setTop(menuBar);

    }

    private void registerListeners() {

    }


}
