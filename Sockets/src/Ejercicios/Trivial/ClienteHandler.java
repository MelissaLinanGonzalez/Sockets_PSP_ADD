package Ejercicios.Trivial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread{
    private Socket cliente;
    private boolean haRespondido;
    private String respuestaActual;
    private Double puntos = 0.0;
    private PrintWriter salida;
    private String nick;

    public ClienteHandler(Socket socket) {
        this.cliente = socket;

    }

    @Override
    public void run() {

        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida = new PrintWriter(cliente.getOutputStream(), true);
            this.nick = entrada.readLine();
            System.out.println("Jugador conectado: " + nick);
            System.out.println("Aforo actual: " + Servidor.clientes.size());

            if (Servidor.clientes.size() == 2){
                System.out.println("Aforo máximo! Inicie la partida");
            }

            while (true){
                String mensaje = entrada.readLine(); // Escucha constante (Bloqueante)
                // Lee la respuesta del cliente
                if (GameManager.isRondaAbierta() && !haRespondido){
                    this.respuestaActual = mensaje;
                    this.haRespondido = true;
                    salida.println("INFO| Respuesta registrada"); // manda al cliente que se ha enviado la respuesta
                }
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void enviarMensaje(String mensaje){
        salida.println(mensaje);
    }

    public void limpiarRespuesta(){
        haRespondido = false;
        respuestaActual = null;
    }

    public void corregirRespuesta(String sol){
        if (respuestaActual == null){
            enviarMensaje("Fuera de tiempo. La respuesta correcta era: " + sol);
        } else if (respuestaActual.equalsIgnoreCase(sol)){
            puntos ++;
            enviarMensaje("Respuesta correcta!");
        } else {
            enviarMensaje("Error. La respueta correcta era: " + sol);
            if (puntos > 0){
                puntos -= 0.3;
            }
        }
    }

    public String mostrarNota(){
        String nota = nick + " ha sacado: " + puntos + "\n";
        salida.println(nota);
        return nota;
    }

    public String getNick(){
        return nick;
    }

    public Double getPuntos(){
        return puntos;
    }
}
