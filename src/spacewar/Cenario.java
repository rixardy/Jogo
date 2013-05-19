    package spacewar;

    import java.awt.Color;
    import java.awt.Font;
    import java.awt.Graphics;
    import java.awt.Graphics2D;
    import java.awt.Image;
    import java.awt.Rectangle;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.KeyAdapter;
    import java.awt.event.KeyEvent;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.awt.event.MouseMotionAdapter;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
    import java.util.logging.Level;
    import java.util.logging.Logger;

    import javax.swing.ImageIcon;
    import javax.swing.JPanel;
    import javax.swing.Timer;

    public class Cenario extends JPanel implements ActionListener {

            private Image telaDeFundo;
            private AirPlane airPlane;
            private Timer tempo;
            private boolean jogando;
            private List<NaveInimiga> inimigos ;
            private SistemaOperacional so;
            private int life = 5;
            private Trilha trilha = new Trilha("Batalha");
            private Thread thread = new Thread(trilha);
            private Trilha trilha2 = new Trilha("Zerou");
            private Thread thread2 = new Thread(trilha2);
            private List <MoverCenario> moveCenario;
            
            private int[][] coordenadas = {{55,0},{190,690},{240,110},{90,170},{350,220},{400,350},{450,430},{300,450},{70,600},{450,650},
                                          {330,500},{410,550},{480,700},{179,300},{250,400},{200,220},{100,100},{300,150}};

            private int[][] coordenadasCenario = {{0,-6300}};
            
            ImageIcon retornaImagem = new ImageIcon(getClass().getResource("/Imagens/barril.png"));
            private Image imagemLife = retornaImagem.getImage();
            
            public Cenario(){
                
                    setFocusable(true);
                    setDoubleBuffered(true);
                    addKeyListener(new PegaEvento());
                    addMouseListener(new PegaMouse());
                    addMouseMotionListener(new MoveMouse());

                    so = new SistemaOperacional();
                    URL im = getClass().getResource("/Imagens/fundo.jpg");
                    
                    switch (so.identificaSO()) {
                    case "mac":
                        {
                            ImageIcon retornaImagem = new ImageIcon(im);
                            telaDeFundo = retornaImagem.getImage();
                            break;
                        }
                    case "win":
                        {
                            ImageIcon retornaImagem = new ImageIcon(getClass().getResource("/Imagens/torre.jpg"));
                            telaDeFundo = retornaImagem.getImage();
                            break;
                        }
                    }
                    
                    airPlane = new AirPlane();

                    jogando = true;
                    
                    adicionaCenario();
                    adicionaInimigos();
                    
                    tempo = new Timer(1/2, this);
                    tempo.start();
                    
                    thread.start();                
                   
            }
            
            private void dormir(long tempo){
            
                try {
            Thread.sleep(tempo);
            } catch (InterruptedException ex) {
            Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
            }
            }

            public void adicionaInimigos(){

            inimigos = new ArrayList<>();

            for(int i = 0;i < coordenadas.length;i++){
                inimigos.add(new NaveInimiga(coordenadas[i][0], coordenadas[i][1]));
            }
            }
            
            public void adicionaCenario(){

            moveCenario = new ArrayList<>();

            for(int p = 0;p < coordenadasCenario.length;p++){
                moveCenario.add(new MoverCenario(coordenadasCenario[p][0], coordenadasCenario[p][1]));
            }
            }

            @Override
            public void paint(Graphics pinta){

                    Graphics2D grafico = (Graphics2D) pinta;
                                                    
                    for(int l = 0;l < moveCenario.size(); l++){

                        MoverCenario moveC = moveCenario.get(l);

                        grafico.drawImage(moveC.getImagem(), moveC.getPosicaoX(), moveC.getPosicaoY(), this);
                        
                    }
                    
                    if(jogando){
                    
                    grafico.drawImage(airPlane.getImagem(), airPlane.getPosicaoX(), airPlane.getPosicaoY(), this);

                    List<Tiro> tiros = airPlane.getTiros();
                    
                    for(int i = 0; i < tiros.size(); i++){

                        Tiro tiro = (Tiro)tiros.get(i);
                        grafico.drawImage(tiro.getImagem(), tiro.getPosicaoX(), tiro.getPosicaoY(), this);
                        
                    }

                    for(int i = 0;i < inimigos.size(); i++){

                        NaveInimiga inimi = inimigos.get(i);

                        grafico.drawImage(inimi.getImagem(), inimi.getPosicaoX(), inimi.getPosicaoY(), this);
                        
                    }
                    
                    grafico.setColor(Color.white);
                    grafico.setFont(new Font("Arial", Font.BOLD, 20));
                    grafico.drawString("INIMIGOS: "+inimigos.size(), 468, 20);
                    
                    grafico.setColor(Color.white);
                    grafico.setFont(new Font("Arial", Font.BOLD, 20));
                    grafico.drawString("LIFE: "+life, 518, 45);
                    
                    dormir(5);
                    
                    }else{
                        
                        thread.stop();
                        thread2.start();
                        
                        ImageIcon creditos = new ImageIcon(getClass().getResource("/Imagens/morreu.png"));
                        grafico.drawImage(creditos.getImage(), 0, 0, this);
                    }

                pinta.dispose();

            }

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if(inimigos.isEmpty()){

                    jogando = false;

                }

                    List<Tiro> tiros = airPlane.getTiros();

                    for(int i = 0; i < tiros.size(); i++){

                        Tiro tiro = (Tiro)tiros.get(i);

                        if(tiro.isVisivel()){
                            tiro.moverTiro();
                        }

                        else{
                            tiros.remove(i);
                        }
                    }
                    
                    for(int i = 0; i < inimigos.size(); i++){

                        NaveInimiga naveIn = inimigos.get(i);

                        if(naveIn.isVisivel()){
                            naveIn.moverInimigo();
                        }

                        else{
                            inimigos.remove(i);
                        }
                    }
                    
                    for(int m = 0; m < moveCenario.size(); m++){

                        MoverCenario cenarioMove = moveCenario.get(m);
                    try {
                        cenarioMove.moverCenario();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    }

                    airPlane.moverAirplane();
                    capturarColisoes();
                    repaint();

            }

            public void capturarColisoes(){

                Rectangle formatoDaNave = airPlane.getBounds();
                Rectangle formatoDoInimigo;
                Rectangle formatoDoTiro;

                for(int i = 0;i < inimigos.size();i++){

                    NaveInimiga inimigoTemporario = inimigos.get(i);
                    formatoDoInimigo = inimigoTemporario.getBounds();

                    if(formatoDaNave.intersects(formatoDoInimigo)){

                        life -= 1;
                        
                        inimigoTemporario.setVisivel(false);
                        
                        if(life == 0){
                            
                        airPlane.setVisivel(false);
                        
                        jogando = false;
                    }
                        //adicionaInimigos();
                    }

                    }
                
                    List<Tiro> tiros = airPlane.getTiros();

                    for(int i = 0;i < tiros.size();i++){

                        Tiro tiroTemporario = tiros.get(i);
                        formatoDoTiro = tiroTemporario.getBounds();

                    for(int j = 0; j < inimigos.size();j++){

                        NaveInimiga inimigoTemporario = inimigos.get(j);
                        formatoDoInimigo = inimigoTemporario.getBounds();
                        
                        if(formatoDoTiro.intersects(formatoDoInimigo)){

                            inimigoTemporario.setVisivel(false);
                            tiroTemporario.setVisivel(false);
                            
                        }
                    }
                    
            }
   }

            private class PegaEvento extends KeyAdapter {

                    @Override
                    public void keyPressed(KeyEvent tecla) {

                            airPlane.keyPressed(tecla);
                    }

                    @Override
                    public void keyReleased(KeyEvent tecla) {

                            airPlane.keyReleased(tecla);
                    }	

            }

            private class PegaMouse extends MouseAdapter {

                   @Override
                   public void mousePressed(MouseEvent e) {

                           airPlane.mousePressed(e);
                   }

            }

            private class MoveMouse extends MouseMotionAdapter {

                   @Override
                   public void mouseMoved(MouseEvent e) {
                         
                         airPlane.mouseMoved(e);
                   }

                   @Override
                   public void mouseDragged(MouseEvent e) {

                          airPlane.mouseMoved(e);
                   }
            }

    }