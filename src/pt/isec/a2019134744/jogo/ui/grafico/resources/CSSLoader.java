package pt.isec.a2019134744.jogo.ui.grafico.resources;

import javafx.scene.Parent;

public class CSSLoader {

    private CSSLoader() {

    }

    public static void setCSS(Parent parent, String name) {
        parent.getStylesheets().add(Resources.getResourceFilename("css/" + name));
    }
}
