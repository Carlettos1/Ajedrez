package main;

import carta.mejoras.UnTurnoC;
import carta.summon.TorreC;
import javax.swing.JFrame;
import pieza.Ariete;
import pieza.Catapulta;
import tablero.TableroManager;
import util.Settings;
import vista.Setup;
import vista.TableroVista;

public class Main {

    public static void main(String[] args) {
        TableroManager tablero = TableroManager.getDefaultState();
        tablero.setPieza(8, Settings.Y - 3, new Ariete(true));
        tablero.setPieza(8, 3, new Ariete(false));
        tablero.setPieza(10, Settings.Y - 3, new Catapulta(true));
        tablero.setPieza(6, 3, new Catapulta(false));
        TableroVista vista = new TableroVista(tablero);
        vista.setVisible(true);
        JFrame frame = Setup.iniciar(tablero);
        frame.add(vista);
        tablero.getJugadorBlanco().getMano().addCarta(new TorreC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new TorreC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new UnTurnoC(true));
        tablero.getJugadorBlanco().getMano().addCarta(new UnTurnoC(true));
        
        tablero.getJugadorNegro().getMano().addCarta(new TorreC(false));
        tablero.getJugadorNegro().getMano().addCarta(new TorreC(false));
        tablero.getJugadorNegro().getMano().addCarta(new UnTurnoC(false));
        tablero.getJugadorNegro().getMano().addCarta(new UnTurnoC(false));
    }
}
