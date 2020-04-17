public class probTickTock {


    static class TickTock {
        String state = "tocked";

        synchronized void tick(boolean running) {
            if (!running) {   //stop clock
                state = "ticked";
                notify(); // notify any waiting threads
                return;
            }
            try {
                while (!state.equals("tocked"))
                    wait();// wait for tock to complete
            } catch (InterruptedException exc) {
                System.out.println("interrupted");
            }
            System.out.print("Tick... ");
            state = "ticked";
            notify();
        }

        synchronized void tock(boolean running) {
            if (!running) { //stop clock
                state = "tocked";
                notify(); // notify any waiting threads
                return;
            }
            try {
                while (!state.equals("ticked"))
                    wait();// wait for tock to complete
            } catch (InterruptedException exc) {
                System.out.println("interrupted");
            }
            System.out.println("Tock ");
            state = "tocked";
            notify();
        }
    }

    static class MyThread implements Runnable {
        Thread thrd;
        TickTock ttOb;// constructor of new thread

        public MyThread(String name, TickTock tt) {
            thrd = new Thread(this, name);
            ttOb = tt;
        }

        public void run() {
            if (thrd.getName().compareTo("Tick") == 0) {
                for (int i = 0; i <= 5; i++) ttOb.tick(true);
                ttOb.tick(false);
            } else {
                for (int i = 0; i <= 5; i++) ttOb.tock(true);
                ttOb.tock(false);
            }
        }
    }

    public static void main(String agrs[]) {
        TickTock tt = new TickTock();
        MyThread mt1 = new MyThread("Tick", tt);
        MyThread mt2 = new MyThread("Tock", tt);
        mt1.thrd.start();
        mt2.thrd.start();
        try {
            mt1.thrd.join();
            mt2.thrd.join();
        } catch (InterruptedException exc) {
            System.out.println("main thread interrupted");
        }
    }

}
