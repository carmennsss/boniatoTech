package controlador;

import modelo.MoView;
import vista.ViMain;
import vista.VistaMenuPrincipal;
import vista.Estilos;

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
        this.menuPrincipal = menuPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();

        if (boton == vista.getPanelMenu().getBotones().get(0)) {
            modeloVista.setTablaActual("especies");
            controlador.getControladorCRUD().rellenarTabla("especies");
            resetearEstiloBotones();
            boton.setBackground(Estilos.COLOR_TABLA_SELECCION);

        } else if (boton == vista.getPanelMenu().getBotones().get(1)) {
            modeloVista.setTablaActual("recintos");
            controlador.getControladorCRUD().rellenarTabla("recintos");
            resetearEstiloBotones();
            boton.setBackground(Estilos.COLOR_TABLA_SELECCION);

        } else if (boton == vista.getPanelMenu().getBotones().get(2)) {
            modeloVista.setTablaActual("cuidadores");
            controlador.getControladorCRUD().rellenarTabla("cuidadores");
            resetearEstiloBotones();
            boton.setBackground(Estilos.COLOR_TABLA_SELECCION);

        } else if (boton == vista.getPanelMenu().getBotones().get(3)) {
            modeloVista.setTablaActual("animales");
            controlador.getControladorCRUD().rellenarTabla("animales");
            resetearEstiloBotones();
            boton.setBackground(Estilos.COLOR_TABLA_SELECCION);
        } else if (boton == vista.getPanelMenu().getBotones().get(4)) {
            modeloVista.setTablaActual("traslados");
            controlador.getControladorCRUD().rellenarTabla("traslados");
            resetearEstiloBotones();
            boton.setBackground(Estilos.COLOR_TABLA_SELECCION);
        } else if (boton == vista.getPanelMenu().getBotones().get(5)) {
            modeloVista.setTablaActual("especies_recintos");
            controlador.getControladorCRUD().rellenarTabla("especies_recintos");
            resetearEstiloBotones();
            boton.setBackground(Estilos.COLOR_TABLA_SELECCION);
        } else if (boton == vista.getPanelMenu().getBotones().get(6)) {
            modeloVista.setTablaActual("elementos");
            controlador.getControladorCRUD().rellenarTabla("elementos");
            resetearEstiloBotones();
            boton.setBackground(Estilos.COLOR_TABLA_SELECCION);
        } else if (boton == vista.getPanelAcciones().getBotones().get(0)) {
            controlador.getControladorCRUD().mostrarFormularioNuevo();

        } else if (boton == vista.getPanelAcciones().getBotones().get(1)) {
            menuPrincipal.hacerVisible();
            vista.setVisible(false);
            controlador.getControladorCRUD().rellenarTabla("");
        }
    }

    private void resetearEstiloBotones() {
        for (JButton btn : vista.getPanelMenu().getBotones()) {
            btn.setBackground(Estilos.COLOR_BOTON_MENU);
        }
    }
}
