package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

/**
 * Componente reutilizable que encapsula una JTable con estilos personalizados.
 * Permite manejar selecciones múltiples de forma visual y aplicar un diseño
 * coherente con la identidad visual de la aplicación.
 */
public class ViTabla extends JPanel {
    /** Instancia del componente JTable interno. */
    private JTable tabla;

    /** Panel de desplazamiento que proporciona soporte para grandes volúmenes de datos. */
    private JScrollPane scrollPane;

    /** Lista que almacena los índices de las filas marcadas como seleccionadas. */
    private ArrayList<Integer> filasSeleccionadas;

    /**
     * Constructor del componente. Inicializa las estructuras de datos y el diseño visual.
     */
    public ViTabla() {
        propiedades();
    }

    /**
     * Orquesta la configuración del panel y la inicialización de la tabla.
     */
    private void propiedades() {
        filasSeleccionadas = new ArrayList<>();
        configurarPanel();
        configurarTabla();
    }

    /**
     * Establece el diseño BorderLayout y los márgenes del panel contenedor.
     */
    private void configurarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setOpaque(false);
    }

    /**
     * Instancia la tabla y la incorpora dentro de un JScrollPane sin bordes.
     */
    private void configurarTabla() {
        tabla = new JTable();
        estilarTabla();

        scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Aplica los estilos visuales de la aplicación, incluyendo fuentes, colores de cabecera
     * y el renderizador de celdas personalizado.
     */
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

        // Renderizador personalizado para gestionar el color de las filas seleccionadas
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
     * Cambia el estado visual de una fila específica para indicar selección.
     *
     * @param fila         Índice de la fila a modificar.
     * @param seleccionado true para desmarcar (quitar de la lista), false para marcar (añadir a la lista).
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
     * Limpia la lista de selección y restaura el color original de todas las filas.
     */
    public void deseleccionarFilas() {
        filasSeleccionadas.clear();
        tabla.repaint();
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Establece el modelo de datos de la tabla.
     * * @param modelo DefaultTableModel con las columnas y filas a mostrar.
     */
    public void setModelo(DefaultTableModel modelo) {
        tabla.setModel(modelo);
    }

    /**
     * Obtiene la instancia del JTable para manipulación directa o asignación de oyentes.
     * * @return El objeto JTable interno.
     */
    public JTable getTabla() {
        return tabla;
    }
}