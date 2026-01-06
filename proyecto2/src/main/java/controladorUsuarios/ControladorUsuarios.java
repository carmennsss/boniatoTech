/*
* @author Daniel - BoniatoTech
* @version 1.0
*/

package controladorUsuarios;

import vista.VistaAdmin;
import vista.VistaRegistroUsuarios;

/**
 * Controlador para la gestión de usuarios.
 * Maneja la navegación y lógica relacionada con el registro y administración de
 * usuarios.
 */
public class ControladorUsuarios {
    /** Vista de administración. */
    private VistaAdmin vistaAdmin;

    /** Vista de registro de usuarios. */
    private VistaRegistroUsuarios vistaUsuarios;

    /**
     * Constructor del controlador de usuarios.
     * * @param vistaAdmin    Vista de administración.
     * @param vistaUsuarios Vista de registro de usuarios.
     */
    public ControladorUsuarios(VistaAdmin vistaAdmin, VistaRegistroUsuarios vistaUsuarios) {
        this.vistaAdmin = vistaAdmin;
        this.vistaUsuarios = vistaUsuarios;
    }

    /**
     * Abre la vista de administración de usuarios.
     * Oculta la vista de administración y muestra la vista de usuarios.
     */
    public void abrirAdministrarUsuarios() {
        vistaUsuarios.hacerVisible();
        vistaAdmin.setVisible(false);
    }

    /**
     * Vuelve a la vista de administración desde la gestión de usuarios.
     * Oculta la vista de usuarios y muestra la vista de administración.
     */
    public void volverAdminDesdeUsuarios() {
        vistaUsuarios.setVisible(false);
        vistaAdmin.hacerVisible();
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene la vista de administración.
     * @return El objeto VistaAdmin.
     */
    public VistaAdmin getVistaAdmin() {
        return vistaAdmin;
    }

    /**
     * Establece la vista de administración.
     * @param vistaAdmin La nueva vista de administración.
     */
    public void setVistaAdmin(VistaAdmin vistaAdmin) {
        this.vistaAdmin = vistaAdmin;
    }

    /**
     * Obtiene la vista de registro de usuarios.
     * @return El objeto VistaRegistroUsuarios.
     */
    public VistaRegistroUsuarios getVistaUsuarios() {
        return vistaUsuarios;
    }

    /**
     * Establece la vista de registro de usuarios.
     * @param vistaUsuarios La nueva vista de usuarios.
     */
    public void setVistaUsuarios(VistaRegistroUsuarios vistaUsuarios) {
        this.vistaUsuarios = vistaUsuarios;
    }
}