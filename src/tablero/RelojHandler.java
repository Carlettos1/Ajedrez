package tablero;

import jugador.Jugador;

public class RelojHandler {
    private final Jugador JugadorBlanco;
    private final Jugador jugadorNegro;
    private int movimientosHechosTotales = 0;
    private int movimientosHechos = 0;
    private boolean isTurnoBlancas = true;
    private TableroManager tablero;
    
    /**
     * @param jugadorBlanco jugador de las blancas
     * @param jugadorNegro jugador de las negras
     */
    public RelojHandler(Jugador jugadorBlanco, Jugador jugadorNegro) {
        this.JugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
    }

    public void setTablero(TableroManager tablero) {
        this.tablero = tablero;
    }
    
    public Jugador getJugadorTurnoActual(){
        return isTurnoBlancas() ? JugadorBlanco : jugadorNegro ;
    }
    
    public boolean isTurnoBlancas(){
        return isTurnoBlancas;
    }
    
    public void movimientoHecho(){
        movimientosHechosTotales++;
        movimientosHechos++;
        if (getMovimientosHechos() >= (isTurnoBlancas() ? JugadorBlanco.getMovimientosPorTurno(): jugadorNegro.getMovimientosPorTurno())) {
            movimientosHechos = 0;
            isTurnoBlancas = !isTurnoBlancas;
            
            for (Escaque[] escaques : tablero.getTablero()) {
                for (Escaque escaque : escaques) {
                    escaque.getPieza().disminurCd();
                    escaque.getPieza().setSeHaMovidoEsteTurno(false);
                }
            }
        }
        
        if(getMovimientosHechosTotales() % 10 == 0){
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
