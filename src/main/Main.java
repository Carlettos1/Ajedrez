package main;

import javax.swing.JFrame;
import tablero.TableroManager;
import vista.Setup;
import vista.TableroVista;

public class Main {

    public static void main(String[] args) {
        TableroManager tablero = TableroManager.getDefaultState();
        TableroVista vista = new TableroVista(tablero);
        vista.setVisible(true);
        JFrame frame = Setup.iniciar();
        frame.add(vista);
    }
}
