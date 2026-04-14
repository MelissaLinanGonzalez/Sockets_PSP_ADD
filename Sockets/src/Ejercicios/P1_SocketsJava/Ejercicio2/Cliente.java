package Ejercicios.P1_SocketsJava.Ejercicio2;

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
        ){

            Scanner entrada = new Scanner(System.in);

            System.out.println("Conectado al servidor");

            while (true){

                String lineaServidor;

                while ((lineaServidor = in.readLine()) != null){
                    if (lineaServidor.equals("FIN_MENU")){
                        break;
                    }

                    System.out.println(lineaServidor);
                }

                System.out.print("Introduzca la opción deseada: ");
                String opcion = entrada.nextLine();
                out.println(opcion);

                if (opcion.equals("salir")){
                    System.out.println("Saliendo del programa...");
                    break;
                }

                String operacion;

                while ((operacion = in.readLine()) != null){

                    if (operacion.equals("FIN_OPERACION")){
                        break;
                    }

                    System.out.println(operacion);

                }
            }
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
