package tablero;

import cartas.base.Carta;
import java.util.ArrayList;
import java.util.List;

public class Baraja {
    private final List<Carta> cartasEnBaraja;
    
    public Baraja(){
        cartasEnBaraja = new ArrayList<>();
    }
    
    public void addCarta(Carta carta){
        cartasEnBaraja.add(carta);
    }

    public List<Carta> getCartasEnBaraja() {
        return cartasEnBaraja;
    }
    
    public Carta robarCarta(){
        return cartasEnBaraja.remove(0);
    }
    
    public int getCantidadDeCartas(){
        return cartasEnBaraja.size();
    }
}
