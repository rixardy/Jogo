package spacewar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Trilha implements Runnable{

    private String som;
    
    public Trilha(String som){
        
        if(som.equals("Batalha")){
            this.som = "Batalha";
        }else if(som.equals("Introducao")){
            this.som = "Introducao";
        }else if(som.equals("Zerou")){
            this.som = "Zerou";
        }
    }
    
    @Override
    public void run() {
        
        int i = 0;
        
        while(i < 10){
            
            if (this.som.equals("Batalha")){
                
                InputStream in;
                
                try {
                    
                    in = new FileInputStream("src\\Imagens\\Batalha.mp3");
                    
                    Player p = new Player(in);
                    
                    p.play();
                }catch (FileNotFoundException | JavaLayerException e){
                }
            }else if(this.som.equals("Introducao")){
               
                InputStream in;
                
                try {
                    
                    in = new FileInputStream("src\\Imagens\\inicio2.mp3");
                    
                    Player p2 = new Player(in);
                    
                    p2.play();
            }catch (FileNotFoundException | JavaLayerException e){
                }
            }else if(this.som.equals("Zerou")){
               
                InputStream in;
                
                try {
                    
                    in = new FileInputStream("src\\Imagens\\ganhou.mp3");
                    
                    Player p3 = new Player(in);
                    
                    p3.play();
            }catch (FileNotFoundException | JavaLayerException e){
                }
            i++;
        }
    }
    
}}
