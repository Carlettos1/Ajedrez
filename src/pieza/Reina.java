package pieza;

import java.util.Arrays;
import jugador.Jugador;
import pieza.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;
import util.Settings;

public class Reina extends Pieza {

    public Reina(boolean isBlanca) {
        super("Reina", isBlanca, Arrays.asList(new EnumTipo[]{EnumTipo.heroica, EnumTipo.inmune, EnumTipo.transportable, EnumTipo.biologico}),
                new Habilidad("Movimiento caballístico", "Permite a la reina mover y comer como caballo", 5, 1,
                        "No hay restricciones"
                        + "\nNecesita que se le especifique el movimiento en x y en y que debe hacer"
                        + "\nEj: \'1 2\' (se mueve 1 en x y 2 en y)"
                        + "\nValores posibles: \'1 2\',    \'2 1\',    \'-1 2\',   \'-2 1\', "
                        + "\n                                  \'-1 -2\', \'-2 -1\', \'1 -2\' y \'2 -1\'"));
    }

    @Override
    public boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (!escaqueFinal.isVacio()) {
            return false;
        }

        boolean mueveComoAlfil = false;

        int xInicio = escaqueInicio.getLocalizacion().x;
        int xFinal = escaqueFinal.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int yFinal = escaqueFinal.getLocalizacion().y;

        int deltaX = xFinal - xInicio;
        int deltaY = yFinal - yInicio;

        int direccionX = 0;
        int direccionY = yFinal > yInicio ? 1 : -1;

        if (xFinal != xInicio) { //se mueve en x
            if (yFinal != yInicio) { //se mueve en y
                if (Math.abs(deltaX) != Math.abs(deltaY)) {
                    return false;
                } else {
                    mueveComoAlfil = true;
                }
            } else {
                direccionX = xFinal > xInicio ? 1 : -1;
                direccionY = 0;
            }
        }

        if (mueveComoAlfil) {

            int signoX = deltaX > 0 ? 1 : -1;
            int signoY = deltaY > 0 ? 1 : -1;

            for (int casilla = 1; casilla <= Math.abs(deltaX); casilla++) {
                if (!tablero.getEscaque(xInicio + casilla * signoX, yInicio + casilla * signoY).isVacio()) {
                    return false;
                }
            }
            return true;
        } else {

            boolean isMovimientoVertical = direccionY != 0;

            for (int casilla = 1; casilla <= (isMovimientoVertical ? Math.abs(yInicio - yFinal) : Math.abs(xInicio - xFinal)); casilla++) {
                if (!tablero.getEscaque(xInicio + casilla * direccionX, yInicio + (casilla * direccionY)).isVacio()) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (escaqueInicio.getPieza().isBlanca() == escaqueFinal.getPieza().isBlanca()) {
            return false;
        }
        if (escaqueFinal.isVacio()) {
            return false;
        }

        boolean mueveComoAlfil = false;

        int xInicio = escaqueInicio.getLocalizacion().x;
        int xFinal = escaqueFinal.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int yFinal = escaqueFinal.getLocalizacion().y;

        int deltaX = xFinal - xInicio;
        int deltaY = yFinal - yInicio;

        int direccionX = 0;
        int direccionY = yFinal > yInicio ? 1 : -1;

        if (xFinal != xInicio) { //se mueve en x
            if (yFinal != yInicio) { //se mueve en y
                if (Math.abs(deltaX) != Math.abs(deltaY)) {
                    return false;
                } else {
                    mueveComoAlfil = true;
                }
            } else {
                direccionX = xFinal > xInicio ? 1 : -1;
                direccionY = 0;
            }
        }

        if (mueveComoAlfil) {

            int signoX = deltaX > 0 ? 1 : -1;
            int signoY = deltaY > 0 ? 1 : -1;

            for (int casilla = 1; casilla < Math.abs(deltaX); casilla++) {
                if (!tablero.getEscaque(xInicio + casilla * signoX, yInicio + casilla * signoY).isVacio()) {
                    return false;
                }
            }
            return true;
        } else {

            boolean isMovimientoVertical = direccionY != 0;

            for (int casilla = 1; casilla < (isMovimientoVertical ? Math.abs(yInicio - yFinal) : Math.abs(xInicio - xFinal)); casilla++) {
                if (!tablero.getEscaque(xInicio + casilla * direccionX, yInicio + (casilla * direccionY)).isVacio()) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        if(!super.canUsarHabilidad(tablero, escaqueInicio, informacionExtra, jugador)){
            return false;
        }
        if (!(informacionExtra.equals("1 2")
                || informacionExtra.equals("2 1")
                || informacionExtra.equals("-1 2")
                || informacionExtra.equals("-2 1")
                || informacionExtra.equals("-1 -2")
                || informacionExtra.equals("-2 -1")
                || informacionExtra.equals("1 -2")
                || informacionExtra.equals("2 -1"))) {
            return false;
        }

        int xInicio = escaqueInicio.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int x = 0;
        int y = 0;

        switch (informacionExtra) {
            case "1 2":
                x = 1;
                y = 2;
                break;
            case "2 1":
                x = 2;
                y = 1;
                break;
            case "-1 2":
                x = -1;
                y = 2;
                break;
            case "-2 1":
                x = -2;
                y = 1;
                break;
            case "-1 -2":
                x = -1;
                y = -2;
                break;
            case "-2 -1":
                x = -2;
                y = -1;
                break;
            case "1 -2":
                x = 1;
                y = -2;
                break;
            case "2 -1":
                x = 2;
                y = -1;
                break;
        }
        y = -y; //para que tenga sentido con el visual

        if (xInicio + x < Settings.X && xInicio + x >= 0) {
            if (yInicio + y < Settings.Y && yInicio + y >= 0) {
                Escaque escaqueFinal = tablero.getEscaque(xInicio + x, yInicio + y);
                if (escaqueFinal.isVacio()) {
                    return true;
                } else {
                    return escaqueFinal.getPieza().isBlanca() != isBlanca();
                }
            }
        }
        return false;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        int xInicio = escaqueInicio.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int x = 0;
        int y = 0;

        switch (informacionExtra) {
            case "1 2":
                x = 1;
                y = 2;
                break;
            case "2 1":
                x = 2;
                y = 1;
                break;
            case "-1 2":
                x = -1;
                y = 2;
                break;
            case "-2 1":
                x = -2;
                y = 1;
                break;
            case "-1 -2":
                x = -1;
                y = -2;
                break;
            case "-2 -1":
                x = -2;
                y = -1;
                break;
            case "1 -2":
                x = 1;
                y = -2;
                break;
            case "2 -1":
                x = 2;
                y = -1;
                break;
        }
        y = -y; //para que tenga sentido con el visual

        Escaque escaqueFinal = tablero.getEscaque(xInicio + x, yInicio + y);
        escaqueFinal.setPieza(this);
        escaqueInicio.quitarPieza();
        
        super.habilidad(tablero, escaqueInicio, informacionExtra, jugador);
    }
}
