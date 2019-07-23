package pieza.base;

import java.awt.Graphics;
import java.util.ArrayList;
import tablero.TableroManager;
import tipo.EnumTipo;
import java.util.List;
import java.util.Objects;
import tablero.Escaque;
import util.Habilidad;

public abstract class Pieza {
    protected final boolean isBlanca;
    protected final List<EnumTipo> tipos;
    protected final String nombre;
    protected final Habilidad habilidad;
    
    public Pieza(String nombre, boolean isBlanca, List<EnumTipo> tipos, Habilidad habilidad){
        this.isBlanca = isBlanca;
        this.tipos = tipos;
        this.nombre = nombre;
        this.habilidad = habilidad;
    }
    
    public abstract boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal);
    public abstract boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal);
    public abstract boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra);
    public abstract void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra);
    public abstract void marcar(Graphics g, Escaque escaqueSeleccionado);

    public boolean isBlanca() {
        return isBlanca;
    }

    public Habilidad getHabilidad() {
        return habilidad;
    }

    public List<EnumTipo> getTipos() {
        return tipos;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Pieza)){
            return false;
        }
        Pieza tmp = (Pieza)obj;
        if(!tmp.getNombre().equals(this.getNombre())){
            return false;
        }
        return tmp.isBlanca() == this.isBlanca();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.tipos);
        return hash;
    }

    @Override
    public String toString() {
        return "Pieza{" + "isBlanca=" + isBlanca + ", nombre=" + nombre + "}";
    }
}
