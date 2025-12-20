package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaGeneralCorreo;
import vista.VistaMenuPrincipal;

/**
 * Oyente del botón "Volver" en la vista de correos.
 * Regresa al menú principal y detiene la recepción automática de correos.
 */
public class OyenteBotonVolver implements ActionListener {

    /** Vista general de correos. */
    private VistaGeneralCorreo vistaGeneral;

    /** Vista del menú principal. */
    private VistaMenuPrincipal vistaMenu;

    /** Controlador de correos. */
    private ControladorCorreos controlador;

    /**
     * Constructor del oyente.
     *
     * @param vistaGeneral       Vista general de correos.
     * @param vistaMenu          Vista del menú principal.
     * @param controladorCorreos Controlador de correos.
     */
    public OyenteBotonVolver(VistaGeneralCorreo vistaGeneral, VistaMenuPrincipal vistaMenu,
            ControladorCorreos controladorCorreos) {
        this.vistaGeneral = vistaGeneral;
        this.vistaMenu = vistaMenu;
        this.controlador = controladorCorreos;
    }

    /**
     * Vuelve al menú principal y detiene la recepción automática de correos.
     *
     * @param e Evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        vistaGeneral.setVisible(false);
        controlador.detenerHiloRecepcion();
        vistaMenu.hacerVisible();
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene la vista general de correos asociada.
     * @return La vista general.
     */
    public VistaGeneralCorreo getVistaGeneral() {
        return vistaGeneral;
    }

    /**
     * Establece la vista general de correos.
     * @param vistaGeneral La nueva vista general.
     */
    public void setVistaGeneral(VistaGeneralCorreo vistaGeneral) {
        this.vistaGeneral = vistaGeneral;
    }

    /**
     * Obtiene la vista del menú principal asociada.
     * @return La vista del menú principal.
     */
    public VistaMenuPrincipal getVistaMenu() {
        return vistaMenu;
    }

    /**
     * Establece la vista del menú principal.
     * @param vistaMenu La nueva vista del menú.
     */
    public void setVistaMenu(VistaMenuPrincipal vistaMenu) {
        this.vistaMenu = vistaMenu;
    }

    /**
     * Obtiene el controlador de correos asociado.
     * @return El controlador.
     */
    public ControladorCorreos getControlador() {
        return controlador;
    }

    /**
     * Establece el controlador de correos.
     * @param controlador El nuevo controlador.
     */
    public void setControlador(ControladorCorreos controlador) {
        this.controlador = controlador;
    }
}