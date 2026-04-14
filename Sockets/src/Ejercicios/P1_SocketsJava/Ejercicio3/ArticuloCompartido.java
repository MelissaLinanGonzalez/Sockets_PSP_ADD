package Ejercicios.P1_SocketsJava.Ejercicio3;

public class ArticuloCompartido {
    private final String nombre;
    private final double pujaInicial;
    private double puja = 0;
    private String mejorPostor = "";

    public ArticuloCompartido(String nombre, double pujaInicial) {
        this.nombre = nombre;
        this.pujaInicial = pujaInicial;
    }

    public synchronized String getNombre() {
        return nombre;
    }

    public synchronized double getPuja() {
        if (pujaInicial > puja){
            return pujaInicial;
        }
        return puja;
    }

    public synchronized boolean realizarPuja(double pujaNueva, String nombre){
        if (pujaNueva > this.puja && pujaNueva >= pujaInicial){
            mejorPostor = nombre;
            puja = pujaNueva;
            return true;
        }
        return false;
    }

    public synchronized String getMejorPostor(){
        return mejorPostor + " con la puja: " + puja;
    }
}
