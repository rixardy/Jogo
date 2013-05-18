    package spacewar;

    import java.awt.Image;
    import java.awt.Rectangle;
    import javax.swing.ImageIcon;

    public class NaveInimiga {

        private Image imagem;
        private Image imagem2;
        private int posicaoX;
        private int posicaoY;
        private int largura;
        private int altura;
        private boolean visivel = true;
        private final int ALTURA_DA_TELA = 0;
        private final float VELOCIDADE_DO_INIMIGO = 5;
        private static int contador = 0;

        public NaveInimiga (int posicaoX, int posicaoY) {

            this.posicaoX = posicaoX;
            this.posicaoY = posicaoY;

            ImageIcon retornaImagem;
            if(contador++ % 3 == 0){
            
                retornaImagem = new ImageIcon(getClass().getResource("/Imagens/naveInimiga.png"));
            
            }else {
                
                retornaImagem = new ImageIcon(getClass().getResource("/Imagens/naveInimiga2.png"));
                
            }

            imagem = retornaImagem.getImage();
           
            this.largura = imagem.getWidth(null);
            this.altura = imagem.getHeight(null);

            visivel();

        }

        public void moverInimigo(){

            if(this.posicaoY < 0 || this.posicaoY == 700){
                this.posicaoY = ALTURA_DA_TELA;
            }else{
                this.posicaoY += VELOCIDADE_DO_INIMIGO;
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