package carta.base;

import java.util.List;
import java.util.Objects;
import pieza.base.Pieza;
import tablero.Escaque;

public abstract class Carta {    
    private final String nombre;
    private final String descripcion;
    private final boolean isBlanca;
    private final Pieza piezaObjetivo;
    private final int costeCarta;
    private final int costeMana;
    private final int costePieza;
    private final List<Pieza> piezasAdmitidas;

    public Carta(boolean isBlanca, Pieza piezaObjetivo, int costeCarta, int costeMana, int costePieza, List<Pieza> piezasAdmitidas, String nombre, String descripcion) {
        this.isBlanca = isBlanca;
        this.piezaObjetivo = piezaObjetivo;
        this.costeCarta = costeCarta;
        this.costeMana = costeMana;
        this.costePieza = costePieza;
        this.piezasAdmitidas = piezasAdmitidas;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public abstract boolean canUsarCarta(Escaque escaqueObjetivo);
    public abstract void usarCartaA(Escaque escaqueObjetivo);

    public boolean isBlanca() {
        return isBlanca;
    }

    public Pieza getPiezaObjetivo() {
        return piezaObjetivo;
    }

    public int getCosteCarta() {
        return costeCarta;
    }

    public int getCosteMana() {
        return costeMana;
    }

    public int getCostePieza() {
        return costePieza;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    
    
    public List<Pieza> getPiezasAdmitidas() {
        return piezasAdmitidas;
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
        final Carta other = (Carta) obj;
        if (this.isBlanca != other.isBlanca) {
            return false;
        }
        if (this.costeCarta != other.costeCarta) {
            return false;
        }
        if (this.costeMana != other.costeMana) {
            return false;
        }
        if (this.costePieza != other.costePieza) {
            return false;
        }
        if (!Objects.equals(this.piezaObjetivo, other.piezaObjetivo)) {
            return false;
        }
        if (!Objects.equals(this.piezasAdmitidas, other.piezasAdmitidas)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
