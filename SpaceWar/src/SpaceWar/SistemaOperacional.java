package SpaceWar;

public class SistemaOperacional {
    private String os;
    private char[] arraySO;
    
    public String identificaSO(){
        //Retorna mac para Mac
        //Retorna win para outros *****IMPLEMENTAR*****

        os = System.getProperty("os.name");
        arraySO = os.toCharArray();
        
        if ((arraySO[0] == 'm' || arraySO[0] == 'M') && (arraySO[1] == 'a' || arraySO[1] == 'A') && 
                (arraySO[2] == 'c' || arraySO[0] == 'C')){

            return "mac"; 
        }else if ((arraySO[0] == 'w' || arraySO[0] == 'W') && (arraySO[1] == 'i' || arraySO[1] == 'I') && 
                (arraySO[2] == 'n' || arraySO[0] == 'N')){

            return "win"; 
        }
        return "outro"; //*****IMPLEMENTAR*****
    }
    
}