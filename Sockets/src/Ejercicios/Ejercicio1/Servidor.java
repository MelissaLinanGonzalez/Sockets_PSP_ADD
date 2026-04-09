package Ejercicios.Ejercicio1;

import javax.swing.*;
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

//            while ((operacion = in.readLine()) != null) {
//
//                System.out.println("Comando recibido: " + operacion);
//
//                if (operacion.equals("EXIT")) {
//                    out.println("Saliendo del servidor. ¡Adiós!");
//                    break;
//                }
//
//
//                String cadena = null;
//                switch (operacion) {
//                    case "CIFRAR":
//                    case "DESCIFRAR":
//                        out.println("Pasame la cadena");
//                        cadena = in.readLine();
//                        out.println(cifrar_descifrar(cadena, operacion, 3));
//                        break;
//                    default:
//                        out.println("Operación no permitida");
//                }
//
//
//            }


            while (true){
                out.println("""
                        *** Menú de selección ***
                        \t1. CIFRAR.
                        \t2. DESCIFRAR.
                        \t3. EXIT.
                        ES IMPORTANTE QUE LAS OPCIONES LAS ESCRIBAS TAL CUAL APARECEN.
                        FIN_MENU
                        """);

                String opcion = in.readLine();

                if (opcion.equals("EXIT")){
                    System.out.println("El cliente se ha desconectado");
                    break;
                }

                String cadena = in.readLine();
                switch (opcion){
                    case "CIFRAR":
                        System.out.println("El cliente ha seleccionado CIFRAR");
                        out.println("Has seleccionado CIFRAR ");
                        out.println("Cadena cifrada: " + cifrar_descifrar(cadena, opcion, 3));

                        break;
                    case "DESCIFRAR":
                        System.out.println("El cliente ha seleccionado DESCIFRAR");
                        out.println("Has seleccionado DESCIFRAR");
                        out.println("Cadena cifrada: " + cifrar_descifrar(cadena, opcion, 3));
                        break;
                    default:
                        out.println("Seleccione una opción correcta");
                        break;
                }

                out.println("FIN_OPERACION");
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
