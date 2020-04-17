import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Ejercicio1 {
    public static void main(String[] args) throws InterruptedException {// Object on which producer and consumer thread will operate
        Buffer buffer = new Buffer();// creating producer and consumer threads
        Producer p = new Producer(buffer);
        Consumer c = new Consumer(buffer);// starting producer and consumer
        p.start();
        c.start();
        p.join();
        c.join();
    }
}

class Buffer {
    private static final int CAPACITY = 10;
    private final Queue queue = new LinkedList<>();
    private final Random theRandom = new Random();
    int contador = 0;

    private final Lock aLock = new ReentrantLock();
    private boolean lleno = false; //para el productor
    private boolean vacio = true; //para el consumidor

    public synchronized void write() throws InterruptedException {
        while (lleno) {
            try {
                System.out.println("Estoy esperando... "+Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                System.err.println("Contenedor: Error en get -> " + e.getMessage());
            }
        }

        queue.add(theRandom.nextDouble());
        System.out.println("He añadido un elemento "+Thread.currentThread().getName());
        contador++;
        vacio = false;
        if (contador == CAPACITY) {
            lleno = true;
        }
        Thread.sleep((int) 1000);
        notify();

    }

    public synchronized void read() throws InterruptedException {
        while (vacio) {
            try {
                System.out.println("Estoy esperando... " +Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                System.err.println("Contenedor: Error en get -> " + e.getMessage());
            }
        }

        queue.remove();
        System.out.println("He quitado un elemento "+Thread.currentThread().getName());
        contador--;
        lleno = false;
        if (queue.isEmpty()) {
            vacio = true;
        }
        Thread.sleep((int) 1000);
        notify();

    }
}

class Producer extends Thread {
    Buffer pc;

    public Producer(Buffer sharedObject) {
        super("PRODUCER");
        this.pc = sharedObject;
    }

    public void run() {
        while (true) {
            try {
                pc.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    Buffer pc;

    public Consumer(Buffer sharedObject) {
        super("CONSUMER");
        this.pc = sharedObject;
    }

    public void run() {
        while (true) {
            try {
                pc.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}