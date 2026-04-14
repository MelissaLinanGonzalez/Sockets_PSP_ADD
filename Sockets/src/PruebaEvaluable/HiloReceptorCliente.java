package PruebaEvaluable;

import java.io.*;
import java.net.*;
import javax.swing.SwingUtilities;

public class HiloReceptorCliente extends Thread {
    private Socket socket;

    public HiloReceptorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String msjServidor = entrada.readLine();
                if (msjServidor == null) {
                    System.out.println("El servidor ha cerrado la conexión.");
                    SwingUtilities.invokeLater(() -> Cliente.textArea.append("El servidor ha cerrado la conexión.\n"));
                    System.exit(0); // Cierra el programa cliente
                    break;
                }

                if (msjServidor.startsWith("PREGUNTA|")) {
                    System.out.println("\n------------------------------------------------");
                    System.out.println(msjServidor.split("\\|")[1]);
                    String preguntaTexto = msjServidor.split("\\|")[1];
                    SwingUtilities.invokeLater(() -> {
                        Cliente.textArea.append("\n------------------------------------------------\n");
                        Cliente.textArea.append(preguntaTexto + "\n");
                    });

                } else if (msjServidor.startsWith("OPCIONES|")) {
                    System.out.println(msjServidor.split("\\|")[1]);
                    System.out.println("------------------------------------------------");
                    System.out.print("Escribe tu opción (a, b, c, d) y pulsa Enter: ");
                    String opcionesTexto = msjServidor.split("\\|")[1];
                    SwingUtilities.invokeLater(() -> {
                        Cliente.textArea.append(opcionesTexto + "\n");
                        Cliente.textArea.append("------------------------------------------------\n");
                        Cliente.textArea.append("Escribe tu opción (a, b, c, d) y pulsa Enviar:\n");
                    });

                } else if (msjServidor.startsWith("MENSAJE|") || msjServidor.startsWith("RESULTADO|")
                        || msjServidor.startsWith("RANKING|")) {
                    String[] partes = msjServidor.split("\\|");
                    if (partes.length > 1) {
                        System.out.println(partes[1]);
                        String contenido = partes[1];
                        SwingUtilities.invokeLater(() -> Cliente.textArea.append(contenido + "\n"));
                    }
                } else if (msjServidor.startsWith("FIN|")) {
                    SwingUtilities.invokeLater(() -> Cliente.textArea.append("\n¡Partida finalizada!\n"));
                    System.exit(0); // Cierra el programa cliente al terminar
                }
            }
        } catch (Exception e) {
            System.out.println("Desconectado del servidor.");
            SwingUtilities.invokeLater(() -> Cliente.textArea.append("Desconectado del servidor.\n"));
            System.exit(0);
        }
    }
}