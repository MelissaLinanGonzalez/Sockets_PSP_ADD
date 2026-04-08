package Ejemplo1;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args){

        try (
                ServerSocket server =  new ServerSocket(1234);
                Socket cliente =  server.accept();
                BufferedReader in =  new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
        ) {


            // 1. Abrir el puerto 1234 y esperar conexión

            System.out.println("Esperando...");

            // BLOQUEO: Esperando clientes, cliente con new Socket.

            System.out.println("¡Cliente conectado!");

            // SUMA:NUMERO1:NUMERO2
            // RESTA:NUMERO1:NUMERO2
            // EXIT

            // 2. Leer mensaje del cliente


            String mensaje = in.readLine();
            String operacion = mensaje.split(":")[0];
            double num1 = Double.parseDouble(mensaje.split(":")[1]);
            double num2 = Double.parseDouble(mensaje.split(":")[2]);


            switch (operacion) {
                case "SUMA":
                    out.println("Suma: " + (num1 + num2));
                    break;
                case "RESTA":
                    out.println("Resta: " + (num1 - num2));
                    break;
                case "DIV":
                    out.println("Division: " + (num1 / num2));
                default:
                    out.println("ADIOS");
                    break;
            }
        }catch (ArithmeticException a) {
            System.out.println("División sobre 0: " + a.getMessage());
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
