import static java.lang.Thread.sleep;

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
    //private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
    private Integer miSuma=0;
    @Override
    public void run() {
        for(int i =0; i<10;i++){
            System.out.println("Started: " +i+  " resultado de la suma: "+miSuma);
            try {
                sleep(1000);
                miSuma+=i;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish: " +  " resultado de la suma: "+miSuma);
    }
}