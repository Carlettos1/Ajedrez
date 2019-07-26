package tablero;

import cartas.base.ManoManager;
import estructuras.Inexistente;
import estructuras.base.Estructura;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import jugador.Jugador;
import piezas.Alfil;
import piezas.Caballo;
import piezas.Peon;
import piezas.Reina;
import piezas.Rey;
import piezas.Torre;
import piezas.Vacia;
import piezas.base.Pieza;
import util.Settings;

public class TableroManager {

    public static final MouseListener listener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Escaque escaque = (Escaque) (e.getSource());

            if (escaque.isVacio() && !escaque.getTablero().isAnyoneSelected()) {
                return;
            }

            Container panel = escaque.getParent().getParent().getParent().getParent().getParent();

            if (escaque.getTablero().isAnyoneSelected()) {
                Escaque anteriorSelected = escaque.getTablero().getFirstSelected();
                if (anteriorSelected.equals(escaque)) {
                    escaque.setIsSelected(false);
                } else {
                    if (escaque.isVacio()) {
                        if (anteriorSelected.moverPieza(escaque)) {
                            escaque.getTablero().getReloj().movimientoHecho();
                        }
                        anteriorSelected.setIsSelected(false);
                    } else if (escaque.getPieza().isBlanca() == anteriorSelected.getPieza().isBlanca()) {
                        if (escaque.getTablero().getReloj().isTurnoBlancas() == escaque.isEntidadBlanca()) {
                            anteriorSelected.setIsSelected(false);
                            escaque.setIsSelected(true);
                        } else {
                            anteriorSelected.setIsSelected(false);
                            escaque.setIsSelected(false);
                        }
                    } else {
                        if (anteriorSelected.comerPieza(escaque)) {
                            escaque.getTablero().getReloj().movimientoHecho();
                        }
                        anteriorSelected.setIsSelected(false);
                    }
                }
            } else {
                if (escaque.getTablero().getReloj().isTurnoBlancas() && escaque.isEntidadBlanca()) {
                    escaque.setIsSelected(true);
                } else if (escaque.getTablero().getReloj().isTurnoBlancas() == escaque.isEntidadBlanca()) {
                    escaque.setIsSelected(true);
                }
            }
            panel.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
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
    private final Jugador jugadorBlanco;
    private final Jugador jugadorNegro;
    private final Baraja barajaJugadorBlanco;
    private final Baraja barajaJugadorNegro;
    private final RelojHandler reloj;

    public TableroManager(int columnas, int filas, RelojHandler reloj, Baraja barajaJugadorNegro, Baraja barajaJugadorBlanco) {
        this(columnas, filas, reloj, reloj.getJugadorBlanco(), reloj.getJugadorNegro(), barajaJugadorNegro, barajaJugadorBlanco);
    }

    public TableroManager(int columnas, int filas, Jugador jugadorBlanco, Jugador jugadorNegro, Baraja barajaJugadorNegro, Baraja barajaJugadorBlanco) {
        this(columnas, filas, new RelojHandler(jugadorBlanco, jugadorNegro), jugadorBlanco, jugadorNegro, barajaJugadorNegro, barajaJugadorBlanco);
    }

    public TableroManager(int columnas, int filas, RelojHandler reloj, Jugador jugadorBlanco, Jugador jugadorNegro, Baraja barajaJugadorNegro, Baraja barajaJugadorBlanco) {
        this.tablero = new Escaque[filas][columnas];
        this.columnas = columnas;
        this.filas = filas;
        this.reloj = reloj;
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
        this.barajaJugadorBlanco = barajaJugadorBlanco;
        this.barajaJugadorNegro = barajaJugadorNegro;
        iniciarTableroVacio();
    }

    private void iniciarTableroVacio() {
        for (int y = 0; y < tablero.length; y++) {
            for (int x = 0; x < tablero[y].length; x++) {
                tablero[y][x] = new Escaque(new Vacia(), new Point(x, y), this);
                tablero[y][x].setEstructura(new Inexistente());
                tablero[y][x].addMouseListener(listener);
            }
        }
        reloj.setTablero(this);
    }

    public void quitarEntidad(int x, int y) {
        tablero[y][x].setPieza(new Vacia());
        tablero[y][x].setEstructura(new Inexistente());
    }

    public Escaque getEscaque(int x, int y) {
        return tablero[y][x];
    }

    public void setPieza(int x, int y, Pieza pieza) {
        quitarEntidad(x, y);
        tablero[y][x].setPieza(pieza);
    }
    
    public void setEstructura(int x, int y, Estructura estructura) {
        quitarEntidad(x, y);
        tablero[y][x].setEstructura(estructura);
    }

    public Pieza getPieza(Point punto) {
        return getPieza(punto.x, punto.y);
    }

    public Pieza getPieza(int x, int y) {
        return getEscaque(x, y).getPieza();
    }

    public Estructura getEstructura(Point punto) {
        return getEstructura(punto.x, punto.y);
    }

    public Estructura getEstructura(int x, int y) {
        return getEscaque(x, y).getEstructura();
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
                System.out.print(escaque.getPieza().getNombre().substring(0, 1) + (escaque.getPieza().getCdActual() != 0 ? escaque.getPieza().getCdActual() : " ") + " ");
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

    public static TableroManager getDefaultState() {
        TableroManager tm = new TableroManager(Settings.X, Settings.Y,
                new RelojHandler(new Jugador(true, new ManoManager()), new Jugador(false, new ManoManager())),
                new Baraja(), new Baraja());
        tm.getJugadorBlanco().getMano().setTablero(tm);
        tm.getJugadorNegro().getMano().setTablero(tm);

        if (Settings.X != 16 || Settings.Y != 17) {
            return tm;
        }

        for (int x = 0; x < Settings.X; x++) {
            tm.setPieza(x, Settings.Y - 2, new Peon(true));
            tm.setPieza(x, 1, new Peon(false));
        }

        tm.setPieza(0, 0, new Torre(false));
        tm.setPieza(Settings.X - 1, 0, new Torre(false));
        tm.setPieza(0, Settings.Y - 1, new Torre(true));
        tm.setPieza(Settings.X - 1, Settings.Y - 1, new Torre(true));

        tm.setPieza(1, 0, new Caballo(false));
        tm.setPieza(Settings.X - 2, 0, new Caballo(false));
        tm.setPieza(1, Settings.Y - 1, new Caballo(true));
        tm.setPieza(Settings.X - 2, Settings.Y - 1, new Caballo(true));

        tm.setPieza(2, 0, new Alfil(false));
        tm.setPieza(Settings.X - 3, 0, new Alfil(false));
        tm.setPieza(2, Settings.Y - 1, new Alfil(true));
        tm.setPieza(Settings.X - 3, Settings.Y - 1, new Alfil(true));

        tm.setPieza(3, 0, new Reina(false));
        tm.setPieza(Settings.X - 4, 0, new Rey(false));
        tm.setPieza(3, Settings.Y - 1, new Reina(true));
        tm.setPieza(Settings.X - 4, Settings.Y - 1, new Rey(true));

        tm.getEscaque(0, 7).setIsFuenteDeMagia(true);
        tm.getEscaque(0, 9).setIsFuenteDeMagia(true);
        tm.getEscaque(Settings.X - 1, 7).setIsFuenteDeMagia(true);
        tm.getEscaque(Settings.X - 1, 9).setIsFuenteDeMagia(true);
        
        return tm;
    }

    public Jugador getJugadorBlanco() {
        return jugadorBlanco;
    }

    public Jugador getJugadorNegro() {
        return jugadorNegro;
    }

    public Baraja getBarajaJugadorBlanco() {
        return barajaJugadorBlanco;
    }

    public Baraja getBarajaJugadorNegro() {
        return barajaJugadorNegro;
    }

    public RelojHandler getReloj() {
        return reloj;
    }
}
