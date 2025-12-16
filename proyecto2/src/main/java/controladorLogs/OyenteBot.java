package controlador;

import modelo.MoView;
import vista.ViMain;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OyenteBot implements ActionListener {
    private CoPrincipal controlador;
    private ViMain vista;
    private MoView modeloVista;

    public OyenteBot(CoPrincipal controlador, ViMain vista, MoView modeloVista) {
        this.controlador = controlador;
        this.vista = vista;
        this.modeloVista = modeloVista;
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
            vista.mostrarLogin();
            vista.getPanelLogin().getCajas().get(0).setText("");
            vista.getPanelLogin().getCajas().get(1).setText("");
            controlador.rellenarTabla("");
        }
    }
}
