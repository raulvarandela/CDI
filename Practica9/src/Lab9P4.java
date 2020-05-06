import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lab9P4 {
    public static void main(String args[]) {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Random r = new Random();


        System.out.println("--------------------------------------------------------------------------");


        es.execute(new factorial(r.nextInt(15)));
        es.execute(new factorial(r.nextInt(15)));
        es.execute(new factorial(r.nextInt(15)));
        es.execute(new factorial(r.nextInt(15)));

        es.shutdown();


    }
}

class factorial implements Runnable {
    int numero;
    int result;

    public factorial(int numero) {
        this.numero = numero;
        this.result = 1;
    }

    @Override
    public void run() {
        if ((numero == 0) || (numero == 1)) {
            result = 1;
        } else {
            for (int i = 2; i <= numero; i++) {
                result *= i;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("El numero es: " + numero);
        System.out.println("Resultado: " + result);
        System.out.println("--------------------------------------------------------------------------");
    }
}
