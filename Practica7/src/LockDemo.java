import java.util.concurrent.locks.*;

public class LockDemo {
    public static void main(String args[]) {
        Shared sbar = new Shared();
        ReentrantLock lock = new ReentrantLock();
        new Thread(new LockThread(lock, sbar, "A")).start();
        new Thread(new LockThread(lock, sbar, "B")).start();
    }
}

// shared object:
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
            System.out.println("wating: " + name);
            // lock
            lock.lock();
            System.out.println("-----locked: " + name);
            shObj.count++;
            System.out.println(shObj.count);
            // sleep:
            System.out.println("sleep:" + name);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        } finally {
            // unlock
            System.out.println("-----unlock " + name);
            lock.unlock();
        }
    }
}


//---------------------------------------------------------------------
class BoundedBuffer {
    final Lock lock = new ReentrantLock(); //se crea el candado
    final Condition notFull = lock.newCondition(); //la condición
    final Condition notEmpty = lock.newCondition(); //otra condición
    final Object[] items = new Object[100]; //array?
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock(); //se bloquea
        try {
            while (count == items.length) notFull.await(); //mientras que el contenido no sea igual al tope, espero
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock(); //se desbloquea
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) notEmpty.await(); //mientras que esté vacio, se espera
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();//señala
            return x;
        } finally {
            lock.unlock();
        }
    }
}