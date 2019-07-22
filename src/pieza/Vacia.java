package pieza;

import java.awt.Graphics;
import tablero.Escaque;
import tablero.TableroManager;
import pieza.base.Pieza;

public class Vacia extends Pieza{

    public Vacia() {
        super("Vacia", false, null, null);
    }

    @Override
    public boolean canMover(TableroManager t, Escaque b, Escaque a) {
        return false;
    }

    @Override
    public boolean canComer(TableroManager tablero, Escaque b, Escaque a) {
        return false;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque b, Escaque a) {
        return false;
    }

    @Override
    public void marcar(Graphics g, Escaque escaqueSeleccionado) {
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
    }
}