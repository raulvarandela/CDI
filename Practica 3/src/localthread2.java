import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class localthread2 {
    public static void main(String[] args) {
        final int NUMERO_THREADS = 3;
        List<Thread> threadList = new ArrayList<Thread>(NUMERO_THREADS);
        for (int i = 1; i <= NUMERO_THREADS; ++i) {
            MyRunnable task = new MyRunnable();
            threadList.add(new Thread(task));
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
