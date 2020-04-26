import javafx.scene.media.MediaPlayer;
import javazoom.jl.player.Player;

import java.io.FileInputStream;

public class ReproductorMP3 {
    public ReproductorMP3() {
        try {
            Player apl = new Player(new FileInputStream("assets/main-menu_music.mp3"));
            apl.play();
        } catch (Exception e) {
            System.out.println("Error al reproduir la musica.");
        }
    }
}
