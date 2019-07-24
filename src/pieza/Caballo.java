package pieza;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;
import pieza.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;
import util.Settings;

public class Caballo extends Pieza {

    public Caballo(boolean isBlanca) {
        super("caballo", isBlanca, Arrays.asList(new EnumTipo[]{EnumTipo.biologico, EnumTipo.transportable}), new Habilidad("Summon peones", "Summonea 2 peones, uno a cada lado del caballo", 10, 0));
    }

    @Override
    public boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (!escaqueFinal.isVacio()) {
            return false;
        }
        int xInicio = escaqueInicio.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;

        if (xInicio + 1 < Settings.X && yInicio + 2 < Settings.Y) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio + 1, yInicio + 2))) {
                return true;
            }
        }
        if (xInicio + 2 < Settings.X && yInicio + 1 < Settings.Y) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio + 2, yInicio + 1))) {
                return true;
            }
        }
        if (xInicio - 1 >= 0 && yInicio + 2 < Settings.Y) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio - 1, yInicio + 2))) {
                return true;
            }
        }
        if (xInicio - 2 >= 0 && yInicio + 1 < Settings.Y) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio - 2, yInicio + 1))) {
                return true;
            }
        }
        if (xInicio - 1 >= 0 && yInicio - 2 >= 0) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio - 1, yInicio - 2))) {
                return true;
            }
        }
        if (xInicio - 2 >= 0 && yInicio - 1 >= 0) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio - 2, yInicio - 1))) {
                return true;
            }
        }
        if (xInicio + 1 < Settings.X && yInicio - 2 >= 0) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio + 1, yInicio - 2))) {
                return true;
            }
        }
        if (xInicio + 2 < Settings.X && yInicio - 1 >= 0) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio + 2, yInicio - 1))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (escaqueFinal.isVacio()) {
            return false;
        }
        if (escaqueInicio.getPieza().isBlanca() == escaqueFinal.getPieza().isBlanca()) {
            return false;
        }
        int xInicio = escaqueInicio.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;

        if (xInicio + 1 < Settings.X && yInicio + 2 < Settings.Y) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio + 1, yInicio + 2))) {
                return true;
            }
        }
        if (xInicio + 2 < Settings.X && yInicio + 1 < Settings.Y) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio + 2, yInicio + 1))) {
                return true;
            }
        }
        if (xInicio - 1 >= 0 && yInicio + 2 < Settings.Y) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio - 1, yInicio + 2))) {
                return true;
            }
        }
        if (xInicio - 2 >= 0 && yInicio + 1 < Settings.Y) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio - 2, yInicio + 1))) {
                return true;
            }
        }
        if (xInicio - 1 >= 0 && yInicio - 2 >= 0) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio - 1, yInicio - 2))) {
                return true;
            }
        }
        if (xInicio - 2 >= 0 && yInicio - 1 >= 0) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio - 2, yInicio - 1))) {
                return true;
            }
        }
        if (xInicio + 1 < Settings.X && yInicio - 2 >= 0) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio + 1, yInicio - 2))) {
                return true;
            }
        }
        if (xInicio + 2 < Settings.X && yInicio - 1 >= 0) {
            if (escaqueFinal.equals(tablero.getEscaque(xInicio + 2, yInicio - 1))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra) {
        int x = escaqueInicio.getLocalizacion().x;
        int y = escaqueInicio.getLocalizacion().y;
        
        if(x + 1 >= Settings.X || x - 1 < 0){
            return false;
        }
        if(!tablero.getEscaque(x + 1, y).isVacio()){
            return false;
        }
        if(!tablero.getEscaque(x - 1, y).isVacio()){
            return false;
        }
        return true;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra) {
        int x = escaqueInicio.getLocalizacion().x;
        int y = escaqueInicio.getLocalizacion().y;
        tablero.getEscaque(x + 1, y).setPieza(new Peon(isBlanca()));
        tablero.getEscaque(x - 1, y).setPieza(new Peon(isBlanca()));
    }
}
