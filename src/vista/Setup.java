package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import tablero.TableroManager;
import util.Settings;

public class Setup{
    public static JFrame iniciar(TableroManager tablero){
        JFrame frame = new JFrame(Settings.TITULO);
        frame.setSize(Settings.ANCHURA_VENTANA, Settings.ALTURA);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        
        JPanel panelNegro = new JugadorVista(tablero.getJugadorNegro(), 0, tablero.getJugadorNegro().getMano());
        JPanel panelBlanco = new JugadorVista(tablero.getJugadorBlanco(), Settings.INICIO_BLANCO, tablero.getJugadorBlanco().getMano());
        
        frame.add(panelNegro);
        frame.add(panelBlanco);
        
        return frame;
    }
}