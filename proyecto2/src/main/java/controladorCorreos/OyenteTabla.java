package controladorCorreos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;

import modelo.Correo;
import vista.VistaCorreoBase;

/**
 * Oyente para la tabla de correos en la vista general.
 * Abre la ventana de lectura al hacer doble clic en un correo.
 */
public class OyenteTabla extends MouseAdapter {

    /** Tabla de correos. */
    private JTable tabla;

    /** Lista de correos. */
    private ArrayList<Correo> correos;

    /** Controlador de correos. */
    private ControladorCorreos controlador;

    /** Dirección de correo del usuario. */
    private String miCorreo;

    /**
     * Constructor del oyente.
     *
     * @param tabla       Tabla de correos.
     * @param correos      Lista de correos.
     * @param controlador Controlador de correos.
     * @param miCorreo    Dirección de correo del usuario.
     */
    public OyenteTabla(JTable tabla, ArrayList<Correo> correos, ControladorCorreos controlador, String miCorreo) {
        this.tabla = tabla;
        this.correos = correos;
        this.controlador = controlador;
        this.miCorreo = miCorreo;
    }

    /**
     * Maneja el doble clic en la tabla de correos para abrir el mensaje
     * seleccionado.
     * Si el mensaje no estaba leído, lo marca como leído en un hilo separado.
     *
     * @param e Evento del ratón.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                Correo correoSeleccionado = correos.get(fila);

                if (!correoSeleccionado.isLeido()) {
                    new Thread(() -> {
                        controlador.marcarCorreoLeido(correoSeleccionado);
                    }).start();
                }

                VistaCorreoBase vistaLectura = new VistaCorreoBase(correoSeleccionado);
                vistaLectura.getBotonEliminar().addActionListener(
                        new OyenteBotonEliminar(correoSeleccionado, controlador, vistaLectura));
                vistaLectura.getBotonExportar().addActionListener(
                        new OyenteExportarCorreo(correoSeleccionado, vistaLectura, miCorreo));
                vistaLectura.getBotonNoLeido()
                        .addActionListener(new OyenteBotonNoLeido(correoSeleccionado, controlador));
                vistaLectura.setVisible(true);
            }
        }
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene la tabla de correos asociada.
     * @return El componente JTable.
     */
    public JTable getTabla() {
        return tabla;
    }

    /**
     * Establece la tabla de correos asociada.
     * @param tabla La nueva JTable.
     */
    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    /**
     * Obtiene la lista de correos actual.
     * @return El ArrayList de correos.
     */
    public ArrayList<Correo> getCorreos() {
        return correos;
    }

    /**
     * Establece la lista de correos.
     * @param correos El nuevo ArrayList de correos.
     */
    public void setCorreos(ArrayList<Correo> correos) {
        this.correos = correos;
    }

    /**
     * Obtiene el controlador de correos asociado.
     * @return El controlador.
     */
    public ControladorCorreos getControlador() {
        return controlador;
    }

    /**
     * Establece el controlador de correos.
     * @param controlador El nuevo controlador.
     */
    public void setControlador(ControladorCorreos controlador) {
        this.controlador = controlador;
    }

    /**
     * Obtiene la dirección de correo del usuario.
     * @return El correo del usuario.
     */
    public String getMiCorreo() {
        return miCorreo;
    }

    /**
     * Establece la dirección de correo del usuario.
     * @param miCorreo El nuevo correo del usuario.
     */
    public void setMiCorreo(String miCorreo) {
        this.miCorreo = miCorreo;
    }
}