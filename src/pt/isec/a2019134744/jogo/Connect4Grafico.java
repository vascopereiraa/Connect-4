package pt.isec.a2019134744.jogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.a2019134744.jogo.logica.GestorDeJogoObs;
import pt.isec.a2019134744.jogo.ui.grafico.PaneOrganizer;
import pt.isec.a2019134744.jogo.ui.grafico.resources.ImageLoader;

import static pt.isec.a2019134744.jogo.ui.grafico.ConstantesUI.ICON_APP;

public class Connect4Grafico extends Application {

    @Override
    public void start(Stage stage) {
        GestorDeJogoObs gestorDeJogoObs = new GestorDeJogoObs();
        PaneOrganizer paneOrganizer = new PaneOrganizer(gestorDeJogoObs);

        stage.getIcons().add(ImageLoader.getImage(ICON_APP));
        stage.setTitle("Connect-4");
        Scene mainScene = new Scene(paneOrganizer, 1000, 630);
        stage.setScene(mainScene);
        stage.setResizable(false);
        /*stage.setMinWidth(1000);
        stage.setMinHeight(630);*/
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
