import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class prob1 {
    public static void main(String[] args) {
        final int NUMERO_THREADS = 3;
        List<Thread> threadList = new ArrayList<Thread>(NUMERO_THREADS);
        for (int i = 1; i <= NUMERO_THREADS; ++i) {
            threadList.add(new MiThread());
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

class MiThread extends Thread {
    private Integer miSuma=0;

    @Override
    public void run() {
        for(int i =0; i<10;i++){
            System.out.println("Started: " + this.getId() + " resultado de la suma: "+miSuma);
            try {
                sleep(1000);
                miSuma+=i;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish: " + this.getId()+ " resultado de la suma: "+miSuma);
    }
}