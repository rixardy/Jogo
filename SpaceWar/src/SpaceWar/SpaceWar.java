package SpaceWar;

    import java.awt.Image;
    import java.awt.Rectangle;
    import java.awt.event.KeyEvent;
    import java.awt.event.MouseEvent;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;
    import javax.swing.ImageIcon;

    
           public class SpaceWar {

            private int posicaoX;
            private int posicaoY;
            private int moveX;
            private int moveY;
            private int altura;
            private int largura;
            private Image imagem;
            private List<Tiro> tiros;
            private boolean visivel;
            private SistemaOperacional so;
            
            public SpaceWar(){
                
                so = new SistemaOperacional();
                URL im = getClass().getResource("/Imagens/nave.png");
                
                    switch (so.identificaSO()) {
                    case "mac":
                        {
                            ImageIcon retornaImagem = new ImageIcon(im);
                            imagem = retornaImagem.getImage();
                            break;
                        }
                    case "win":
                        {
                            ImageIcon retornaImagem = new ImageIcon(getClass().getResource("/Imagens/nave.png"));
                            imagem = retornaImagem.getImage();
                            break;
                        }
                }
                   

                    altura = imagem.getHeight(null);
                    largura = imagem.getWidth(null);

                    tiros = new ArrayList<>();
                    this.posicaoX = 0;
                    this.posicaoY = 600;

            }

            public void atirar(){

                this.tiros.add(new Tiro(posicaoX+largura, posicaoY+altura));

            }

            public Rectangle getBounds(){
            return new Rectangle(posicaoX, posicaoY, largura, altura);

            }

            public void moverAirplane(){

                    posicaoX += moveX;
                    posicaoY += moveY;

                    if(this.posicaoX < 1){
                        posicaoX = 1;
                    }
                    if(this.posicaoX > 549){
                        posicaoX = 549;
                    }
                    if(this.posicaoY < 0){
                        posicaoY = 0;
                    }
                    if(this.posicaoY > 620){
                        posicaoY = 620;
                    }

            }

            public void keyPressed (KeyEvent teclado) {

                    int tecla = teclado.getKeyCode();
                    if(tecla == KeyEvent.VK_SPACE){
                        
                        atirar();
                        
                    }
                    
                    if(tecla == KeyEvent.VK_UP){

                            moveY = -2;
                    }

                    if(tecla == KeyEvent.VK_DOWN){

                            moveY = 2;

                    }

                    if(tecla == KeyEvent.VK_LEFT){

                            moveX = -2;

                    }

                    if(tecla == KeyEvent.VK_RIGHT){

                            moveX = 2;

                    }

            }

            public void keyReleased(KeyEvent teclado) {

            int tecla = teclado.getKeyCode();
            
                    if(tecla == KeyEvent.VK_SPACE){
                        
                           
                        
                    }

                    if(tecla == KeyEvent.VK_UP){

                            moveY = 0;

                    }

                    if(tecla == KeyEvent.VK_DOWN){

                            moveY = 0;

                    }

                    if(tecla == KeyEvent.VK_LEFT){

                            moveX = 0;

                    }

                    if(tecla == KeyEvent.VK_RIGHT){

                            moveX = 0;

                    }

            }

            public void mousePressed(MouseEvent e) {

                atirar();

            }

            public void mouseMoved(MouseEvent e) {  

                posicaoX = e.getX();
                posicaoY = e.getY();

            }

            public void mouseDragged(MouseEvent e) {  

                posicaoX = e.getX();
                posicaoY = e.getY();

            }

            public int getPosicaoX() {
                    return posicaoX;
            }

            public int getPosicaoY() {
                    return posicaoY;
            }

            public Image getImagem() {
                    return imagem;
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