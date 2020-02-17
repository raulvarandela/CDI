package Ejercicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ejercicio1c {
    public static void main(String[] args) throws InterruptedException {
        final int NUMBER_OF_THREADS = 32;
        List<MyThread> threadList = new ArrayList<MyThread>(NUMBER_OF_THREADS);
        for (int i = 1; i <= NUMBER_OF_THREADS; ++i) {
            threadList.add(new MyThread("Hilo numero: " + i));  //meto en la lista los hilos
        }

        Iterator<MyThread> l1 = threadList.iterator();
        while (l1.hasNext()) {
            l1.next().start();
        }

        for (int i = 0; i < NUMBER_OF_THREADS; ++i){
            threadList.get(i).join();

        }


        System.out.println("El programa ha terminado");


    }
}
