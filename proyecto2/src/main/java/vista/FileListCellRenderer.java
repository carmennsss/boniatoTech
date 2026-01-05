package vista;

import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import org.apache.commons.net.ftp.FTPFile;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

/**
 * Renderizador personalizado para la lista de archivos FTP.
 * Muestra iconos diferentes para archivos y carpetas usando Material Design
 * Icons.
 */
public class FileListCellRenderer extends DefaultListCellRenderer {

    /**
     * Renderiza cada celda de la lista con el icono apropiado según el tipo de
     * archivo.
     *
     * @param list         Lista que contiene los elementos.
     * @param value        Valor del elemento actual.
     * @param index        Índice del elemento.
     * @param isSelected   Indica si el elemento está seleccionado.
     * @param cellHasFocus Indica si la celda tiene el foco.
     * @return Componente renderizado para la celda.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof FTPFile) {
            FTPFile file = (FTPFile) value;
            label.setText(file.getName());

            // Configurar el icono según el tipo
            if (file.isDirectory()) {
                // Icono de carpeta
                FontIcon folderIcon = FontIcon.of(MaterialDesign.MDI_FOLDER, 16);
                label.setIcon(folderIcon);
            } else {
                // Icono de archivo
                FontIcon fileIcon = FontIcon.of(MaterialDesign.MDI_FILE, 16);
                label.setIcon(fileIcon);
            }

            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        return label;
    }
}