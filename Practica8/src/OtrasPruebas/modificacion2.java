package OtrasPruebas;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class modificacion2 {
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

     static MySemaphore semCon = new MySemaphore(0);
     static MySemaphore semProd = new MySemaphore(1);
   /* static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);*/

    public synchronized void write() throws InterruptedException {
         semProd.down();
        //semProd.acquire();
        queue.add(theRandom.nextDouble());
        System.out.println("He añadido un elemento " + Thread.currentThread().getName());
        semCon.up();
        //semCon.release();
    }

    public synchronized void read() throws InterruptedException {
         semCon.down();
        //semCon.acquire();
        queue.remove();
        System.out.println("He quitado un elemento " + Thread.currentThread().getName());
        semCon.down();
        //semProd.release();

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

class MySemaphore {
    private int value;

    public MySemaphore(int initialValue) {
        value = initialValue;
    }

    public MySemaphore() {
        this(0);
    }

    public void down() throws InterruptedException {
        synchronized (this) {
            while (value == 0) this.wait();
            value--;
        }
    }

    public void up() {
        synchronized (this) {
            value++;
            this.notify();
        }
    }
}