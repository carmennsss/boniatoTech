/*
* @author Carmen - BoniatoTech
* @version 1.0
*/
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
 * Oyente que maneja los eventos de la vista CRUD (botones de men� de tablas y
 * botones de acci�n nuevo/actualizar).
 */
public class OyenteCRUD implements ActionListener {
    /** Controlador principal de la aplicaci�n. */
    private CoPrincipal controlador;

    /** Vista CRUD principal. */
    private VistaCRUD vistaCRUD;

    /** Vista del formulario de edici�n. */
    private ViFormulario viFormulario;

    /** Vista del men� principal. */
    private VistaMenuPrincipal menuPrincipal;

    /** Modelo de vista para gesti�n de datos. */
    private MoView modeloVista;

    /**
     * Constructor del oyente CRUD.
     *
     * @param controlador   Controlador principal.
     * @param vistaCRUD     Vista CRUD.
     * @param viFormulario  Vista del formulario.
     * @param modeloVista   Modelo de vista.
     * @param menuPrincipal Vista del men� principal.
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
     * Cambia la tabla activa seg�n el bot�n del men� presionado o abre formularios
     * de edici�n.
     *
     * @param e El evento de acci�n.
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
     * Resetea el color de todos los botones del men� a su estado por defecto.
     */
    private void resetearEstiloBotones() {
        for (JButton btn : vistaCRUD.getPanelMenu().getBotones()) {
            btn.setBackground(Estilos.COLOR_BOTON_MENU);
        }
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene el controlador principal.
     * 
     * @return El controlador principal.
     */
    public CoPrincipal getControlador() {
        return controlador;
    }

    /**
     * Establece el controlador principal.
     * 
     * @param controlador El nuevo controlador principal.
     */
    public void setControlador(CoPrincipal controlador) {
        this.controlador = controlador;
    }

    /**
     * Obtiene la vista CRUD principal.
     * 
     * @return La vista CRUD.
     */
    public VistaCRUD getVistaCRUD() {
        return vistaCRUD;
    }

    /**
     * Establece la vista CRUD principal.
     * 
     * @param vistaCRUD La nueva vista CRUD.
     */
    public void setVistaCRUD(VistaCRUD vistaCRUD) {
        this.vistaCRUD = vistaCRUD;
    }

    /**
     * Obtiene la vista del formulario de edici�n.
     * 
     * @return La vista del formulario.
     */
    public ViFormulario getViFormulario() {
        return viFormulario;
    }

    /**
     * Establece la vista del formulario de edici�n.
     * 
     * @param viFormulario La nueva vista del formulario.
     */
    public void setViFormulario(ViFormulario viFormulario) {
        this.viFormulario = viFormulario;
    }

    /**
     * Obtiene el modelo de vista.
     * 
     * @return El modelo de vista.
     */
    public MoView getModeloVista() {
        return modeloVista;
    }

    /**
     * Establece el modelo de vista.
     * 
     * @param modeloVista El nuevo modelo de vista.
     */
    public void setModeloVista(MoView modeloVista) {
        this.modeloVista = modeloVista;
    }
}