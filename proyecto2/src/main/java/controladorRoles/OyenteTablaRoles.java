package controladorRoles;

import controladorPrincipal.CoPrincipal;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import modelo.MoView;
import vista.VistaAsignarRol;

/**
 * Oyente para manejar la selección múltiple de usuarios en la tabla de
 * asignación de roles.
 */
public class OyenteTablaRoles extends MouseAdapter {
    /** Controlador principal de la aplicación. */
    private CoPrincipal controlador;

    /** Vista de asignación de roles. */
    private VistaAsignarRol vistaAsignarRol;

    /** Modelo de vista para gestión de datos. */
    private MoView modeloVista;

    /**
     * Constructor del oyente de tabla de roles.
     *
     * @param controlador     Controlador principal.
     * @param vistaAsignarRol Vista de asignar rol.
     * @param modeloVista     Modelo de vista.
     */
    public OyenteTablaRoles(CoPrincipal controlador, VistaAsignarRol vistaAsignarRol, MoView modeloVista) {
        this.controlador = controlador;
        this.vistaAsignarRol = vistaAsignarRol;
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
            int fila = vistaAsignarRol.getTabla().getTabla().getSelectedRow();
            if (fila == -1)
                return;

            String correo = vistaAsignarRol.getTabla().getTabla().getModel().getValueAt(fila, 1).toString();

            if (modeloVista.getCorreoSeleccionados().contains(correo)) {
                modeloVista.getCorreoSeleccionados().remove(correo);
                vistaAsignarRol.getTabla().cambiarColorFila(fila, true);
            } else {
                modeloVista.getCorreoSeleccionados().add(correo);
                vistaAsignarRol.getTabla().cambiarColorFila(fila, false);
            }
        }
    }
}
