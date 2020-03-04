import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


class Matriz {
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

    public static Matriz SimpleFilter(Matriz A) {
        Matriz aux = new Matriz(A.dimension);
        int d = A.dimension;
        int[][] matA = A.matrix;
        int[][] matAux = aux.matrix;//System.out.println(A.toString());
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
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
                matAux[i][j] = (int) (sumIJ / 9.);
                System.out.printf(" %s ", matAux[i][j]);
            }
            System.out.println();
        }
        aux.setMatrix(matAux);
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

class MyThread extends Thread {
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

    public static Matriz FilterThreaded(Matriz A, int numHilos) throws InterruptedException {
        Matriz aux = new Matriz(A.dimension);
        int div = (int) A.dimension / numHilos;
        int rStart = 0;
        int rFinish = div - 1;
        MyThread[] mt = new MyThread[numHilos];
        for (int i = 0; i < numHilos; i++) {
            System.out.printf("%s %s %s\n", i, rStart, rFinish);
            mt[i] = new MyThread(A, aux, rStart, rFinish);
            mt[i].start();
            rStart = rFinish + 1;
            rFinish += div;
        }
        try {
            for (int i = 0; i < numHilos; i++) mt[i].join();
        } catch (InterruptedException e) {
            System.out.println("exception found");
        }
        return aux;
    }