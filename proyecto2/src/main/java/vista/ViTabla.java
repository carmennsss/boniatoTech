package vista;

import javax.swing.*;
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
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    private void estilarTabla() {
        tabla.setRowHeight(30);
        tabla.setFont(Estilos.FONT_TEXTO);
        tabla.setGridColor(Estilos.BEIGE_CANVAS);
        tabla.setSelectionBackground(Estilos.COLOR_TABLA_SELECCION);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setShowVerticalLines(false);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(Estilos.FONT_BOTON);
        header.setBackground(Estilos.COLOR_TABLA_HEADER);
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(0, 40));
    }

    public void setModelo(DefaultTableModel modelo) {
        tabla.setModel(modelo);
    }

    public JTable getTabla() {
        return tabla;
    }
}
