package carta.mejoras;

import carta.base.Carta;
import java.util.ArrayList;
import pieza.Vacia;
import tablero.Escaque;

public class UnTurnoC extends Carta{

    public UnTurnoC(boolean isBlanca) {
        super(isBlanca, new Vacia(), 0, 0, 0, new ArrayList<>(), "Movimiento +1", "Da 1 movimiento mas por turno");
    }

    @Override
    public boolean canUsarCarta(Escaque escaqueObjetivo) {
        return true;
    }

    @Override
    public void usarCarta(Escaque escaqueObjetivo) {
        if (isBlanca()) {
            escaqueObjetivo.getTablero().getJugadorBlanco().getMano().quitarCarta(this);
            escaqueObjetivo.getTablero().getJugadorBlanco().addTurno(1);
        } else {
            escaqueObjetivo.getTablero().getJugadorNegro().getMano().quitarCarta(this);
            escaqueObjetivo.getTablero().getJugadorNegro().addTurno(1);
        }
        escaqueObjetivo.getTablero().getReloj().movimientoHecho();
    }
}
