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

    private JTable tabla;
    private ArrayList<Correo> correos;
    private ControladorCorreos controlador;
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
}