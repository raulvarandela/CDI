import java.io.File;
import java.util.concurrent.TimeUnit;

public class Prog10 {
    public static void main(String[] args) {
        FileSearch searcher = new FileSearch("C:\\Users\\Steve", "Aplicaciones quitadas.html"); //creo la clase la clase
        Thread thread = new Thread(searcher); //creo el hilo
        thread.start();//y lo inicio
        try {
            TimeUnit.SECONDS.sleep(5);//lo duermo 5 segundos, mientras tanto el programa busca los archivos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt(); //lo interrumpo
    }
}
