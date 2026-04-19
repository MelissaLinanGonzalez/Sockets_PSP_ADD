package Ejercicios.Trivial;

import java.util.Scanner;

public class EntradaServidor extends Thread{
    @Override
    public void run() {
        Scanner entrada = new Scanner(System.in);

        while (true){
            String comienzo = entrada.nextLine();
            if(comienzo.equalsIgnoreCase("START")){
                System.out.println("Comenzamos la partida!");
                Servidor.juegoIniciado = true;
                try {
                    if (Servidor.server !=null){
                        Servidor.server.close();
                    }
                } catch (Exception e){
                    System.out.println("Cerrando la conexión del Servidor...");
                }
                break;
            }
        }
    }
}
