import static java.lang.Thread.sleep;

class TstLocal {
    public static void main(String[] args) { //usando local thrad el problema está corregido
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
   // private Integer miSuma=0;
   private static ThreadLocal<Integer> miSuma = new ThreadLocal<Integer>() { //esto lo que me hace es que en cada hilo haya una variable local
       @Override protected Integer initialValue() {
           return 0;
       }
   };
    @Override
    public void run() {
        for(int i =0; i<10;i++){
            System.out.println("Started: " +i+  " resultado de la suma: "+miSuma.get());
            try {
                sleep(1000);
                miSuma.set(miSuma.get()+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish: " +  " resultado de la suma: "+miSuma.get());
    }
}