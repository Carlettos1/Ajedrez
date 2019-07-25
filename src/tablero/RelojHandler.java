package tablero;

import jugador.Jugador;
import vista.TableroVista;

public class RelojHandler {

    private final Jugador JugadorBlanco;
    private final Jugador jugadorNegro;
    private int movimientosHechosTotales = 0;
    private int movimientosHechos = 0;
    private boolean isTurnoBlancas = true;
    private TableroManager tablero;
    private TableroVista vista;

    /**
     * @param jugadorBlanco jugador de las blancas
     * @param jugadorNegro jugador de las negras
     */
    public RelojHandler(Jugador jugadorBlanco, Jugador jugadorNegro) {
        this.JugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
    }

    public TableroVista getVista() {
        return vista;
    }

    public void setVista(TableroVista vista) {
        this.vista = vista;
    }

    public void setTablero(TableroManager tablero) {
        this.tablero = tablero;
    }

    public Jugador getJugadorTurnoActual() {
        return isTurnoBlancas() ? JugadorBlanco : jugadorNegro;
    }

    public boolean isTurnoBlancas() {
        return isTurnoBlancas;
    }

    public void movimientoHecho() {
        movimientosHechosTotales++;
        movimientosHechos++;

        vista.getMovimientos().setText(movimientosHechos + " movimientos de " + (isTurnoBlancas ? JugadorBlanco.getMovimientosPorTurno() : jugadorNegro.getMovimientosPorTurno()));
        if (getMovimientosHechos() >= (isTurnoBlancas() ? JugadorBlanco.getMovimientosPorTurno() : jugadorNegro.getMovimientosPorTurno())) {
            movimientosHechos = 0;
            isTurnoBlancas = !isTurnoBlancas;

            vista.getTurno().setText("Es el turno de las " + (isTurnoBlancas ? "Blancas" : "Negras"));
            vista.getMovimientos().setText(movimientosHechos + " movimientos de " + (isTurnoBlancas ? JugadorBlanco.getMovimientosPorTurno() : jugadorNegro.getMovimientosPorTurno()));

            for (Escaque[] escaques : tablero.getTablero()) {
                for (Escaque escaque : escaques) {
                    escaque.getPieza().disminurCd();
                    escaque.getPieza().setSeHaMovidoEsteTurno(false);
                    escaque.setIsSelected(false);
                }
            }
        }

        if (getMovimientosHechosTotales() % 10 == 0) {
            JugadorBlanco.addMana(1);
            jugadorNegro.addMana(1);
        }
    }

    public int getMovimientosHechosTotales() {
        return movimientosHechosTotales;
    }

    public Jugador getJugadorBlanco() {
        return JugadorBlanco;
    }

    public Jugador getJugadorNegro() {
        return jugadorNegro;
    }

    public int getMovimientosHechos() {
        return movimientosHechos;
    }
}
