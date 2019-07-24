package pieza;

import java.awt.Graphics;
import jugador.Jugador;
import tablero.Escaque;
import tablero.TableroManager;
import pieza.base.Pieza;

public class Vacia extends Pieza{

    public Vacia() {
        super("Vacia", false, null, null);
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
    public boolean canUsarHabilidad(TableroManager a1, Escaque a2, String a3, Jugador jugador) {
        return false;
    }

    @Override
    public void marcar(Graphics a1, Escaque a2) {
    }

    @Override
    public void habilidad(TableroManager a1, Escaque a2, String a3, Jugador jugador) {
    }
}