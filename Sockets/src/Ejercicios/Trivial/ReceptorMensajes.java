package Ejercicios.Trivial;

import java.io.BufferedReader;

public class ReceptorMensajes extends Thread{
    BufferedReader in;

    public ReceptorMensajes(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run(){
        try {
            String message;
            while ((message = in.readLine()) != null){
                System.out.println(message);
            }
            System.out.println("Servidor finalizado");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.exit(0); // esto es para cerrar los clientes que no puedan acceder a la partida por exceso de aforo
        }
    }
}