package SpaceWar;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class SpaceWar {//classe da nave

    private int posicaoX;//posicao x
    private int posicaoY;//posicao y
    private int moveX;//vai acrescentar pixels a posicao x
    private int moveY;//vai acrescentar pixels a posicao y
    private int altura;//define a altura
    private int largura;//define a altura
    private Image imagemDaNave;//cria a imagem da nave
    private List<Tiro> tiros;//cria uma lista de tiros para adicionar a nave
    private boolean visivel;//define o estado do jogo

    public SpaceWar() {

        ImageIcon retornaImagem = new ImageIcon(getClass().getResource("/Imagens/nave.png"));//insere uma imagem
        imagemDaNave = retornaImagem.getImage();//pega a imagem e retorna para um objeto do tipo Image 

        altura = imagemDaNave.getHeight(null);//define altura para utilizar exatamente o valor da imagem
        largura = imagemDaNave.getWidth(null);//define largura para utilizar exatamente o valor da imagem

        tiros = new ArrayList<>();//passa a lista de tiros para um array
        this.posicaoX = 0;//define a posição x inicial da nave
        this.posicaoY = 600;//define a posição y inicial da nave

    }

    public void atirar() {//cria o metodo de atirar

        this.tiros.add(new Tiro(posicaoX + largura, posicaoY + altura));//adiciona o tiro a mesma posicao da nave

    }

    public Rectangle getBounds() {//metodo para retornar posicoes,largura e altura da imagem da nave
        return new Rectangle(posicaoX, posicaoY, largura, altura);

    }

    public void moverSpaceWar() {//metodo para mover a nave

        posicaoX += moveX;//adiciona pixels a posicao x
        posicaoY += moveY;//adiciona pixels a posicao y

        if (this.posicaoX < 1) {//limita a area para que a nave nao ultrapasse a tela
            posicaoX = 1;
        }
        if (this.posicaoX > 549) {
            posicaoX = 549;
        }
        if (this.posicaoY < 0) {
            posicaoY = 0;
        }
        if (this.posicaoY > 620) {
            posicaoY = 620;
        }

    }

    public void keyPressed(KeyEvent teclado) {//eventos ao pressionar teclas do teclado

        int tecla = teclado.getKeyCode();

        if (tecla == KeyEvent.VK_UP) {//se apertar pra seta pra cima ela sobe 2 pixels

            moveY = -2;
        } else if (tecla == KeyEvent.VK_DOWN) {//se apertar pra seta pra baixo ela desce 2 pixels

            moveY = 2;

        } else if (tecla == KeyEvent.VK_LEFT) {//se apertar pra seta pra esquerda ela move 2 pixels para esquerda

            moveX = -2;

        } else if (tecla == KeyEvent.VK_RIGHT) {//se apertar pra seta pra direita ela move 2 pixels para direira

            moveX = 2;

        }

    }

    public void keyReleased(KeyEvent teclado) {//evento ao largar tecla,nave fica na mesma posicao

        int tecla = teclado.getKeyCode();

        if (tecla == KeyEvent.VK_SPACE) {//se apertar space a nave atira

            atirar();

            Reprodutor reproduz = new Reprodutor();
            try {
                reproduz.abrirArquivo();
                reproduz.tocar();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(SpaceWar.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (tecla == KeyEvent.VK_UP) {

            moveY = 0;

        } else if (tecla == KeyEvent.VK_DOWN) {

            moveY = 0;

        } else if (tecla == KeyEvent.VK_LEFT) {

            moveX = 0;

        } else if (tecla == KeyEvent.VK_RIGHT) {

            moveX = 0;

        }

    }

    public int getPosicaoX() {
        return posicaoX;
    }

    public int getPosicaoY() {
        return posicaoY;
    }

    public Image getImagem() {
        return imagemDaNave;
    }

    public List<Tiro> getTiros() {
        return tiros;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }
}
