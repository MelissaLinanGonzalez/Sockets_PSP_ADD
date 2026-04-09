package Ejercicios.P1_SocketsJava.Ejercicio1;

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
            out.println("El usuario ha accedido al servidor");

            Scanner entrada = new Scanner(System.in);

            System.out.println("Cliente conectado al servidor");

            boolean loggeado = false;
            for (int i = 0; i < 3; i++) {
                System.out.print(in.readLine());
                String usuario = entrada.nextLine();
                out.println(usuario);
                String mensaje = in.readLine();
                if (mensaje.equals("Introduzca la contraseña: ")){
                    System.out.print(mensaje);
                    String password = entrada.nextLine();
                    out.println(password);
                    String menFinal = in.readLine();
                    if (menFinal.equals("FIN_LOGGIN")){
                        System.out.println("Usuario loggeado con éxito");
                        System.out.println(" ");
                        loggeado = true;
                        break;
                    } else {
                        System.out.println(menFinal);
                    }
                } else {
                    System.out.println(mensaje);
                }
            }

            if (loggeado){
                while (true){
                    String menu;

                    // Leemos el menú completo
                    while ((menu = in.readLine()) != null){
                        if (menu.equals("FIN_MENU")){
                            break;
                        }
                        System.out.println(menu);
                    }

                    // Seleccionamos la opción
                    System.out.print("Introduce la opción deseada: ");
                    String opcion = entrada.nextLine();
                    out.println(opcion);

                    if (opcion.equals("1")){
                        System.out.print("Introduce el primer numero para sumar: ");
                        String num1 = entrada.nextLine();
                        out.println(num1);
                        System.out.print("Introduce el segundo numero para sumar: ");
                        String num2 = entrada.nextLine();
                        out.println(num2);
                    }

                    if (opcion.equals("2")){
                        System.out.print("Introduce un texto para contar las vocales: ");
                        String texto = entrada.nextLine();
                        out.println(texto);
                    }

                    if (opcion.equals("3")){
                        System.out.print("Introduce un texto para invertirlo: ");
                        String texto = entrada.nextLine();
                        out.println(texto);
                    }

                    if (opcion.equals("4")){
                        System.out.print("Introduce un número para saber si es primo: ");
                        String num = entrada.nextLine();
                        out.println(num);
                    }

                    // Bucle hasta que finalice la operación seleccionada
                    String operacion;
                    while ((operacion = in.readLine()) != null){
                        if (operacion.equals("FIN_OPERACION")){
                            break;
                        }

                        System.out.println(operacion);
                        System.out.println(" ");
                    }

                    if (opcion.equals("0")){
                        System.out.println("Cliente desconectándose...");
                        break;
                    }

                }
            } else {
                System.out.println("Se han agotado los intentos. Cerrando conexión al servidor");
            }

        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
