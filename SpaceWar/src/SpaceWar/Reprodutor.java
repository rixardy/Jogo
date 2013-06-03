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

    public void abrirArquivoTiro() throws BasicPlayerException {

        player.open(getLocal("/Imagens/tiro.mp3"));
    }
    
    public void abrirArquivoGanhou() throws BasicPlayerException {

        player.open(getLocal("/Imagens/ganhou.mp3"));
    }
    
    public void abrirArquivoPerdeu() throws BasicPlayerException {

        player.open(getLocal("/Imagens/perdeu.mp3"));
    }
    
    public void abrirArquivoExplosao() throws BasicPlayerException {

        player.open(getLocal("/Imagens/explosao.mp3"));
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