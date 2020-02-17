import java.util.concurrent.TimeUnit;

public class Prog9 {
    public static void main(String[] args) {
        Thread task = new PrimeGenerator(); //creo el hilo
        task.start();  //inicio el hilo
        try {
            TimeUnit.SECONDS.sleep(5);//espero 5 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.interrupt();//interrumpo
        System.out.printf("Main: Status of the Thread: %s\n", task.getState());//supongo que si el hilo está interrumpido
        System.out.printf("Main: isInterrupted: %s\n", task.isInterrupted());//seguirá vivo--> preguntar
        System.out.printf("Main: isAlive: %s\n", task.isAlive());
    }
}
