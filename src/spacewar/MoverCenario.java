    package spacewar;

    import java.awt.Image;
    import javax.swing.ImageIcon;

    public class MoverCenario {

        private Image imagemCenario;
        private int posicaoX;
        private int posicaoY;
        private int largura;
        private int altura;
        private boolean visivel = true;
        private final int ALTURA_DA_TELA = -6300;
        private final float VELOCIDADE_DA_TELA = 1;

        public MoverCenario (int posicaoX, int posicaoY) {

            this.posicaoX = posicaoX;
            this.posicaoY = posicaoY;

            ImageIcon retornaCenario;     
                
            retornaCenario = new ImageIcon(getClass().getResource("/Imagens/torre.jpg"));
            imagemCenario = retornaCenario.getImage();

            this.largura = imagemCenario.getWidth(null);
            this.altura = imagemCenario.getHeight(null);

            visivel();

        }

        public void moverCenario() throws InterruptedException{

            if(this.posicaoY > 0){
                this.posicaoY = ALTURA_DA_TELA;
            }else{
                this.posicaoY += VELOCIDADE_DA_TELA;
            }
            Thread.sleep(10);
        }

        private void visivel(){
            visivel = true;
        }

        public Image getImagem() {
            return imagemCenario;
        }

        public int getPosicaoX() {
            return posicaoX;
        }

        public int getPosicaoY() {
            return posicaoY;
        }

    }