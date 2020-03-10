import java.util.concurrent.TimeUnit;

public class prob1c {
    static class MiThread3 extends Thread {
        private int pi = 0;
        //private ThreadLocal<Integer> pi = new ThreadLocal<>();
        private boolean negative=true;

        @Override
        public void run() {
            while (true) {
                for (int i = 3; i < 100000; i += 2) {
                    if (negative) pi -= (1.0 / i);
                    else pi += (1.0 / i);
                    negative = !negative;
                }
                pi += 1.0;
                pi *= 4.0;
                System.out.println("pi: " + pi+" en el hilo: "+this.getId());
                if (isInterrupted()) {
                    System.out.printf("hilo " +this.getId()+" interrumpido\n");
                    return; //para cuando es interrumpido
                }
            }
        }
    }

    public static void main(String[] args) {
        MiThread3 thread = new MiThread3(); //creo el hilo
        MiThread3 thread2 = new MiThread3(); //creo el hilo
        MiThread3 thread3 = new MiThread3(); //creo el hilo
        thread.start();//y lo inicio
        thread2.start();//y lo inicio
        thread3.start();//y lo inicio
        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt(); //lo interrumpo
        thread2.interrupt();
        thread3.interrupt();
    }
}
