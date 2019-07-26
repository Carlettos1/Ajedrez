package estructuras.base;

import java.util.Objects;
import tablero.Escaque;
import tablero.TableroManager;
import util.Habilidad;

public abstract class Estructura {

    protected final String nombre;
    protected final boolean requiereMagia;
    protected final int alto;
    protected final int ancho;
    protected int cdActual = 0;
    protected final boolean isBlanca;
    protected final Habilidad habilidad;

    public Estructura(boolean isBlanca, boolean requiereMagia, int alto, int ancho, String nombre, Habilidad habilidad) {
        this.requiereMagia = requiereMagia;
        this.ancho = ancho;
        this.alto = alto;
        this.isBlanca = isBlanca;
        this.nombre = nombre;
        this.habilidad = habilidad;
    }

    public Habilidad getHabilidad() {
        return habilidad;
    }
    
    public int getCdActual() {
        return cdActual;
    }

    public void setCdActual(int cd) {
        cdActual = cd;
    }
    
    public void disminurCd(){
        if(cdActual == 0){
        } else {
            cdActual--;
        }
    }
    public boolean isBlanca() {
        return isBlanca;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean requiereMagia() {
        return requiereMagia;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }
    
    public abstract void usar(TableroManager tablero, Escaque escaqueActual);

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.nombre);
        hash = 89 * hash + (this.isBlanca ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estructura other = (Estructura) obj;
        if (this.isBlanca != other.isBlanca) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
}
