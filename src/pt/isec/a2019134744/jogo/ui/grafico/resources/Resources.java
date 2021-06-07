package pt.isec.a2019134744.jogo.ui.grafico.resources;

import java.io.InputStream;
import java.util.Objects;

public class Resources {

    private Resources() {

    }

    public static InputStream getResourceFileAsStream(String name) {
        return Resources.class.getResourceAsStream(name);
    }

    public static String getResourceFilename(String name) {
        return Objects.requireNonNull(Resources.class.getResource(name)).toExternalForm();
    }

}
