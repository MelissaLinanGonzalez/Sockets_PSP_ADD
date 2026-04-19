package Ejercicios.Trivial;

import java.util.ArrayList;

public class GameManager {
    private ArrayList<ClienteHandler> clientes = new ArrayList<>();
    private static boolean rondaAbierta;
    private ArrayList<Preguntas> preguntas = new ArrayList<>();

    public GameManager(ArrayList<ClienteHandler> clientes) {
        this.clientes = clientes;
        this.preguntas = preguntas;
        this.preguntas.add(new Preguntas("cómo me llamo?", "dolores","Melissa", "pepe", "silvia", "b"));
        this.preguntas.add(new Preguntas("cómo se llama mi perro?", "gerbasio","paco", "thor", "Cooper", "d"));
        this.preguntas.add(new Preguntas("cuál es la capital de Andalucía", "sevilla","granada", "Córdoba", "almería", "c"));
    }

    public void iniciarPartida() throws InterruptedException {
        for (Preguntas p: preguntas){
            enviarTodos(extraerPregunta(p));
            rondaAbierta = true;
            Thread.sleep(15000);
            corregirRespuesta(p.getRespuestaCorrecta());
            limpiarRespuestas();
            System.out.println("*** Ranking ***\n");
            mostrarRanking();
        }
        System.out.println("*** Ranking Final***\n");
        mostrarRanking();
    }

    public void mostrarRanking(){
        String total = "";
        for (ClienteHandler cl : clientes){
            total += cl.mostrarNota();
        }
        System.out.println(total);
    }

    public void limpiarRespuestas(){
        for (ClienteHandler cl: clientes){
            cl.limpiarRespuesta();
        }

    }

    public String extraerPregunta(Preguntas p){
        return p.getEnunciado() + " | A: " + p.getRespuestaA() +
                " | B: " + p.getRespuestaB() +
                " | C: " + p.getRespuestaC() +
                " | D: " + p.getRespuestaD();
    }

    public void enviarTodos(String mensaje){
        for (ClienteHandler cl: clientes){
            cl.enviarMensaje(mensaje);
        }

    }

    public void corregirRespuesta(String sol){
        // Actualiza el campo puntos de clienteHandler para las respuetas correctas
        for (ClienteHandler cl : clientes){
            cl.corregirRespuesta(sol);
        }

    }

    public ArrayList<ClienteHandler> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<ClienteHandler> clientes) {
        this.clientes = clientes;
    }

    public static boolean isRondaAbierta() {
        return rondaAbierta;
    }

    public void setRondaAbierta(boolean rondaAbierta) {
        this.rondaAbierta = rondaAbierta;
    }

    public ArrayList<Preguntas> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<Preguntas> preguntas) {
        this.preguntas = preguntas;
    }
}
