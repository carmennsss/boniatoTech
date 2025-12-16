package controlador;

import modelo.MoView;
import vista.ViMain;
import vista.VistaMenuPrincipal;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OyenteBot implements ActionListener {
    private CoPrincipal controlador;
    private ViMain vista;
    private VistaMenuPrincipal menuPrincipal;
    private MoView modeloVista;

    public OyenteBot(CoPrincipal controlador, ViMain vista, MoView modeloVista, VistaMenuPrincipal menuPrincipal) {
        this.controlador = controlador;
        this.vista = vista;
        this.modeloVista = modeloVista;
        this.menuPrincipal=menuPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();

        if (boton == vista.getPanelMenu().getBotones().get(0)) {
            modeloVista.setTablaActual("especies");
            controlador.rellenarTabla("especies");

        } else if (boton == vista.getPanelMenu().getBotones().get(1)) {
            modeloVista.setTablaActual("recintos");
            controlador.rellenarTabla("recintos");

        } else if (boton == vista.getPanelMenu().getBotones().get(2)) {
            modeloVista.setTablaActual("cuidadores");
            controlador.rellenarTabla("cuidadores");

        } else if (boton == vista.getPanelMenu().getBotones().get(3)) {
            modeloVista.setTablaActual("animales");
            controlador.rellenarTabla("animales");

        } else if (boton == vista.getPanelAcciones().getBotones().get(0)) {
            controlador.mostrarFormularioNuevo();

        } else if (boton == vista.getPanelAcciones().getBotones().get(1)) {
            menuPrincipal.hacerVisible();
            vista.setVisible(false);
            controlador.rellenarTabla("");
        }
    }
}
