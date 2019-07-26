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
import piezas.Ariete;
import piezas.Brujo;
import piezas.Caballo;
import piezas.Catapulta;
import piezas.Defensor;
import piezas.Hechicero;
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
        
        tm.getEscaque(0, 7).setIsFuenteDeMagia(true);
        tm.getEscaque(0, 9).setIsFuenteDeMagia(true);
        tm.getEscaque(15, 7).setIsFuenteDeMagia(true);
        tm.getEscaque(15, 9).setIsFuenteDeMagia(true);
        
        //tm.setPieza(0, 0, new Cañon(false));
        //tm.setPieza(15, 0, new Cañon(false));
        //tm.setPieza(0, 16, new Cañon(true));
        //tm.setPieza(15, 16, new Cañon(true));
        
        tm.setPieza(1, 0, new Torre(false));
        tm.setPieza(14, 0, new Torre(false));
        tm.setPieza(1, 16, new Torre(true));
        tm.setPieza(14, 16, new Torre(true));
        
        tm.setPieza(2, 0, new Catapulta(false));
        tm.setPieza(13, 0, new Catapulta(false));
        tm.setPieza(2, 16, new Catapulta(true));
        tm.setPieza(13, 16, new Catapulta(true));
        
        tm.setPieza(3, 0, new Caballo(false));
        tm.setPieza(12, 0, new Caballo(false));
        tm.setPieza(3, 16, new Caballo(true));
        tm.setPieza(12, 16, new Caballo(true));
        
        tm.setPieza(4, 0, new Brujo(false));
        tm.setPieza(11, 0, new Brujo(false));
        tm.setPieza(4, 16, new Brujo(true));
        tm.setPieza(11, 16, new Brujo(true));
        
        tm.setPieza(5, 0, new Alfil(false));
        tm.setPieza(10, 0, new Alfil(false));
        tm.setPieza(5, 16, new Alfil(true));
        tm.setPieza(10, 16, new Alfil(true));
        
        tm.setPieza(6, 0, new Hechicero(false));
        //tm.setPieza(9, 0, new Paladin(false));
        tm.setPieza(6, 16, new Hechicero(true));
        //tm.setPieza(9, 16, new Paladin(true));
        
        tm.setPieza(7, 0, new Reina(false));
        tm.setPieza(8, 0, new Rey(false));
        tm.setPieza(7, 16, new Reina(true));
        tm.setPieza(8, 16, new Rey(true));
        
        //tm.setPieza(0, 1, new Nao(false));
        //tm.setPieza(15, 1, new Nao(false));
        //tm.setPieza(0, 15, new Nao(true));
        //tm.setPieza(15, 15, new Nao(true));
        
        //tm.setPieza(1, 1, new Ingeniero(false));
        //tm.setPieza(14, 1, new Ingeniero(false));
        //tm.setPieza(1, 15, new Ingeniero(true));
        //tm.setPieza(14, 15, new Ingeniero(true));
        
        tm.setPieza(2, 1, new Ariete(false));
        tm.setPieza(13, 1, new Ariete(false));
        tm.setPieza(2, 15, new Ariete(true));
        tm.setPieza(13, 15, new Ariete(true));
        
        //tm.setPieza(3, 1, new Constructor(false));
        //tm.setPieza(12, 1, new Constructor(false));
        //tm.setPieza(3, 15, new Constructor(true));
        //tm.setPieza(12, 15, new Constructor(true));
        
        tm.setPieza(4, 1, new Peon(false));
        tm.setPieza(11, 1, new Peon(false));
        tm.setPieza(4, 15, new Peon(true));
        tm.setPieza(11, 15, new Peon(true));
        
        tm.setPieza(5, 1, new Peon(false));
        tm.setPieza(10, 1, new Peon(false));
        tm.setPieza(5, 15, new Peon(true));
        tm.setPieza(10, 15, new Peon(true));
        
        //tm.setPieza(6, 1, new PeonLoco(false));
        //tm.setPieza(9, 1, new PeonLoco(false));
        //tm.setPieza(6, 15, new PeonLoco(true));
        //tm.setPieza(9, 15, new PeonLoco(true));
        
        //tm.setPieza(7, 1, new SuperPeon(false));
        //tm.setPieza(8, 1, new SuperPeon(false));
        //tm.setPieza(7, 15, new SuperPeon(true));
        //tm.setPieza(8, 15, new SuperPeon(true));
        
        //tm.setPieza(0, 2, new Ballesta(false));
        //tm.setPieza(15, 2, new Ballesta(false));
        //tm.setPieza(0, 14, new Ballesta(true));
        //tm.setPieza(15, 14, new Ballesta(true));
        
        //tm.setPieza(1, 2, new Arquero(false));
        //tm.setPieza(14, 2, new Arquero(false));
        //tm.setPieza(1, 14, new Arquero(true));
        //tm.setPieza(14, 14, new Arquero(true));
        
        //tm.setPieza(2, 2, new Arquero(false));
        //tm.setPieza(13, 2, new Arquero(false));
        //tm.setPieza(2, 14, new Arquero(true));
        //tm.setPieza(13, 14, new Arquero(true));
        
        tm.setPieza(3, 2, new Defensor(false));
        tm.setPieza(12, 2, new Defensor(false));
        tm.setPieza(3, 14, new Defensor(true));
        tm.setPieza(12, 14, new Defensor(true));
        
        tm.setPieza(0, 3, new Peon(false));
        tm.setPieza(15, 3, new Peon(false));
        tm.setPieza(0, 13, new Peon(true));
        tm.setPieza(15, 13, new Peon(true));
        
        //tm.setPieza(1, 3, new PeonLoco(false));
        //tm.setPieza(14, 3, new PeonLoco(false));
        //tm.setPieza(1, 13, new PeonLoco(true));
        //tm.setPieza(14, 13, new PeonLoco(true));
        
        tm.setPieza(2, 3, new Peon(false));
        tm.setPieza(13, 3, new Peon(false));
        tm.setPieza(2, 13, new Peon(true));
        tm.setPieza(13, 13, new Peon(true));
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
