import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//practica3 ejercicio 1 apartado c
public class prob1b {
    public static void main(String[] args) {
        final int NUMERO_THREADS = 3;
        List<Thread> threadList = new ArrayList<Thread>(NUMERO_THREADS);
        for (int i = 1; i <= NUMERO_THREADS; ++i) {
            threadList.add(new MiThread2());
        }
        Iterator<Thread> l1 = threadList.iterator();
        while (l1.hasNext()) {
            l1.next().start();
        }
        while (l1.hasNext()) {
            Thread t = l1.next();
            try {
                t.join();
                System.out.println("Terminado realmente " + t.getName());
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }
        System.out.println("El programa ha terminado");
    }
}

class MiThread2 extends Thread { //en este caso uso hilos locales
    //private Integer miSuma=0;
    private static ThreadLocal<Integer> miSuma = new ThreadLocal<Integer>() { //esto lo que me hace es que en cada hilo haya una variable local
        @Override protected Integer initialValue() {
            return 0;
        }
    };
    @Override
    public void run() {
        for(int i =0; i<10;i++){
            System.out.println("Started: " + this.getId() + " resultado de la suma: "+miSuma.get());
            try {
                sleep(1000);
                miSuma.set(miSuma.get()+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish: " + this.getId()+ " resultado de la suma: "+miSuma.get());
    }
}