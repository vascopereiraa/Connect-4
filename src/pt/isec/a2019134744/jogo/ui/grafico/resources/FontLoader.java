package pt.isec.a2019134744.jogo.ui.grafico.resources;

import javafx.scene.text.Font;

public class FontLoader {

    private FontLoader() {

    }

    public static Font loadFont(String name, double size) {
        return Font.loadFont(Resources.getResourceFileAsStream("fonts/" + name), size);
    }
}
