public class solProblema6 {
    public static void main(String[] args) {
        int MaxCount = 20;
        int sTurn = 5;
        int Nthreads = 5;
        A objetoA = new A(MaxCount, sTurn, Nthreads);
        int nh = Nthreads;
        B bs[] = new B[nh];
        Thread ts[] = new Thread[nh];
        long startTime = System.nanoTime();
        for (int i = 0; i < nh; i++) {
            int sig = i + 1;
            if (sig == bs.length) sig = 0;
            bs[i] = new B(i, objetoA, nh, sig, bs);//ts[i] = new Thread(bs[i]);
            bs[i].start();
        }
        synchronized (bs[sTurn]) {
            bs[sTurn].notify();
        }
        while (!objetoA.fin()) ;
        try {
            for (int i = 0; i < nh; i++) {
                ts[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println((float) estimatedTime / (1.e6));
    }
}

class A {
    volatile private int Ncurrent;
    private int turn, Nthreads;
    boolean done = false;

    public A(int nc, int tu, int nt) {
        Ncurrent = nc;
        turn = tu;
        Nthreads = nt;
    }

    synchronized public void EnterAndWait(int nh) {
        try {//System.out.println("Start Thread " + nh + " Ncurrent=" + Ncurrent);//Thread.sleep((int)(Math.random() * 100));
            Thread.sleep(1);//System.out.println("Finish Thread " + nh + " Ncurrent" + Ncurrent);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean fin() {
        return Ncurrent <= 0;
    }

    public int getTurn() {
        return turn;
    }

    public int getNc() {
        return Ncurrent;
    }

    public void setTurn(int tu) {
        this.turn = tu;
    }

    public void setNc(int nc) {
        this.Ncurrent = nc;
    }

    public void setDone(boolean d) {
        this.done = d;
    }

    public boolean getDone() {
        return this.done;
    }
}

class B extends Thread {
    A a;
    B[] bs;
    int nh;
    private int turn;
    volatile private int Ncurrent;
    private int Nthreads;
    int sig;
    boolean done = false;

    B(int nh, A a, int nt, int s, B[] bk) {
        this.nh = nh;
        this.a = a;
        this.Nthreads = nt;
        this.sig = s;
        this.bs = bk;
    }

    public void run() {
        synchronized (this) {
            try {
                wait();
                Ncurrent = a.getNc();
                boolean flag = false;
                boolean done = false;

                while (!flag) {
                    if (a.getDone()) {
                        synchronized (bs[sig]) {
                            bs[sig].notify();
                        }
                        flag = true;
                    } else {
                        if (nh != sig) {
                            synchronized (bs[sig]) {
                                a.EnterAndWait(nh);
                                a.setNc(a.getNc() - 1);
                                if (a.getNc() < 0) {
                                    done = true;
                                    a.setDone(done);
                                }
                                bs[sig].notify();
                            }
                            wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
