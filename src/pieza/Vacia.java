package pieza;

import java.awt.Graphics;
import tablero.Escaque;
import tablero.TableroManager;
import pieza.base.Pieza;

public class Vacia extends Pieza{

    public Vacia() {
        super("vacia", false, null, null);
    }

    @Override
    public boolean canMover(TableroManager a1, Escaque a2, Escaque a3) {
        return false;
    }

    @Override
    public boolean canComer(TableroManager a1, Escaque a2, Escaque a3) {
        return false;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager a1, Escaque a2, String a3) {
        return false;
    }

    @Override
    public void marcar(Graphics a1, Escaque a2) {
    }

    @Override
    public void habilidad(TableroManager a1, Escaque a2, String a3) {
    }
}