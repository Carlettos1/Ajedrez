package carta.base;

import java.util.List;
import pieza.base.Pieza;

public abstract class Carta {    
    private final boolean isBlanca;
    private final Pieza piezaObjetivo;
    private final int costeCarta;
    private final int costeMana;
    private final int costePieza;
    private final List<Pieza> piezasAdmitidas;

    private Carta(boolean isBlanca, Pieza piezaObjetivo, int costeCarta, int costeMana, int costePieza, List<Pieza> piezasAdmitidas) {
        this.isBlanca = isBlanca;
        this.piezaObjetivo = piezaObjetivo;
        this.costeCarta = costeCarta;
        this.costeMana = costeMana;
        this.costePieza = costePieza;
        this.piezasAdmitidas = piezasAdmitidas;
    }
}
