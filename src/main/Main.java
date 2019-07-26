package main;

import cartas.mejoras.UnTurnoC;
import cartas.summon.TorreC;
import estructuras.Portal;
import javax.swing.JFrame;
import piezas.Ariete;
import piezas.Brujo;
import piezas.Caballo;
import piezas.Catapulta;
import piezas.Defensor;
import piezas.Hechicero;
import piezas.Peon;
import tablero.TableroManager;
import util.Settings;
import vista.Setup;
import vista.TableroVista;

public class Main {

    public static void main(String[] args) {
        TableroManager tablero = TableroManager.getDefaultState();
        tablero.setPieza(8, Settings.Y - 3, new Ariete(true));
        tablero.setPieza(8, 1, new Defensor(false));
        tablero.setPieza(8, 3, new Peon(false));
        tablero.setPieza(15, 8, new Brujo(true));
        
        tablero.setPieza(10, Settings.Y - 3, new Catapulta(true));
        tablero.setPieza(6, 3, new Catapulta(false));
        tablero.setPieza(2, 5, new Hechicero(false));
        tablero.setPieza(0, 6, new Brujo(false));
        
        tablero.setPieza(0, 7, new Caballo(true));
        
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
