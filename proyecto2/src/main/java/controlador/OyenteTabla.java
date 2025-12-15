package controlador;

import modelo.MoView;
import vista.ViMain;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OyenteTabla extends MouseAdapter {
    private CoPrincipal controlador;
    private ViMain vista;
    private MoView modeloVista;

    public OyenteTabla(CoPrincipal controlador, ViMain vista, MoView modeloVista) {
        this.controlador = controlador;
        this.vista = vista;
        this.modeloVista = modeloVista;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int fila = vista.getPanelTabla().getTabla().getSelectedRow();
            if (fila != -1) {
                modeloVista.setFilaSeleccionada(fila);
                mostrarOpciones();
            }
        }
    }

    private void mostrarOpciones() {
        int eleccion = vista.mostrarOpcionesTabla();

        if (eleccion == 0) {
            controlador.mostrarFormularioNuevo();
        } else if (eleccion == 1) {
            controlador.mostrarFormularioActualizar();
        } else if (eleccion == 2) {
            controlador.eliminarRegistro();
        }
    }
}
