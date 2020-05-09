package paquete;

import java.net.*;
import java.io.*;
import java.io.Serializable;

public class MySockets {
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
            ServerSocket server = new ServerSocket(4444);
            while (true) {
                socket = server.accept();
                ois = new ObjectInputStream(socket.getInputStream());
               //String message = (String) ois.readObject();
                Message recibido = (Message) ois.readObject();
                System.out.println("paquete.Server Received: " + recibido.getMsg());
                oos = new ObjectOutputStream(socket.getOutputStream());
                Message mensaje = new Message ("paquete.Server Reply",0);
                oos.writeObject(mensaje);
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
            for (int x = 0; x < 5; x++) {
                host = InetAddress.getLocalHost();
                socket = new Socket(host.getHostName(), 4444);
                oos = new ObjectOutputStream(socket.getOutputStream());
                Message mensaje = new Message ("paquete.Client paquete.Message ",x);
                oos.writeObject(mensaje);
                ois = new ObjectInputStream(socket.getInputStream());
                Message recibido = (Message) ois.readObject();
               // String message = (String) ois.readObject();
                System.out.println("paquete.Client Received: " + recibido.getMsg());
                ois.close();
                oos.close();
                socket.close();
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