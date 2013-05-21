package testea;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Trilha implements Runnable{

    private String som;
    
    public Trilha(String som){
        
        if(som.equals("Batalha")){
            this.som = "Batalha";
        }else if(som.equals("Introducao")){
            this.som = "Introducao";
        }else if(som.equals("Perdeu")){
            this.som = "Perdeu";
        }else if(som.equals("Ganhou")){
            this.som = "Ganhou";
    }
    }
    
    @Override
    public void run() {
        
        int i = 0;
        
        while(i < 5){
            
            if (this.som.equals("Batalha")){
                
                InputStream in;
                
                    in = getClass().getClassLoader().getResourceAsStream("Imagens/Batalha.mp3"); 
                    
                    Player p;
                try {
                    
                    p = new Player(in);
                    p.play();
                    
                } catch (JavaLayerException ex) {
                    Logger.getLogger(Trilha.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }else if(this.som.equals("Introducao")){
                
                InputStream in;
                
                    in = getClass().getClassLoader().getResourceAsStream("Imagens/inicio.mp3"); 
                    
                    Player p;
                try {
                    
                    p = new Player(in);
                    p.play();
                    
                } catch (JavaLayerException ex) {
                    Logger.getLogger(Trilha.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }else if(this.som.equals("Perdeu")){
                
                InputStream in;
                
                    in = getClass().getClassLoader().getResourceAsStream("Imagens/perdeu.mp3"); 
                    
                    Player p;
                try {
                    
                    p = new Player(in);
                    p.play();
                    
                } catch (JavaLayerException ex) {
                    Logger.getLogger(Trilha.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }else if(this.som.equals("Ganhou")){
                
                InputStream in;
                
                    in = getClass().getClassLoader().getResourceAsStream("Imagens/ganhou.mp3"); 
                    
                    Player p;
                try {
                    
                    p = new Player(in);
                    p.play();
                    
                } catch (JavaLayerException ex) {
                    Logger.getLogger(Trilha.class.getName()).log(Level.SEVERE, null, ex);
                }
             i++;
            }
    }
    
}}
