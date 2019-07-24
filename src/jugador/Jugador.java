package jugador;

import carta.base.Carta;
import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private final boolean isBlanco;
    private final int movimientosPorTurno;
    private int mana;
    private final List<Carta> cartasEnMano;

    public Jugador(boolean isBlanco) {
        this.isBlanco = isBlanco;
        movimientosPorTurno = 1;
        mana = 0;
        cartasEnMano = new ArrayList<>();
    }

    public boolean isBlanco() {
        return isBlanco;
    }

    public int getMovimientosPorTurno() {
        return movimientosPorTurno;
    }

    public int getMana() {
        return mana;
    }

    public List<Carta> getCartasEnMano() {
        return cartasEnMano;
    }
    
    public void recibirCarta(Carta carta){
        cartasEnMano.add(carta);
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
    
    public void addMana(int quantity){
        mana += quantity;
    }
}
