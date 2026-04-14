package Ejercicios.P1_SocketsJava.Ejercicio2;


import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Servidor concurrente escuchando en el puerto 5000...");
            ContadorCompartido contador = new ContadorCompartido();
            while (true) {

                Socket cliente = server.accept();
                System.out.println("¡Nuevo cliente conectado desde " + cliente.getInetAddress() + "!");

                // Iniciamos el hilo sin bloquear el servidor
                new ClienteHandler(cliente, contador).start();
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
