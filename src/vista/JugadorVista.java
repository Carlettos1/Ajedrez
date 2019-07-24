package vista;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jugador.Jugador;
import util.Settings;
import carta.base.ManoManager;
import java.awt.Graphics;

public final class JugadorVista extends JPanel {

    private final Jugador jugador;

    private final ManoManager mano;

    private final JLabel titulo = new JLabel();

    public JugadorVista(Jugador jugador, int inicio, ManoManager mano) {
        setLayout(null);
        this.jugador = jugador;
        this.mano = mano;
        setBounds(inicio, 0, Settings.ANCHURA_JUGADOR, Settings.ALTURA);
        setBackground(jugador.isBlanco() ? new Color(255, 255, 255, 122) : new Color(0, 0, 0, 122));
        setVisible(true);
        initComponentes();
        repaint();
    }

    private void initComponentes() {
        titulo.setText("Jugador " + getJugador().getColor());
        titulo.setBounds(150, 10, 150, 30);
        add(titulo);
        for (int y = 0; y < mano.getCantidadDeCartasMaximas() / 2; y++) {
            for (int x = 0; x < 2; x++) {
                mano.getCartaEscaque(x * 5 + y).setBounds(x * Settings.ANCHURA_JUGADOR/2 + 30, (int)((y + 0.6) * Settings.ALTO_CARTAS_ESCAQUE), Settings.ANCHURA_JUGADOR / 3, Settings.ALTO_CARTAS_ESCAQUE - 10);
                mano.getCartaEscaque(x * 5 + y).setVisible(true);
                add(mano.getCartaEscaque(x * 5 + y));
            }
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int y = 0; y < mano.getCantidadDeCartasMaximas() / 2; y++) {
            for (int x = 0; x < 2; x++) {
                //pintar cuadrados
                g.setColor(Color.RED);
                g.drawRect(x * Settings.ANCHURA_JUGADOR/2 + 30, (int)((y + 0.6) * Settings.ALTO_CARTAS_ESCAQUE), Settings.ANCHURA_JUGADOR / 3, Settings.ALTO_CARTAS_ESCAQUE - 10);
                g.setColor(Color.BLACK);
                if (!mano.getCartaEscaque(x * 5 + y).isNula()) {
                    //TODO drawImage
                    g.drawString(mano.getCartaEscaque(x * 5 + y).getCarta().getNombre(), x * Settings.ANCHURA_JUGADOR/2 + 35, (int)((y + 0.6) * Settings.ALTO_CARTAS_ESCAQUE) + 15);
                    g.drawString(mano.getCartaEscaque(x * 5 + y).getCarta().getDescripcion(), x * Settings.ANCHURA_JUGADOR/2 + 35, (int)((y + 0.6) * Settings.ALTO_CARTAS_ESCAQUE + 30));
                }
            }
        }
    }
}
