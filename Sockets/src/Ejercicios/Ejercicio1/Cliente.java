package Ejercicios.Ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
            Scanner entrada = new Scanner(System.in);

//            out.println("CIFRAR");
//
//            String respuesta = in.readLine();
//            if (respuesta.equals("Pasame la cadena")){
//                out.println("MELISSA");
//                System.out.println("Resultado: " + in.readLine());
//            } else {
//                System.out.println(respuesta);
//            }
//
//            out.println("DESCIFRAR");
//            String respuesta2 = in.readLine();
//            if (respuesta2.equals("Pasame la cadena")){
//                out.println("PHOLVVD");
//                System.out.println("Resultado Descifrado: " + in.readLine());
//            } else {
//                System.out.println(respuesta2);
//            }
//
//            out.println("EXIT");
//            System.out.println("\nRespuesta del servidor al salir: " + in.readLine());

            while (true){
                String lineaServidor;

                while ((lineaServidor = in.readLine()) != null){
                    if (lineaServidor.equals("FIN_MENU")){
                        break;
                    }

                    System.out.println(lineaServidor);
                }

                System.out.print("Introduce la opción que desea: ");
                String opcion = entrada.nextLine();

                out.println(opcion);

                String operacion;

                if (opcion.equals("CIFRAR") || opcion.equals("DESCIFRAR")){
                    System.out.print("Introduce la cadena a cifrar: ");
                    String cadena = entrada.nextLine();
                    out.println(cadena);
                }


                while ((operacion = in.readLine()) != null){

                    if (operacion.equals("FIN_OPERACION")){
                        break;
                    }

                    System.out.println(operacion);

                }

                if (opcion.equals("EXIT")){
                    System.out.println("Cerrando programa...");
                    break;
                }
            }

        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
