package piezas;

import estructuras.Inexistente;
import estructuras.Muro;
import estructuras.base.IBloquea;
import java.util.Arrays;
import jugador.Jugador;
import piezas.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;
import util.Settings;

public class Ariete extends Pieza {

    public Ariete(boolean isBlanca) {
        super("Ariete", isBlanca,
                Arrays.asList(new EnumTipo[]{EnumTipo.estructura}),
                new Habilidad("Carga de Ariete", "Se avalanza en una dirección,"
                        + "\nmientras más espacios se mueva, más piezas destruye.", 15, 0,
                        "Cada 5 espacios que recorre, atravieza 1 unidad más"
                        + "\nNecesita que se le indique hacia donde avalanzarse"
                        + "\n(arriba, abajo, derecha o izquierda)"));
    }

    @Override
    public boolean canMover(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (!escaqueFinal.isVacio()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }
        int xInicio = escaqueInicio.getLocalizacion().x;
        int xFinal = escaqueFinal.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int yFinal = escaqueFinal.getLocalizacion().y;

        int deltaX = xFinal - xInicio;
        int deltaY = yFinal - yInicio;

        return (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 0) || (Math.abs(deltaX) == 0 && Math.abs(deltaY) == 1);
    }

    @Override
    public boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        return false;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        if (!super.canUsarHabilidad(tablero, escaqueInicio, informacionExtra, jugador)) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }

        switch (informacionExtra) {
            case "arriba":
            case "abajo":
            case "derecha":
            case "izquierda":
                return true;
        }
        return false;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        int casillasPasadas = 0;
        switch (informacionExtra) {
            case "arriba":
                for (int y = escaqueInicio.getLocalizacion().y - 1; y >= 0; y--) {
                    if (tablero.getEscaque(escaqueInicio.getLocalizacion().x, y).isVacio()) {
                        casillasPasadas++;
                    } else if (tablero.getEscaque(escaqueInicio.getLocalizacion().x, y).getPieza().getTipos().contains(EnumTipo.impenetrable)) {
                        escaqueInicio.quitarPieza();
                        return;
                    } else if (tablero.getEstructura(escaqueInicio.getLocalizacion().x, y) instanceof IBloquea) {
                        escaqueInicio.quitarPieza();
                        ((IBloquea) tablero.getEstructura(escaqueInicio.getLocalizacion().x, y)).usarBloqueo(tablero, tablero.getEscaque(escaqueInicio.getLocalizacion().x, y));
                        return;
                    }
                    if (y - 1 == 0 || !tablero.getEscaque(escaqueInicio.getLocalizacion().x, y).isVacio()) {
                        for (int y2 = 0; y2 <= casillasPasadas / 5; y2++) {
                            if (y - y2 == 0 || y2 == casillasPasadas / 5) {
                                if (!tablero.getEscaque(escaqueInicio.getLocalizacion().x, y - y2).isVacio()
                                        && tablero.getPieza(escaqueInicio.getLocalizacion().x, y - y2).getTipos().contains(EnumTipo.impenetrable)) {
                                    escaqueInicio.quitarPieza();
                                    return;
                                } else if (tablero.getEstructura(escaqueInicio.getLocalizacion().x, y - y2) instanceof IBloquea) {
                                    escaqueInicio.quitarPieza();
                                    ((IBloquea) tablero.getEstructura(escaqueInicio.getLocalizacion().x, y - y2)).usarBloqueo(tablero, tablero.getEscaque(escaqueInicio.getLocalizacion().x, y - y2));
                                    return;
                                }
                                tablero.setPieza(escaqueInicio.getLocalizacion().x, y - y2, this);
                                escaqueInicio.quitarPieza();
                                break;
                            }
                            if (!tablero.getEscaque(escaqueInicio.getLocalizacion().x, y - y2).isVacio()
                                    && tablero.getPieza(escaqueInicio.getLocalizacion().x, y - y2).getTipos().contains(EnumTipo.impenetrable)) {
                                escaqueInicio.quitarPieza();
                                return;
                            } else if (tablero.getEstructura(escaqueInicio.getLocalizacion().x, y - y2) instanceof IBloquea) {
                                escaqueInicio.quitarPieza();
                                ((IBloquea) tablero.getEstructura(escaqueInicio.getLocalizacion().x, y - y2)).usarBloqueo(tablero, tablero.getEscaque(escaqueInicio.getLocalizacion().x, y - y2));
                                return;
                            }
                            tablero.quitarEntidad(escaqueInicio.getLocalizacion().x, y - y2);
                        }
                        break;
                    }
                }
                break;
            case "abajo":
                for (int y = escaqueInicio.getLocalizacion().y + 1; y < Settings.Y; y++) {
                    if (tablero.getEscaque(escaqueInicio.getLocalizacion().x, y).isVacio()) {
                        casillasPasadas++;
                    } else if (tablero.getEscaque(escaqueInicio.getLocalizacion().x, y).getPieza().getTipos().contains(EnumTipo.impenetrable)) {
                        escaqueInicio.quitarPieza();
                        return;
                    } else if (tablero.getEstructura(escaqueInicio.getLocalizacion().x, y) instanceof IBloquea) {
                        escaqueInicio.quitarPieza();
                        ((IBloquea) tablero.getEstructura(escaqueInicio.getLocalizacion().x, y)).usarBloqueo(tablero, tablero.getEscaque(escaqueInicio.getLocalizacion().x, y));
                        return;
                    }
                    if (y + 1 == Settings.Y || !tablero.getEscaque(escaqueInicio.getLocalizacion().x, y).isVacio()) {
                        for (int y2 = 0; y2 <= casillasPasadas / 5; y2++) {
                            if (y + y2 == Settings.Y - 1 || y2 == casillasPasadas / 5) {
                                if (!tablero.getEscaque(escaqueInicio.getLocalizacion().x, y + y2).isVacio()
                                        && tablero.getPieza(escaqueInicio.getLocalizacion().x, y + y2).getTipos().contains(EnumTipo.impenetrable)) {
                                    escaqueInicio.quitarPieza();
                                    return;
                                } else if (tablero.getEstructura(escaqueInicio.getLocalizacion().x, y + y2) instanceof IBloquea) {
                                    escaqueInicio.quitarPieza();
                                    ((IBloquea) tablero.getEstructura(escaqueInicio.getLocalizacion().x, y + y2)).usarBloqueo(tablero, tablero.getEscaque(escaqueInicio.getLocalizacion().x, y + y2));
                                    return;
                                }
                                tablero.setPieza(escaqueInicio.getLocalizacion().x, y + y2, this);
                                escaqueInicio.quitarPieza();
                                break;
                            }
                            if (!tablero.getEscaque(escaqueInicio.getLocalizacion().x, y + y2).isVacio()
                                    && tablero.getPieza(escaqueInicio.getLocalizacion().x, y + y2).getTipos().contains(EnumTipo.impenetrable)) {
                                escaqueInicio.quitarPieza();
                                return;
                            } else if (tablero.getEstructura(escaqueInicio.getLocalizacion().x, y + y2) instanceof IBloquea) {
                                escaqueInicio.quitarPieza();
                                ((IBloquea) tablero.getEstructura(escaqueInicio.getLocalizacion().x, y + y2)).usarBloqueo(tablero, tablero.getEscaque(escaqueInicio.getLocalizacion().x, y + y2));
                                return;
                            }
                            tablero.quitarEntidad(escaqueInicio.getLocalizacion().x, y + y2);
                        }
                        break;
                    }
                }
                break;
            case "derecha":
                for (int x = escaqueInicio.getLocalizacion().x + 1; x < Settings.X; x++) {
                    if (tablero.getEscaque(x, escaqueInicio.getLocalizacion().y).isVacio()) {
                        casillasPasadas++;
                    } else if (tablero.getEscaque(x, escaqueInicio.getLocalizacion().y).getPieza().getTipos().contains(EnumTipo.impenetrable)) {
                        escaqueInicio.quitarPieza();
                        return;
                    } else if (tablero.getEstructura(x, escaqueInicio.getLocalizacion().y) instanceof IBloquea) {
                        escaqueInicio.quitarPieza();
                        ((IBloquea) tablero.getEstructura(x, escaqueInicio.getLocalizacion().y)).usarBloqueo(tablero, tablero.getEscaque(x, escaqueInicio.getLocalizacion().y));
                        return;
                    }
                    if (x + 1 == Settings.X || !tablero.getEscaque(x, escaqueInicio.getLocalizacion().y).isVacio()) {
                        for (int x2 = 0; x2 <= casillasPasadas / 5; x2++) {
                            if (x + x2 == Settings.X - 1 || x2 == casillasPasadas / 5) {
                                if (!tablero.getEscaque(x + x2, escaqueInicio.getLocalizacion().y).isVacio()
                                        && tablero.getPieza(x + x2, escaqueInicio.getLocalizacion().y).getTipos().contains(EnumTipo.impenetrable)) {
                                    escaqueInicio.quitarPieza();
                                    return;
                                } else if (tablero.getEstructura(x + x2, escaqueInicio.getLocalizacion().y) instanceof IBloquea) {
                                    escaqueInicio.quitarPieza();
                                    ((IBloquea) tablero.getEstructura(x + x2, escaqueInicio.getLocalizacion().y)).usarBloqueo(tablero, tablero.getEscaque(x + x2, escaqueInicio.getLocalizacion().y));
                                    return;
                                }
                                tablero.setPieza(x + x2, escaqueInicio.getLocalizacion().y, this);
                                escaqueInicio.quitarPieza();
                                break;
                            }
                            if (!tablero.getEscaque(x + x2, escaqueInicio.getLocalizacion().y).isVacio()
                                    && tablero.getPieza(x + x2, escaqueInicio.getLocalizacion().y).getTipos().contains(EnumTipo.impenetrable)) {
                                escaqueInicio.quitarPieza();
                                return;
                            } else if (tablero.getEstructura(x + x2, escaqueInicio.getLocalizacion().y) instanceof IBloquea) {
                                escaqueInicio.quitarPieza();
                                ((IBloquea) tablero.getEstructura(x + x2, escaqueInicio.getLocalizacion().y)).usarBloqueo(tablero, tablero.getEscaque(x + x2, escaqueInicio.getLocalizacion().y));
                                return;
                            }
                            tablero.quitarEntidad(x + x2, escaqueInicio.getLocalizacion().y);
                        }
                        break;
                    }
                }
                break;
            case "izquierda":
                for (int x = escaqueInicio.getLocalizacion().x - 1; x >= 0; x--) {
                    if (tablero.getEscaque(x, escaqueInicio.getLocalizacion().y).isVacio()) {
                        casillasPasadas++;
                    } else if (tablero.getEscaque(x, escaqueInicio.getLocalizacion().y).getPieza().getTipos().contains(EnumTipo.impenetrable)) {
                        escaqueInicio.quitarPieza();
                        return;
                    } else if (tablero.getEstructura(x, escaqueInicio.getLocalizacion().y) instanceof IBloquea) {
                        escaqueInicio.quitarPieza();
                        ((IBloquea) tablero.getEstructura(x, escaqueInicio.getLocalizacion().y)).usarBloqueo(tablero, tablero.getEscaque(x, escaqueInicio.getLocalizacion().y));
                        return;
                    }
                    if (x == 0 || !tablero.getEscaque(x, escaqueInicio.getLocalizacion().y).isVacio()) {
                        for (int x2 = 0; x2 <= casillasPasadas / 5; x2++) {
                            if (x - x2 == 0 || x2 == casillasPasadas / 5) {
                                if (!tablero.getEscaque(x - x2, escaqueInicio.getLocalizacion().y).isVacio()
                                        && tablero.getPieza(x - x2, escaqueInicio.getLocalizacion().y).getTipos().contains(EnumTipo.impenetrable)) {
                                    escaqueInicio.quitarPieza();
                                    return;
                                } else if (tablero.getEstructura(x - x2, escaqueInicio.getLocalizacion().y) instanceof IBloquea) {
                                    escaqueInicio.quitarPieza();
                                    ((IBloquea) tablero.getEstructura(x - x2, escaqueInicio.getLocalizacion().y)).usarBloqueo(tablero, tablero.getEscaque(x - x2, escaqueInicio.getLocalizacion().y));
                                    return;
                                }
                                tablero.setPieza(x - x2, escaqueInicio.getLocalizacion().y, this);
                                escaqueInicio.quitarPieza();
                                break;
                            }
                            if (!tablero.getEscaque(x - x2, escaqueInicio.getLocalizacion().y).isVacio()
                                    && tablero.getPieza(x - x2, escaqueInicio.getLocalizacion().y).getTipos().contains(EnumTipo.impenetrable)) {
                                escaqueInicio.quitarPieza();
                                return;
                            } else if (tablero.getEstructura(x - x2, escaqueInicio.getLocalizacion().y) instanceof IBloquea) {
                                escaqueInicio.quitarPieza();
                                ((IBloquea) tablero.getEstructura(x - x2, escaqueInicio.getLocalizacion().y)).usarBloqueo(tablero, tablero.getEscaque(x - x2, escaqueInicio.getLocalizacion().y));
                                return;
                            }
                            tablero.quitarEntidad(x - x2, escaqueInicio.getLocalizacion().y);
                        }
                        break;
                    }
                }
                break;
        }

        super.habilidad(tablero, escaqueInicio, informacionExtra, jugador);
    }
}
