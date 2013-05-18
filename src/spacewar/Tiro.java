    package spacewar;

    import java.awt.Image;
    import java.awt.Rectangle;
    import java.net.URL;
    import javax.swing.ImageIcon;

    public class Tiro {

        private Image imagem;
        private int posicaoX;
        private int posicaoY;
        private int largura;
        private int altura;
        private boolean visivel = true;
        private final int ALTURA_DA_TELA = 700;
        private final int VELOCIDADE_DO_TIRO = 4;
        private SistemaOperacional so;
        
        public Tiro(int posicaoX, int posicaoY) {

            this.posicaoX = posicaoX - 35;
            this.posicaoY = posicaoY - 50;
            
                so = new SistemaOperacional();
                URL im = getClass().getResource("/Imagens/tiro.png");

                if(so.identificaSO().equals("mac")){
                    
                    ImageIcon retornaImagem = new ImageIcon(im);
                    imagem = retornaImagem.getImage();
                }
                
                else if(so.identificaSO().equals("win")){
                    
                    ImageIcon retornaImagem = new ImageIcon(getClass().getResource("/Imagens/tiro.png"));
                    imagem = retornaImagem.getImage();
                }
                
                this.largura = imagem.getWidth(null);
                this.altura = imagem.getHeight(null);
            
            visivel();

        }

            public void moverTiro(){

                this.posicaoY -= VELOCIDADE_DO_TIRO;

                if(this.posicaoY > ALTURA_DA_TELA){

                    invisivel();
                }
            }
            
            public void moverTiroDireita(){

                this.posicaoY -= VELOCIDADE_DO_TIRO;
                this.posicaoX +=1-1/2;

                if(this.posicaoY > ALTURA_DA_TELA){

                    invisivel();
                }
            }
            
            public void moverTiroEsquerda(){

                this.posicaoY -= VELOCIDADE_DO_TIRO;
                this.posicaoX -=1+1/2;

                if(this.posicaoY > ALTURA_DA_TELA){

                    invisivel();
                }
            }

        public void visivel(){
            visivel = true;
        }

        public void invisivel(){
            visivel = false;
        }

        public Image getImagem() {
            return imagem;
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

        public Rectangle getBounds(){
            return new Rectangle(posicaoX, posicaoY, largura, altura);

        }

    }