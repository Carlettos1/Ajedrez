package main;

import carta.summon.TorreC;
import javax.swing.JFrame;
import tablero.TableroManager;
import vista.Setup;
import vista.TableroVista;

public class Main {

    public static void main(String[] args) {
        TableroManager tablero = TableroManager.getDefaultState();
        TableroVista vista = new TableroVista(tablero);
        vista.setVisible(true);
        JFrame frame = Setup.iniciar(tablero);
        frame.add(vista);
        tablero.getJugadorBlanco().getMano().addCarta(new TorreC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new TorreC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new TorreC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new TorreC(true));
    }
}
