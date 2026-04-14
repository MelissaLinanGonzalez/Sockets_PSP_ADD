package Ejercicios.P1_SocketsJava.Ejercicio2;

public class ContadorCompartido {
    private int valor = 0;

    public synchronized void incrementar() {
        valor++;
    }

    public synchronized int getValor() {
        return valor;
    }

    public synchronized void resetear() {
        valor = 0;
    }
}