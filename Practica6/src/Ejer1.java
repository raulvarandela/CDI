public class Ejer1 {


    static class A {
        volatile private int Ncurrent;
        private int thLast = 0;

        public A(int nc) {
            this.Ncurrent = nc;
        }

        synchronized public void EnterAndWait(int nh) {
            try {
                System.out.println("Start Thread " + nh + " Ncurrent=" + Ncurrent);
                Thread.sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        public boolean fin() {
            return Ncurrent <= 0;
        }

        public int getNc() {
            return Ncurrent;
        }

        public void setNc(int nc) {
            this.Ncurrent = nc;
        }

        public int getLastTh() {
            return thLast;
        }

        public void setLastTh(int thId) {
            this.thLast = thId;
        }
    }

    static class B extends Thread {
        A a;
        int thId;
        Thread siguienteHilo;

        B(int id, A a) {
            this.thId = id;
            this.a = a;
            siguienteHilo = null;
        }

        public void run() {
            while (!this.isInterrupted()) {
                synchronized (a) {
                    while (!a.fin()) {
                        System.out.println("thId=" + thId + " name=" + Thread.currentThread().getName() + " state=" + Thread.currentThread().getState() + " lastTh=" + a.getLastTh());
                        while (a.getLastTh() == thId) {
                            try {
                                System.out.println("sent waiting=" + this.thId);
                                a.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                        if (!a.fin()) {
                            a.EnterAndWait(thId);
                            a.setNc(a.getNc() - 1);
                            a.setLastTh(this.thId);
                            a.notifyAll();
                            this.siguienteHilo.notify();
                            System.out.println("---------------");
                        }
                    }
                }
            }
        }

        void setThread(Thread t) {
            this.siguienteHilo = t;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        A a = new A(400);
        B b0 = new B(0, a);
        B b1 = new B(1, a);
        b0.start();
        b1.start();
        b0.setThread(b1);
        b1.setThread(b0);
        Thread.sleep(10000);
        b0.interrupt();
        b1.interrupt();
    }
}


