package main;

import cartas.mejoras.UnTurnoC;
import cartas.summon.TorreC;
import javax.swing.JFrame;
import tablero.TableroManager;
import vista.Setup;
import vista.TableroVista;

public class Main {

    public static void main(String[] args) {
        TableroManager tablero = TableroManager.getDefaultState();
        
        tablero.getJugadorBlanco().getMano().addCarta(new TorreC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new TorreC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new UnTurnoC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new UnTurnoC(true));
        tablero.getJugadorBlanco().setMana(50);
        
        tablero.getJugadorNegro().getMano().addCarta(new TorreC(false));
        tablero.getJugadorNegro().getMano().addCarta(new TorreC(false));
        tablero.getJugadorNegro().getMano().addCarta(new UnTurnoC(false));
        tablero.getJugadorNegro().getMano().addCarta(new UnTurnoC(false));
        
        TableroVista vista = new TableroVista(tablero);
        vista.setVisible(true);
        
        tablero.getReloj().setVista(vista);
        
        JFrame frame = Setup.iniciar(tablero);
        frame.add(vista);
    }
}
