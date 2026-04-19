package Repaso;

import Ejercicios.P1_SocketsJava.Ejercicio3.ClientHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread{
    private Socket cliente;
    private VueloCompartido vueloCompartido;

    public ClienteHandler(Socket socket, VueloCompartido vueloCompartido){
        this. cliente = socket;
        this.vueloCompartido = vueloCompartido;
    }

    @Override
    public void run() {
        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter escritor = new PrintWriter(cliente.getOutputStream(), true);
            System.out.println("Cliente conectado");

            while (true){

                escritor.println("""
                        *** Menú de selección ***
                        \t1. CONSULTAR
                        \t2. RESERVAR
                        \t3. DEVOLVER
                        \t4. Salir
                        Introduzca el número de su selección
                        FIN_MENU
                        """);

                String opcion = lector.readLine();

                if (opcion.equals("4")){
                    System.out.println("El cliente se ha desconectado");
                    break;
                }

                switch (opcion){
                    case "1":
                        System.out.println("El cliente ha seleccionado CONSULTAR");
                        escritor.println("El vuelo cuenta con: " + vueloCompartido.getAsientosLibres() + " asientos libres");
                        break;
                    case "2":
                        int cantidadAsientos = Integer.parseInt(lector.readLine());
                        boolean haReservado = vueloCompartido.reservar(cantidadAsientos);
                        if (haReservado){
                            escritor.println("Se ha reservado correctamente");
                            System.out.println("El cliente ha reservado: " + cantidadAsientos + "\nQuedan libres: " + vueloCompartido.getAsientosLibres());

                        } else {
                            escritor.println("No se ha podido reservar los asientos por falta de espacio");
                            System.out.println("El cliente ha intentando reservar " + cantidadAsientos + " y no se ha podido");
                        }
                        break;
                    case "3":
                        int cantidadADevolver = Integer.parseInt(lector.readLine());
                        boolean seHaDevuelto = vueloCompartido.devolver(cantidadADevolver);
                        if (seHaDevuelto){
                            escritor.println("Se han devuelto correctamente los asientos");
                            System.out.println("El cliente ha devuelto " + cantidadADevolver);
                        } else {
                            escritor.println("No se ha podido devolver la cantidad indicada, supera el número de asientos totales del vuelo");
                            System.out.println("El cliente ha intentado devolver una cantidad superior a aforo total del vuelo");
                        }
                        break;
                    default:
                        escritor.println("Seleccione una opción correcta");
                        break;
                }
                escritor.println("FIN_OPERACION");
            }

        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
