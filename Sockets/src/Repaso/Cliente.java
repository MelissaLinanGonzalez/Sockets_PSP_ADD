package Repaso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ){
            Scanner entrada = new Scanner(System.in);
            System.out.println("Conectado al servidor");

            while (true){
                String lineaServidor;

                while ((lineaServidor = lector.readLine()) != null){
                    if (lineaServidor.equals("FIN_MENU")){
                        break;
                    };
                    System.out.println(lineaServidor);
                }
                System.out.print("Introduzca la opción deseada: ");

                String opcion = entrada.nextLine();
                escritor.println(opcion);

                if (opcion.equals("4")){
                    System.out.println("Saliendo del programa");
                    break;
                }

                if (opcion.equals("2")){
                    System.out.print("Introduzca la cantidad de asientos que desea reservar: ");
                    String cantidad = entrada.nextLine();
                    escritor.println(cantidad);
                }

                if (opcion.equals("3")){
                    System.out.print("Introduzca la cantidad de asientos que desea devolver: ");
                    String cantidadDevolucion = entrada.nextLine();
                    escritor.println(cantidadDevolucion);
                }

                String operacion;
                while ((operacion = lector.readLine()) != null){
                    if (operacion.equals("FIN_OPERACION")){
                        break;
                    }
                    System.out.println(operacion);
                }
            }




        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
