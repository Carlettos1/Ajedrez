package vista;

import javax.swing.JFrame;
import util.Settings;

public class Setup{
    public static JFrame iniciar(){
        JFrame frame = new JFrame(Settings.TITULO);
        frame.setSize(Settings.ANCHURA, Settings.ALTURA);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }
}