package vista;

import java.awt.Component;
import java.awt.Color;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

/**
 * Renderizador personalizado para el ComboBox de selección de idioma.
 * Permite mostrar iconos (banderas) junto con el comportamiento de selección.
 */
public class RenderComboIdioma extends DefaultListCellRenderer {

    /**
     * Devuelve el componente visual para cada elemento de la lista (icono).
     *
     * @param list         Lista que contiene los elementos.
     * @param value        Valor del elemento actual.
     * @param index        Índice del elemento.
     * @param isSelected   Indica si el elemento está seleccionado.
     * @param cellHasFocus Indica si la celda tiene el foco.
     * @return Componente renderizado para la celda.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof ImageIcon) {
            label.setIcon((ImageIcon) value);
            label.setText("");
        }

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(new EmptyBorder(5, 5, 5, 5));

        if (isSelected) {
            label.setBackground(new Color(230, 230, 230));
        } else {
            label.setBackground(Color.WHITE);
        }

        return label;
    }
}
