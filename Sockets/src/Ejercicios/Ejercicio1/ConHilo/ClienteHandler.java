package Ejercicios.Ejercicio1.ConHilo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread{
    private Socket cliente;

    public ClienteHandler(Socket socket) {
        this.cliente = socket; // Recibe el socket de la conexión
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
            String operacion;


            while (true){
                salida.println("""
                        *** Menú de selección ***
                        \t1. CIFRAR.
                        \t2. DESCIFRAR.
                        \t3. EXIT.
                        ES IMPORTANTE QUE LAS OPCIONES LAS ESCRIBAS TAL CUAL APARECEN.
                        FIN_MENU
                        """);

                String opcion = entrada.readLine();

                if (opcion.equals("EXIT")){
                    System.out.println("El cliente se ha desconectado");
                    break;
                }

                String cadena = entrada.readLine();
                switch (opcion){
                    case "CIFRAR":
                        System.out.println("El cliente ha seleccionado CIFRAR");
                        salida.println("Has seleccionado CIFRAR ");
                        salida.println("Cadena cifrada: " + cifrar_descifrar(cadena, opcion, 3));

                        break;
                    case "DESCIFRAR":
                        System.out.println("El cliente ha seleccionado DESCIFRAR");
                        salida.println("Has seleccionado DESCIFRAR");
                        salida.println("Cadena cifrada: " + cifrar_descifrar(cadena, opcion, 3));
                        break;
                    default:
                        salida.println("Seleccione una opción correcta");
                        break;
                }

                salida.println("FIN_OPERACION");
            }
        } catch (Exception e) { e.printStackTrace(); }
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
