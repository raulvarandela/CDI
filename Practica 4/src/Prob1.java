import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Prob1 {

    static class Contador {
        private volatile int acumulador;

        public Contador() {
            acumulador = 0;
        }

        public synchronized int incrementar() {// add code

            return ++acumulador;
        }

        public int getAcumulador() {// add codde
            return acumulador;
        }
    }

    static class MyThread extends Thread {
        private int numHilo;
        private Contador counter;

        public MyThread(int numHilo, Contador counter) {
            this.numHilo = numHilo;
            this.counter = counter;
        }

        public void run() {
            try {
                int num_veces = (int) (Math.random() * 10);
                for (int i = 0; i < 20; i++) {
                    //synchronized (counter) {
                        //counter.incrementar();
                        System.out.println("Numero de hilo: " + this.numHilo + " valor del contador: " + counter.incrementar());
                        Thread.sleep((int) (Math.random() * 10));
                    //}
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Contador a = new Contador();
        int numeroHilos = 15;
        ArrayList<MyThread> lista = new ArrayList<>(numeroHilos);
        for (int i =0; i<numeroHilos;i++){
            lista.add(new MyThread(i,a));
        }

        Iterator<MyThread> it = lista.iterator();
        while (it.hasNext()){
            it.next().start();
        }

        Iterator<MyThread> it2 = lista.iterator();
        while (it2.hasNext()){
            it2.next().join();
        }
        System.out.println("salida: " +a.getAcumulador());

    }
}
