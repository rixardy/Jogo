package SpaceWar;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Cenario extends JPanel implements Runnable {

    private SpaceWar spaceWar;
    private boolean jogando;
    private List<NaveInimiga> arrayInimigos;
    private Trilha trilhaBatalha = new Trilha("Batalha");
    private Thread threadTrilhaBatalha = new Thread(trilhaBatalha);
    private List<MoverCenario> arrayMoveCenario;
    private List<Life> arrayLife;
    private int velocidadeDoInimigo;
    private boolean pause = false;
    private Reprodutor somExplosao;
    private Reprodutor somPerdeu;
    private Reprodutor somGanhou;
    private Reprodutor somDoTiro;
    private int movimentoInimigo;
    private int contador = 0;
    private boolean valida;
    private int[][] coordenadasInimigos = new int[200][9];
    private int[][] coordenadasCenario = new int[1][2];
    private int[][] coordenadasLife = {{650, 10}, {670, 10}, {690, 10}, {710, 10}, {730, 10}, {750, 10}, {770, 10}};

    public Cenario() {

        setFocusable(true);//foca o cenario
        setDoubleBuffered(true);//pré carrega as imagens

        jogando = true;//ativa o jogo

        insereCenario();
        insereInimigos();
        insereLife();

        threadTrilhaBatalha.start();//inicia a musica de fundo do cenario

    }

    private void insereInimigos() {//metodo para inserir os inimigos

        Random randomX = new Random();//gera numeros aleatorios

        arrayInimigos = new ArrayList<>();

        for (int i = 0; i < coordenadasInimigos.length; i++) {
            int posicaoX = randomX.nextInt(10) * 60;//passa os numeros aleatorios para a posição X da nave inimiga
            arrayInimigos.add(new NaveInimiga(posicaoX, i * -100));
        }

    }

    private void insereCenario() {//metodo para inserir cenario

        arrayMoveCenario = new ArrayList<>();

        for (int i = 0; i < coordenadasCenario.length; i++) {
            arrayMoveCenario.add(new MoverCenario(coordenadasCenario[i][0], coordenadasCenario[i][1]));
        }
    }

    private void insereLife() {//metodo para inserir vida

        arrayLife = new ArrayList<>();

        for (int i = 0; i < coordenadasLife.length; i++) {
            arrayLife.add(new Life(coordenadasLife[i][0], coordenadasLife[i][1]));

        }
    }

    @Override
    public void paint(Graphics pinta) {//metodo para pintar os objetos

        Graphics2D grafico = (Graphics2D) pinta;//passa por referência o objeto pinta para graphics2D

        ImageIcon fundoLateral = new ImageIcon(getClass().getResource("/Imagens/telapreta.png"));
        grafico.drawImage(fundoLateral.getImage(), 600, 0, this);//pinta a imagem na lateral(onde estão as instruções do jogo)

        if (jogando) {//se estiver jogando

            for (int l = 0; l < arrayMoveCenario.size(); l++) {//pinta a imagem de fundo do cenario

                MoverCenario moveC = arrayMoveCenario.get(l);

                grafico.drawImage(moveC.getImagem(), moveC.getPosicaoX(), moveC.getPosicaoY(), this);

            }

            grafico.drawImage(spaceWar.getImagem(), spaceWar.getPosicaoX(), spaceWar.getPosicaoY(), this);

            List<Tiro> tiros = spaceWar.getTiros();//coloca os tiros na mesma posição que a nave

            for (int i = 0; i < tiros.size(); i++) {//pinta os tiros

                Tiro tiro = (Tiro) tiros.get(i);
                grafico.drawImage(tiro.getImagem(), tiro.getPosicaoX(), tiro.getPosicaoY(), this);

            }

            for (int i = 0; i < arrayInimigos.size(); i++) {//pinta os inimigos

                NaveInimiga inimi = arrayInimigos.get(i);

                grafico.drawImage(inimi.getImagemNaveInimiga(), inimi.getPosicaoX(), inimi.getPosicaoY(), this);

                if (inimi.isVisivel() == false) {//se inimigo for atingido pinta a explosão

                    grafico.drawImage(inimi.getImagemExplosao(), inimi.getPosicaoX(), inimi.getPosicaoY(), this);

                }

            }

            for (int m = 0; m < arrayLife.size(); m++) {//pinta vida

                Life vidaL = arrayLife.get(m);

                grafico.drawImage(vidaL.getImagemLife(), vidaL.getPosicaoX(), vidaL.getPosicaoY(), this);

            }

        } else if (arrayLife.isEmpty()) {//se perder todas as vidas

            somPerdeu = new Reprodutor();

            try {//reproduz o som de perdeu
                if (contador == 0) {
                    trilhaBatalha.parar_trilha(1);
                    somPerdeu.abrirArquivoPerdeu();
                    somPerdeu.tocar();
                }

                contador = 1;

            } catch (BasicPlayerException ex) {
                Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
            }

            ImageIcon creditos = new ImageIcon(getClass().getResource("/Imagens/perdeu.jpg"));
            grafico.drawImage(creditos.getImage(), 0, 0, this);//pinta a imagem de game over

            pause = false;
            valida = false;

        } else if (arrayInimigos.isEmpty()) {//se derrotar todos os inimigos

            somGanhou = new Reprodutor();

            try {//reproduz o som de ganhou
                if (contador == 0) {
                    trilhaBatalha.parar_trilha(1);
                    somGanhou.abrirArquivoGanhou();
                    somGanhou.tocar();
                }

                contador = 1;

            } catch (BasicPlayerException ex) {
                Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
            }

            ImageIcon creditos2 = new ImageIcon(getClass().getResource("/Imagens/ganhou.jpg"));
            grafico.drawImage(creditos2.getImage(), 0, -30, this);

            pause = false;
            valida = false;

        }
        if (pause == true) {//se pause for ativado,congela a tela e todos os objetos

            ImageIcon imPause = new ImageIcon(getClass().getResource("/Imagens/pause.png"));
            grafico.drawImage(imPause.getImage(), 0, 0, null);//pinta a imagem de pause
        }
        pinta.dispose();//destroi a imagem anterior

    }

    @Override
    public void run() {//loop do jogo

        spaceWar = new SpaceWar();
        addKeyListener(new PegaEvento());

        while (jogando) {//emquanto estiver jogando,atualiza os dados,pinta e destroi.

            if (pause == false) {//se o pause estiver desativado os elementos descongelam

                if (arrayInimigos.isEmpty() || arrayLife.isEmpty()) {//se não tiver vidas ou os inimigos forem derrotados o jogo termina

                    jogando = false;

                }

                List<Tiro> tiros = spaceWar.getTiros();

                for (int i = 0; i < tiros.size(); i++) {

                    Tiro tiro = (Tiro) tiros.get(i);

                    if (tiro.isVisivel()) {//se o tiro esta visivel move o tiro,se não remove
                        tiro.moverTiro();
                    } else {
                        tiros.remove(i);
                    }
                }

                for (int i = 0; i < arrayInimigos.size(); i++) {

                    NaveInimiga naveIn = arrayInimigos.get(i);

                    if (naveIn.isVisivel()) {//se nave inimiga estiver visivel ela aparece nessas posições,se não desaparece

                        Random xRn = new Random();
                        Random zRn = new Random();

                        int z = zRn.nextInt(100);

                        if (movimentoInimigo != 0) {//determina o movimento aleatorio
                            movimentoInimigo = xRn.nextInt(50) + 1;
                            if (z == 1) {
                                naveIn.moverInimigo(movimentoInimigo);
                            } else if (z == 2) {
                                naveIn.moverInimigo(-movimentoInimigo);
                            }
                        }
                        naveIn.moverInimigo(0);
                        naveIn.setVelocidadeInimigo(velocidadeDoInimigo);

                    } else {
                        arrayInimigos.remove(i);
                    }
                }

                for (int m = 0; m < arrayMoveCenario.size(); m++) {//move cenario

                    MoverCenario cenarioMove = arrayMoveCenario.get(m);

                    cenarioMove.moverCenario();

                }

                spaceWar.moverSpaceWar();//move nave amiga
                capturarColisoes();//checa colisões
                repaint();//repinta as imagens
            }

            try {//faz com que os elementos ganhe movimentos
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void capturarColisoes() {//trata as colisões

        Rectangle formatoDaNave = spaceWar.getBounds();//pega o retangulo das imagens
        Rectangle formatoDoInimigo;
        Rectangle formatoDoTiro;

        for (int i = 0; i < arrayInimigos.size(); i++) {

            NaveInimiga inimigoTemporario = arrayInimigos.get(i);
            formatoDoInimigo = inimigoTemporario.getBounds();

            if (formatoDaNave.intersects(formatoDoInimigo)) {//se nave amiga atinge nave inimiga toca o som da explosão,inimigo some e remove uma vida

                somExplosao = new Reprodutor();

                try {

                    somExplosao.abrirArquivoExplosao();
                    somExplosao.tocar();

                } catch (BasicPlayerException ex) {
                    Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
                }

                arrayLife.remove(0);
                inimigoTemporario.setVisivel(false);

            }

        }

        List<Tiro> tiros = spaceWar.getTiros();

        for (int i = 0; i < tiros.size(); i++) {

            Tiro tiroTemporario = tiros.get(i);
            formatoDoTiro = tiroTemporario.getBounds();

            for (int j = 0; j < arrayInimigos.size(); j++) {

                NaveInimiga inimigoTemporario = arrayInimigos.get(j);
                formatoDoInimigo = inimigoTemporario.getBounds();

                if (formatoDoTiro.intersects(formatoDoInimigo)) {//se tiro atinge nave inimiga toca o som da explosão e inimigo some

                    somExplosao = new Reprodutor();

                    try {

                        somExplosao.abrirArquivoExplosao();
                        somExplosao.tocar();

                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    inimigoTemporario.setVisivel(false);
                    tiroTemporario.setVisivel(false);

                }
            }

        }
    }

    private class PegaEvento implements KeyListener {//pega os eventos de teclado e passa no construtos de cenario

        @Override
        public void keyPressed(KeyEvent tecla) {

            int key = tecla.getKeyCode();

            if (key == KeyEvent.VK_P) {//se apertar p o jogo pausa

                if (pause == false) {
                    pause = true;
                } else {
                    pause = false;
                }

            }

            if (key == KeyEvent.VK_ESCAPE) {//se apertar esc aparece a mensagem de sair

                pause = true;

                int opcao = JOptionPane.showConfirmDialog(null, "Deseja Realmente Sair?", null, JOptionPane.YES_NO_OPTION, JOptionPane.YES_OPTION, new ImageIcon(getClass().getResource("/Imagens/s.png")));

                if (opcao == JOptionPane.OK_OPTION) {
                    System.exit(0);

                } else {
                    pause = false;
                }
            }
            spaceWar.keyPressed(tecla);
        }

        @Override
        public void keyReleased(KeyEvent tecla) {

            spaceWar.keyReleased(tecla);

            valida = true;

        }

        @Override
        public void keyTyped(KeyEvent teclado) {

            char tecla = teclado.getKeyChar();

            if (tecla == 32 && valida) {

                somDoTiro = new Reprodutor();

                try {
                    somDoTiro.abrirArquivoTiro();
                    somDoTiro.tocar();

                } catch (BasicPlayerException ex) {
                    Logger.getLogger(SpaceWar.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
            spaceWar.keyTyped(teclado);

            valida = false;
        }
        
    }
    
    public void setVelocidadeDoInimigo(int velocidadeDoInimigo) {//seta a velocidade do inimigo
        this.velocidadeDoInimigo = velocidadeDoInimigo;
    }

    public void setMovimentoInimigo(int movimentoInimigo) {//seta o movimento aleatorio ou não
        this.movimentoInimigo = movimentoInimigo;
    }
}