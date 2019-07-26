package piezas;

import java.util.Arrays;
import piezas.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;

public class Hechicero extends Pieza{

    public Hechicero(boolean isBlanca) {
        super("Hechicero", isBlanca,
                Arrays.asList(new EnumTipo[]{EnumTipo.biologico, EnumTipo.heroica}),
                new Habilidad("Muchas Habilidades", "Depende de las cartas que se le adhieran"
                        , 4, 0, "Restricciones de maná y cd"));
    }

    @Override
    public boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (!escaqueFinal.isVacio()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
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
        
        if(Math.abs(xFinal - xInicio) > 2){
            return false;
        }
        if(Math.abs(yFinal - yInicio) > 2){
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
}