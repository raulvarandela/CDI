
class TstLocal {
    public static void main(String[] args) {
        MyRunnable task = new MyRunnable();
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }
    }
}

class MyRunnable implements Runnable {
    ThreadLocal<Integer> tL = new ThreadLocal<Integer>(); //uso de hilos locales para que no haya interferencias con las variables

    @Override
    public void run() {
        
        for (int i = 0; i < 5; i++) {
            final int thread = i;
            tL.set(thread);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.printf("%s %d\n", Thread.currentThread().getName(), tL.get());
        }
    }
}