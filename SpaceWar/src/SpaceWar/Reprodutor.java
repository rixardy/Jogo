package SpaceWar;

import java.io.InputStream;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Reprodutor {

    public BasicPlayer player;
    public InputStream in;

    public Reprodutor() {
        player = new BasicPlayer();
    }

    public void tocar() throws BasicPlayerException {
        player.play();
    }

    public void abrirArquivo() throws BasicPlayerException {
        in = getClass().getClassLoader().getResourceAsStream("Imagens/tiro.mp3");
        player.open(in);
    }

    public void pausar() throws BasicPlayerException {
        player.pause();
    }

    public void continuar() throws BasicPlayerException {
        player.resume();
    }

    public void parar() throws BasicPlayerException {
        player.stop();
    }
}