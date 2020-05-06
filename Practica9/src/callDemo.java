import java.util.concurrent.*;

public class callDemo {
    public static void main(String args[]) {
        ExecutorService es = Executors.newFixedThreadPool(3); //un pool con 3 hilos
        Future<Integer> f;
        Future<Double> f2;
        Future<Integer> f3;
        System.out.println("Starting");
        f = es.submit(new Sum(10)); //Se "ejecuta"
        f2 = es.submit(new Hypot(3, 4));
        f3 = es.submit(new Factorial(5));
        try {
            System.out.println(f.get()); //obtengo el valor
            System.out.println(f2.get());
            System.out.println(f3.get());
        } catch (InterruptedException exc) {
            System.out.println(exc);
        } catch (ExecutionException exc) {
            System.out.println(exc);
        }
        es.shutdown();//y lo apago
        System.out.println("Done");
    }
}

class Sum implements Callable<Integer> {
    int stop;

    public Sum(int v) {
        stop = v;
    }

    public Integer call() {
        int sum = 0;
        for (int i = 1; i <= stop; i++) {
            sum += i;
        }
        return sum;
    }
}

class Hypot implements Callable<Double> {
    double side1, side2;

    public Hypot(double s1, double s2) {
        side1 = s1;
        side2 = s2;
    }

    public Double call() {
        return Math.sqrt(side1 * side1 + side2 * side2);
    }
}

class Factorial implements Callable<Integer> {
    int stop;

    public Factorial(int v) {
        stop = v;
    }

    public Integer call() {
        int fact = 1;
        for (int i = 2; i <= stop; i++) {
            fact *= i;
        }
        return fact;
    }
}