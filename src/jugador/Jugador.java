package jugador;

import carta.base.ManoManager;

public class Jugador {

    private final boolean isBlanco;
    private int movimientosPorTurno;
    private int mana;
    private final ManoManager mano;

    public Jugador(boolean isBlanco, ManoManager mano) {
        this.isBlanco = isBlanco;
        movimientosPorTurno = 1;
        mana = 0;
        this.mano = mano;
    }

    public boolean isBlanco() {
        return isBlanco;
    }

    public int getMovimientosPorTurno() {
        return movimientosPorTurno;
    }

    public int getMana() {
        return mana;
    }

    public ManoManager getMano(){
        return mano;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
    
    public void addMana(int quantity){
        mana += quantity;
    }
    
    public void addTurno(int quantity){
        movimientosPorTurno += quantity;
    }
            
    public String getColor(){
        return isBlanco() ? "Blanco": "Negro";
    }
}
