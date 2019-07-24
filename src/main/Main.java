package main;

import javax.swing.JFrame;
import pieza.Alfil;
import pieza.Caballo;
import pieza.Peon;
import pieza.Reina;
import pieza.Torre;
import tablero.TableroManager;
import util.Settings;
import vista.Setup;
import vista.TableroVista;

public class Main {

    public static void main(String[] args) {
        TableroManager tablero = new TableroManager(Settings.X, Settings.Y);
        tablero.setPieza(4, 0, new Peon(true));
        tablero.setPieza(4, 2, new Reina(true));
        tablero.setPieza(3, 1, new Alfil(true));
        tablero.setPieza(5, 1, new Caballo(true));
        tablero.setPieza(6, 1, new Torre(true));

        tablero.setPieza(0, 3, new Alfil(false));
        tablero.setPieza(1, 1, new Peon(false));
        tablero.setPieza(2, 3, new Peon(false));
        tablero.setPieza(5, 3, new Torre(false));
        tablero.setPieza(6, 3, new Torre(false));

        TableroVista vista = new TableroVista(tablero);
        vista.setVisible(true);
        JFrame frame = Setup.iniciar();
        frame.add(vista);
    }
}
