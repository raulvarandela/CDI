package Ejercicio2;

import static java.lang.StrictMath.*;

public class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < 1000000; ++i) {
            double d = tan(atan(tan(atan(tan(atan(tan(atan(tan(atan(123456789.123456789))))))))));
            cbrt(d);
        }
        long fin = System.currentTimeMillis();
        double tiempo = ((fin-inicio)/1000);
        System.out.println("Soy {" + Thread.currentThread().getName() + "} Tiempo transcurrido: "+tiempo);
    }


}
