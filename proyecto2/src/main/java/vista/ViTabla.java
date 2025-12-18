package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

import java.util.ArrayList;

public class ViTabla extends JPanel {
    private JTable tabla;
    private JScrollPane scrollPane;
    private ArrayList<Integer> filasSeleccionadas;

    public ViTabla() {
        propiedades();
    }

    private void propiedades() {
        filasSeleccionadas = new ArrayList<>();
        configurarPanel();
        configurarTabla();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setOpaque(false);
    }

    private void configurarTabla() {
        tabla = new JTable();
        estilarTabla();

        scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    private void estilarTabla() {
        tabla.setRowHeight(30);
        tabla.setFont(Estilos.FONT_TEXTO);
        tabla.setGridColor(Estilos.BEIGE_CANVAS);
        tabla.setShowVerticalLines(false);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(Estilos.FONT_BOTON);
        header.setBackground(Estilos.COLOR_TABLA_HEADER);
        header.setOpaque(true);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (filasSeleccionadas.contains(row)) {
                    comp.setBackground(Estilos.COLOR_TABLA_SELECCION);
                    comp.setForeground(Color.WHITE);
                } else {
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Estilos.TEXTO_PRINCIPAL);
                }

                return comp;
            }
        });
    }

    public void setModelo(DefaultTableModel modelo) {
        tabla.setModel(modelo);
    }

    public JTable getTabla() {
        return tabla;
    }

    public void cambiarColorFila(int fila, boolean seleccionado) {
        if (!seleccionado) {
            if (!filasSeleccionadas.contains(fila)) {
                filasSeleccionadas.add(fila);
            }
        } else {
            filasSeleccionadas.remove(Integer.valueOf(fila));
        }
        tabla.repaint();
    }

    public void deseleccionarFilas() {
        filasSeleccionadas.clear();
        tabla.repaint();
    }
}
