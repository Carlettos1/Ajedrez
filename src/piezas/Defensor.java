package piezas;

import java.awt.Graphics;
import java.util.Arrays;
import jugador.Jugador;
import piezas.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;
import util.Settings;

public class Defensor extends Pieza {
    
    public Defensor(boolean isBlanca) {
        super("Defensor", isBlanca,
                Arrays.asList(new EnumTipo[]{EnumTipo.biologico, EnumTipo.transportable, EnumTipo.impenetrable}),
                new Habilidad("Defender", "Destruye al ariete y a la ballesta", 0, 0,
                        "Si un ariete arremete contra él, es destruido"
                        + "\nSi una ballesta lo ataca, la ballesta es destruida"));
    }

    @Override
    public boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (!escaqueFinal.isVacio()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }

        if (escaqueFinal.getLocalizacion().x != escaqueInicio.getLocalizacion().x) {
            return false;
        }

        if (Math.abs(escaqueFinal.getLocalizacion().y - escaqueInicio.getLocalizacion().y) > 2) {
            return false;
        }

        if (!escaqueInicio.getPieza().isBlanca()) {//TODO
            if (escaqueFinal.getLocalizacion().y < escaqueInicio.getLocalizacion().y) {
                return false;
            } else if (!escaqueInicio.getTablero().getEscaque(escaqueInicio.getLocalizacion().x, escaqueInicio.getLocalizacion().y + 1).isVacio()) {
                return false;
            }
        } else {
            if (escaqueFinal.getLocalizacion().y > escaqueInicio.getLocalizacion().y) {
                return false;
            } else if (!escaqueInicio.getTablero().getEscaque(escaqueInicio.getLocalizacion().x, escaqueInicio.getLocalizacion().y - 1).isVacio()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (escaqueFinal.isVacio()) {
            return false;
        }
        if (escaqueInicio.getPieza().isBlanca() == escaqueFinal.getPieza().isBlanca()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }
        if (!escaqueInicio.getPieza().isBlanca()) {//TODO
            if (escaqueFinal.getLocalizacion().y - 1 == escaqueInicio.getLocalizacion().y
                    && (escaqueFinal.getLocalizacion().x == escaqueInicio.getLocalizacion().x + 1
                    || escaqueFinal.getLocalizacion().x == escaqueInicio.getLocalizacion().x - 1)) {
                return true;
            }
        } else {
            if (escaqueFinal.getLocalizacion().y + 1 == escaqueInicio.getLocalizacion().y
                    && (escaqueFinal.getLocalizacion().x == escaqueInicio.getLocalizacion().x + 1
                    || escaqueFinal.getLocalizacion().x == escaqueInicio.getLocalizacion().x - 1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        return true;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
    }

    @Override
    public void marcar(Graphics g, Escaque escaqueSeleccionado) {
        int x = escaqueSeleccionado.getLocalizacion().x;
        int y = escaqueSeleccionado.getLocalizacion().y;
        int direccion = !isBlanca() ? 1 : -1;//TODO
        if (y + direccion >= 0 && y + direccion < Settings.Y) {
            if (canMover(escaqueSeleccionado.getTablero(), escaqueSeleccionado, escaqueSeleccionado.getTablero().getEscaque(x, y + direccion))) {
                g.fillOval((int) ((x + 0.3) * Settings.TILE_SIZE) + Settings.ANCHURA_JUGADOR + 10, (int) ((y + 0.3 + direccion) * Settings.TILE_SIZE), Settings.CIRCULO, Settings.CIRCULO);
            }
            if (x + 1 >= 0 && x + 1 < Settings.X) {
                if (canComer(escaqueSeleccionado.getTablero(), escaqueSeleccionado, escaqueSeleccionado.getTablero().getEscaque(x + 1, y + direccion))) {
                    g.fillOval((int) ((x + 1.3) * Settings.TILE_SIZE) + Settings.ANCHURA_JUGADOR + 10, (int) ((y + 0.3 + direccion) * Settings.TILE_SIZE), Settings.CIRCULO, Settings.CIRCULO);
                }
            }
            if (x - 1 >= 0 && x - 1 < Settings.X) {
                if (canComer(escaqueSeleccionado.getTablero(), escaqueSeleccionado, escaqueSeleccionado.getTablero().getEscaque(x - 1, y + direccion))) {
                    g.fillOval((int) ((x - 0.7) * Settings.TILE_SIZE) + Settings.ANCHURA_JUGADOR + 10, (int) ((y + 0.3 + direccion) * Settings.TILE_SIZE), Settings.CIRCULO, Settings.CIRCULO);
                }

            }
        }
        if (y + 2 * direccion >= 0 && y + 2 * direccion < Settings.Y) {
            if (canMover(escaqueSeleccionado.getTablero(), escaqueSeleccionado, escaqueSeleccionado.getTablero().getEscaque(x, y + 2 * direccion))) {
                g.fillOval((int) ((x + 0.3) * Settings.TILE_SIZE) + Settings.ANCHURA_JUGADOR + 10, (int) ((y + 0.3 + 2 * direccion) * Settings.TILE_SIZE), Settings.CIRCULO, Settings.CIRCULO);
            }
        }
    }
}
