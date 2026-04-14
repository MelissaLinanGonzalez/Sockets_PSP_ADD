package PruebaEvaluable;

import java.io.*;
import java.net.*;

public class HiloAdmin extends Thread {
    private ServerSocket server;

    public HiloAdmin(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("-> Escribe START y pulsa Enter para arrancar la partida.");

            while (true) {
                String comando = teclado.readLine();
                if (comando != null && comando.equalsIgnoreCase("START")) {
                    Servidor.juegoIniciado = true;
                    // Al cerrar el server, el método server.accept() del hilo principal se rompe
                    // y así el juego puede continuar.
                    server.close(); 
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Cerrando puerto de admisiones...");
        }
    }
}