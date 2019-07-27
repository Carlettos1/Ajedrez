package estructuras;

import estructuras.base.Estructura;
import estructuras.base.IBloquea;
import tablero.Escaque;
import tablero.TableroManager;
import util.Habilidad;

public class Muro extends Estructura implements IBloquea{

    public Muro(boolean isBlanca) {
        super(isBlanca, false, 1, 1, "Muro",
                new Habilidad("Defender", "Destruye al ariete y a la ballesta", 0, 0,
                        "Si un ariete arremete contra él, es destruido"
                        + "\nSi una ballesta lo ataca, la ballesta es destruida"));
    }

    @Override
    public void usar(TableroManager tablero, Escaque escaqueActual) {
    }

    @Override
    public void usarBloqueo(TableroManager tablero, Escaque escaqueActual) {
        escaqueActual.setEstructura(new Inexistente());
    }
}
