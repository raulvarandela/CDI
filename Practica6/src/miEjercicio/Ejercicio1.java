package miEjercicio;//autor: Raúl Varandela Marra

class A {
    void EnterAndWait() {
        System.out.println("El hilo  " + Thread.currentThread().getId() + " se está iniciando");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("El hilo  " + Thread.currentThread().getId() + " está acabando");
    }
}

class B extends Thread {

    private A a;
    private Thread t;


    public  B(A a) {
        this.a = a;
        this.t = null;
    }

    @Override
    public void run() {
        synchronized (a) {
            while (!this.isInterrupted()) {
                    a.EnterAndWait();
                    t.notify();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    void setThread(Thread t) {
        this.t = t;
    }
}

public class Ejercicio1 {
    public static void main(String[] args) throws InterruptedException {
        A objetoCompartido = new A();
        int numeroThreads = 2;
        B[] threads = new B[numeroThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new B(objetoCompartido);
        }

        for (int i = 0; i < threads.length; i++) {
            if (i < threads.length - 1) {
                threads[i].setThread(threads[i + 1]);
            } else {
                threads[i].setThread(threads[0]);
            }
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        Thread.sleep(10000);
        for (int i = 0; i < threads.length; i++) {
            threads[i].interrupt();
        }

    }

}
