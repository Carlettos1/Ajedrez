package estructuras;

import estructuras.base.Estructura;
import tablero.Escaque;
import tablero.TableroManager;

public class Inexistente extends Estructura{

    public Inexistente() {
        super(false, false, 1, 1, "Inexistente", null);
    }

    @Override
    public void usar(TableroManager tablero, Escaque escaqueActual) {
    }
}