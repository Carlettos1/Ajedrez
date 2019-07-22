package util;
public class Habilidad {
    private final String NOMBRE;
    private final String DESCRIPCION;
    private final int CD;
    private final int COSTO;

    public Habilidad(String nombre, String descripcion, int cd, int costo) {
        NOMBRE = nombre;
        DESCRIPCION = descripcion;
        CD = cd;
        COSTO = costo;
    }

    public String getNombre() {
        return NOMBRE;
    }

    public String getDescripcion() {
        return DESCRIPCION;
    }

    public int getCD() {
        return CD;
    }

    public int getCosto() {
        return COSTO;
    }
}