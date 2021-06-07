package pt.isec.a2019134744.jogo.ui.grafico.resources;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageLoader {

    private ImageLoader() {

    }

    static HashMap<String, Image> imgCache;

    static {
        imgCache = new HashMap<>();
    }

    public static Image getImage(String filename) {
        Image img = imgCache.get(filename);
        if(img != null)
            return img;

        try {
            img = new Image(Resources.getResourceFileAsStream("images/" + filename));
            imgCache.put(filename, img);
            return img;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getImageForce(String filename) {
        imgCache.remove(filename);
        return getImage(filename);
    }
}
