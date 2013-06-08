package SpaceWar;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class Life {

    private Image imagemLife;
    private int posicaoX;
    private int posicaoY;
    private int largura;
    private int altura;
    private boolean visivel;
    private int velociidadeLife = 1;

    public Life(int posicaoX, int posicaoY) {

        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;

        ImageIcon retornaLife;

        retornaLife = new ImageIcon(getClass().getResource("/Imagens/vida.png"));
        imagemLife = retornaLife.getImage();

        this.largura = imagemLife.getWidth(null);
        this.altura = imagemLife.getHeight(null);

        visivel();

    }

    public void moverLife() {

        Random randomX = new Random();

        if (this.posicaoY < 0) {

            this.posicaoX = randomX.nextInt(10) * 55;
        } else {
            this.posicaoY += velociidadeLife;
        }
    }

    private void visivel() {
        visivel = true;
    }

    public Image getImagemLife() {
        return imagemLife;
    }

    public int getPosicaoX() {
        return posicaoX;
    }

    public int getPosicaoY() {
        return posicaoY;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public Rectangle getBounds() {
        return new Rectangle(posicaoX, posicaoY, largura, altura);
    }
}