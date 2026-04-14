package PruebaEvaluable;

import java.io.*;
import java.net.*;
import javax.swing.SwingUtilities;

public class ClientHandler extends Thread {
    private Socket cliente;
    private BufferedReader entrada;
    private PrintWriter salida;

    private String nick = "Anónimo";
    public int puntos = 0;
    private String respuestaActual = "";
    private long tiempoRecibido = 0;
    private long inicioPregunta = 0;

    public ClientHandler(Socket socket) {
        this.cliente = socket;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida = new PrintWriter(cliente.getOutputStream(), true);

            salida.println("MENSAJE|Bienvenido a la Trivia. Introduce tu Nick:");
            nick = entrada.readLine();
            System.out.println("Jugador conectado: " + nick);
            SwingUtilities.invokeLater(() -> Servidor.textArea.append("Jugador conectado: " + nick + "\n"));
            salida.println("MENSAJE|Hola " + nick + ", esperando a que el servidor inicie la partida...");

            while (true) {
                String mensaje = entrada.readLine();
                if (mensaje == null)
                    break;

                if (mensaje.startsWith("RESPUESTA|") && respuestaActual.equals("")) {
                    respuestaActual = mensaje.split("\\|")[1].toLowerCase().trim();
                    tiempoRecibido = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            System.out.println("El cliente " + nick + " se ha desconectado.");
            SwingUtilities.invokeLater(() -> Servidor.textArea.append("El cliente " + nick + " se ha desconectado.\n"));
        }
    }

    public void nuevaPregunta(String pregunta, String opciones) {
        respuestaActual = "";
        inicioPregunta = System.currentTimeMillis();
        salida.println("PREGUNTA|" + pregunta);
        salida.println("OPCIONES|" + opciones);
    }

    public void validarRespuesta(String correcta) {
        if (!respuestaActual.equals("")) {
            long tiempoTardado = tiempoRecibido - inicioPregunta;
            if (tiempoTardado <= 15000) {
                if (respuestaActual.equals(correcta)) {
                    puntos++;
                    salida.println("RESULTADO|¡Correcto! Sumas 1 punto.");
                } else {
                    salida.println("RESULTADO|Incorrecto. La respuesta era la " + correcta);
                }
            } else {
                salida.println("RESULTADO|Has respondido, pero fuera de tiempo.");
            }
        } else {
            salida.println("RESULTADO|No has enviado ninguna respuesta a tiempo.");
        }
    }

    public void enviarMensaje(String msg) {
        if (salida != null) {
            salida.println(msg);
        }
    }

    public String getNick() {
        return nick;
    }
}