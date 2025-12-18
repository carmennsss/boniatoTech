package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

import java.util.ArrayList;

/**
 * Componente reutilizable que encapsula una JTable con estilos personalizados.
 * Permite manejar selecciones múltiples y coloreado de filas.
 */
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

    /**
     * Establece el modelo de datos de la tabla.
     * 
     * @param modelo DefaultTableModel con los datos a mostrar.
     */
    public void setModelo(DefaultTableModel modelo) {
        tabla.setModel(modelo);
    }

    /**
     * Obtiene la instancia del JTable interno.
     * 
     * @return El JTable.
     */
    public JTable getTabla() {
        return tabla;
    }

    /**
     * Cambia el color de fondo de una fila específica para indicar selección o
     * deselección visual.
     *
     * @param fila         Índice de la fila.
     * @param seleccionado true para marcar como seleccionada (quita el color),
     *                     false para marcar como no seleccionada (añade color).
     *                     (Nota: La lógica parece invertida en el nombre del
     *                     parámetro vs implementación, se mantiene comportamiento
     *                     original).
     */
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

    /**
     * Deselecciona todas las filas de la tabla.
     */
    public void deseleccionarFilas() {
        filasSeleccionadas.clear();
        tabla.repaint();
    }
}
