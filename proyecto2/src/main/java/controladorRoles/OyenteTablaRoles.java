/*
* @author Carmen - BoniatoTech
* @version 1.0
*/
package controladorRoles;

import controladorPrincipal.CoPrincipal;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import modelo.MoView;
import vista.VistaAsignarRol;

/**
 * Oyente para manejar la selecci�n m�ltiple de usuarios en la tabla de
 * asignaci�n de roles.
 */
public class OyenteTablaRoles extends MouseAdapter {
    /** Controlador principal de la aplicaci�n. */
    private CoPrincipal controlador;

    /** Vista de asignaci�n de roles. */
    private VistaAsignarRol vistaAsignarRol;

    /** Modelo de vista para gesti�n de datos. */
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
     * @param e El evento de rat�n.
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

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene el controlador principal asociado.
     * 
     * @return El objeto CoPrincipal.
     */
    public CoPrincipal getControlador() {
        return controlador;
    }

    /**
     * Establece el controlador principal asociado.
     * 
     * @param controlador El nuevo controlador.
     */
    public void setControlador(CoPrincipal controlador) {
        this.controlador = controlador;
    }

    /**
     * Obtiene la vista de asignaci�n de roles.
     * 
     * @return El objeto VistaAsignarRol.
     */
    public VistaAsignarRol getVistaAsignarRol() {
        return vistaAsignarRol;
    }

    /**
     * Establece la vista de asignaci�n de roles.
     * 
     * @param vistaAsignarRol La nueva vista.
     */
    public void setVistaAsignarRol(VistaAsignarRol vistaAsignarRol) {
        this.vistaAsignarRol = vistaAsignarRol;
    }

    /**
     * Obtiene el modelo de vista.
     * 
     * @return El objeto MoView.
     */
    public MoView getModeloVista() {
        return modeloVista;
    }

    /**
     * Establece el modelo de vista.
     * 
     * @param modeloVista El nuevo modelo de vista.
     */
    public void setModeloVista(MoView modeloVista) {
        this.modeloVista = modeloVista;
    }
}