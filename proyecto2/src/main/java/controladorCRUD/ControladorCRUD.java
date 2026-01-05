/*
* @author Carmen - BoniatoTech
* @version 1.0
*/
package controladorCRUD;

import controladorPrincipal.CoPrincipal;

import modelo.ModeloBaseDatos;
import modelo.MoView;
import vista.VistaCRUD;
import vista.ViFormulario;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controlador para las operaciones CRUD (Crear, Leer, Actualizar, Borrar) en la
 * base de datos.
 * Maneja la interacci�n entre las tablas de la vista y el modelo de datos.
 */
public class ControladorCRUD {
    /** Modelo de base de datos para acceder a la informaci�n. */
    private ModeloBaseDatos bd;

    /** Vista principal del CRUD. */
    private VistaCRUD vistaCRUD;

    /** Formulario din�mico para creaci�n y edici�n de registros. */
    private ViFormulario viFormulario;

    /** Modelo de la vista para gestionar el estado de la interfaz. */
    private MoView modeloVista;

    /** Controlador principal de la aplicaci�n. */
    private CoPrincipal coPrincipal;

    /** Oyente de eventos para los botones del formulario. */
    private ActionListener oyente;

    /**
     * Constructor del controlador CRUD.
     * 
     * @param coPrincipal  Controlador principal.
     * @param bd           Modelo de base de datos.
     * @param vistaCRUD    Vista principal del CRUD.
     * @param viFormulario Formulario din�mico.
     * @param modeloVista  Modelo de la vista.
     */
    public ControladorCRUD(CoPrincipal coPrincipal, ModeloBaseDatos bd, VistaCRUD vistaCRUD, ViFormulario viFormulario,
            MoView modeloVista) {
        this.coPrincipal = coPrincipal;
        this.bd = bd;
        this.vistaCRUD = vistaCRUD;
        this.viFormulario = viFormulario;
        this.modeloVista = modeloVista;
    }

    /**
     * Rellena la tabla de la vista con los datos de la base de datos.
     * Configura el modelo de la tabla para que no sea editable directamente.
     *
     * @param tabla Nombre de la tabla en la base de datos.
     */
    public void rellenarTabla(String tabla) {
        if (tabla.isEmpty()) {
            vistaCRUD.getPanelTabla().setModelo(new DefaultTableModel());
            return;
        }
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = bd.getTabla(tabla);

        try {
            if (rs != null) {
                int totalColumnas = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= totalColumnas; i++) {
                    modelo.addColumn(rs.getMetaData().getColumnName(i));
                }

                while (rs.next()) {
                    String[] row = new String[totalColumnas];
                    for (int i = 1; i <= totalColumnas; i++) {
                        Object obj = rs.getObject(i);
                        row[i - 1] = (obj != null) ? obj.toString() : "";
                    }
                    modelo.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        vistaCRUD.getPanelTabla().setModelo(modelo);
    }

    /**
     * Prepara y muestra el formulario para insertar un nuevo registro.
     * Configura los campos y listeners necesarios.
     */
    public void mostrarFormularioNuevo() {
        coPrincipal.setEditando(false);
        String tabla = modeloVista.getTablaActual();
        ArrayList<String> columnas = bd.getNombresColumnas(tabla);

        if (!columnas.isEmpty() && !tabla.equals("especies_recintos")) {
            columnas.remove(0);
        }

        viFormulario.crearFormulario(columnas);

        configurarCombos(tabla);

        if (this.oyente != null) {
            for (ActionListener al : viFormulario.getBotones().get(0)
                    .getActionListeners()) {
                viFormulario.getBotones().get(0).removeActionListener(al);
            }
            for (ActionListener al : viFormulario.getBotones().get(1)
                    .getActionListeners()) {
                viFormulario.getBotones().get(1).removeActionListener(al);
            }

            viFormulario.getBotones().get(0).addActionListener(this.oyente);
            viFormulario.getBotones().get(1).addActionListener(this.oyente);
        }

        viFormulario.hacerVisible();
    }

    /**
     * Prepara y muestra el formulario para actualizar un registro existente.
     * Rellena los campos con los valores de la fila seleccionada.
     */
    public void mostrarFormularioActualizar() {
        coPrincipal.setEditando(true);
        int fila = vistaCRUD.getPanelTabla().getTabla().getSelectedRow();
        if (fila == -1)
            return;

        String tabla = modeloVista.getTablaActual();
        ArrayList<String> columnas = bd.getNombresColumnas(tabla);

        if (!columnas.isEmpty() && !tabla.equals("especies_recintos")) {
            columnas.remove(0);
        }

        viFormulario.crearFormulario(columnas);

        configurarCombos(tabla);

        int totalColumnas = vistaCRUD.getPanelTabla().getTabla().getColumnCount();
        ArrayList<String> valoresList = new ArrayList<>();
        int inicio = 1;

        if (tabla.equals("especies_recintos")) {
            inicio = 0;
        }

        for (int i = inicio; i < totalColumnas; i++) {
            Object val = vistaCRUD.getPanelTabla().getTabla().getValueAt(fila, i);
            valoresList.add(val != null ? val.toString() : "");
        }

        String[] valores = valoresList.toArray(new String[0]);

        viFormulario.rellenarDatos(valores);

        if (this.oyente != null) {
            for (ActionListener al : viFormulario.getBotones().get(0)
                    .getActionListeners()) {
                viFormulario.getBotones().get(0).removeActionListener(al);
            }
            for (ActionListener al : viFormulario.getBotones().get(1)
                    .getActionListeners()) {
                viFormulario.getBotones().get(1).removeActionListener(al);
            }

            viFormulario.getBotones().get(0).addActionListener(this.oyente);
            viFormulario.getBotones().get(1).addActionListener(this.oyente);
        }

        viFormulario.hacerVisible();
    }

    /**
     * Guarda un nuevo registro en la base de datos con la informaci�n del
     * formulario.
     */
    public void guardarNuevo() {
        String tabla = modeloVista.getTablaActual();
        ArrayList<String> columnas = bd.getNombresColumnas(tabla);
        columnas.remove(0);

        String[] valores = viFormulario.obtenerValores();

        String consulta = "INSERT INTO " + tabla + " (";
        String clausulaValores = "VALUES (";

        for (int i = 0; i < columnas.size(); i++) {
            consulta += columnas.get(i);
            clausulaValores += "?";
            if (i < columnas.size() - 1) {
                consulta += ", ";
                clausulaValores += ", ";
            }
        }
        consulta += ") " + clausulaValores + ")";

        ArrayList<String> parametros = new ArrayList<>(Arrays.asList(valores));

        if (bd.ejecutarActualizacion(consulta, parametros) > 0) {
            JOptionPane.showMessageDialog(vistaCRUD, modelo.MoTextos.msg_saved_ok);
            viFormulario.setVisible(false);
            rellenarTabla(tabla);
        } else {
            JOptionPane.showMessageDialog(vistaCRUD, modelo.MoTextos.msg_save_error, modelo.MoTextos.msg_error_title,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza el registro seleccionado en la base de datos con la informaci�n
     * del formulario.
     */
    public void guardarActualizar() {
        int fila = vistaCRUD.getPanelTabla().getTabla().getSelectedRow();
        String tabla = modeloVista.getTablaActual();
        Object id = vistaCRUD.getPanelTabla().getTabla().getValueAt(fila, 0);
        String idCol = vistaCRUD.getPanelTabla().getTabla().getColumnName(0);

        ArrayList<String> columnas = bd.getNombresColumnas(tabla);
        if (!tabla.equals("especies_recintos")) {
            columnas.remove(0);
        }

        String[] valores = viFormulario.obtenerValores();

        String consulta = "UPDATE " + tabla + " SET ";
        for (int i = 0; i < columnas.size(); i++) {
            consulta += columnas.get(i) + " = ?";
            if (i < columnas.size() - 1)
                consulta += ", ";
        }

        if (tabla.equals("especies_recintos")) {
            String idCol2 = vistaCRUD.getPanelTabla().getTabla().getColumnName(1);
            consulta += " WHERE " + idCol + " = ? AND " + idCol2 + " = ?";
        } else {
            consulta += " WHERE " + idCol + " = ?";
        }

        ArrayList<String> parametros = new ArrayList<>(Arrays.asList(valores));
        parametros.add(id.toString());

        if (tabla.equals("especies_recintos")) {
            Object id2 = vistaCRUD.getPanelTabla().getTabla().getValueAt(fila, 1);
            parametros.add(id2.toString());
        }

        if (bd.ejecutarActualizacion(consulta, parametros) > 0) {
            JOptionPane.showMessageDialog(vistaCRUD, modelo.MoTextos.msg_updated_ok);
            viFormulario.setVisible(false);
            rellenarTabla(tabla);
        } else {
            JOptionPane.showMessageDialog(vistaCRUD, modelo.MoTextos.msg_update_error, modelo.MoTextos.msg_error_title,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el registro seleccionado de la tabla y de la base de datos.
     * Pide confirmaci�n al usuario antes de proceder.
     */
    public void eliminarRegistro() {
        int fila = vistaCRUD.getPanelTabla().getTabla().getSelectedRow();
        if (fila == -1)
            return;

        if (JOptionPane.showConfirmDialog(vistaCRUD, modelo.MoTextos.msg_confirm_delete,
                modelo.MoTextos.msg_confirm_title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String tabla = modeloVista.getTablaActual();
            String idCol = vistaCRUD.getPanelTabla().getTabla().getColumnName(0);

            String consulta = "DELETE FROM " + tabla + " WHERE " + idCol + " = ?";

            if (tabla.equals("especies_recintos")) {
                String idCol2 = vistaCRUD.getPanelTabla().getTabla().getColumnName(1);
                consulta += " AND " + idCol2 + " = ?";
            }

            ArrayList<String> parametros = new ArrayList<>();
            parametros.add(vistaCRUD.getPanelTabla().getTabla().getValueAt(fila, 0).toString());

            if (tabla.equals("especies_recintos")) {
                parametros.add(vistaCRUD.getPanelTabla().getTabla().getValueAt(fila, 1).toString());
            }

            if (bd.ejecutarActualizacion(consulta, parametros) > 0) {
                JOptionPane.showMessageDialog(vistaCRUD, modelo.MoTextos.msg_deleted_ok);
                rellenarTabla(tabla);
            } else {
                JOptionPane.showMessageDialog(vistaCRUD, modelo.MoTextos.msg_delete_error,
                        modelo.MoTextos.msg_error_title,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Agrega combos dependiendo la tabla actual.
     * * @param tabla Nombre de la tabla en la base de datos.
     */
    private void configurarCombos(String tabla) {
        if (tabla.equals("animales")) {
            viFormulario.agregarComboEspecies("especie_id", bd.getEspecies());
            viFormulario.agregarComboCuidadores("cuidador_id", bd.getCuidadores());
            viFormulario.agregarComboTipos("tipo",
                    Arrays.asList(modelo.MoTextos.gender_male, modelo.MoTextos.gender_female));
        } else if (tabla.equals("especies_recintos")) {
            viFormulario.agregarComboEspecies("especie_id", bd.getEspecies());
            viFormulario.agregarComboRecintos("recinto_id", bd.getRecintos());
        } else if (tabla.equals("elementos")) {
            viFormulario.agregarComboRecintos("recinto_id", bd.getRecintos());
        } else if (tabla.equals("traslados")) {
            viFormulario.agregarComboAnimales("animal_id", bd.getAnimales());
            viFormulario.agregarComboRecintos("recinto_origen_id", bd.getRecintos());
            viFormulario.agregarComboRecintos("recinto_destino_id", bd.getRecintos());
        }
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene el modelo de base de datos.
     * 
     * @return El objeto ModeloBaseDatos.
     */
    public ModeloBaseDatos getBd() {
        return bd;
    }

    /**
     * Establece el modelo de base de datos.
     * 
     * @param bd El nuevo modelo.
     */
    public void setBd(ModeloBaseDatos bd) {
        this.bd = bd;
    }

    /**
     * Obtiene la vista principal del CRUD.
     * 
     * @return El objeto VistaCRUD.
     */
    public VistaCRUD getVistaCRUD() {
        return vistaCRUD;
    }

    /**
     * Obtiene el modelo de la vista.
     * 
     * @return El objeto MoView.
     */
    public MoView getModeloVista() {
        return modeloVista;
    }

    /**
     * Establece el oyente para los eventos del formulario.
     * 
     * @param oyente El nuevo ActionListener.
     */
    public void setOyente(ActionListener oyente) {
        this.oyente = oyente;
    }
}