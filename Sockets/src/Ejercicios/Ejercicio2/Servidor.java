package Ejercicios.Ejercicio2;

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

            List<Integer> listaNumeros = new ArrayList<>();

            while (true){
                out.println("""
                        *** Menú de conexión CLIENTE - SERVIDOR ***
                        \t1. Agregar
                        \t2. Mostrar
                        \t3. Calcular media
                        \t4. Buscar máximo
                        \t5. Borrar lista
                        \t0. Cerrar sesión
                        FIN_MENU""");

                String eleccion = in.readLine();

                if (eleccion == null || eleccion.equals("0")){
                    System.out.println("El cliente ha cerrado la sesión");
                    break;
                }

                System.out.println("El cliente ha elegido la opción: " + eleccion);

                switch (eleccion){
                    case "1":
                        int num = Integer.parseInt(in.readLine());
                        out.println(agregar(num, listaNumeros));
                        break;
                    case "2":
                        out.println("La lista contiene los siguientes números: " + listaNumeros);
                        break;
                    case "3":
                        out.println("Media: " + calcularMedia(listaNumeros));
                        break;
                    case "4":
                        out.println("El número máximo de la lista es: " + buscarMaximo(listaNumeros));
                        break;
                    case "5":
                        listaNumeros.clear();
                        out.println("Lista eliminada: " + listaNumeros);
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

    public static String agregar(int num, List<Integer> listaNumeros){
        String mensaje = "";
        listaNumeros.add(num);
        if ((listaNumeros.getLast()) == num){
            mensaje = "Número " + num + " añadido correctamente a la lista";
        }
        return mensaje;
    }

    public static double calcularMedia(List<Integer> lista){
        double sumaNum = 0;
        double total = 0;

        if (lista.isEmpty()){
            return 0.0;
        }
        for (double numero : lista){
            sumaNum += numero;
        }

        total = sumaNum / lista.size();

        return total;
    }

    public static int buscarMaximo(List<Integer> lista){
        int numMax = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) > numMax){
                numMax = lista.get(i);
            }
        }

        return numMax;
    }
}
