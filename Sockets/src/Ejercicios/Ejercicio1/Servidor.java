package Ejercicios.Ejercicio1;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args){
        System.out.println("Esperando...");
        try (
                ServerSocket server =  new ServerSocket(1234);
                Socket cliente =  server.accept();
                BufferedReader in =  new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
        ) {
            String operacion;

            while ((operacion = in.readLine()) != null) {

                System.out.println("Comando recibido: " + operacion);

                if (operacion.equals("EXIT")) {
                    out.println("Saliendo del servidor. ¡Adiós!");
                    break;
                }


                String cadena = null;
                switch (operacion) {
                    case "CIFRAR":
                    case "DESCIFRAR":
                        out.println("Pasame la cadena");
                        cadena = in.readLine();
                        out.println(cifrar_descifrar(cadena, operacion, 3));
                        break;
                    default:
                        out.println("Operación no permitida");
                }


            }

        }catch (ArithmeticException a) {
            System.out.println("División sobre 0: " + a.getMessage());
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String cifrar_descifrar(String cadena, String operacion, int desplazamiento){
        String resultado = "";
        int rango = 'z' - 'a' + 1;
        char c;
        switch (operacion){
            case "CIFRAR":

                for (int i = 0; i < cadena.length(); i++){
                    c = cadena.charAt(i);

                    if (c >= 'a' && c <= 'z'){
                        resultado += (char) (((c - 'a') + desplazamiento) % rango + 'a');
                    } else if (c >= 'A' && c <= 'Z') {
                        resultado += (char) (((c - 'A') + desplazamiento) % rango + 'A');
                    } else {
                        resultado += c;
                    }
                }
                break;
            case "DESCIFRAR":
                //resultado = cadena;
                for (int i = 0; i < cadena.length(); i++){
                    c = cadena.charAt(i);

                    if (c > 'a' && c <= 'z'){
                        resultado += (char) (((c - 'a') - desplazamiento + rango) % rango + 'a');
                    } else if (c >= 'A' && c <= 'Z') {
                        resultado += (char) (((c - 'A') - desplazamiento + rango) % rango + 'A');
                    } else {
                        resultado += c;
                    }
                }
                break;
        }

        return resultado;
    }
}
