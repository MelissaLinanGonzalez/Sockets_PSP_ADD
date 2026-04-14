package Ejercicios.P1_SocketsJava.Ejercicio3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket cliente;
    private ArticuloCompartido articuloCompartido;

    public ClientHandler(Socket socket, ArticuloCompartido articuloCompartido) {
        this.cliente = socket;
        this.articuloCompartido = articuloCompartido;
    }


    @Override
    public void run() {

        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
            String nombre = entrada.readLine();
            System.out.println("Cliente conectado: " + nombre);


            while (true){



                salida.println("""
                        *** Menú de selección ***
                        \t1. PUJAR.
                        \t2. CONSULTAR (ARTÍCULO).
                        \t3. PUJAMAX (VER PUJA MÁS ALTA).
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
                    case "PUJAR":
                        System.out.println("El cliente ha seleccionado PUJAR");
                        double pujaNueva = Double.parseDouble(entrada.readLine());
                        if (articuloCompartido.realizarPuja(pujaNueva, nombre)){
                            System.out.println("Nueva puja válida de: " + nombre + ": " + pujaNueva + "€");
                            salida.println("Puja realizada con éxito");
                        } else {
                            System.out.println("Intento de puja de: " + nombre + ": " + pujaNueva + "€. NO VÁLIDO");
                            salida.println("No se pudo realizar la puja ya que es inferior a la actual");
                        }
                        break;
                    case "CONSULTAR":
                        System.out.println("El cliente ha seleccionado LEER");
                        salida.println("Has seleccionado LEER");
                        salida.println("Arículo: " + articuloCompartido.getNombre());
                        break;
                    case "PUJAMAX":
                        System.out.println("El cliente ha seleccionado PUJAMAX");
                        salida.println("Has seleccionado PUJAMAX");
                        salida.println("El mejor postor es: " + articuloCompartido.getMejorPostor());
                        break;
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
