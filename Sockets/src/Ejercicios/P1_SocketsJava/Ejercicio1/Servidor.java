package Ejercicios.P1_SocketsJava.Ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    public static void main(String[] args) {

        System.out.println("Esperando...");
        try (
                ServerSocket server =  new ServerSocket(1234);
                Socket cliente =  server.accept();
                BufferedReader in =  new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
        )
        {
            System.out.println(in.readLine());

            // Inicio de sesión 2 oportunidades, a la tercera fallida se desconecta

            String usuario = "melissa";
            String password = "12345";
            boolean continuar = false;

            for (int i = 0; i < 3; i++) {

                // Pregunta el nombre del usuario y lo comprueba con el que tenemos establecido
                out.println("Introduzca el usuario: ");
                String usuarioConectado = in.readLine();
                if (usuarioConectado.equals(usuario)){
                    // Una vez que reconoce al usuario, pregunta la contraseña y la comprueba con la establecida
                    out.println("Introduzca la contraseña: ");
                    String passwordIntroducida = in.readLine();
                    // Si la contraseña es correcta, continúa mostrando el menú.
                    if (passwordIntroducida.equals(password)){
                        System.out.println("Usuario loggeado con éxito");
                        continuar = true;
                        out.println("FIN_LOGGIN");
                        break;
                    } else {
                        out.println("Contraseña incorrecta");
                    }
                } else {
                    out.println("Usuario no existente");
                }
            }

            while (continuar){

                out.println("""
                        *** Menú de conexión CLIENTE - SERVIDOR ***
                        \t1. Sumar
                        \t2. Contar vocales
                        \t3. Invertir texto
                        \t4. Primo
                        \t0. Cerrar sesión
                        FIN_MENU""");

                String opcion = in.readLine();

                switch (opcion){
                    case "1":
                        out.println("Opcion seleccionada 1");
                        System.out.println("El cliente ha seleccionado la opción 1");
                        int num1 = Integer.parseInt(in.readLine());
                        int num2 = Integer.parseInt(in.readLine());

                        out.println("La suma de " + num1 + " y " + num2 + " es: " + sumar(num1, num2));
                        break;
                    case "2":
                        out.println("Opcion seleccionada 2");
                        System.out.println("El cliente ha seleccionado la opción 2");
                        String texto = in.readLine();
                        out.println("Total de vocales: " + contarVocales(texto));
                        break;
                    case "3":
                        out.println("Opcion seleccionada 3");
                        System.out.println("El cliente ha seleccionado la opción 3");
                        String cadena = in.readLine();
                        out.println("La cadena invertida es: " + invertirTexto(cadena));
                        break;
                    case "4":
                        out.println("Opcion seleccionada 4");
                        System.out.println("El cliente ha seleccionado la opción 4");
                        int num = Integer.parseInt(in.readLine());
                        boolean primo = esPrimo(num);
                        if (primo){
                            out.println("El número introducido es PRIMO");
                        } else {
                            out.println("El número introducido NO es primo");
                        }
                        break;
                    case "0":
                        out.println("Saliendo del programa...");
                        System.out.println("El cliente ha seleccionado la opción 0");
                        continuar = false;
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

    public static int sumar (int num1, int num2){
        return num1 + num2;
    }

    public static int contarVocales (String texto){
        int totalVocales = 0;

        String textoMin = texto.toLowerCase();

        for (int i = 0; i < textoMin.length(); i++) {
            char c = textoMin.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
                totalVocales++;
            }
        }

        return totalVocales;
    }

    public static String invertirTexto (String texto){
        return new StringBuilder(texto).reverse().toString();
    }

    public static boolean esPrimo(int num){
        boolean primo = true;
        for (int i = 2; i < num; i++) {
            if ((num % i) == 0){
                primo = false;
                break;
            }
        }

        return primo;
    }
}
