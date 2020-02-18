class Prog8 {
    public static void main(String[] args) throws InterruptedException {
        // Thread priority infomation
        System.out.printf("Minimum Priority: %s\n", Thread.MIN_PRIORITY);
        System.out.printf("Normal Priority: %s\n", Thread.NORM_PRIORITY);
        System.out.printf("Maximun Priority: %s\n", Thread.MAX_PRIORITY);
        Thread threads[]; //declaro un array de hilos
        Thread.State status[]; //declaro un array de prioridad

        // 10 threads do work, 5 with the max priority
        // 5 with the min priority
        threads = new Thread[10];//el tamaño van a ser 10 hilos
        status = new Thread.State[10];// tamaño 10, una prioridad por cada hilo
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Calculator());//en cada hilo voy a meter la calculadora, o eso creo
            if ((i % 2) == 0) {//a los hilos pares van a tener máxima prioridad
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else { //los hilos que no son pares, mínima prioridad
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("My Thread " + i); //y le pongo un nombre
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("Main : Status of Thread " + i + " : " + threads[i].getState()); //muestro por pantalla los hilos , el estado
            status[i] = threads[i].getState(); // almaceno el estado de cada hilo en el array de status
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start(); //inicio todos lo hilos
        }
        // Espera la finalización de los hilos. Guardamos el estado de
        // los hilos y solo escriben el estado si cambia
        boolean finish = false;
        while (!finish) {
            for (int i = 0; i < 10; i++) {
                if (threads[i].getState() != status[i]) {
                    writeThreadInfo(threads[i], status[i]);
                    status[i] = threads[i].getState();
                }
            }
            finish = true;
            for (int i = 0; i < 10; i++) {//recorre todos los hilos para saber si han acabado
                finish = finish && (threads[i].getState() == Thread.State.TERMINATED); //no se muy bien que hace esta linea, pero
                                                                                        //creo que si no han terminado los hilos no pone a true
                                                                                        // la variable finish
            }
        }
    }

    private static void writeThreadInfo(Thread thread, Thread.State state) { //metodo que muestra info sobre los hilos
        System.out.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
        System.out.printf("Main : Priority: %d\n", thread.getPriority());
        System.out.printf("Main : Old State: %s\n", state);
        System.out.printf("Main : New State: %s\n", thread.getState());
        System.out.printf("Main : ************************************\n");
    }
}
