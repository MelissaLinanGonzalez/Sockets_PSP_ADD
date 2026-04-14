package Ejercicios.P1_SocketsJava.Ejercicio3;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Servidor concurrente escuchando en el puerto 5000...");
            ArticuloCompartido articuloCompartido = new ArticuloCompartido("Seat Leon FR", 7000);
            while (true) {

                Socket cliente = server.accept();
                //System.out.println("¡Nuevo cliente conectado desde " + cliente.getInetAddress() + "!");

                // Iniciamos el hilo sin bloquear el servidor
                new ClientHandler(cliente, articuloCompartido).start();
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
