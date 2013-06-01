package SpaceWar;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class NaveInimiga {

    private Image imagemNaveInimiga;
    private Image imagemExplosao;
    private Image imagemChefao;
    private int posicaoX;
    private int posicaoY;
    private int largura;
    private int altura;
    private boolean visivel = true;
    private final int ALTURA_DA_TELA = 0;
    private int velocidadeInimigo;
    private static int contador = 0;
    private ImageIcon retornaImagemNaveInimiga;
    private ImageIcon retornaImagemExplosao;
    private ImageIcon retornaImagemChefao;
    
    public NaveInimiga(int posicaoX, int posicaoY) {

        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;


        if (contador++ % 3 == 0) {

            retornaImagemNaveInimiga = new ImageIcon(getClass().getResource("/Imagens/naveInimiga.png"));

        } else if (contador++ % 2 == 0) {

            retornaImagemNaveInimiga = new ImageIcon(getClass().getResource("/Imagens/naveInimiga3.png"));

        } else {

            retornaImagemNaveInimiga = new ImageIcon(getClass().getResource("/Imagens/naveInimiga2.png"));

        }

        retornaImagemExplosao = new ImageIcon(getClass().getResource("/Imagens/explosao.gif"));

        retornaImagemChefao = new ImageIcon(getClass().getResource("/Imagens/chefao.png"));
        
        imagemNaveInimiga = retornaImagemNaveInimiga.getImage();
        imagemExplosao = retornaImagemExplosao.getImage();
        imagemChefao = retornaImagemChefao.getImage();

        this.largura = imagemNaveInimiga.getWidth(null);
        this.altura = imagemNaveInimiga.getHeight(null);

        visivel();

    }

    public void moverInimigo() {

        Random randomX = new Random();

        if (this.posicaoY >= 700) {
            this.posicaoY = -20100;
            this.posicaoX = randomX.nextInt(10) * 55;
        } else {
            this.posicaoY += velocidadeInimigo;
        }
    }

    private void visivel() {
        visivel = true;
    }

    public void invisivel() {
        visivel = false;
    }

    public Image getImagemNaveInimiga() {
        return imagemNaveInimiga;
    }

    public Image getImagemExplosao() {
        return imagemExplosao;
    }

    public Image getImagemChefao() {
        return imagemChefao;
    }

    public int getPosicaoX() {
        return posicaoX;
    }

    public int getPosicaoY() {
        return posicaoY;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public Rectangle getBounds() {
        return new Rectangle(posicaoX, posicaoY, largura, altura);
    }

    public void setVelocidadeInimigo(int velocidadeInimigo) {
        this.velocidadeInimigo = velocidadeInimigo;
    }
}