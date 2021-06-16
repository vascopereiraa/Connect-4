package pt.isec.a2019134744.jogo.ui.grafico.resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {

    private MusicPlayer() {

    }

    static MediaPlayer mp;

    public static void playMusic(String name) {
        String path = Resources.getResourceFilename("music/"+name);
        if (path == null)
            return;
        Media music = new Media(path);
        mp = new MediaPlayer(music);
        mp.setStartTime(Duration.ZERO);
        mp.setStopTime(music.getDuration());
        mp.setAutoPlay(true);
    }
}
