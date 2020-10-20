
package watki_zegar;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Watki_zegar extends Thread {
    int sekunda = 0, minuta = 0, godzina = 0;
    
    public void run(){
        while(true){
            sekunda++;
            if(sekunda ==60){
                minuta++;
                sekunda = 0;
            }
            if(minuta == 60){
                godzina++;
                minuta=0;
            }
            System.out.println(godzina+":"+minuta+":"+sekunda);
            
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Watki_zegar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        Watki_zegar z = new Watki_zegar();
        z.start();
    }
    
}
