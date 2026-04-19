package Repaso;

public class VueloCompartido {
    private final int totalAsientos;
    private int asientosOcupados = 0;

    public VueloCompartido(int totalAsientos) {
        this.totalAsientos = totalAsientos;
    }

    public synchronized int getAsientosLibres() {
        return totalAsientos - asientosOcupados;
    }

    public synchronized boolean reservar(int cantidad){
        if (cantidad > 0 && cantidad <= getAsientosLibres()){
            asientosOcupados += cantidad;
            return true; // Esto nos indica que se ha podido reservar correctamente los asientos
        }
        return false;
    }

    public synchronized boolean devolver(int cantidad){
        if (cantidad > 0 && (getAsientosLibres() + cantidad <= totalAsientos)){
            asientosOcupados -= cantidad;
            return true;
        }
        return false;
    }
}
