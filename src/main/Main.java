package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pieza.Alfil;
import pieza.Peon;
import pieza.Torre;
import tablero.TableroManager;
import util.Settings;
import vista.Setup;
import vista.TableroVista;

public class Main {

    public static void main(String[] args) {
        TableroManager tablero = new TableroManager(Settings.X, Settings.Y);
        tablero.setPieza(4, 0, new Peon(true));
        tablero.setPieza(4, 2, new Peon(true));
        tablero.setPieza(3, 1, new Alfil(true));
        tablero.setPieza(5, 1, new Torre(true));
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

        /*JOptionPane.showMessageDialog(null, " Para usar una habilidad especial debes escribir la información adicional"
                + "\ny tener seleccionada la pieza (para saber si está seleccionada solo hace falta"
                + "\nver si aparece su movimiento o si la casilla está levemente azul)"
                + "\n\nEl peón requiere que escribas la pieza "
                + "\n(en minúscula y sin tilde)"
                + "\nen la que se va a coronar"
                + "\n\nEl  Alfil requiere que escribas arriba, abajo, derecha o izquierda"
                + "\n(recuerda que la habilidad del alfil es el poder cambiar de color)"
                + "\n\n hasta ahora solo existen esas 2 piezas uwu");*/
    }
}
