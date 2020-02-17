import java.util.concurrent.TimeUnit;
import java.util.Date;

// Class that shows the usage of ThreadLocal variables to share data between
// Thread objects
class SafeTask implements Runnable {
    // ThreadLocal shared between the Thread objects
    private static ThreadLocal<Date> startDate = new ThreadLocal<Date>() { //esto lo que me hace es que en cada hilo haya una variable local
        protected Date initialValue() {
            return new Date();
        }
    };

    @Override
    public void run() {
// Writes the start date
        System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread().getId(), startDate.get());//fecha al inicio del hilo
        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));//espera un tiempo para que otros hilos se ejecuten por medio y asi
                                                                        // demostrar que las variables no cambian de valor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
// Writes the start date
        System.out.printf("Thread Finished: %s : %s\n", Thread.currentThread().getId(), startDate.get());//fecha al final del hilo
    }
}

public class Prog12 {
    public static void main(String[] args) {
// Creates a task
        SafeTask task = new SafeTask(); //creo la clase
// Creates and start three Thread objects for that Task
        for (int i = 0; i < 2 * Runtime.getRuntime().availableProcessors(); i++) {
            Thread thread = new Thread(task); //varios hilos de una misma clase
            try {
                TimeUnit.SECONDS.sleep(2); //espero 2 segundos hasta iniciar el siguiente hilo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.start();//inicio el hilo
        }
    }
}

