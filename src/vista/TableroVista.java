package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import tablero.Escaque;
import tablero.TableroManager;
import util.Habilidad;
import util.Settings;

public final class TableroVista extends javax.swing.JPanel {

    private final JButton botonUsarHabilidad, botonInfo, botonInfoHabilidad;
    private final JLabel label;
    private final JTextField informacionExtra;

    private final TableroManager tablero;

    public TableroVista(TableroManager tablero) {
        initComponents();
        this.tablero = tablero;

        label = new JLabel("Acá va la info adicional:");
        label.setBounds((tablero.getColumnas()) * Settings.TILE_SIZE + 20, 60, Settings.ANCHO_BOTON, 20);
        add(label);

        informacionExtra = new JTextField();
        informacionExtra.setBounds((tablero.getColumnas()) * Settings.TILE_SIZE + 20 + Settings.ANCHURA_JUGADOR, 80, Settings.ANCHO_BOTON, 30);
        informacionExtra.setVisible(true);
        add(informacionExtra);

        botonUsarHabilidad = new JButton("Usar Habilidad");
        botonUsarHabilidad.setBounds((tablero.getColumnas()) * Settings.TILE_SIZE + 20 + Settings.ANCHURA_JUGADOR, 10, Settings.ANCHO_BOTON, 30);
        botonUsarHabilidad.addActionListener((ActionEvent e) -> {
            if (tablero.isAnyoneSelected()) {
                Escaque escaque = tablero.getFirstSelected();
                String información = informacionExtra.getText().trim().toLowerCase()
                        .replaceAll("á", "a").replaceAll("é", "e").replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
                if (escaque.getPieza().canUsarHabilidad(tablero, escaque, información, tablero.getReloj().getJugadorTurnoActual())) {
                    escaque.habilidadPieza(información, tablero.getReloj().getJugadorTurnoActual());
                    escaque.setIsSelected(false);
                    informacionExtra.setText("");
                    tablero.getReloj().movimientoHecho();
                    getParent().getParent().getParent().getParent().repaint();
                }
            }
        });
        botonUsarHabilidad.setVisible(true);
        add(botonUsarHabilidad);

        botonInfo = new JButton("Info Habilidad");
        botonInfo.setBounds((tablero.getColumnas()) * Settings.TILE_SIZE + 20 + Settings.ANCHURA_JUGADOR, 130, Settings.ANCHO_BOTON, 30);
        botonInfo.addActionListener((ActionEvent e) -> {
            if (tablero.isAnyoneSelected()) {
                Habilidad habilidad = tablero.getFirstSelected().getPieza().getHabilidad();
                JOptionPane.showMessageDialog(null, habilidad.getNombre()
                        + "\nTiempo de enfriamiento = " + habilidad.getCD()
                        + "\nCoste de Maná = " + habilidad.getCosto()
                        + "\n\n" + habilidad.getDescripcion()
                        + "\n\n" + habilidad.getParametros(),
                        tablero.getFirstSelected().getPieza().getNombre(), JOptionPane.INFORMATION_MESSAGE);
            }
        });
        botonInfo.setVisible(true);
        add(botonInfo);

        botonInfoHabilidad = new JButton("Estado Actual");
        botonInfoHabilidad.setBounds((tablero.getColumnas()) * Settings.TILE_SIZE + 20 + Settings.ANCHURA_JUGADOR, 180, Settings.ANCHO_BOTON, 30);
        botonInfoHabilidad.addActionListener((ActionEvent e) -> {
            if (tablero.isAnyoneSelected()) {
                Escaque escaque = tablero.getFirstSelected();
                Habilidad habilidad = escaque.getPieza().getHabilidad();
                JOptionPane.showMessageDialog(null, habilidad.getNombre()
                        + "\nTiempo de enfriamiento actual = " + escaque.getPieza().getCdActual()
                        + "\nManá Actual/Mana Necesario = " + escaque.getDueño().getMana() + "/" + habilidad.getCosto()
                        + "\nSe ha movido este turno? " + (escaque.getPieza().seHaMovidoEsteTurno() ? "Si" : "No"),
                        tablero.getFirstSelected().getPieza().getNombre(), JOptionPane.INFORMATION_MESSAGE);
            }
        });
        botonInfoHabilidad.setVisible(true);
        add(botonInfoHabilidad);

        for (int x = 0; x < tablero.getColumnas(); x++) {
            for (int y = 0; y < tablero.getFilas(); y++) {
                tablero.getEscaque(x, y).setBounds(x * Settings.TILE_SIZE + Settings.ANCHURA_JUGADOR + 10, y * Settings.TILE_SIZE, Settings.TILE_SIZE, Settings.TILE_SIZE);
                tablero.getEscaque(x, y).setVisible(true);
                add(tablero.getEscaque(x, y));
            }
        }
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
                g.fillRect(x * Settings.TILE_SIZE + Settings.ANCHURA_JUGADOR + 10, y * Settings.TILE_SIZE, Settings.TILE_SIZE, Settings.TILE_SIZE);

                g.setColor((x + y) % 2 == 1 ? Color.WHITE : Color.BLACK);
                if (!tablero.getEscaque(x, y).isVacio()) {
                    //TODO drawImage
                    g.drawString(tablero.getEscaque(x, y).getPieza().getNombre(), x * Settings.TILE_SIZE + 12 + Settings.ANCHURA_JUGADOR, y * Settings.TILE_SIZE + 15);
                    g.drawString(tablero.getEscaque(x, y).getPieza().isBlanca() ? "Blanco" : "Negro", x * Settings.TILE_SIZE + 12 + Settings.ANCHURA_JUGADOR, y * Settings.TILE_SIZE + 25);
                }
            }
        }

        if (tablero.isAnyoneSelected()) {
            Escaque selected = tablero.getFirstSelected();
            g.setColor(Color.LIGHT_GRAY);
            selected.getPieza().marcar(g, selected);
            g.setColor(new Color(0, 0, 255, 50));
            g.fillRect(selected.getLocalizacion().x * Settings.TILE_SIZE + Settings.ANCHURA_JUGADOR + 10,
                    selected.getLocalizacion().y * Settings.TILE_SIZE,
                    Settings.TILE_SIZE, Settings.TILE_SIZE);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
