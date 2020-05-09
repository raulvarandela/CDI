import java.net.*;
import java.io.*;
import java.io.Serializable;
import java.util.*;

public class Lab10 {
    public static void main(String args[]) {
        new Server().start();
        new Client().start();

    }
}

class Server extends Thread {
    Socket socket = null;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

    public void run() {
        try {
            Matriz matriz = new Matriz(3);
            Matriz nueva = new Matriz(3);
            ServerSocket server = new ServerSocket(4444);

            LinkedList<Message> lista = new LinkedList<>();

            for (int i = 0; i < matriz.getDim(); i++) {
                for (int j = 0; j < matriz.getDim(); j++) {
                    lista.add(new Message(matriz, i, j));
                }
            }
            while (!lista.isEmpty()) {
                //enviar
                System.out.println("Server enviando");
                oos = new ObjectOutputStream(socket.getOutputStream());
                Message mensaje = lista.remove();
                oos.writeObject(mensaje);
                //recibir
                System.out.println("server recibiendo");
                socket = server.accept();
                ois = new ObjectInputStream(socket.getInputStream());
                Message recibido = (Message) ois.readObject(); //pendiente
                //guardar
                nueva.set(recibido.getCol(), recibido.getFila(), recibido.getNormal());
                ois.close();
                oos.close();
                socket.close();
            }
        } catch (Exception e) {
        }
    }
}

class Client extends Thread {
    InetAddress host = null;
    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    public void run() {
        try {

            host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), 4444);
            //recibir
            System.out.println("cliente recibiendo");
            ois = new ObjectInputStream(socket.getInputStream());
            Message recibido = (Message) ois.readObject();

            //operacion
            int d = recibido.getMatrix().getDim();
            int[][] matA = recibido.getNormal();
            Matriz matC = new Matriz(3);
            for (int i = recibido.getCol() - 1; i < recibido.getCol() + 1; i++) {
                for (int j = recibido.getFila() - 1; j < recibido.getFila() + 1; j++) {
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
                    matC.set2(i, j, (int) (sumIJ / 9.));
                }
            }

            //enviar
            System.out.println("cliente enviando");
            oos = new ObjectOutputStream(socket.getOutputStream());
            Message mensaje = new Message(matC, recibido.getCol(), recibido.getFila());
            oos.writeObject(mensaje);


            ois.close();
            oos.close();
            socket.close();

        } catch (Exception e) {
        }
    }
}

class Message implements Serializable {
    private Matriz matrix;
    private int col;
    private int fila;

    public Message(Matriz matrix, int col, int fila) {
        this.matrix = matrix;
        this.col = col;
        this.fila = fila;
    }

    public Matriz getMatrix() {
        return matrix;
    }

    public int[][] getNormal() {
        return matrix.getMatrix();
    }

    public void setMatrix(Matriz matrix) {
        this.matrix = matrix;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }
}

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


    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getDim() {
        return dimension;
    }

    public void set(int col, int fila, int[][] matrix) {
        this.matrix[col][fila] = matrix[col][fila];
    }

    public void set2(int col, int fila, int valor) {
        this.matrix[col][fila] = valor;
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