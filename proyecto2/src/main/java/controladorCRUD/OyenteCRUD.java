package controladorCRUD;

import controladorPrincipal.CoPrincipal;

import modelo.MoView;
import vista.VistaCRUD;
import vista.ViFormulario;
import vista.VistaMenuPrincipal;
import vista.Estilos;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Oyente que maneja los eventos de la vista CRUD (botones de menú de tablas y
 * botones de acción nuevo/actualizar).
 */
public class OyenteCRUD implements ActionListener {
    /** Controlador principal de la aplicación. */
    private CoPrincipal controlador;

    /** Vista CRUD principal. */
    private VistaCRUD vistaCRUD;

    /** Vista del formulario de edición. */
    private ViFormulario viFormulario;

    /** Vista del menú principal. */
    private VistaMenuPrincipal menuPrincipal;

    /** Modelo de vista para gestión de datos. */
    private MoView modeloVista;

    /**
     * Constructor del oyente CRUD.
     *
     * @param controlador   Controlador principal.
     * @param vistaCRUD     Vista CRUD.
     * @param viFormulario  Vista del formulario.
     * @param modeloVista   Modelo de vista.
     * @param menuPrincipal Vista del menú principal.
     */
    public OyenteCRUD(CoPrincipal controlador, VistaCRUD vistaCRUD, ViFormulario viFormulario, MoView modeloVista,
            VistaMenuPrincipal menuPrincipal) {
        this.controlador = controlador;
        this.vistaCRUD = vistaCRUD;
        this.viFormulario = viFormulario;
        this.modeloVista = modeloVista;
        this.menuPrincipal = menuPrincipal;
    }

    /**
     * Maneja las acciones de los botones.
     * Cambia la tabla activa según el botón del menú presionado o abre formularios
     * de edición.
     *
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (JButton btn : vistaCRUD.getPanelMenu().getBotones()) {
            if (source == btn) {
                String tabla = btn.getName();
                modeloVista.setTablaActual(tabla);
                controlador.getControladorCRUD().rellenarTabla(tabla);
                resetearEstiloBotones();
                btn.setBackground(Estilos.COLOR_TABLA_SELECCION);
                return;
            }
        }

        ArrayList<JButton> actionButtons = vistaCRUD.getPanelAcciones().getBotones();
        if (actionButtons.size() > 0 && source == actionButtons.get(0)) {
            controlador.getControladorCRUD().mostrarFormularioNuevo();
        } else if (actionButtons.size() > 1 && source == actionButtons.get(1)) {
            menuPrincipal.hacerVisible();
            vistaCRUD.setVisible(false);
            controlador.getControladorCRUD().rellenarTabla("");
        }

        if (viFormulario != null) {
            if (source == viFormulario.getBtnGuardar()) {
                if (controlador.isEditando()) {
                    controlador.getControladorCRUD().guardarActualizar();
                } else {
                    controlador.getControladorCRUD().guardarNuevo();
                }
            } else if (source == viFormulario.getBtnCancelar()) {
                viFormulario.setVisible(false);
            }
        }
    }

    /**
     * Resetea el color de todos los botones del menú a su estado por defecto.
     */
    private void resetearEstiloBotones() {
        for (JButton btn : vistaCRUD.getPanelMenu().getBotones()) {
            btn.setBackground(Estilos.COLOR_BOTON_MENU);
        }
    }
}
