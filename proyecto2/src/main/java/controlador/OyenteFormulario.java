package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import vista.ViMain;

public class OyenteFormulario implements ActionListener {
    private CoPrincipal controlador;
    private ViMain vista;

    public OyenteFormulario(CoPrincipal controlador, ViMain vista) {
        this.controlador = controlador;
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String texto = source.getText();

        if (texto.equals("Guardar")) {
            if (controlador.isEditando()) {
                controlador.getControladorCRUD().guardarActualizar();
            } else {
                controlador.getControladorCRUD().guardarNuevo();
            }
        } else if (texto.equals("Cancelar")) {
            vista.getVentanaFormulario().setVisible(false);
        }
    }
}
