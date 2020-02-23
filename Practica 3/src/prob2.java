import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class prob2 {

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

        public void SimpleFilter(Matriz resultado) {// write sequential filter code here
            for (int fila = 0; fila < this.dimension; fila++) {
                for (int columna = 0; columna < this.dimension; columna++) {
                    int suma = 0;
                    int media = 0;
                    for (int i = fila - 1; i < fila + 2; i++) {
                        for (int j = columna - 1; j < columna + 2; j++) {
                            int valori = i;
                            int valorj = j;
                            if (i < 0) {
                                i = i * -1;
                            }
                            if (j < 0) {
                                j = j * -1;
                            }
                            if(i>= dimension){
                                i=i-dimension;
                            }
                            if(j>= dimension){
                                j=j-dimension;
                            }
                            if (i == fila && j == columna) ;
                            else suma += this.getMatrix()[i][j];

                            i = valori;
                            j = valorj;
                        }
                    }
                    media = suma / 8;
                   //  System.out.println("media para la pocicion: "+fila+columna+" es: "+media);
                    resultado.setMatrix(fila, columna, media);
                }
            }
        }

        public void Filter() throws InterruptedException {
            int casillas = this.dimension * this.dimension;
            final int NUMERO_THREADS = casillas;
            List<Thread> threadList = new ArrayList<Thread>(NUMERO_THREADS);
            for (int i = 0; i < this.dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    for (int k = 1; k <= NUMERO_THREADS; ++k) {
                        threadList.add(new MyThread(this, i, j));
                    }
                }
            }
            Iterator<Thread> l1 = threadList.iterator();
            while (l1.hasNext()) {
                l1.next().start();
            }

            while (l1.hasNext()) {
                Thread t = l1.next();
                t.join();
            }
        }

        public int[][] getMatrix() {
            return matrix;
        }

        public void setMatrix(int fila, int col, int valor) {
            this.matrix[fila][col] = valor;
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

    static class MyThread extends Thread {
        private Matriz A;
        int fila, columna;

        public MyThread(Matriz inicial, int fila, int columna) {
            this.A = inicial;
            this.fila = fila;
            this.columna = columna;
        }

        public void run() {
            int suma = 0;
            int media = 0;
            for (int i = fila - 1; i < fila + 1; i++) {
                for (int j = columna - 1; j < columna + 1; j++) {
                    int valori = i;
                    int valorj = j;
                    if (i < 0) {
                        i = i * -1;
                    }
                    if (j < 0) {
                        j = j * -1;
                    }
                    if (i == fila && j == columna) ;
                    else suma += A.getMatrix()[i][j];

                    i = valori;
                    j = valorj;
                }
            }
            media = suma / 8;
            // System.out.println("media para la pocicion: "+fila+columna+" es: "+media);
            A.setMatrix(fila, columna, media);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Matriz matriz = new Matriz(3);
        Matriz matriz2 = matriz;
        System.out.println("Matriz original: ");
        System.out.println(matriz.toString());
        matriz.SimpleFilter(matriz2);
        System.out.println("Matriz modificada: ");
        System.out.println(matriz2.toString());

    }
}