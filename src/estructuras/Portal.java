package estructuras;

import estructuras.base.Estructura;
import piezas.Catapulta;
import tablero.Escaque;
import tablero.TableroManager;
import util.Habilidad;

public class Portal extends Estructura {

    private final boolean isTopPortal;

    public Portal(boolean isBlanca, boolean isTopPortal) {
        super(isBlanca, true, 1, 1, "Portal", new Habilidad("Summon",
                "summonea algo", 5, 0, "requiere que la casilla de summoneo esté vacía"));
        this.isTopPortal = isTopPortal;
    }

    @Override
    public void usar(TableroManager tablero, Escaque escaqueActual) {
        if (cdActual == 0) {
            int y = escaqueActual.getLocalizacion().y + (isTopPortal ? 1 : -1);
            int x = escaqueActual.getLocalizacion().x;

            if (tablero.getEscaque(x, y).isVacio()) {
                tablero.setPieza(x, y, new Catapulta(isBlanca()));
                cdActual = 5;
            }
        }
    }
}
