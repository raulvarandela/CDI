import java.util.concurrent.locks.*;

public class LockDemo {
    public static void main(String args[]) {
        Shared sbar = new Shared();
        ReentrantLock lock = new ReentrantLock();
        new Thread(new LockThread(lock, sbar, "A")).start();
        new Thread(new LockThread(lock, sbar, "B")).start();
    }
}// shared object:

class Shared {
    static int count = 0;
}

class LockThread implements Runnable {
    String name;
    ReentrantLock lock;
    Shared shObj;

    LockThread(ReentrantLock lk, Shared sk, String n) {
        this.lock = lk;
        this.shObj = sk;
        this.name = n;
    }

    public void run() {
        System.out.println("Start: " + name);
        try {
            System.out.println("wating:" + name);
            // lock
            lock.lock();
            System.out.println("-----locked:" + name);
            shObj.count++;
            System.out.println(shObj.count);
            // sleep:
            System.out.println("sleep:" + name);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        } finally {
            // unlock
            System.out.println("-----unlock" + name);
            lock.unlock();
        }
    }
}
