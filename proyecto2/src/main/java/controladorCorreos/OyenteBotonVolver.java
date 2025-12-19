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

    private VistaGeneralCorreo vistaGeneral;
    private VistaMenuPrincipal vistaMenu;
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
}