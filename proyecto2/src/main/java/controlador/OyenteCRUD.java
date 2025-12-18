package controlador;

import modelo.MoView;
import vista.ViMain;
import vista.VistaMenuPrincipal;
import vista.Estilos;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OyenteCRUD implements ActionListener {
    private CoPrincipal controlador;
    private ViMain vista;
    private VistaMenuPrincipal menuPrincipal;
    private MoView modeloVista;

    public OyenteCRUD(CoPrincipal controlador, ViMain vista, MoView modeloVista, VistaMenuPrincipal menuPrincipal) {
        this.controlador = controlador;
        this.vista = vista;
        this.modeloVista = modeloVista;
        this.menuPrincipal = menuPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (JButton btn : vista.getPanelMenu().getBotones()) {
            if (source == btn) {
                String tabla = btn.getText().toLowerCase();
                modeloVista.setTablaActual(tabla);
                controlador.getControladorCRUD().rellenarTabla(tabla);
                resetearEstiloBotones();
                btn.setBackground(Estilos.COLOR_TABLA_SELECCION);
                return;
            }
        }

        ArrayList<JButton> actionButtons = vista.getPanelAcciones().getBotones();
        if (actionButtons.size() > 0 && source == actionButtons.get(0)) {
            controlador.getControladorCRUD().mostrarFormularioNuevo();
        } else if (actionButtons.size() > 1 && source == actionButtons.get(1)) {
            menuPrincipal.hacerVisible();
            vista.setVisible(false);
            controlador.getControladorCRUD().rellenarTabla("");
        }

        if (vista.getVentanaFormulario() != null) {
            if (source == vista.getVentanaFormulario().getBtnGuardar()) {
                if (controlador.isEditando()) {
                    controlador.getControladorCRUD().guardarActualizar();
                } else {
                    controlador.getControladorCRUD().guardarNuevo();
                }
            } else if (source == vista.getVentanaFormulario().getBtnCancelar()) {
                vista.getVentanaFormulario().setVisible(false);
            }
        }
    }

    private void resetearEstiloBotones() {
        for (JButton btn : vista.getPanelMenu().getBotones()) {
            btn.setBackground(Estilos.COLOR_BOTON_MENU);
        }
    }
}
