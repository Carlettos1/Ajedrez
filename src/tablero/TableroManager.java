package tablero;

import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import pieza.Torre;
import pieza.Vacia;
import pieza.base.Pieza;
import util.Settings;

public class TableroManager {

    public static final MouseListener listener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("Pressed!");
            if (!(e.getSource() instanceof Escaque)) {
                return;
            }
            Escaque escaque = (Escaque) (e.getSource());
            if (escaque.isVacio() && !escaque.getTablero().isAnyoneSelected()) {
                return;
            }
            Container parent = escaque.getParent().getParent().getParent().getParent().getParent();

            if (escaque.getTablero().isAnyoneSelected()) {
                Escaque anteriorSelected = escaque.getTablero().getFirstSelected();
                if (anteriorSelected.equals(escaque)) {
                    escaque.setIsSelected(false);
                } else {
                    if ((escaque.getPieza().isBlanca() == anteriorSelected.getPieza().isBlanca()) && !escaque.isVacio()) {
                        escaque.setIsSelected(true);
                        anteriorSelected.setIsSelected(false);
                    } else if (escaque.isVacio()) {
                        if (anteriorSelected.moverPieza(escaque)) {
                            escaque.setIsSelected(false);
                            anteriorSelected.setIsSelected(false);
                        } else {
                            escaque.setIsSelected(false);
                            anteriorSelected.setIsSelected(false);
                        }
                    } else {
                        if (anteriorSelected.comerPieza(escaque)) {
                            escaque.setIsSelected(false);
                            anteriorSelected.setIsSelected(false);
                        } else {
                            escaque.setIsSelected(true);
                            anteriorSelected.setIsSelected(false);
                        }
                    }
                }
            } else {
                escaque.setIsSelected(true);
            }
            parent.repaint();
            escaque.getTablero().show();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("Released!");
            if (!(e.getSource() instanceof Escaque)) {
                return;
            }

            Escaque escaque = (Escaque) (e.getSource());

            if (escaque.isVacio()) {
                return;
            }

            Container parent = escaque.getParent().getParent().getParent().getParent().getParent();
            Point loc = parent.getLocation();
            int x = (MouseInfo.getPointerInfo().getLocation().x - loc.x + 38) / Settings.TILE_SIZE - 1;
            int y = (MouseInfo.getPointerInfo().getLocation().y - loc.y + 7) / Settings.TILE_SIZE - 1;
            Escaque destino = escaque.getTablero().getEscaque(x, y);

            if (destino.equals(escaque)) {
                return;
            }

            escaque.moverPieza(destino);
            escaque.comerPieza(destino);
            parent.repaint();
            escaque.setIsSelected(false);
            escaque.getTablero().show();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
    private final Escaque[][] tablero;
    private final int columnas;
    private final int filas;

    public TableroManager(int columnas, int filas) {
        this.tablero = new Escaque[filas][columnas];
        this.columnas = columnas;
        this.filas = filas;
        iniciarTableroVacio();
    }

    private void iniciarTableroVacio() {
        for (int y = 0; y < tablero.length; y++) {
            for (int x = 0; x < tablero[y].length; x++) {
                tablero[y][x] = new Escaque(new Vacia(), new Point(x, y), this);
                tablero[y][x].addMouseListener(listener);
            }
        }
    }

    public void quitarPieza(int x, int y) {
        setPieza(x, y, new Vacia());
    }

    public Escaque getEscaque(int x, int y) {
        return tablero[y][x];
    }

    public void setPieza(int x, int y, Pieza pieza) {
        tablero[y][x].setPieza(pieza);
    }

    public Pieza getPieza(Point punto) {
        return getPieza(punto.x, punto.y);
    }

    public Pieza getPieza(int x, int y) {
        return getEscaque(x, y).getPieza();
    }

    public Escaque[][] getTablero() {
        return tablero;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void show() {
        for (Escaque[] escaques : tablero) {
            for (Escaque escaque : escaques) {
                System.out.print(escaque.getPieza().getNombre().substring(0, 1) + (escaque.isSelected() ? "-S " : "   "));
            }
            System.out.println("");
        }
    }

    public boolean isAnyoneSelected() {
        for (Escaque[] escaques : tablero) {
            for (Escaque escaque : escaques) {
                if (escaque.isSelected() && !escaque.isVacio()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Escaque getFirstSelected() {
        for (Escaque[] escaques : tablero) {
            for (Escaque escaque : escaques) {
                if (escaque.isSelected()) {
                    return escaque;
                }
            }
        }
        return null;
    }

    public List<Escaque> getEscaquesCercanos(Escaque escaque) {

        List<Escaque> escaques = new ArrayList<>();

        int xInicio = escaque.getLocalizacion().x;
        int yInicio = escaque.getLocalizacion().y;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (x == y && x == 1) {
                    continue;
                }
                if (yInicio + y - 1 < Settings.Y && yInicio + y - 1 >= 0) {
                    if (xInicio + x - 1 < Settings.X && xInicio + x - 1 >= 0) {
                        escaques.add(getEscaque(xInicio + x - 1, yInicio + y - 1));
                    }
                }
            }
        }
        return escaques;
    }
}
