import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//ejemplo en el que se comporta como quiero, el uso que se le deberia de dar sería con hilos locales
public class prob1 {

    public static void main(String[] args) {
        final int NUMERO_THREADS = 3;//el numero de hilos que voy a usar
        List<Thread> threadList = new ArrayList<Thread>(NUMERO_THREADS);//lista para almacenar los hilos
        for (int i = 1; i <= NUMERO_THREADS; ++i) { //bucle para añadir los hilos a la lista
            threadList.add(new MiThread()); //añado los hilos
        }
        Iterator<Thread> l1 = threadList.iterator(); //creo un iterador para iniciar los hilos
        while (l1.hasNext()) {
            l1.next().start(); //inicio los hilos
        }
        while (l1.hasNext()) {
            Thread t = l1.next();
            try {
                t.join(); // quiero conseguir que los hilos esperen
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