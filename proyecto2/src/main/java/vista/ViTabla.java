package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ViTabla extends JPanel {
    private JTable tabla;
    private JScrollPane scrollPane;

    public ViTabla() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setOpaque(false);

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
        header.setPreferredSize(new Dimension(0, 40));
    }

    public void setModelo(DefaultTableModel modelo) {
        tabla.setModel(modelo);
    }

    public JTable getTabla() {
        return tabla;
    }

    public void cambiarColorFila(int fila, boolean seleccionado) {
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Si la fila es la que se pasó como parámetro, se pone verde, si no, blanca
                if (row == fila) {
                    if (!seleccionado) {
                        comp.setBackground(Color.GREEN);
                    } else {
                        comp.setBackground(Color.WHITE);
                    }
                } else {
                    comp.setBackground(Color.WHITE);
                }

                return comp;
            }
        });

        tabla.repaint(); // Refresca la tabla para aplicar el cambio
    }

    public void deseleccionarFilas() {
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                comp.setBackground(Color.WHITE);

                return comp;
            }
        });

        tabla.repaint();
    }
}
