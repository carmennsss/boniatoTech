package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaGeneralCorreo;
import vista.VistaMenuPrincipal;

public class OyenteBotonVolver implements ActionListener {

    private VistaGeneralCorreo vistaGeneral;
    private VistaMenuPrincipal vistaMenu;

    public OyenteBotonVolver(VistaGeneralCorreo vistaGeneral, VistaMenuPrincipal vistaMenu) {
        this.vistaGeneral = vistaGeneral;
        this.vistaMenu = vistaMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        vistaGeneral.setVisible(false);
        vistaMenu.hacerVisible();
    }
}