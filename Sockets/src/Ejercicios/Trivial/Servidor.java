package Ejercicios.Trivial;


import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    public static ArrayList<ClienteHandler> clientes = new ArrayList<>();
    public static boolean juegoIniciado = false;
    public static ServerSocket server;

    public static void main(String[] args) {

        try {
            server = new ServerSocket(5000);
            System.out.println("Servidor concurrente escuchando en el puerto 5000...");
            System.out.println("Escribe 'START' para comenzar la partida en cualquier momento");

            EntradaServidor entradaServidor = new EntradaServidor();
            entradaServidor.start();


            while (!juegoIniciado) {

                try {
                    Socket cliente = server.accept();

                    if (clientes.size() < 2){
                        ClienteHandler cl = new ClienteHandler(cliente);
                        cl.start();

                        clientes.add(cl);
                    } else {
                        PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                        out.println("Sala llena. El servidor ha alcanzado su límete de aforo");
                        cliente.close();
                        System.out.println("Sala llena, inicie la partida");
                    }
                } catch (Exception e){

                    if (!juegoIniciado){
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }

            if (juegoIniciado){
                new GameManager(clientes).iniciarPartida();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}