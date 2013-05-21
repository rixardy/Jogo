package testea;

    import java.awt.Image;
    import javax.swing.ImageIcon;

    public class Life {

        private Image imagemLife;
        private int posicaoX;
        private int posicaoY;
        private int largura;
        private int altura;
        private boolean visivel = true;

        public Life (int posicaoX, int posicaoY) {

            this.posicaoX = posicaoX;
            this.posicaoY = posicaoY;

            ImageIcon retornaLife;     
                
            retornaLife = new ImageIcon(getClass().getResource("/Imagens/vida.png"));
            imagemLife = retornaLife.getImage();

            this.largura = imagemLife.getWidth(null);
            this.altura = imagemLife.getHeight(null);

            visivel();

        }
        
        public void vida(){

            if(this.posicaoY > 0){
                this.posicaoY = 10;
            
        }
        }
        private void visivel(){
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

    }