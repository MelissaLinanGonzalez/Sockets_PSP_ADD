package Ejemplo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args){

        try (
                // 1. Conectarse al servidor
                Socket socket = new Socket("localhost", 1234);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // 2º parámetro es para el flush, hace
                // flush de forma automática despueś de un println.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {


            System.out.println("Conectado al servidor.");

            // 2. Enviar un mensaje

            //out.println("Hola desde el cliente!");
            out.println("DIV:5:2");
            String respuesta = in.readLine();
            if (respuesta == null){
                throw new ArithmeticException();
            } else {
                System.out.println(respuesta);
            }



        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
