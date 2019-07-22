package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import tablero.Escaque;
import tablero.TableroManager;
import util.Settings;

public final class TableroVista extends javax.swing.JPanel {

    private final TableroManager tablero;

    public TableroVista(TableroManager tablero) {
        initComponents();
        this.tablero = tablero;
        JButton boton = new JButton("Usar Habilidad");
        boton.setBounds((tablero.getColumnas()) * Settings.TILE_SIZE + 10, 10, Settings.ANCHO_BOTON, 30);
        boton.addActionListener((ActionEvent e) -> {
            if (tablero.isAnyoneSelected()) {
                Escaque escaque = tablero.getFirstSelected();
                if (escaque.getPieza().canUsarHabilidad(tablero, escaque, escaque)) {
                    escaque.habilidadPieza(escaque);
                    escaque.setIsSelected(false);
                    repaint();
                }
            }
        });
        boton.setVisible(true);
        add(boton);
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < tablero.getColumnas(); x++) {
            for (int y = 0; y < tablero.getFilas(); y++) {
                //pintar cuadrados
                g.setColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
                g.fillRect(x * Settings.TILE_SIZE, y * Settings.TILE_SIZE, Settings.TILE_SIZE, Settings.TILE_SIZE);
                tablero.getEscaque(x, y).setBounds(x * Settings.TILE_SIZE, y * Settings.TILE_SIZE, Settings.TILE_SIZE, Settings.TILE_SIZE);
                tablero.getEscaque(x, y).setVisible(true);
                add(tablero.getEscaque(x, y));

                g.setColor((x + y) % 2 == 1 ? Color.WHITE : Color.BLACK);
                if (!tablero.getEscaque(x, y).isVacio()) {
                    g.drawString(tablero.getEscaque(x, y).getPieza().getNombre(), x * Settings.TILE_SIZE + 2, y * Settings.TILE_SIZE + 15);
                    g.drawString(tablero.getEscaque(x, y).getPieza().isBlanca() ? "Blanco" : "Negro", x * Settings.TILE_SIZE + 2, y * Settings.TILE_SIZE + 25);
                }
            }
        }

        if (tablero.isAnyoneSelected()) {
            Escaque selected = tablero.getFirstSelected();
            g.setColor(Color.LIGHT_GRAY);
            selected.getPieza().marcar(g, selected);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
