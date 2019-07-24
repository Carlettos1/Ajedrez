package pieza;

import java.util.Arrays;
import pieza.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;

public class Alfil extends Pieza {

    public Alfil(boolean isBlanca) {
        super("alfil", isBlanca, Arrays.asList(new EnumTipo[]{EnumTipo.biologico, EnumTipo.transportable}), new Habilidad("Cambio de color", "Cambia de color lol", 5, 0));
    }

    @Override
    public boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (!escaqueFinal.isVacio()) {
            return false;
        }
        int xInicio = escaqueInicio.getLocalizacion().x;
        int xFinal = escaqueFinal.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int yFinal = escaqueFinal.getLocalizacion().y;

        int deltaX = xFinal - xInicio;
        int deltaY = yFinal - yInicio;

        if (Math.abs(deltaX) != Math.abs(deltaY)) {
            return false;
        }

        int signoX = deltaX > 0 ? 1 : -1;
        int signoY = deltaY > 0 ? 1 : -1;

        for (int casilla = 1; casilla <= Math.abs(deltaX); casilla++) {
            if (!tablero.getEscaque(xInicio + casilla * signoX, yInicio + casilla * signoY).isVacio()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (escaqueInicio.getPieza().isBlanca() == escaqueFinal.getPieza().isBlanca()) {
            return false;
        }
        if (escaqueFinal.isVacio()) {
            return false;
        }
        int xi = escaqueInicio.getLocalizacion().x;
        int xf = escaqueFinal.getLocalizacion().x;
        int yi = escaqueInicio.getLocalizacion().y;
        int yf = escaqueFinal.getLocalizacion().y;

        int deltaX = xf - xi;
        int deltaY = yf - yi;

        if (Math.abs(deltaX) != Math.abs(deltaY)) {
            return false;
        }

        int signoX = deltaX > 0 ? 1 : -1;
        int signoY = deltaY > 0 ? 1 : -1;

        for (int casilla = 1; casilla < Math.abs(deltaX); casilla++) {
            if (!tablero.getEscaque(xi + casilla * signoX, yi + casilla * signoY).isVacio()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra) {
        switch (informacionExtra) {
            case "arriba":
                return tablero.getEscaque(escaqueInicio.getLocalizacion().x, escaqueInicio.getLocalizacion().y - 1).isVacio();
            case "abajo":
                return tablero.getEscaque(escaqueInicio.getLocalizacion().x, escaqueInicio.getLocalizacion().y + 1).isVacio();
            case "derecha":
                return tablero.getEscaque(escaqueInicio.getLocalizacion().x + 1, escaqueInicio.getLocalizacion().y).isVacio();
            case "izquierda":
                return tablero.getEscaque(escaqueInicio.getLocalizacion().x - 1, escaqueInicio.getLocalizacion().y).isVacio();
        }
        return false;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra) {
        switch (informacionExtra) {
            case "arriba":
                tablero.getEscaque(escaqueInicio.getLocalizacion().x, escaqueInicio.getLocalizacion().y - 1).setPieza(this);
                escaqueInicio.quitarPieza();
                break;
            case "abajo":
                tablero.getEscaque(escaqueInicio.getLocalizacion().x, escaqueInicio.getLocalizacion().y + 1).setPieza(this);
                escaqueInicio.quitarPieza();
                break;
            case "derecha":
                tablero.getEscaque(escaqueInicio.getLocalizacion().x + 1, escaqueInicio.getLocalizacion().y).setPieza(this);
                escaqueInicio.quitarPieza();
                break;
            case "izquierda":
                tablero.getEscaque(escaqueInicio.getLocalizacion().x - 1, escaqueInicio.getLocalizacion().y).setPieza(this);
                escaqueInicio.quitarPieza();
                break;
        }
    }
}
