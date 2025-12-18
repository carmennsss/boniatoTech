package controlador;

import modelo.MoView;
import vista.ViMain;
import vista.VistaMenuPrincipal;
import vista.Estilos;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JButton boton = (JButton) e.getSource();
        String texto = boton.getText().toUpperCase();

        switch (texto) {
            case "ESPECIES":
            case "RECINTOS":
            case "CUIDADORES":
            case "ANIMALES":
            case "TRASLADOS":
            case "ESPECIES_RECINTOS":
            case "ELEMENTOS":
                String tabla = texto.toLowerCase();
                modeloVista.setTablaActual(tabla);
                controlador.getControladorCRUD().rellenarTabla(tabla);
                resetearEstiloBotones();
                boton.setBackground(Estilos.COLOR_TABLA_SELECCION);
                break;
            case "NEW":
                controlador.getControladorCRUD().mostrarFormularioNuevo();
                break;
            case "MAIN MENU":
                menuPrincipal.hacerVisible();
                vista.setVisible(false);
                controlador.getControladorCRUD().rellenarTabla("");
                break;
            case "GUARDAR":
                if (controlador.isEditando()) {
                    controlador.getControladorCRUD().guardarActualizar();
                } else {
                    controlador.getControladorCRUD().guardarNuevo();
                }
                break;
            case "CANCELAR":
                vista.getVentanaFormulario().setVisible(false);
                break;
            default:
                break;
        }
    }

    private void resetearEstiloBotones() {
        for (JButton btn : vista.getPanelMenu().getBotones()) {
            btn.setBackground(Estilos.COLOR_BOTON_MENU);
        }
    }
}
