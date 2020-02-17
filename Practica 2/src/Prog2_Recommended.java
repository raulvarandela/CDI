import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.StrictMath.*;

public class Prog2_Recommended {
    public static void main(String[] args) {
        final int NUMBER_THREADS = 20;
        double a = Math.atan(12.3);
        List<Thread> threadList = new ArrayList<Thread>(NUMBER_THREADS);
        Iterator<Thread> l1 = threadList.iterator();
        while (l1.hasNext()) {
            l1.next().start();
        }

        for (int i = 0; i < 1000000; ++i) {
            double d = tan(atan(tan(atan(tan(atan(tan(atan(tan(atan(123456789.123456789))))))))));
            cbrt(d);
        }
        System.out.println("El programa ha terminado");
    }
}


class MyThread extends Thread {
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
// Code to calculate function
        System.out.println("Finish run"+((endTime-startTime)/1000)); // include info of time
    }
}
