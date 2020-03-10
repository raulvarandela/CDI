import java.util.Random;


public class prob2 {

    static class MyThread extends Thread {
        private final Matriz A;
        private Matriz C;
        int rStart, rFinish;

        public MyThread(Matriz Abar, Matriz Cbar, int rStart, int rFinish) {
            this.A = Abar;
            this.C = Cbar;
            this.rStart = rStart;
            this.rFinish = rFinish;
        }

        public void run() {
            int[][] matA, matC;
            matA = this.A.getMatrix();
            matC = this.C.getMatrix();
            System.out.println("Printed from thread");
            int d = A.getDim();
            for (int i = 0; i < d; i++) {
                for (int j = this.rStart; j < this.rFinish; j++) {
                    int sumIJ = 0;
                    for (int q = -1; q <= 1; q++) {
                        for (int p = -1; p <= 1; p++) {
                            int c = i + q;
                            if (c < 0) c = -c;
                            if (c >= d) c = 2 * d - c - 1;
                            int r = j + p;
                            if (r < 0) r = -r;
                            if (r >= d) r = 2 * d - r - 1;
                            sumIJ += matA[c][r];
                        }
                    }
                    matC[i][j] = (int) (sumIJ / 9.);
                }
            }
            C.setMatrix(matC);
        }
    }


    static class Matriz {
        private final int dimension;
        private int[][] matrix;
        private Long tiempo = (long) 0;

        public Matriz(Integer d) {
            dimension = d;
            matrix = new int[dimension][dimension];
            Random rand = new Random();
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < d; j++) {
                    matrix[i][j] = rand.nextInt(10);
                }
            }
        }

        public static Matriz SimpleFilter(Matriz A) {// write sequential filter code here
            Matriz aux = new Matriz(A.dimension); //declaro una matriz auxiliar
            int d = A.dimension; //guardo la dimension
            int[][] matA = A.matrix; //guardo el contenido de la matriz
            int[][] matAux = aux.matrix; //guardo en contenido de la matriz aux
            System.out.println(A.toString()); //muestro por pantalla el contenido de la matriz original
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < d; j++) {
                    int sumIJ = 0; //variable para la suma
                    for (int q = -1; q <= 1; q++) { // desde -1 a 1 (3 posiciones) -1 0 1
                        for (int p = -1; p <= 1; p++) { // desde -1 a 1 (3 posiciones) -1 0 1
                            int c = i + q; //en c se suma i a q
                            if (c < 0) c = -c;// si c es negativo se pone positivo
                            if (c >= d) c = 2 * d - c - 1; //si se pasa de la dimensión o la iguala lo dejo en el borde
                            int r = j + p;
                            if (r < 0) r = -r; //si es negativo lo vuelvo positivo
                            if (r >= d) r = 2 * d - r - 1; //
                            sumIJ += matA[c][r]; //se guarda ese valor
                        }
                    }
                    matAux[i][j] = (int) (sumIJ / 9.); //se guarda el valor de la suma en esa posicion
                    System.out.printf(" %s ", matAux[i][j]); //salida por pantalla
                }
                System.out.println();
            }
            aux.setMatrix(matAux);
            return aux;
        }

        public static Matriz Filter(Matriz A, int numHilos) throws InterruptedException {// write the threaded version that calls a thread.
            Matriz aux = new Matriz(A.dimension);
            int div = (int) A.dimension / numHilos;
            int rStart = 0; //donde empieza el 1º hilo
            int rFinish = div - 1; //donde acaba cada hilo el 1º hilo
            MyThread[] mt = new MyThread[numHilos];
            for (int i = 0; i < numHilos; i++) {
                System.out.printf("%s %s %s\n", i, rStart, rFinish);
                mt[i] = new MyThread(A, aux, rStart, rFinish);
                mt[i].start();
                rStart = rFinish + 1; //donde empieza el hilo i
                rFinish += div; //donde acaba el hilo i
            }
            try {
                for (int i = 0; i < numHilos; i++) mt[i].join();
            } catch (InterruptedException e) {
                System.out.println("exception found");
            }
            return aux;
        }

        public int[][] getMatrix() {
            return matrix;
        }

        public void setMatrix(int[][] matrix) {
            this.matrix = matrix;
        }

        public int getDim() {
            return dimension;
        }

        public String toString() {
            String ret = "";
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) ret += matrix[i][j] + " ";
                ret += "\n";
            }
            return ret;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Matriz matriz = new Matriz(1000);
        //matriz.SimpleFilter(matriz);
        matriz.Filter(matriz,2);
        //System.out.printf(matriz.toString());

    }
}

