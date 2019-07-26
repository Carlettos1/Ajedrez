package piezas;

import java.awt.Graphics;
import java.util.Arrays;
import jugador.Jugador;
import tablero.Escaque;
import tablero.TableroManager;
import piezas.base.Pieza;
import tipo.EnumTipo;
import util.Habilidad;
import util.Settings;

public class Peon extends Pieza {

    public Peon(boolean isBlanca) {
        super("Peón", isBlanca,
                Arrays.asList(new EnumTipo[]{EnumTipo.biologico, EnumTipo.transportable}),
                new Habilidad("Coronar", "Corona al peón", 0, 1,
                        "El peón debe situarse en el extremo contrario del tablero"
                        + "\nRequiere que se indique la pieza en la que se va a transformar"));
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
        if (!super.canUsarHabilidad(tablero, escaqueInicio, informacionExtra, jugador)) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }
        if (!(informacionExtra.equals("peon")
                || informacionExtra.equals("alfil")
                || informacionExtra.equals("torre")
                || informacionExtra.equals("caballo")
                || informacionExtra.equals("reina"))) {
            return false;
        }

        if (!escaqueInicio.getPieza().isBlanca()) {//TODO
            if (escaqueInicio.getLocalizacion().y == Settings.Y - 1) {
                return true;
            }
        } else {
            if (escaqueInicio.getLocalizacion().y == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        if (informacionExtra.equals("peon")) {
            escaqueInicio.setPieza(new Peon(escaqueInicio.getPieza().isBlanca()));
        }
        if (informacionExtra.equals("alfil")) {
            escaqueInicio.setPieza(new Alfil(escaqueInicio.getPieza().isBlanca()));
        }
        if (informacionExtra.equals("torre")) {
            escaqueInicio.setPieza(new Torre(escaqueInicio.getPieza().isBlanca()));
        }
        if (informacionExtra.equals("caballo")) {
            escaqueInicio.setPieza(new Caballo(escaqueInicio.getPieza().isBlanca()));
        }
        if (informacionExtra.equals("reina")) {
            escaqueInicio.setPieza(new Reina(escaqueInicio.getPieza().isBlanca()));
        }
        escaqueInicio.getPieza().setSeHaMovidoEsteTurno(true);
        escaqueInicio.getPieza().setCdActual(escaqueInicio.getPieza().getHabilidad().getCD());
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
