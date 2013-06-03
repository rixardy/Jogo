package SpaceWar;

import java.net.URL;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Reprodutor {

    public BasicPlayer player;

    public Reprodutor() {
        player = new BasicPlayer();
    }

    public void tocar() throws BasicPlayerException {
        player.play();
    }

    public void abrirArquivo(String som) throws BasicPlayerException {

        String urlSom = null;

        switch (som) {
            case "Perdeu":
                urlSom = "/Imagens/perdeu.mp3";
                break;

            case "Ganhou":
                urlSom = "/Imagens/ganhou.mp3";
                break;

            case "Explosao":
                urlSom = "/Imagens/explosao.mp3";
                break;
            
            case "Tiro":
                urlSom = "/Imagens/tiro.mp3";
                break;
        }
        player.open(getLocal(urlSom));
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

    public URL getLocal(String filename) {

        URL url = null;

        try {
            url = this.getClass().getResource(filename);
        } catch (Exception e) {
        }
        return url;
    }
}