package SpaceWar;

    import java.awt.Color;
    import java.awt.Font;
    import java.awt.Graphics;
    import java.awt.Graphics2D;
    import java.awt.Rectangle;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.KeyAdapter;
    import java.awt.event.KeyEvent;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.awt.event.MouseMotionAdapter;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.logging.Level;
    import java.util.logging.Logger;

    import javax.swing.ImageIcon;
    import javax.swing.JPanel;
    import javax.swing.Timer;

    public class CenarioNormal extends JPanel implements ActionListener,Runnable{

            private SpaceWar airPlane;
            private Timer tempo;
            private boolean jogando;
            private List<NaveInimigaNormal> inimigos;
            private SistemaOperacional so;
            private Trilha batalha = new Trilha("Batalha");
            private Thread musicaDoCenario = new Thread(batalha);
            private Trilha perdeu = new Trilha("Perdeu");
            private Thread musicaDoGameOver = new Thread(perdeu);
            private Trilha ganhou = new Trilha("Ganhou");
            private Thread musicaDaVitoria = new Thread(ganhou);
            private List <MoverCenario> moveCenario;
            private List <Life> vida;
            
            private int[][] coordenadas = {{55,0},{190,690},{240,110},{90,170},{350,220},{400,350},{450,430},{300,450},{70,600},{450,650},
                                          {330,500},{410,550},{480,700},{179,300},{250,400},{200,220},{100,100},{300,150}};
                    
  
            private int[][] coordenadasCenario = {{0,-6300}};
            
            private int[][] coordenadasLife = {{690,10},{710,10},{730,10},{750,10},{770,10}};
            
            
            @Override
             public void run() {
                
                setFocusable(true);
                setDoubleBuffered(true);
                addKeyListener(new PegaEvento());
                //addMouseListener(new PegaMouse());
                //addMouseMotionListener(new MoveMouse());
                
                airPlane = new SpaceWar();

                    jogando = true;
                    
                    adicionaCenario();
                    adicionaInimigos();
                    adicionaLife();
                    
                    tempo = new Timer(5, this);
                    tempo.start();
                    
                    musicaDoCenario.start();
                    Thread.yield();  
                
            }
            
            private void adicionaInimigos(){

            inimigos = new ArrayList<>();

            for(int i = 0;i < coordenadas.length;i++){
                inimigos.add(new NaveInimigaNormal(coordenadas[i][0], coordenadas[i][1]));
            }
            }
            
            private void adicionaCenario(){

            moveCenario = new ArrayList<>();

            for(int i = 0;i < coordenadasCenario.length;i++){
                moveCenario.add(new MoverCenario(coordenadasCenario[i][0], coordenadasCenario[i][1]));
            }
            }
            
            private void adicionaLife(){

            vida = new ArrayList<>();

            for(int i = 0;i < coordenadasLife.length;i++){
                vida.add(new Life(coordenadasLife[i][0], coordenadasLife[i][1]));
            }
            }
            
            @Override
            public void paint(Graphics pinta){

                    Graphics2D grafico = (Graphics2D) pinta;
                    
                    ImageIcon fundoLateral = new ImageIcon(getClass().getResource("/Imagens/telapreta.png"));
                    grafico.drawImage(fundoLateral.getImage(), 600, -5, this);
                    
                    if(jogando){
                        
                    for(int l = 0;l < moveCenario.size(); l++){

                    MoverCenario moveC = moveCenario.get(l);
                    
                    grafico.drawImage(moveC.getImagem(), moveC.getPosicaoX(), moveC.getPosicaoY(), this);
                                     
                    }
                    
                    grafico.drawImage(airPlane.getImagem(), airPlane.getPosicaoX(), airPlane.getPosicaoY(), this);

                    List<Tiro> tiros = airPlane.getTiros();
                    
                    for(int i = 0; i < tiros.size(); i++){

                        Tiro tiro = (Tiro)tiros.get(i);
                        grafico.drawImage(tiro.getImagem(), tiro.getPosicaoX(), tiro.getPosicaoY(), this);
                        
                    }

                    for(int i = 0;i < inimigos.size(); i++){
                        
                        NaveInimigaNormal inimi = inimigos.get(i);

                        grafico.drawImage(inimi.getImagem(), inimi.getPosicaoX(), inimi.getPosicaoY(), this);
                        
                        if(inimi.isVisivel() == false){
                            
                            grafico.drawImage(inimi.getImagem2(), inimi.getPosicaoX(), inimi.getPosicaoY(), this);
                           
                        }
                        
                    }
                    
                    for(int m = 0;m < vida.size(); m++){

                    Life vidaL = vida.get(m);

                    grafico.drawImage(vidaL.getImagemLife(), vidaL.getPosicaoX(), vidaL.getPosicaoY(), this);
                        
                    }
                    
                    grafico.setColor(Color.white);
                    grafico.setFont(new Font("Arial", Font.BOLD, 15));
                    grafico.drawString("INIMIGOS: "+inimigos.size(), 690, 50);
                    
                    }else if(vida.isEmpty()){
                        
                        musicaDoCenario.stop();
                        musicaDoGameOver.start();
                        
                        ImageIcon creditos = new ImageIcon(getClass().getResource("/Imagens/morreu.png"));
                        grafico.drawImage(creditos.getImage(), 0, 0, this);
                    
                    }else if(inimigos.isEmpty()){
                        
                        musicaDoCenario.stop();
                        musicaDaVitoria.start();
                        
                        ImageIcon creditos2 = new ImageIcon(getClass().getResource("/Imagens/vencedor.png"));
                        grafico.drawImage(creditos2.getImage(), 0, 0, this);
                        
                    }
                                        
                pinta.dispose();

            }

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if(inimigos.isEmpty() || vida.isEmpty()){

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

                        NaveInimigaNormal naveIn = inimigos.get(i);

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
                        Logger.getLogger(CenarioNormal.class.getName()).log(Level.SEVERE, null, ex);
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

                    NaveInimigaNormal inimigoTemporario = inimigos.get(i);
                    formatoDoInimigo = inimigoTemporario.getBounds();

                    if(formatoDaNave.intersects(formatoDoInimigo)){
                        
                        vida.remove(0);
                        inimigoTemporario.setVisivel(false);
                        
                        if(vida.isEmpty()){
                            
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

                        NaveInimigaNormal inimigoTemporario = inimigos.get(j);
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

            /*private class PegaMouse extends MouseAdapter {

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
            }*/

    }