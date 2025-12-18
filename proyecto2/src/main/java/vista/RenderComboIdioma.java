package vista;

import java.awt.Component;
import java.awt.Color;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

public class RenderComboIdioma extends DefaultListCellRenderer {

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
