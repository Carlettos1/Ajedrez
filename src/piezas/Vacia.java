package piezas;

import java.awt.Graphics;
import java.util.ArrayList;
import jugador.Jugador;
import tablero.Escaque;
import tablero.TableroManager;
import piezas.base.Pieza;
import tipo.EnumTipo;

public class Vacia extends Pieza{

    public Vacia() {
        super("Vacia", false, new ArrayList<EnumTipo>(), null);
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