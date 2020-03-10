import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class Prob1b {
    static class MiThread extends Thread {
        private int numHilo;
        private AtomicInteger counter;

        public MiThread(int numHilo, AtomicInteger counter) {
            this.numHilo = numHilo;
            this.counter = counter;
        }

        public void run() {
            try {
                int num_veces = (int) (Math.random() * 10);
                for (int i = 0; i < 20; i++) {
                    //synchronized (counter) {
                        counter.incrementAndGet();
                        System.out.println("Numero de hilo:" + this.numHilo + " valor del contador: " + counter.get());
                        Thread.sleep((int) (Math.random() * 10));
                   // }
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args)throws InterruptedException{
        AtomicInteger a = new AtomicInteger();
        int numeroHilos = 15;
        ArrayList<MiThread> lista = new ArrayList<>(numeroHilos);
        for (int i =0; i<numeroHilos;i++){
            lista.add(new MiThread(i,a));
        }

        Iterator<MiThread> it = lista.iterator();
        while (it.hasNext()){
            it.next().start();
        }
        Iterator<MiThread> it2 = lista.iterator();
        while (it2.hasNext()){
            it2.next().join();
        }
        System.out.println("salida: " +a.get());

    }
}
