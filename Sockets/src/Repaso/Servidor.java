package Repaso;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Servidor escuchando en el puerto 5000");
            VueloCompartido vueloCompartido = new VueloCompartido(50);

            while (true){
                Socket cliente = server.accept();
                new ClienteHandler(cliente, vueloCompartido).start();
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
