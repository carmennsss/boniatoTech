package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaGeneralCorreo;
import vista.VistaMenuPrincipal;

public class OyenteBotonVolver implements ActionListener {

    private VistaGeneralCorreo vistaGeneral;
    private VistaMenuPrincipal vistaMenu;
    private ControladorCorreos controlador;

    public OyenteBotonVolver(VistaGeneralCorreo vistaGeneral, VistaMenuPrincipal vistaMenu,
            ControladorCorreos controladorCorreos) {
        this.vistaGeneral = vistaGeneral;
        this.vistaMenu = vistaMenu;
        this.controlador = controladorCorreos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        vistaGeneral.setVisible(false);
        controlador.detenerHiloRecepcion();
        vistaMenu.hacerVisible();
    }
}