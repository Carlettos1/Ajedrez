package estructuras.base;

import tablero.Escaque;
import tablero.TableroManager;

public interface IBloquea {
    public void usarBloqueo(TableroManager tablero, Escaque escaqueActual);
}
