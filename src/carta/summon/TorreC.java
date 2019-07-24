package carta.summon;

import carta.base.Carta;
import java.util.ArrayList;
import pieza.Torre;
import pieza.Vacia;
import tablero.Escaque;

public class TorreC extends Carta {

    public TorreC(boolean isBlanca) {
        super(isBlanca, new Vacia(), 0, 2, 0, new ArrayList<>(), "Colocar Torre", "Coloca una torre");
    }

    @Override
    public boolean canUsarCarta(Escaque escaqueObjetivo) {
        if (escaqueObjetivo.isVacio()) {
            return true;
        }
        return false;
    }

    @Override
    public void usarCartaA(Escaque escaqueObjetivo) {
        escaqueObjetivo.setPieza(new Torre(isBlanca()));
        if (isBlanca()) {
            escaqueObjetivo.getTablero().getJugadorBlanco().getMano().quitarCarta(this);
        } else {
            escaqueObjetivo.getTablero().getJugadorNegro().getMano().quitarCarta(this);
        }
        escaqueObjetivo.getTablero().getReloj().movimientoHecho();
    }
}
