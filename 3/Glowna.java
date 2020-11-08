
package glowna;
import java.util.Random;

class Kelner extends Thread {
    static int KUCHNIA=1;
    static int WYDAJ=2;
    static int WYDAWANIE=3;
    static int WYDANE=4;
    static int ROZBITE_TALERZE=5;
    static int ODPOCZYNEK=1000;
    static int SILY=500;
    
    int numer;
    int sila;
    int stan;
    Kuchnia l;
    Random rand;
    
    public Kelner(int numer, int sila, Kuchnia l){
        this.numer=numer;
        this.sila=sila;
        this.stan=WYDAWANIE;
        this.l=l;
        rand=new Random();
    }
    
    public void run(){
        while(true){
            if(stan==KUCHNIA){
                if(rand.nextInt(2)==1){
                    stan=WYDAJ;
                    sila=ODPOCZYNEK;
                    System.out.println("Czy gotowe do wydania?, kelner "+numer);
                    stan=l.wydaj(numer);
                }else{
                    System.out.println("Oczekuje na wydanie");
                }
            }
            else if(stan==WYDAJ){
                System.out.println("Wydaje, kelner "+numer);
                stan=WYDAWANIE;
            }
            else if(stan==WYDAWANIE){
                sila-=rand.nextInt(500);
                System.out.println("Kelner "+numer+" w drodze");
                if(sila<=SILY){
                    stan=WYDANE;
                }
                else try{
                    sleep(rand.nextInt(1000));
                }
                catch (Exception e){}
                
            }
            else if(stan==WYDANE){
                System.out.println("Dostarczono do stolika "+numer+" ilość energii "+sila);
                stan=l.oddaj();
                if(stan==WYDANE){
                    sila-=rand.nextInt(500);
                    System.out.println("Zmęczenie "+sila);
                    if(sila<=0) stan=ROZBITE_TALERZE;
                }
            }
            else if(stan==ROZBITE_TALERZE){
                System.out.println("Rozbiły się talerze "+numer);
                l.zmniejsz();
            }
        }
    }
    
}

class Kuchnia {
    static int KUCHNIA=1;
    static int WYDAJ=2;
    static int WYDAWANIE=3;
    static int WYDANE=4;
    static int ROZBITE_TALERZE=5;
    
    int ilosc_talerzy;
    int ilosc_zajetych;
    int ilosc_kelnerow;
    
    Kuchnia(int ilosc_talerzy,int ilosc_kelnerow){
        this.ilosc_talerzy=ilosc_talerzy;
        this.ilosc_kelnerow=ilosc_kelnerow;
        this.ilosc_zajetych=0;
    }
    synchronized int wydaj(int numer){
        ilosc_zajetych--;
        System.out.println("Gotowość do wydania "+numer);
        return WYDAJ;
    }
    synchronized int oddaj(){
        try{
            Thread.currentThread().sleep(1000);
        }catch(Exception ie){
            
        }
        if(ilosc_zajetych<ilosc_talerzy){
            ilosc_zajetych++;
            System.out.println("Możesz wydać "+ilosc_zajetych);
            return KUCHNIA;
        }
        else{
            return WYDANE;
        }
    }
    synchronized void zmniejsz(){
        ilosc_kelnerow--;
        System.out.println("Kończę pracę");
        if(ilosc_kelnerow==ilosc_talerzy) System.out.println("Ilosc kelnerów jaka sama jak talerzy");
    }
}



public class Glowna {
    static int ilosc_kelnerow=5;
    static int ilosc_talerzy=10;
    static Kuchnia kuchnia;
    public Glowna(){ }
    
    public static void main(String[] args) {
        kuchnia=new Kuchnia(ilosc_talerzy, ilosc_kelnerow);
        for(int i=0;i<ilosc_kelnerow;i++){
            new Kelner(i,2000,kuchnia).start();
        }
    }
    
}
