package piezas;

import java.util.Arrays;
import jugador.Jugador;
import piezas.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;
import util.Settings;

public class Catapulta extends Pieza {

    public Catapulta(boolean isBlanca) {
        super("Catapulta", isBlanca,
                Arrays.asList(new EnumTipo[]{EnumTipo.estructura}),
                new Habilidad("Lanzamiento de Catapulta", "Lanza una unidad transportable"
                        + "\n6 casillas hacia alguna dirección (debe estar cerca)", 7, 0,
                        "Debe indicarse la dirección (arriba, abajo, derecha o izquierda)"
                        + "\nAdemás de, separado con un espacio, que unidad va a lanzar"
                        + "\nGraficación:"
                        + "\n7   8   9"
                        + "\n4   C   6"
                        + "\n1   2   3, donde C es la catapulta, y el número la unidad a lanzar"
                        + "\nEj: \'arriba 2\', lanza la unidad justo abajo de la catapulta"));
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

        String direccionS;
        String unidad;
        String[] tmp = informacionExtra.split(" ");

        if (tmp.length != 2) {
            return false;
        }

        direccionS = tmp[0];
        unidad = tmp[1];

        int direccionX = 0;
        int direccionY = 0;
        switch (direccionS) {
            case "arriba":
                direccionY = -1;
                break;
            case "abajo":
                direccionY = 1;
                break;
            case "derecha":
                direccionX = 1;
                break;
            case "izquierda":
                direccionX = -1;
                break;
            default:
                return false;
        }
        int X = escaqueInicio.getLocalizacion().x;
        int Y = escaqueInicio.getLocalizacion().y;

        if (direccionX * 6 + X >= Settings.X || direccionX * 6 + X < 0) {
            return false;
        }

        if (direccionY * 6 + Y >= Settings.Y || direccionY * 6 + Y < 0) {
            return false;
        }

        switch (unidad) {
            case "1":
                if (X - 1 >= 0 && Y + 1 < Settings.Y) {
                    if (tablero.getEscaque(X - 1, Y + 1).getPieza().getTipos().contains(EnumTipo.transportable)) {
                        return true;
                    }
                }
                break;
            case "2":
                if (Y + 1 < Settings.Y) {
                    if (tablero.getEscaque(X, Y + 1).getPieza().getTipos().contains(EnumTipo.transportable)) {
                        return true;
                    }
                }
                break;
            case "3":
                if (X + 1 < Settings.X && Y + 1 < Settings.Y) {
                    if (tablero.getEscaque(X + 1, Y + 1).getPieza().getTipos().contains(EnumTipo.transportable)) {
                        return true;
                    }
                }
                break;
            case "4":
                if (X - 1 >= 0) {
                    if (tablero.getEscaque(X - 1, Y).getPieza().getTipos().contains(EnumTipo.transportable)) {
                        return true;
                    }
                }
                break;
            case "6":
                if (X + 1 < Settings.X) {
                    if (tablero.getEscaque(X + 1, Y).getPieza().getTipos().contains(EnumTipo.transportable)) {
                        return true;
                    }
                }
                break;
            case "7":
                if (X - 1 >= 0 && Y - 1 >= 0) {
                    if (tablero.getEscaque(X - 1, Y - 1).getPieza().getTipos().contains(EnumTipo.transportable)) {
                        return true;
                    }
                }
                break;
            case "8":
                if (Y - 1 >= 0) {
                    if (tablero.getEscaque(X, Y - 1).getPieza().getTipos().contains(EnumTipo.transportable)) {
                        return true;
                    }
                }
                break;
            case "9":
                if (X + 1 < Settings.X && Y - 1 >= 0) {
                    if (tablero.getEscaque(X + 1, Y - 1).getPieza().getTipos().contains(EnumTipo.transportable)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        System.out.println("CAPATULTA");

        String direccionS;
        String unidad;
        String[] tmp = informacionExtra.split(" ");

        direccionS = tmp[0];
        unidad = tmp[1];

        int direccionX = 0;
        int direccionY = 0;
        switch (direccionS) {
            case "arriba":
                direccionY = -1;
                break;
            case "abajo":
                direccionY = 1;
                break;
            case "derecha":
                direccionX = 1;
                break;
            case "izquierda":
                direccionX = -1;
                break;
        }

        int X = escaqueInicio.getLocalizacion().x;
        int Y = escaqueInicio.getLocalizacion().y;

        int xFinal = direccionX * 6 + X;
        int yFinal = direccionY * 6 + Y;

        switch (unidad) {
            case "1":
                tablero.setPieza(xFinal, yFinal, tablero.getEscaque(X - 1, Y + 1).getPieza());
                tablero.getEscaque(X - 1, Y + 1).quitarPieza();
                break;
            case "2":
                tablero.setPieza(xFinal, yFinal, tablero.getEscaque(X, Y + 1).getPieza());
                tablero.getEscaque(X, Y + 1).quitarPieza();
                break;
            case "3":
                tablero.setPieza(xFinal, yFinal, tablero.getEscaque(X + 1, Y + 1).getPieza());
                tablero.getEscaque(X + 1, Y + 1).quitarPieza();
                break;
            case "4":
                tablero.setPieza(xFinal, yFinal, tablero.getEscaque(X - 1, Y).getPieza());
                tablero.getEscaque(X - 1, Y).quitarPieza();

                break;
            case "6":
                tablero.setPieza(xFinal, yFinal, tablero.getEscaque(X + 1, Y).getPieza());
                tablero.getEscaque(X + 1, Y).quitarPieza();
                break;
            case "7":
                tablero.setPieza(xFinal, yFinal, tablero.getEscaque(X - 1, Y - 1).getPieza());
                tablero.getEscaque(X - 1, Y - 1).quitarPieza();
                break;
            case "8":
                tablero.setPieza(xFinal, yFinal, tablero.getEscaque(X, Y - 1).getPieza());
                tablero.getEscaque(X, Y - 1).quitarPieza();
                break;
            case "9":
                tablero.setPieza(xFinal, yFinal, tablero.getEscaque(X + 1, Y - 1).getPieza());
                tablero.getEscaque(X + 1, Y - 1).quitarPieza();
                break;
        }
        tablero.getPieza(xFinal, yFinal).setSeHaMovidoEsteTurno(true);
        super.habilidad(tablero, escaqueInicio, informacionExtra, jugador);
    }
}
