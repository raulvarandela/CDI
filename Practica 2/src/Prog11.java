import java.util.concurrent.TimeUnit;
import java.util.Date;

class UnsafeTask implements Runnable {
    private Date startDate; //declaro una variable de tipo date

    @Override
    public void run() {
        startDate = new Date(); //creo la variable
        System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread().getId(), startDate); // fecha el inicio del hilo
        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10)); //esto lo que hace es esperar,creo para que así por medio se
                                                                            //se puedan ejecuatar otros hilos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread Finished: %s : %s\n", Thread.currentThread().getId(), startDate); //fecha cuando acaba el hilo
    }
}

public class Prog11 {

    public static void main(String[] args) {
        UnsafeTask task = new UnsafeTask(); //creo la clase
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task); //creo 10 hilos para esa clase
            thread.start(); //inicio los hilos
            try {
                TimeUnit.SECONDS.sleep(2);//duermo 2 segundos la ejecución del bucle
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
//la problemática aquí es que las variables cambian de valor debido es porque usamos la misma clase la cual implemeta Runnable