package SpaceWar;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Trilha implements Runnable {

    private String som;
    private InputStream in;
    private int stop = 0;
    private Player p1 = null;

    public Trilha(String som) {

        if (som.equals("Batalha")) {
            this.som = "Batalha";
        } else if (som.equals("Introducao")) {
            this.som = "Introducao";
        }
    }

    public boolean parar_trilha(int x) {

        stop = x;
        if (stop == 1) {
            p1.close();
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void run() {

        while (parar_trilha(stop)) {
            if (this.som.equals("Batalha")) {

                in = getClass().getClassLoader().getResourceAsStream("Imagens/Batalha.mp3");

                try {

                    p1 = new Player(in);
                    p1.play();

                } catch (JavaLayerException ex) {
                    Logger.getLogger(Trilha.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (this.som.equals("Introducao")) {

                in = getClass().getClassLoader().getResourceAsStream("Imagens/inicio.mp3");

                try {

                    p1 = new Player(in);
                    p1.play();

                } catch (JavaLayerException ex) {
                    Logger.getLogger(Trilha.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }
}