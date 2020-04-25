import java.util.LinkedList;
import java.util.Queue;

public class lab8P4 {
    public static void main(String args[]) {// creating buffer queue
        Q q = new Q();
        // starting consumer thread
        new Consumer(q);
        // starting producer thread
        new Producer(q);
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
    static MySemaphore semCon = new MySemaphore(0);
    static MySemaphore semProd = new MySemaphore(1);

    public Q() {
    }

    //  get an item from buffer:
    void get(int i) throws InterruptedException {
        semCon.down();
        System.out.println("He eliminado el elemento: " + i);
        queue.remove(i);
        semProd.up();
    }

    //  put an item into buffer:
    void put(int i) throws InterruptedException {
        semProd.down();
        System.out.println("He añadido el elemento: " + i);
        queue.add(i);
        semCon.up();
    }

}

