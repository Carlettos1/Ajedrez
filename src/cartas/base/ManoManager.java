package cartas.base;

import cartas.Nula;
import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import tablero.Escaque;
import tablero.TableroManager;
import vista.TableroVista;

public class ManoManager {

    private static final MouseListener listener = new MouseListener() {
        private CartaEscaque carta;

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            CartaEscaque cartaEscaque = (CartaEscaque) (e.getSource());
            if (cartaEscaque.isNula()) {
                carta = null;
                return;
            }
            carta = cartaEscaque;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            CartaEscaque cartaEscaque = (CartaEscaque) (e.getSource());
            if (cartaEscaque.isNula()) {
                return;
            }
            try {
                Container vista = cartaEscaque.getParent().getParent();
                int x = vista.getMousePosition().x;
                int y = vista.getMousePosition().y;
                if (vista.getComponentAt(x, y) instanceof TableroVista) {
                    Component tableroVista = vista.getComponentAt(x, y);
                    if (tableroVista.getComponentAt(tableroVista.getMousePosition()) instanceof Escaque) {
                        Escaque escaque = (Escaque) tableroVista.getComponentAt(tableroVista.getMousePosition());
                        if (carta != null) {
                            if (carta.getCarta().isBlanca() == carta.getMano().getTablero().getReloj().getJugadorTurnoActual().isBlanco()) {
                                if (carta.getCarta().canUsarCarta(escaque)) {
                                    carta.getCarta().usarCarta(escaque);
                                    vista.repaint();
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
    private final CartaEscaque[] cartas;
    private TableroManager tablero;

    public ManoManager() {
        cartas = new CartaEscaque[10];
        iniciarManoVacia();
    }

    public boolean canAddCarta() {
        for (CartaEscaque carta : cartas) {
            if (carta.isNula()) {
                return true;
            }
        }
        return false;
    }

    public void setTablero(TableroManager tablero) {
        this.tablero = tablero;
    }

    public TableroManager getTablero() {
        return tablero;
    }

    public void addCarta(Carta carta) {
        for (CartaEscaque escaqueCarta : cartas) {
            if (escaqueCarta.isNula()) {
                escaqueCarta.setCarta(carta);
                break;
            }
        }
    }

    public void quitarCarta(Carta carta) {
        for (CartaEscaque escaqueCarta : cartas) {
            if (escaqueCarta.getCarta().equals(carta)) {
                escaqueCarta.quitarCarta();
                break;
            }
        }
    }

    public boolean containsCarta(Carta carta) {
        for (CartaEscaque escaqueCarta : cartas) {
            if (escaqueCarta.getCarta().equals(carta)) {
                return true;
            }
        }
        return false;
    }

    public CartaEscaque getCartaEscaque(int x) {
        return cartas[x];
    }

    public int getCantidadDeCartasMaximas() {
        return 10;
    }

    public int getCantidadDeCartas() {
        int cantidad = 0;
        for (CartaEscaque escaqueCarta : cartas) {
            if (!escaqueCarta.isNula()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    private void iniciarManoVacia() {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new CartaEscaque(new Nula(), this);
            cartas[i].addMouseListener(listener);
        }
    }
}
