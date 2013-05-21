package testea;

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
            private Trilha trilha2 = new Trilha("Perdeu");
            private Thread thread2 = new Thread(trilha2);
            private Trilha trilha3 = new Trilha("Ganhou");
            private Thread thread3 = new Thread(trilha3);
            private List <MoverCenario> moveCenario;
            private List <Life> vida;

            private int[][] coordenadas = {{55,0},{190,690},{240,110},{90,170},{350,220},{400,350},{450,430},{300,450},{70,600},{450,650},
                                          {330,500},{410,550},{480,700},{179,300},{250,400},{200,220},{100,100},{300,150}};
            
            private int[][] coordenadasCenario = {{0,-6300}};
            
            private int[][] coordenadasLife = {{490,10},{510,10},{530,10},{550,10},{570,10}};
            
            public Cenario(){
                
                    setFocusable(true);
                    setDoubleBuffered(true);
                    addKeyListener(new PegaEvento());
                    addMouseListener(new PegaMouse());
                    addMouseMotionListener(new MoveMouse());

                    so = new SistemaOperacional();
                    URL im = getClass().getResource("/Imagens/torre.jpg");
                    
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
                    adicionaLife();
                    
                    tempo = new Timer(1, this);
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

            for(int i = 0;i < coordenadasCenario.length;i++){
                moveCenario.add(new MoverCenario(coordenadasCenario[i][0], coordenadasCenario[i][1]));
            }
            }
            
            public void adicionaLife(){

            vida = new ArrayList<>();

            for(int i = 0;i < coordenadasLife.length;i++){
                vida.add(new Life(coordenadasLife[i][0], coordenadasLife[i][1]));
            }
            }
            
            @Override
            public void paint(Graphics pinta){

                    Graphics2D grafico = (Graphics2D) pinta;
                                                    
                    if(jogando){
                        
                    for(int l = 0;l < moveCenario.size(); l++){

                    MoverCenario moveC = moveCenario.get(l);

                    grafico.drawImage(moveC.getImagem(), moveC.getPosicaoX(), moveC.getPosicaoY(), this);
                        
                    }
                    
                    grafico.drawImage(airPlane.getImagem(), airPlane.getPosicaoX(), airPlane.getPosicaoY(), this);

                    List<Tiro2> tiros = airPlane.getTiros();
                    
                    for(int i = 0; i < tiros.size(); i++){

                        Tiro2 tiro = (Tiro2)tiros.get(i);
                        grafico.drawImage(tiro.getImagem(), tiro.getPosicaoX(), tiro.getPosicaoY(), this);
                        
                    }

                    for(int i = 0;i < inimigos.size(); i++){

                        NaveInimiga inimi = inimigos.get(i);

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
                    grafico.drawString("INIMIGOS: "+inimigos.size(), 490, 665);
                    
                    dormir(5);
                    
                    }else if(vida.isEmpty()){
                        
                        thread.stop();
                        thread2.start();
                        
                        ImageIcon creditos = new ImageIcon(getClass().getResource("/Imagens/morreu.png"));
                        grafico.drawImage(creditos.getImage(), 0, 0, this);
                    
                    }else if(inimigos.isEmpty()){
                        
                        thread.stop();
                        thread3.start();
                        
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

                    List<Tiro2> tiros = airPlane.getTiros();

                    for(int i = 0; i < tiros.size(); i++){

                        Tiro2 tiro = (Tiro2)tiros.get(i);

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
                        
                        vida.remove(0);
                        inimigoTemporario.setVisivel(false);
                        
                        if(vida.isEmpty()){
                            
                        airPlane.setVisivel(false);
                        
                        jogando = false;
                    }
                        //adicionaInimigos();
                    }

                    }
                
                    List<Tiro2> tiros = airPlane.getTiros();

                    for(int i = 0;i < tiros.size();i++){

                        Tiro2 tiroTemporario = tiros.get(i);
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