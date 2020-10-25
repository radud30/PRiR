
package watki_numeryczne;

import java.util.logging.Level;
import java.util.logging.Logger;


class M_prostokatow extends Thread{
    double suma; 
    
    private static double calka(double x) {
        return x*x; //wzór naszej całki
    }

    
    public void run() {
        double a, b, h, n;
        a = 1;
        b = 2;

        n = 3;

        h = (b - a) / n;

        suma = 0;
        for (int i = 1; i <= n; i++) {
        suma += calka(a + i * h);
        }
        suma *= h;

        System.out.println("PROSTOKATOW: "+suma);
 
}
}

class M_trapezow extends Thread{
    double suma; 
   
    private static double calka(double x) {
        return x*x; //wzór naszej całki
    }

    public void run(){
        double a,b,n,h;
        a = 2;
        b = 4;

        n = 3;

        h = (b - a) / n;
        suma = 0;

        for(int i = 1; i < n ; i++){
            suma = suma + calka(a + i * h); 
        }
        suma = h * (suma + ((calka(a) + calka(b))) / 2);
        System.out.println("TRAPEZY: " + suma);

    }    
}

class M_simpsona extends Thread{
    double suma; 
    
    private static double calka(double x) {
        return x*x; //wzór naszej całki
    }

    public void run(){     
        double a,b,n,h,ti;
        a = 4;
        b = 6;

        n = 3;

        h = (b - a) / n;
        suma = 0;
        ti = 0;

        for(int i = 1; i < n; i++){
            ti = ti + calka((a + i * h) - h / 2);
            suma = suma + calka(a + i * h);
        }
        ti = ti + calka(b - h / 2);
        suma = (calka(a) + calka(b) + 2 * suma + 4 * ti)*(h / 6);
        System.out.println("SIMSONA: "+suma);

    }    
}

public class Watki_numeryczne {
    public static void main(String[] args) {
        M_prostokatow p = new M_prostokatow();
        M_trapezow t = new M_trapezow();
        M_simpsona s = new M_simpsona();
        
        p.start();
        t.start();
        s.start();
        
        try {
            p.join();
            t.join();
            s.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Watki_numeryczne.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        System.out.println(p.suma + t.suma + s.suma);
    }
    
}
