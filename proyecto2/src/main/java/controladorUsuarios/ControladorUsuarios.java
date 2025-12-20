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
     * 
     * @param vistaAdmin    Vista de administración.
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
}
