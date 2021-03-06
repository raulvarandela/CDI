class prog11 {
    //solucion de porque peta
    static class TransThread extends Thread {
        private FinTrans ft;

        TransThread(FinTrans ft, String name) {
            super(name); // Save thread's name
            this.ft = ft; // Save reference to financial transaction object
        }

        public void run() {
            for (int i = 0; i < 100; i++) {
                if (getName().equals("Deposit")) {
                    synchronized (ft) { //uso de monitores y candados
                        ft.transName = "Deposit";
                        try {
                            Thread.sleep((int) (Math.random() * 1000));
                        } catch (InterruptedException e) {
                        }
                        ft.amount = 2000.0;
                        System.out.println(ft.transName + " " + ft.amount);
                    }
                } else {
                    synchronized (ft) {
                        ft.transName = "Withdrawal";
                        try {
                            Thread.sleep((int) (Math.random() * 1000));
                        } catch (InterruptedException e) {
                        }
                        ft.amount = 250.0;
                        System.out.println(ft.transName + " " + ft.amount);
                    }
                }
            }
        }
    }




    public static void main(String[] args) {
        FinTrans ft = new FinTrans();
        TransThread tt1 = new TransThread(ft, "Deposit"); //en ambos casos es el mismo objeto
        TransThread tt2 = new TransThread(ft, "Withdrawal");
        tt1.start();
        tt2.start();
    }


    static class FinTrans {
        public static String transName;
        public static double amount;
    }
}
