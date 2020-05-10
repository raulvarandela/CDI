import java.net.*;
import java.io.*;
import java.io.Serializable;
import java.util.*;

public class Lab10 {
    static Message2 matrizOG;
    static Message2 matrizDestino;
    static boolean b = false;

    public static void main(String args[]) {
        matrizOG = new Message2(3);
        matrizDestino = new Message2(3);
        new Server().start();
        new Client().start();
    }


    static class Server extends Thread {
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        public void run() {
            try {
                ServerSocket server = new ServerSocket(4444);
                LinkedList<trozo> lista = new LinkedList<>();
                for (int i = 0; i < matrizOG.getDimension(); i++) {
                    for (int j = 0; j < matrizOG.getDimension(); j++) {
                        lista.add(new trozo(i, j));
                    }
                }
                //imprimimos la matriz original por pantalla
                System.out.println("Matriz original: ");
                System.out.println(matrizOG.toString());
                System.out.println("--------------------");

                while (!lista.isEmpty()) {
                    //declaraciones
                    socket = server.accept();
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());


                    //enviar
                    trozo mensaje = lista.remove();
                    if (lista.isEmpty()){
                        b = true;
                    }
                    oos.writeObject(mensaje);

                    //recibir

                    trozo recibido = (trozo) ois.readObject();
                    System.out.println("El servidor ha recibido: " + recibido.valor);
                    matrizDestino.matriz[recibido.col][recibido.fila] = recibido.valor;

                    //cerrar
                    ois.close();
                    oos.close();
                    socket.close();


                }
                System.out.println("Se ha acabado la lista");

                System.out.println("--------------------");
                System.out.println("Matriz resultado: ");
                System.out.println(matrizDestino.toString());

            } catch (Exception e) {
            }

        }
    }

    static class Client extends Thread {
        InetAddress host = null;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        public void run() {
            try {
                while (true) {
                    //declaraciones
                    host = InetAddress.getLocalHost();
                    socket = new Socket(host.getHostName(), 4444);
                    ois = new ObjectInputStream(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());

                    //recibido
                    trozo recibido = (trozo) ois.readObject();

                    System.out.println("El cliente ha recibido: " + recibido.toString());

                    //operaciones:
                    int d = matrizOG.dimension;
                    int[][] copia = matrizOG.matriz;
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
                                    sumIJ += copia[c][r];
                                }
                            }
                            recibido.valor = (int) (sumIJ / 9.);
                        }
                    }


                    // enviar
                    trozo mensaje = recibido;
                    oos.writeObject(mensaje);

                    //cerrar
                    ois.close();
                    oos.close();
                    socket.close();
                    if (b){
                        break;
                    }

                }
            } catch (Exception e) {
            }
        }
    }

    class Message implements Serializable {
        public String msg = null;
        public int code = 0;

        public Message(String msg, int code) {
            this.msg = msg;
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    static class Message2 implements Serializable {
        private int[][] matriz;
        private int dimension;

        public Message2(int d) {
            dimension = d;
            matriz = new int[dimension][dimension];
            Random rand = new Random();
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < d; j++) {
                    matriz[i][j] = rand.nextInt(10);
                }
            }
        }

        public int getDimension() {
            return dimension;
        }

        public void setDimension(int dimension) {
            this.dimension = dimension;
        }

        public int[][] getMatriz() {
            return matriz;
        }

        public void setMatriz(int[][] matriz) {
            this.matriz = matriz;
        }

        public String toString() {
            String ret = "";
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) ret += matriz[i][j] + " ";
                ret += "\n";
            }
            return ret;
        }
    }

    static class trozo implements Serializable {
        private int col;
        private int fila;
        private int valor;

        public trozo(int col, int fila) {
            this.col = col;
            this.fila = fila;
            this.valor = 0;
        }

        public int getCol() {
            return col;
        }

        public int getValor() {
            return valor;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }

        public void setCol(int col) {
            this.col = col;
        }

        @Override
        public String toString() {
            return "trozo{" +
                    "col=" + col +
                    ", fila=" + fila +
                    '}';
        }

        public int getFila() {
            return fila;
        }

        public void setFila(int fila) {
            this.fila = fila;
        }
    }
}