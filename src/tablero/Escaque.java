package tablero;

import estructuras.Inexistente;
import estructuras.base.Estructura;
import java.awt.Point;
import java.util.Objects;
import javax.swing.JComponent;
import jugador.Jugador;
import piezas.Vacia;
import piezas.base.Pieza;

public final class Escaque extends JComponent {

    private boolean isSelected;
    private final Point localizacion;
    private final TableroManager tablero;
    private Pieza pieza;
    private Estructura estructura;
    private boolean isFuenteDeMagia;
    private boolean isConstruible;

    public Escaque(Pieza pieza, Point punto, TableroManager tablero) {
        this.isSelected = false;
        this.pieza = pieza;
        this.localizacion = punto;
        this.tablero = tablero;
        this.isConstruible = true;
        this.isFuenteDeMagia = false;
    }

    public boolean isEntidadBlanca() {
        if (hasEstructura()) {
            return getEstructura().isBlanca();
        } else if (hasPieza()) {
            return getPieza().isBlanca();
        }
        return false;
    }

    public boolean isFuenteDeMagia() {
        return isFuenteDeMagia;
    }

    public void setIsFuenteDeMagia(boolean isFuenteDeMagia) {
        this.isFuenteDeMagia = isFuenteDeMagia;
    }

    public boolean isConstruible() {
        return isConstruible;
    }

    public void setIsConstruible(boolean isConstruible) {
        this.isConstruible = isConstruible;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public Point getLocalizacion() {
        return localizacion;
    }

    public TableroManager getTablero() {
        return tablero;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public void quitarPieza() {
        setPieza(new Vacia());
    }

    public boolean hasPieza() {
        return !getPieza().equals(new Vacia());
    }

    public boolean hasEstructura() {
        return !getEstructura().equals(new Inexistente());
    }

    public boolean isVacio() {
        return !(hasEstructura() || hasPieza());
    }

    public boolean habilidadPieza(String info, Jugador jugador) {
        getPieza().habilidad(tablero, this, info, jugador);
        return true;
    }

    public boolean moverPieza(Escaque escaqueFinal) {
        if (!escaqueFinal.getPieza().equals(new Vacia())) {
            return false;
        }
        if (!getPieza().canMover(tablero, this, escaqueFinal)) {
            return false;
        }
        getPieza().setSeHaMovidoEsteTurno(true);
        escaqueFinal.setPieza(getPieza());
        quitarPieza();
        return true;
    }

    public boolean comerPieza(Escaque escaqueFinal) {
        if (!getPieza().canComer(tablero, this, escaqueFinal)) {
            return false;
        }
        getPieza().setSeHaMovidoEsteTurno(true);
        escaqueFinal.setPieza(getPieza());
        quitarPieza();
        return true;
    }

    public Jugador getDueño() {
        if (isVacio()) {
            return null;
        }
        return getPieza().isBlanca() ? tablero.getJugadorBlanco() : tablero.getJugadorNegro();
    }

    @Override
    public String toString() {
        return "Escaque{" + "isSelected=" + isSelected + ", localizacion=" + localizacion + ", tablero=" + tablero + ", pieza=" + pieza + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.localizacion);
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
        final Escaque other = (Escaque) obj;
        if (!Objects.equals(this.localizacion, other.localizacion)) {
            return false;
        }
        return true;
    }
}
