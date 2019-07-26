package piezas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jugador.Jugador;
import piezas.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;
import util.Settings;

public class Torre extends Pieza {

    public Torre(boolean isBlanca) {
        super("Torre", isBlanca,
                Arrays.asList(new EnumTipo[]{EnumTipo.estructura}),
                new Habilidad("Muro de berlín", "Lanza todas las torres contiguas en una dirección"
                        + "\nSe detienen si alcanzan el borde del tablero, comen una pieza enemiga"
                        + "\no colisionan con una pieza aliada", 4, 0,
                        "Se puede usar sin restricciones"
                        + "\nRequiere que se indique la dirección (arriba, abajo, derecha o izquierda)"));
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

        int direccionX = 0;
        int direccionY = yFinal > yInicio ? 1 : -1;

        if (xFinal != xInicio) { //se mueve en x
            if (yFinal != yInicio) { //se mueve en y
                return false;
            }
            direccionX = xFinal > xInicio ? 1 : -1;
            direccionY = 0;
        }

        boolean isMovimientoVertical = direccionY != 0;

        for (int casilla = 1; casilla <= (isMovimientoVertical ? Math.abs(yInicio - yFinal) : Math.abs(xInicio - xFinal)); casilla++) {
            if (!tablero.getEscaque(xInicio + casilla * direccionX, yInicio + (casilla * direccionY)).isVacio()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canComer(TableroManager tablero, Escaque escaqueInicio, Escaque escaqueFinal) {
        if (escaqueInicio.getPieza().isBlanca() == escaqueFinal.getPieza().isBlanca()) {
            return false;
        }
        if (escaqueFinal.isVacio()) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }
        int xInicio = escaqueInicio.getLocalizacion().x;
        int xFinal = escaqueFinal.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;
        int yFinal = escaqueFinal.getLocalizacion().y;

        int direccionX = 0;
        int direccionY = yFinal > yInicio ? 1 : -1;

        if (xFinal != xInicio) { //se mueve en x
            if (yFinal != yInicio) { //se mueve en y
                return false;
            }
            direccionX = xFinal > xInicio ? 1 : -1;
            direccionY = 0;
        }

        boolean isMovimientoVertical = direccionY != 0;

        for (int casilla = 1; casilla < (isMovimientoVertical ? Math.abs(yInicio - yFinal) : Math.abs(xInicio - xFinal)); casilla++) {
            if (!tablero.getEscaque(xInicio + casilla * direccionX, yInicio + casilla * direccionY).isVacio()) {
                return false;
            }
        }
        return true;
    }

    private List<Escaque> getTorresAdyacentes(TableroManager tablero, Escaque escaque) {
        List<Escaque> escaques = new ArrayList<>();

        if (escaque.getLocalizacion().x + 1 < Settings.X) {
            if (tablero.getPieza(escaque.getLocalizacion().x + 1, escaque.getLocalizacion().y).equals(new Torre(escaque.getPieza().isBlanca()))) {
                escaques.add(tablero.getEscaque(escaque.getLocalizacion().x + 1, escaque.getLocalizacion().y));
            }
        }
        if (escaque.getLocalizacion().x - 1 >= 0) {
            if (tablero.getPieza(escaque.getLocalizacion().x - 1, escaque.getLocalizacion().y).equals(new Torre(escaque.getPieza().isBlanca()))) {
                escaques.add(tablero.getEscaque(escaque.getLocalizacion().x - 1, escaque.getLocalizacion().y));
            }
        }
        if (escaque.getLocalizacion().y + 1 < Settings.Y) {
            if (tablero.getPieza(escaque.getLocalizacion().x, escaque.getLocalizacion().y + 1).equals(new Torre(escaque.getPieza().isBlanca()))) {
                escaques.add(tablero.getEscaque(escaque.getLocalizacion().x, escaque.getLocalizacion().y + 1));
            }
        }
        if (escaque.getLocalizacion().y - 1 >= 0) {
            if (tablero.getPieza(escaque.getLocalizacion().x, escaque.getLocalizacion().y - 1).equals(new Torre(escaque.getPieza().isBlanca()))) {
                escaques.add(tablero.getEscaque(escaque.getLocalizacion().x, escaque.getLocalizacion().y - 1));
            }
        }
        return escaques;
    }

    private ArrayList<Escaque> ordenarTorres(String informacionExtra, ArrayList<Escaque> torres, TableroManager tablero) {
        ArrayList<Escaque> lista = new ArrayList<>();
        switch (informacionExtra) {
            case "arriba":
                for (int y = 0; y < Settings.Y; y++) {
                    for (int x = 0; x < Settings.X; x++) {
                        if (torres.contains(tablero.getEscaque(x, y))) {
                            lista.add(tablero.getEscaque(x, y));
                        }
                    }
                }
                break;
            case "abajo":
                for (int y = Settings.Y - 1; y >= 0; y--) {
                    for (int x = 0; x < Settings.X; x++) {
                        if (torres.contains(tablero.getEscaque(x, y))) {
                            lista.add(tablero.getEscaque(x, y));
                        }
                    }
                }
                break;
            case "derecha":
                for (int x = Settings.X - 1; x >= 0; x--) {
                    for (int y = 0; y < Settings.Y; y++) {
                        if (torres.contains(tablero.getEscaque(x, y))) {
                            lista.add(tablero.getEscaque(x, y));
                        }
                    }
                }
                break;
            case "izquierda":
                for (int x = 0; x < Settings.X; x++) {
                    for (int y = 0; y < Settings.Y; y++) {
                        if (torres.contains(tablero.getEscaque(x, y))) {
                            lista.add(tablero.getEscaque(x, y));
                        }
                    }
                }
        }
        return lista;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio,
            String informacionExtra, Jugador jugador) {
        if (!super.canUsarHabilidad(tablero, escaqueInicio, informacionExtra, jugador)) {
            return false;
        }

        if (seHaMovidoEsteTurno()) {
            return false;
        }
        return (informacionExtra.equals("arriba")
                || informacionExtra.equals("abajo")
                || informacionExtra.equals("derecha")
                || informacionExtra.equals("izquierda"));
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio,
            String informacionExtra, Jugador jugador) {

        ArrayList<Escaque> escaquesTorres = new ArrayList<>();

        escaquesTorres.add(escaqueInicio);
        escaquesTorres.addAll(getTorresAdyacentes(tablero, escaqueInicio));

        boolean flag = true;

        while (flag) {
            List<Escaque> tmp = new ArrayList<>();
            flag = false;
            for (Escaque escaqueTorre : escaquesTorres) {
                List<Escaque> torresAdyacentes = getTorresAdyacentes(tablero, escaqueTorre);
                for (Escaque torreAdyacente : torresAdyacentes) {
                    if (!(escaquesTorres.contains(torreAdyacente) || tmp.contains(torreAdyacente))) {
                        tmp.add(torreAdyacente);
                        flag = true;
                    }
                }
            }
            if (!tmp.isEmpty()) {
                escaquesTorres.addAll(tmp);
            }
        }

        escaquesTorres = ordenarTorres(informacionExtra, escaquesTorres, tablero);

        switch (informacionExtra) {
            case "arriba":
                escaquesTorres.forEach((torre) -> {
                    torre.getPieza().setSeHaMovidoEsteTurno(false);
                    for (int i = 0; i < Settings.Y; i++) {
                        if (i == torre.getLocalizacion().y) {
                            torre.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                        Escaque escaqueObjetivo = tablero.getEscaque(torre.getLocalizacion().x, i);
                        if (torre.getPieza().canComer(tablero, torre, escaqueObjetivo)) {
                            torre.comerPieza(escaqueObjetivo);
                            escaqueObjetivo.getPieza().setCdActual(escaqueObjetivo.getPieza().getHabilidad().getCD());
                            escaqueObjetivo.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        } else if (torre.getPieza().canMover(tablero, torre, escaqueObjetivo)) {
                            torre.moverPieza(escaqueObjetivo);
                            escaqueObjetivo.getPieza().setCdActual(escaqueObjetivo.getPieza().getHabilidad().getCD());
                            escaqueObjetivo.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                    }
                });
                break;
            case "abajo":
                escaquesTorres.forEach((torre) -> {
                    torre.getPieza().setSeHaMovidoEsteTurno(false);
                    for (int i = Settings.Y - 1; i >= 0; i--) {
                        if (i == torre.getLocalizacion().y) {
                            torre.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                        Escaque escaqueObjetivo = tablero.getEscaque(torre.getLocalizacion().x, i);
                        if (torre.getPieza().canComer(tablero, torre, escaqueObjetivo)) {
                            torre.comerPieza(escaqueObjetivo);
                            escaqueObjetivo.getPieza().setCdActual(escaqueObjetivo.getPieza().getHabilidad().getCD());
                            escaqueObjetivo.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        } else if (torre.getPieza().canMover(tablero, torre, escaqueObjetivo)) {
                            torre.moverPieza(escaqueObjetivo);
                            escaqueObjetivo.getPieza().setCdActual(escaqueObjetivo.getPieza().getHabilidad().getCD());
                            escaqueObjetivo.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                    }
                });
                break;
            case "derecha":
                escaquesTorres.forEach((torre) -> {
                    torre.getPieza().setSeHaMovidoEsteTurno(false);
                    for (int i = Settings.X - 1; i >= 0; i--) {
                        if (i == torre.getLocalizacion().x) {
                            torre.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                        Escaque escaqueObjetivo = tablero.getEscaque(i, torre.getLocalizacion().y);
                        if (torre.getPieza().canComer(tablero, torre, escaqueObjetivo)) {
                            torre.comerPieza(escaqueObjetivo);
                            escaqueObjetivo.getPieza().setCdActual(escaqueObjetivo.getPieza().getHabilidad().getCD());
                            escaqueObjetivo.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        } else if (torre.getPieza().canMover(tablero, torre, escaqueObjetivo)) {
                            torre.moverPieza(escaqueObjetivo);
                            escaqueObjetivo.getPieza().setCdActual(escaqueObjetivo.getPieza().getHabilidad().getCD());
                            escaqueObjetivo.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                    }
                });
                break;
            case "izquierda":
                escaquesTorres.forEach((torre) -> {
                    torre.getPieza().setSeHaMovidoEsteTurno(false);
                    for (int i = 0; i < Settings.X; i++) {
                        if (i == torre.getLocalizacion().x) {
                            torre.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                        Escaque escaqueObjetivo = tablero.getEscaque(i, torre.getLocalizacion().y);
                        if (torre.getPieza().canComer(tablero, torre, escaqueObjetivo)) {
                            torre.comerPieza(escaqueObjetivo);
                            escaqueObjetivo.getPieza().setCdActual(escaqueObjetivo.getPieza().getHabilidad().getCD());
                            escaqueObjetivo.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        } else if (torre.getPieza().canMover(tablero, torre, escaqueObjetivo)) {
                            torre.moverPieza(escaqueObjetivo);
                            escaqueObjetivo.getPieza().setCdActual(escaqueObjetivo.getPieza().getHabilidad().getCD());
                            escaqueObjetivo.getPieza().setSeHaMovidoEsteTurno(true);
                            break;
                        }
                    }
                });
                break;
        }
    }
}
