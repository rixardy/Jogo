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

    public class JanelaDoJogo extends JFrame {
        
        private static Trilha trilha = new Trilha("Introducao");
        private static Thread thread = new Thread(trilha);

            public JanelaDoJogo(){

                    setTitle("Space War");
                    setSize(600, 700);
                    setLocationRelativeTo(null);
                    setResizable(false);
                    
                    ImageIcon imageI = new ImageIcon(getClass().getResource("/Imagens/14bis.png"));
                    final JLabel label = new JLabel(imageI);
                    label.setBounds(0, 0, 600, 700);

                    final JPanel painel = new JPanel();
                    add(painel);
                    painel.add(label);
                    
                    ImageIcon start = new ImageIcon(getClass().getResource("/Imagens/start.png"));
                    JButton botaoNormal = new JButton("",start);
                    botaoNormal.setRolloverIcon(start);
                    botaoNormal.setPressedIcon(start);
                    botaoNormal.setBorderPainted(false);
                    botaoNormal.setContentAreaFilled(false);
                    botaoNormal.setFocusPainted(false);
                    botaoNormal.setBounds(30, 560, 80, 80);
                    botaoNormal.setVisible(true);
                    label.add(botaoNormal);
                    
                    ImageIcon dificil = new ImageIcon(getClass().getResource("/Imagens/start.png"));
                    JButton botaoDificil = new JButton("",dificil);
                    botaoDificil.setRolloverIcon(dificil);
                    botaoDificil.setPressedIcon(dificil);
                    botaoDificil.setBorderPainted(false);
                    botaoDificil.setContentAreaFilled(false);
                    botaoDificil.setFocusPainted(false);
                    botaoDificil.setBounds(500, 560, 80, 80);
                    botaoDificil.setVisible(true);
                    label.add(botaoDificil);
                    
                    painel.setVisible(true);
                    setVisible(true);
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                    botaoNormal.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                            add(new Cenario());
                            setSize(800, 700);
                            
                            Image cursorImage = Toolkit.getDefaultToolkit().getImage("");
                            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0),"");
                            setCursor( blankCursor );
                            
                            thread.stop();
                            
                            painel.setVisible(false);
                        }
                    });
                    
                    botaoDificil.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                            add(new CenarioDificil());
                            setSize(800, 700);
                            
                            Image cursorImage = Toolkit.getDefaultToolkit().getImage("");
                            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0),"");
                            setCursor( blankCursor );
                            
                            thread.stop();
                            
                            painel.setVisible(false);
                        }
                    });
                    
                        }

            public static void main(String[] args) {
                
                    JanelaDoJogo janelaDoJogo = new JanelaDoJogo();

                    thread.start();

            }

    }
