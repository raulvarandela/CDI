import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Prog7 {
    public static void main(String[] args) {
        final int NUMERO_THREADS = 32;
        List<Thread> threadList = new ArrayList<Thread>(NUMERO_THREADS); //lista con los hilos
        for (int i = 1; i <= NUMERO_THREADS; ++i) { //bucle para meter los hilos en las listas
            threadList.add(new MyThread());
        }
        Iterator<Thread> l1 = threadList.iterator();
        while (l1.hasNext()) {
            l1.next().start(); //bucle para iniciar los hilos
        }
        while (l1.hasNext()) {
            Thread t = l1.next();
            try {
                t.join(); //espera a la ejecución del hilo anterior
                System.out.println("Terminado realmente " + t.getName());
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }
        System.out.println("El programa ha terminado");
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Mi nombre es: " + this.getName());
        System.out.println("Finalizado el proceso " + this.getName());
    }
}