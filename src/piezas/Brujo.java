package piezas;

import estructuras.Portal;
import java.util.Arrays;
import jugador.Jugador;
import piezas.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;

public class Brujo extends Pieza {

    private int turnosInmovil = 0;
    public boolean hizoClick = false;

    public Brujo(boolean isBlanca) {
        super("Brujo", isBlanca,
                Arrays.asList(new EnumTipo[]{EnumTipo.biologico, EnumTipo.demonio, EnumTipo.transportable}),
                new Habilidad("Portal de invocación", "Crea un portal de invocación en la fuente de magia cercana",
                        10, 3, "Requiere que haya una casilla vacía con fuente de magia a"
                        + "\n1 casilla de distancia"
                        + "\nrequiere 2 turnos de casteo, no se puede mover mientras tanto"));
    }

    @Override
    public boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (!escaqueFinal.isVacio()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }

        if (turnosInmovil != 0) {
            return false;
        }

        int xInicio = escaqueInicio.getLocalizacion().x;
        int xFinal = escaqueFinal.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int yFinal = escaqueFinal.getLocalizacion().y;

        int direccionX = 0;
        int direccionY = yFinal > yInicio ? 1 : -1;

        if (xFinal != xInicio) { //se mueve en x
            if (yFinal != yInicio) { //se mueve en y
                return false;
            }
            direccionX = xFinal > xInicio ? 1 : -1;
            direccionY = 0;
        }

        if (Math.abs(xFinal - xInicio) > 2) {
            return false;
        }
        if (Math.abs(yFinal - yInicio) > 2) {
            return false;
        }

        boolean isMovimientoVertical = direccionY != 0;

        for (int casilla = 1; casilla <= (isMovimientoVertical ? Math.abs(yInicio - yFinal) : Math.abs(xInicio - xFinal)); casilla++) {
            if (!tablero.getEscaque(xInicio + casilla * direccionX, yInicio + (casilla * direccionY)).isVacio()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        return false;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        if (!super.canUsarHabilidad(tablero, escaqueInicio, informacionExtra, jugador)) {
            return false;
        }

        if (turnosInmovil != 0) {
            return false;
        }

        if (tablero.getEscaquesCercanos(escaqueInicio).stream().anyMatch((escaque) -> (escaque.isFuenteDeMagia()))) {
            hizoClick = true;
            turnosInmovil = 4;
            jugador.setMana(jugador.getMana() - getHabilidad().getCosto());
            return true;
        }
        return false;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {

        if (turnosInmovil != 0) {
            return;
        }

        for (Escaque escaque : tablero.getEscaquesCercanos(escaqueInicio)) {
            if (escaque.isFuenteDeMagia() && !escaque.hasEstructura()) {
                escaque.setEstructura(new Portal(isBlanca(), escaque.getLocalizacion().y == 7));
                setCdActual(getHabilidad().getCD());
                setSeHaMovidoEsteTurno(true);
                hizoClick = false;
                return;
            }
        }
    }

    public int getTurnosInmovil() {
        return turnosInmovil;
    }

    public void setTurnosInmovil(int turnosInmovil) {
        this.turnosInmovil = turnosInmovil;
    }

    public void dimTurnosInmovil() {
        turnosInmovil -= turnosInmovil == 0 ? 0 : 1;
    }
}
