package pieza.base;

import java.awt.Graphics;
import tablero.TableroManager;
import tipo.EnumTipo;
import java.util.List;
import java.util.Objects;
import jugador.Jugador;
import tablero.Escaque;
import util.Habilidad;
import util.Settings;

public abstract class Pieza {

    protected final boolean isBlanca;
    protected final List<EnumTipo> tipos;
    protected final String nombre;
    protected final Habilidad habilidad;
    protected int cdActual = 0;
    protected boolean seHaMovidoEsteTurno = false;

    public Pieza(String nombre, boolean isBlanca, List<EnumTipo> tipos, Habilidad habilidad) {
        this.isBlanca = isBlanca;
        this.tipos = tipos;
        this.nombre = nombre;
        this.habilidad = habilidad;
    }

    public abstract boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal);

    public abstract boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal);

    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        if (jugador.getMana() < getHabilidad().getCosto()) {
            return false;
        }

        if (cdActual > 0) {
            return false;
        }
        return true;
    }

    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        setCdActual(getHabilidad().getCD());
        setSeHaMovidoEsteTurno(true);
    }

    public void marcar(Graphics g, Escaque escaqueSeleccionado) {
        for (int x = 0; x < escaqueSeleccionado.getTablero().getColumnas(); x++) {
            for (int y = 0; y < escaqueSeleccionado.getTablero().getFilas(); y++) {
                if (canComer(escaqueSeleccionado.getTablero(), escaqueSeleccionado, escaqueSeleccionado.getTablero().getEscaque(x, y))
                        || canMover(escaqueSeleccionado.getTablero(), escaqueSeleccionado, escaqueSeleccionado.getTablero().getEscaque(x, y))) {
                    if (!escaqueSeleccionado.getTablero().getEscaque(x, y).equals(escaqueSeleccionado)) {
                        g.fillOval((int) ((x + 0.3) * Settings.TILE_SIZE) + Settings.ANCHURA_JUGADOR + 10, (int) ((y + 0.3) * Settings.TILE_SIZE), Settings.CIRCULO, Settings.CIRCULO);
                    }
                }
            }
        }
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

    public Habilidad getHabilidad() {
        return habilidad;
    }

    public List<EnumTipo> getTipos() {
        return tipos;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean seHaMovidoEsteTurno() {
        return seHaMovidoEsteTurno;
    }

    public void setSeHaMovidoEsteTurno(boolean seHaMovidoEsteTurno) {
        this.seHaMovidoEsteTurno = seHaMovidoEsteTurno;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pieza)) {
            return false;
        }
        Pieza tmp = (Pieza) obj;
        if (!tmp.getNombre().equals(this.getNombre())) {
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
