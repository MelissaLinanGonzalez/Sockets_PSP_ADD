package Ejercicios.P1_SocketsJava.Ejercicio2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread{
    private Socket cliente;
    private ContadorCompartido contador;

    public ClienteHandler(Socket socket, ContadorCompartido contador) {
        this.cliente = socket;
        this.contador = contador;
    }


    @Override
    public void run() {

        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);



            while (true){
                salida.println("""
                        *** Menú de selección ***
                        \t1. INCREMENTAR.
                        \t2. LEER.
                        \t3. RESETEAR.
                        \t4. salir
                        ES IMPORTANTE QUE LAS OPCIONES LAS ESCRIBAS TAL CUAL APARECEN.
                        FIN_MENU
                        """);

                String opcion = entrada.readLine();

                if (opcion.equals("salir")){
                    System.out.println("El cliente se ha desconectado");
                    break;
                }

                switch (opcion){
                    case "INCREMENTAR":
                        System.out.println("El cliente ha seleccionado INCREMENTAR");
                        contador.incrementar();
                        System.out.println("Contador incrementado: " + contador.getValor());
                        salida.println("Has seleccionado INCREMENTAR");
                        salida.println("Contador: " + contador.getValor());
                        break;
                    case "LEER":
                        System.out.println("El cliente ha seleccionado LEER");
                        salida.println("Has seleccionado LEER");
                        salida.println("Contador: " + contador.getValor());
                        break;
                    case "RESETEAR":
                        System.out.println("El cliente ha seleccionado RESETEAR");
                        salida.println("Has seleccionado RESETEAR");
                        contador.resetear();
                        salida.println("Contador reseteado: " + contador);
                    default:
                        salida.println("Seleccione una opción correcta");
                        break;
                }

                salida.println("FIN_OPERACION");
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
