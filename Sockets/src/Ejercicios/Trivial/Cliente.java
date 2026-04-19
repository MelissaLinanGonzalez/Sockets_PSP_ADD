package Ejercicios.Trivial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (
                // 1. Conectarse al servidor
                Socket socket = new Socket("localhost", 5000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // 2º parámetro es para el flush, hace
                // flush de forma automática despueś de un println.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner entrada = new Scanner(System.in);
        ) {

            System.out.print("Introduce tu nombre de usuario: ");
            String nick = entrada.nextLine();
            out.println(nick);

            new ReceptorMensajes(in).start();
            System.out.println("Cliente conectado, esperando respuesta del servidor");

            while (true){
                String respuesta = entrada.nextLine();
                out.println(respuesta);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
