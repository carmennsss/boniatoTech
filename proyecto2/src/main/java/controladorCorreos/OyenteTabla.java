package controladorCorreos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;

import modelo.Correo;
import vista.VistaCorreoBase;

public class OyenteTabla extends MouseAdapter {

    private JTable tabla;
    private ArrayList<Correo> correos;
    private ControladorCorreos controlador;
    private String miCorreo;

    public OyenteTabla(JTable tabla, ArrayList<Correo> correos, ControladorCorreos controlador, String miCorreo) {
        this.tabla = tabla;
        this.correos = correos;
        this.controlador = controlador;
        this.miCorreo = miCorreo;
    }

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
