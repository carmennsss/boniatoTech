package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import modelo.MoView;
import modelo.User;
import vista.ViMain;

/**
 * Oyente para manejar la selección múltiple de usuarios en la tabla de
 * asignación de roles.
 */
public class OyenteTablaRoles extends MouseAdapter {
    private CoPrincipal controlador;
    private ViMain vista;
    private MoView modeloVista;

    /**
     * Constructor del oyente de tabla de roles.
     *
     * @param controlador Controlador principal.
     * @param vista       Vista principal.
     * @param modeloVista Modelo de vista.
     */
    public OyenteTablaRoles(CoPrincipal controlador, ViMain vista, MoView modeloVista) {
        this.controlador = controlador;
        this.vista = vista;
        this.modeloVista = modeloVista;
    }

    /**
     * Maneja el clic en la tabla para seleccionar o deseleccionar un usuario
     * (fila).
     *
     * @param e El evento de ratón.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            int fila = vista.getViAsignarRol().getTabla().getTabla().getSelectedRow();
            if (fila == -1)
                return;

            String correo = vista.getViAsignarRol().getTabla().getTabla().getModel().getValueAt(fila, 1).toString();

            if (modeloVista.getCorreoSeleccionados().contains(correo)) {
                modeloVista.getCorreoSeleccionados().remove(correo);
                vista.getViAsignarRol().getTabla().cambiarColorFila(fila, true);
            } else {
                modeloVista.getCorreoSeleccionados().add(correo);
                vista.getViAsignarRol().getTabla().cambiarColorFila(fila, false);
            }
        }
    }
}
