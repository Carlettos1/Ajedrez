package main;

import javax.swing.JFrame;
import pieza.Peon;
import tablero.TableroManager;
import util.Settings;
import vista.Setup;
import vista.TableroVista;

public class Main {

    public static void main(String[] args) {
        TableroManager tablero = new TableroManager(Settings.X, Settings.Y);
        tablero.setPieza(4, 0, new Peon(true));
        tablero.setPieza(4, 2, new Peon(true));
        tablero.setPieza(3, 1, new Peon(true));
        
        tablero.setPieza(0, 3, new Peon(false));
        tablero.setPieza(1, 1, new Peon(false));
        tablero.setPieza(2, 3, new Peon(false));
        
        TableroVista vista = new TableroVista(tablero);
        vista.setVisible(true);
        JFrame frame = Setup.iniciar();
        frame.add(vista);
    }
}
