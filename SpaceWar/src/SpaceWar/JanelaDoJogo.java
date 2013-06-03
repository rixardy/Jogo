package SpaceWar;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JanelaDoJogo extends JFrame {//classe quer herda de JFrame

    private static Trilha trilhaIntruducao = new Trilha("Introducao");//cria um objeto da classe trilha
    private static Thread threadTrilhaIntroducao = new Thread(trilhaIntruducao);//cria um objeto thread e passa como parametro a trilha que sera utilizada
    private Thread threadCenario;
    private Image imagemSeta;//cria a imagem para colocar no cursor do mouse
    private Cursor setaMouse;//cria um objeto cursor para alterar suas propriedades
    private Cenario cenarioNormal;
    private Cenario cenarioDificil;

    public JanelaDoJogo() {//construtor

        //setUndecorated(true);
        this.setTitle("Space War");//titulo da janela
        this.setSize(600, 700);//tamanho da janela
        this.setLocationRelativeTo(null);//faz que a janela apareça no centro
        this.setResizable(false);//desativa a função de maximizar

        ImageIcon imageI = new ImageIcon(getClass().getResource("/Imagens/14bis.png"));//insere uma imagem
        final JLabel label = new JLabel(imageI);//passa a imagem dentro do label para poder aparecer
        label.setBounds(0, 0, 600, 700);//posicao x e y,largura e altura

        final JPanel painel = new JPanel();//cria um painel
        add(painel);//adiciona o painel a janela
        painel.add(label);//adiciona o label que contem a imagem ao painel

        ImageIcon start = new ImageIcon(getClass().getResource("/Imagens/normal.png"));//insere uma imagem
        JButton botaoNormal = new JButton("", start);//cria um botao e passa o nome e a imagem que vai conter
        botaoNormal.setRolloverIcon(start);//retorna o icone de rolagem
        botaoNormal.setPressedIcon(start);//Define o ícone pressionado para o botão.
        botaoNormal.setBorderPainted(false);//faz sumir as bordas do botão
        botaoNormal.setContentAreaFilled(false);//faz o fundo do botão ficar transparente
        botaoNormal.setFocusPainted(false);//faz com que o botão não tenha o foco inicial
        botaoNormal.setBounds(30, 560, 100, 100);//define a posicao x e y,largura e altura
        botaoNormal.setVisible(true);//deixa o botão visivel
        label.add(botaoNormal);//adiciona o botao ao label da tela de jogo

        ImageIcon dificil = new ImageIcon(getClass().getResource("/Imagens/dificil.png"));
        JButton botaoDificil = new JButton("", dificil);
        botaoDificil.setRolloverIcon(dificil);
        botaoDificil.setPressedIcon(dificil);
        botaoDificil.setBorderPainted(false);
        botaoDificil.setContentAreaFilled(false);
        botaoDificil.setFocusPainted(false);
        botaoDificil.setBounds(490, 560, 80, 80);
        botaoDificil.setVisible(true);
        label.add(botaoDificil);

        painel.setVisible(true);//deixa o painel visivel
        this.setVisible(true);//deixa o frame visivel
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//faz com que a aplicação pare de rodar ao fechar a janela

        botaoNormal.addActionListener(new ActionListener() {//cria um evento para o botao
            @Override
            public void actionPerformed(ActionEvent e) {

                cenarioNormal = new Cenario();
                cenarioNormal.setVelocidadeDoInimigo(3);
                threadCenario = new Thread((Runnable) add(cenarioNormal));
                threadCenario.start();

                setSize(800, 700);//altera a largura da janela

                imagemSeta = Toolkit.getDefaultToolkit().getImage("");//cria uma imagem vazia
                setaMouse = Toolkit.getDefaultToolkit().createCustomCursor(imagemSeta, new Point(0, 0), "");//edita propriedades da seta do mouse passando um novo ponto e uma imagem vazia 
                setCursor(setaMouse);

                trilhaIntruducao.parar_trilha(1);//para a musica de introducao ao apertar o botao

                painel.setVisible(false);//faz com que o painel inicial desapareça
            }
        });

        botaoDificil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cenarioDificil = new Cenario();
                cenarioDificil.setVelocidadeDoInimigo(4);
                threadCenario = new Thread((Runnable) add(cenarioDificil));
                threadCenario.start();

                setSize(800, 700);

                imagemSeta = Toolkit.getDefaultToolkit().getImage("");
                setaMouse = Toolkit.getDefaultToolkit().createCustomCursor(imagemSeta, new Point(0, 0), "");
                setCursor(setaMouse);

                trilhaIntruducao.parar_trilha(1);

                painel.setVisible(false);
            }
        });

    }

    public static void main(String[] args) {

        new JanelaDoJogo();

        threadTrilhaIntroducao.start();//toca a musica de introducao

    }
}
