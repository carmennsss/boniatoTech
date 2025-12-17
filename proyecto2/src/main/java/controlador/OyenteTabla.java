package controlador;

import modelo.MoView;
import vista.ViMain;

import javax.swing.event.TableModelListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OyenteTabla extends MouseAdapter implements TableModelListener {
    private CoPrincipal controlador;
    private ViMain vista;
    private MoView modeloVista;

    public OyenteTabla(CoPrincipal controlador, ViMain vista, MoView modeloVista) {
        this.controlador = controlador;
        this.vista = vista;
        this.modeloVista = modeloVista;
    }

    public void setListaNombresPermisos(java.util.ArrayList<String> listaNombresPermisos) {
        this.listaNombresPermisos = listaNombresPermisos;
    }

    private java.util.ArrayList<String> listaNombresPermisos;

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

    @Override
    public void tableChanged(javax.swing.event.TableModelEvent e) {
        if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (row >= 0 && column >= 2 && listaNombresPermisos != null) {
                javax.swing.table.TableModel model = (javax.swing.table.TableModel) e.getSource();
                String rolNombre = (String) model.getValueAt(row, 0);
                String permisoNombre = listaNombresPermisos.get(column - 2);
                Boolean isChecked = (Boolean) model.getValueAt(row, column);

                controlador.actualizarPermiso(rolNombre, permisoNombre, isChecked);
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