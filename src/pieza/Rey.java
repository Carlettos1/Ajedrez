package pieza;

import java.util.Arrays;
import java.util.List;
import jugador.Jugador;
import pieza.base.Pieza;
import tablero.Escaque;
import tablero.TableroManager;
import tipo.EnumTipo;
import util.Habilidad;
import util.Settings;

public class Rey extends Pieza {

    private boolean seHaTeletransportado = false;

    public Rey(boolean isBlanca) {
        super("Rey", isBlanca, Arrays.asList(new EnumTipo[]{EnumTipo.heroica, EnumTipo.inmune, EnumTipo.biologico}),
                new Habilidad("Teletransporte", "Se teletransporta a cualquier casilla en un rango de 5"
                        + "\nSolo puede usarse una vez", 0, 2,
                        "La casilla de llegada debe estar vacía"
                        + "\nNo debe haberse teletrasportado antes en la partida"
                        + "\nNo puede teletransportarse cerca del rey enemigo"));
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

        if (Math.abs(deltaX) > 1) {
            return false;
        }
        if (Math.abs(deltaY) > 1) {
            return false;
        }

        if (!tablero.getEscaquesCercanos(escaqueFinal).stream().noneMatch((escaque) -> (escaque.getPieza().equals(new Rey(!isBlanca()))))) {
            return false;
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

        int deltaX = xFinal - xInicio;
        int deltaY = yFinal - yInicio;

        if (Math.abs(deltaX) > 1) {
            return false;
        }
        if (Math.abs(deltaY) > 1) {
            return false;
        }

        if (!tablero.getEscaquesCercanos(escaqueFinal).stream().noneMatch((escaque) -> (escaque.getPieza().equals(new Rey(!isBlanca()))))) {
            return false;
        }

        return true;
    }

    @Override
    public boolean canUsarHabilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        if(!super.canUsarHabilidad(tablero, escaqueInicio, informacionExtra, jugador)){
            return false;
        }
        if (seHaTeletransportado) {
            return false;
        }
        int deltaX;
        int deltaY;
        int xInicio = escaqueInicio.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;

        String[] infoSpliteada = informacionExtra.split(" ");
        if (infoSpliteada.length != 2) {
            return false;
        }

        try {
            deltaX = Integer.parseInt(infoSpliteada[0]);
            deltaY = Integer.parseInt(infoSpliteada[1]);
        } catch (NumberFormatException e) {
            System.out.println(e);
            return false;
        }

        deltaY = -deltaY; //para que tenga correlación con lo visual

        if (Math.abs(deltaY) > 5) {
            return false;
        }
        if (Math.abs(deltaX) > 5) {
            return false;
        }

        if (xInicio + deltaX >= Settings.X) {
            return false;
        }
        if (xInicio + deltaX < 0) {
            return false;
        }

        if (yInicio + deltaY >= Settings.Y) {
            return false;
        }
        if (yInicio + deltaY < 0) {
            return false;
        }

        Escaque escaqueFinal = tablero.getEscaque(xInicio + deltaX, yInicio + deltaY);

        if (!escaqueFinal.isVacio()) {
            return false;
        }

        if (!tablero.getEscaquesCercanos(escaqueFinal).stream().noneMatch((escaque) -> (escaque.getPieza().equals(new Rey(!isBlanca()))))) {
            return false;
        }

        return true;
    }

    @Override
    public void habilidad(TableroManager tablero, Escaque escaqueInicio, String informacionExtra, Jugador jugador) {
        seHaTeletransportado = true;
        int deltaX;
        int deltaY;
        int xInicio = escaqueInicio.getLocalizacion().x;
        int yInicio = escaqueInicio.getLocalizacion().y;

        String[] infoSpliteada = informacionExtra.split(" ");
        deltaX = Integer.parseInt(infoSpliteada[0]);
        deltaY = Integer.parseInt(infoSpliteada[1]);
        deltaY = -deltaY; // para que tenga relacción con lo visual

        Escaque escaqueFinal = tablero.getEscaque(xInicio + deltaX, yInicio + deltaY);
        escaqueFinal.setPieza(this);
        escaqueInicio.quitarPieza();
        
        super.habilidad(tablero, escaqueInicio, informacionExtra, jugador);
    }
}
