package controlador;

import modelo.MoView;
import vista.ViMain;

import javax.swing.event.TableModelListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Oyente para manejar eventos en la tabla CRUD general y la tabla de roles.
 * Maneja doble clic para menú de opciones y cambios en celdas (checkboxes de
 * permisos).
 */
public class OyenteTablaCRUD extends MouseAdapter implements TableModelListener {
    private CoPrincipal controlador;
    private ViMain vista;
    private MoView modeloVista;

    /**
     * Constructor del oyente de tabla CRUD.
     *
     * @param controlador Controlador principal.
     * @param vista       Vista principal.
     * @param modeloVista Modelo de vista.
     */
    public OyenteTablaCRUD(CoPrincipal controlador, ViMain vista, MoView modeloVista) {
        this.controlador = controlador;
        this.vista = vista;
        this.modeloVista = modeloVista;
    }

    /**
     * Establece la lista de nombres de permisos para la tabla de roles.
     * 
     * @param listaNombresPermisos Lista de nombres de permisos.
     */
    public void setListaNombresPermisos(java.util.ArrayList<String> listaNombresPermisos) {
        this.listaNombresPermisos = listaNombresPermisos;
    }

    private java.util.ArrayList<String> listaNombresPermisos;

    /**
     * Maneja el doble clic para mostrar las opciones (Nuevo, Actualizar, Borrar).
     *
     * @param e El evento de ratón.
     */
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

    /**
     * Detecta cambios en la tabla, específicamente para la edición de permisos de
     * roles.
     *
     * @param eventoModelo El evento de cambio en el modelo de la tabla.
     */
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

    /**
     * Muestra el diálogo de opciones y ejecuta la acción seleccionada.
     */
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