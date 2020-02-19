public class localthread3 {
    public static void main(String[] args) {
        Thread thread1 = new MiThread();
        Thread thread2 = new MiThread();
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }
    }
}

