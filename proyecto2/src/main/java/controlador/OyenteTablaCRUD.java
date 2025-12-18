package controlador;

import modelo.MoView;
import vista.ViMain;

import javax.swing.event.TableModelListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OyenteTablaCRUD extends MouseAdapter implements TableModelListener {
    private CoPrincipal controlador;
    private ViMain vista;
    private MoView modeloVista;

    public OyenteTablaCRUD(CoPrincipal controlador, ViMain vista, MoView modeloVista) {
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
    public void tableChanged(javax.swing.event.TableModelEvent eventoModelo) {
        if (eventoModelo.getType() == javax.swing.event.TableModelEvent.UPDATE) {
            int filaModificada = eventoModelo.getFirstRow();
            int columnaModificada = eventoModelo.getColumn();

            if (filaModificada >= 0 && columnaModificada >= 3 && listaNombresPermisos != null) {
                javax.swing.table.TableModel modeloTabla = (javax.swing.table.TableModel) eventoModelo.getSource();

                Object idObj = modeloTabla.getValueAt(filaModificada, 0);
                int idRol = -1;
                if (idObj instanceof Integer) {
                    idRol = (Integer) idObj;
                }

                if (idRol == 3) {
                    return;
                }

                String nombreRol = (String) modeloTabla.getValueAt(filaModificada, 1);
                String nombrePermiso = listaNombresPermisos.get(columnaModificada - 3);
                Boolean nuevoValor = (Boolean) modeloTabla.getValueAt(filaModificada, columnaModificada);

                controlador.getControladorRoles().actualizarPermiso(nombreRol, nombrePermiso, nuevoValor);
            }
        }
    }

    private void mostrarOpciones() {
        int eleccion = vista.mostrarOpcionesTabla();

        if (eleccion == 0) {
            controlador.getControladorCRUD().mostrarFormularioNuevo();
        } else if (eleccion == 1) {
            controlador.getControladorCRUD().mostrarFormularioActualizar();
        } else if (eleccion == 2) {
            controlador.getControladorCRUD().eliminarRegistro();
        }
    }
}