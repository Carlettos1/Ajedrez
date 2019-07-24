package carta;

import carta.base.Carta;
import tablero.Escaque;

public class Nula extends Carta{
    
    public Nula() {
        super(false, null, 0, 0, 0, null, "Nula", "Nada");
    }

    @Override
    public boolean canUsarCarta(Escaque escaqueObjetivo) {
        return false;
    }

    @Override
    public void usarCarta(Escaque escaqueObjetivo) {
    }
}