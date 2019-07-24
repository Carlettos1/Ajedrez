package carta.base;

import carta.Nula;
import javax.swing.JComponent;

public class CartaEscaque extends JComponent{
    private Carta carta;
    private final ManoManager mano;

    public CartaEscaque(Carta carta, ManoManager mano) {
        this.carta = carta;
        this.mano = mano;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }
    
    public boolean isNula(){
        return carta.equals(new Nula());
    }
    
    public void quitarCarta(){
        setCarta(new Nula());
    }

    public ManoManager getMano() {
        return mano;
    }
}
