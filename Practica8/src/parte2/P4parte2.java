package parte2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class P4parte2 {
    public static void main(String args[]) {
        // creating buffer queue
        Q q = new Q();
        // starting consumer thread
        new Consumer(q);
        // starting producer thread
        new Producer(q);
    }
}



class Producer implements Runnable {
    Q q;

    Producer(Q q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    public void run() {
        for (int i = 0; i < 5; i++) // producer put items
        {
            try {
                q.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    Q q;

    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    public void run() {
        for (int i = 0; i < 5; i++) // producer put items
        {
            try {
                q.get(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Q {
    // semCon initialized with 0 permits
    private final Queue queue = new LinkedList<>();
    static Semaphore semCon = new Semaphore(0);
    static Semaphore  semProd = new Semaphore(1);

    public Q() {
    }

    //  get an item from buffer:
    void get(int i) throws InterruptedException {
        semCon.acquire();
        System.out.println("He eliminado el elemento: " + i);
        queue.remove(i);
        Thread.sleep((int) 1000);
        semProd.release();
    }

    //  put an item into buffer:
    void put(int i) throws InterruptedException {
        semProd.acquire();
        System.out.println("He añadido el elemento: " + i);
        queue.add(i);
        Thread.sleep((int) 1000);
        semCon.release();
    }

}

