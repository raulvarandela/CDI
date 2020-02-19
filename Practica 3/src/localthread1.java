import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class localthread1 { //aray normal con extends thread
    public static void main(String[] args) {
        final int NUMERO_THREADS = 32;
        Thread[] threadList = new Thread[NUMERO_THREADS];
        //List<Thread> threadList = new ArrayList<Thread>(NUMERO_THREADS);
        for (int i = 0; i < NUMERO_THREADS; ++i) {
            threadList[i]=new MiThread();
        }

        for (int i = 0; i < NUMERO_THREADS; ++i) {
            threadList[i].start();
        }

        for (int i = 0; i < NUMERO_THREADS; ++i) {
            try {
                threadList[i].join();
                System.out.println("Terminado realmente " + threadList[i].getName());
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }
        System.out.println("El programa ha terminado");
    }
}

