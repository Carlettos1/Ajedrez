package util;
public class Habilidad {
    private final String NOMBRE;
    private final String DESCRIPCION;
    private final int CD;
    private final int COSTO;
    private final String PARAMETROS;

    public Habilidad(String nombre, String descripcion, int cd, int costo, String parametros) {
        NOMBRE = nombre;
        DESCRIPCION = descripcion;
        CD = cd;
        COSTO = costo;
        PARAMETROS = parametros;
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

    public String getParametros() {
        return PARAMETROS;
    }
}