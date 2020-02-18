package Ejercicio2;

import Ejercicio2.MyThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ejercicio2 {
    public static void main(String[] args) throws InterruptedException {
        final int NUMBER_OF_THREADS = 50;
        List<MyThread> threadList = new ArrayList<MyThread>(NUMBER_OF_THREADS);

        for (int i = 1; i <= NUMBER_OF_THREADS; ++i) {
            threadList.add(new MyThread("Hilo numero: " + i));  //meto en la lista los hilos
        }
        Iterator<MyThread> l1 = threadList.iterator();
        while (l1.hasNext()) {
            l1.next().start();
        }
    }


}
