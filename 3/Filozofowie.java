
package filozofowie;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


class Filozof_1 extends Thread {
    int MAX;
    Semaphore [] widelec;
    int mojNum;
    
    public Filozof_1 ( int nr, int aMAX, Semaphore [] awidelec ) {
        mojNum=nr ;
        MAX = aMAX;
        widelec = awidelec;
    }
    
    public void run ( ) {
        while ( true ) {
            System.out.println ( "Mysle ¦ " + mojNum) ;
            try {
                Thread.sleep ( ( long ) (7000* Math.random( ) ) ) ;
            }catch ( InterruptedException e ){
                
            }
            widelec [mojNum].acquireUninterruptibly ( ) ;
            widelec [ (mojNum+1)%MAX].acquireUninterruptibly ( ) ;
            System.out.println ( "Zaczyna jesc "+mojNum) ;
            try {
                Thread.sleep ( ( long ) (5000* Math.random( ) ) ) ;
            } catch ( InterruptedException e ) {
                
            }
            System.out.println ( "Konczy jesc "+mojNum) ;
            widelec [mojNum].release ( ) ;
            widelec [ (mojNum+1)%MAX].release ( ) ;
        }
    }
}

class Filozof_2 extends Thread {
    int MAX;
    Semaphore [] widelec;
    int mojNum;
    
    public Filozof_2 ( int nr, int aMAX, Semaphore [] awidelec ) {
        mojNum=nr ;
        MAX = aMAX;
        widelec = awidelec;
    }
    
    public void run ( ) {
        while ( true ) {
            System.out.println ( "Mysle ¦ " + mojNum) ;
            try {
                Thread.sleep ( ( long ) (5000 * Math.random( ) ) ) ;
            }catch ( InterruptedException e ) {
                
            }
            if (mojNum == 0) {
                widelec [ (mojNum+1)%MAX].acquireUninterruptibly ( ) ;
                widelec [mojNum].acquireUninterruptibly ( ) ;
            }else {
                widelec [mojNum].acquireUninterruptibly ( ) ;
                widelec [ (mojNum+1)%MAX].acquireUninterruptibly ( ) ;
            }
            System.out.println ( "Zaczyna jesc "+mojNum) ;
            try{
                Thread.sleep ( ( long ) (3000 * Math.random( ) ) ) ;
            } catch ( InterruptedException e ) {
                
            }
            System.out.println ( "Konczy jesc "+mojNum) ;
            widelec [mojNum].release ( ) ;
            widelec [ (mojNum+1)%MAX].release ( ) ;
        }
    }
}

class Filozof_3 extends Thread {
    int MAX;
    Semaphore [] widelec;
    int mojNum;
    Random losuj ;
    
    
    public Filozof_3 ( int nr, int aMAX, Semaphore [] awidelec ) {
        mojNum=nr ;
        MAX = aMAX;
        widelec = awidelec;
        losuj = new Random(mojNum) ;
    }
    
    public void run ( ) {
        while ( true ) {
            System.out.println ( "Mysle ¦ " + mojNum) ;
            try{
                Thread.sleep ( ( long ) (5000 * Math.random( ) ) ) ;
            }catch ( InterruptedException e ){
                
            }
            int strona = losuj.nextInt ( 2 ) ;
            boolean podnioslDwaWidelce = false ;
            do {
                if ( strona == 0) {
                    widelec [mojNum].acquireUninterruptibly ( ) ;
                    if( ! ( widelec [ (mojNum+1)%MAX].tryAcquire ( ) ) ) {
                        widelec[mojNum].release ( ) ;
                    } else {
                        podnioslDwaWidelce = true ;
                }
            }else {
                    widelec[(mojNum+1)%MAX].acquireUninterruptibly ( ) ;
                    if ( ! (widelec[mojNum].tryAcquire ( ) ) ) {
                        widelec[(mojNum+1)%MAX].release ( ) ;
                    }else {
                        podnioslDwaWidelce = true ;
                    }
                }
            }
            while ( podnioslDwaWidelce == false ) ;
            System.out.println ( "Zaczyna jesc "+mojNum) ;
            try {
                Thread.sleep ( ( long ) (3000 * Math.random( ) ) ) ;
            } catch ( InterruptedException e ) {
                
            }
            System.out.println ( "Konczy jesc "+mojNum) ;
            widelec [mojNum].release ( ) ;
            widelec [ (mojNum+1)%MAX].release ( ) ;
        }
    
    }
}



public class Filozofowie {
    public static void main(String[] args) {
        
        System.out.println("Wybierz liczbe filozofów 2-100");
        Scanner scan = new Scanner(System.in);
        try{
            int filozofowie = scan.nextInt();
            Semaphore[] widelec = new Semaphore[filozofowie];
            if(filozofowie <= 100 && filozofowie >= 2){
                try{
                    System.out.println("Podaj waraint programu który zostanie wykonany 1-3");
                    int wariant_programu = scan.nextInt();
                    if(wariant_programu <= 3 && wariant_programu >= 1){
                        if(wariant_programu ==1){
                            for ( int i =0; i<filozofowie; i++) {
                                widelec [ i ]=new Semaphore ( 1 ) ;
                            }
                            for ( int i =0; i<filozofowie; i++) {
                                new Filozof_1(i, filozofowie, widelec).start();
                            }
                        }
                        if(wariant_programu ==2){
                            for ( int i =0; i<filozofowie; i++) {
                                widelec [ i ]=new Semaphore ( 1 ) ;
                            }
                            for ( int i =0; i<filozofowie; i++) {
                                new Filozof_2(i, filozofowie, widelec).start();
                            }
                        }
                        if(wariant_programu ==3){
                            for ( int i =0; i<filozofowie; i++) {
                                widelec [ i ]=new Semaphore ( 1 ) ;
                            }
                            for ( int i =0; i<filozofowie; i++) {
                                new Filozof_3(i, filozofowie, widelec).start();
                            }
                        }
                        
                    }else{
                        System.out.println("Wybrano nr z poza przedziału");
                    }
                }catch ( InputMismatchException e ){
                    System.out.println("Podano nieprawidłową wartosć");
                }
                
                

            }else{
                System.out.println("Podano niepoprawną ilośc filozofów");
            }
            }catch ( InputMismatchException e ){
                System.out.println("Podano nieprawidłową wartosć");
        }        
    }
}
