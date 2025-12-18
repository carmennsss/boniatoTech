package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import modelo.MoView;
import modelo.User;
import vista.ViMain;

public class OyenteTablaRoles extends MouseAdapter {
    private CoPrincipal controlador;
    private ViMain vista;
    private MoView modeloVista;

    public OyenteTablaRoles(CoPrincipal controlador, ViMain vista, MoView modeloVista) {
        this.controlador = controlador;
        this.vista = vista;
        this.modeloVista = modeloVista;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            int fila = vista.getViAsignarRol().getTabla().getTabla().getSelectedRow();
            if (fila == -1)
                return;

            String correo = vista.getViAsignarRol().getTabla().getTabla().getModel().getValueAt(fila, 1).toString();

            if (modeloVista.getCorreoSeleccionados().contains(correo)) {
                modeloVista.getCorreoSeleccionados().remove(correo);
                vista.getViAsignarRol().getTabla().cambiarColorFila(fila, true);
            } else {
                modeloVista.getCorreoSeleccionados().add(correo);
                vista.getViAsignarRol().getTabla().cambiarColorFila(fila, false);
            }
        }
    }
}
