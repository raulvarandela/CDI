package Ejercicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ejercicio1 {
    public static void main(String[] args) throws InterruptedException {
        // MyThread myThread = new MyThread("myThread");
        // myThread.start();

        final int NUMBER_OF_THREADS = 32;
        List<MyThread> threadList = new ArrayList<MyThread>(NUMBER_OF_THREADS);
        for (int i = 1; i <= NUMBER_OF_THREADS; ++i) {
            threadList.add(new MyThread("Hilo numero: " + i));  //meto en la lista los hilos
        }

        Iterator<MyThread> l1 = threadList.iterator();
        while (l1.hasNext()) {
            l1.next().start();
        }// Code for testing if alive and join.
        System.out.println("El programa ha terminado");
    }
}

//en este ejemplo no hay orden ninguno

