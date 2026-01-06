/*
 * @author Carmen - BoniatoTech
 * @version 1.0
 */
package controladorWhitelist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.MoTextos;
import modelo.MoView;
import modelo.ModeloBaseDatos;
import vista.VistaAdmin;
import vista.VistaWhitelist;

/**
 * Controlador para la gesti�n de la lista blanca (Whitelist) de correos.
 * Permite a�adir y eliminar usuarios de la whitelist.
 */
public class ControladorWhitelist {
    /** Modelo de base de datos para operaciones de persistencia. */
    private ModeloBaseDatos modeloBaseDatos;

    /** Modelo de vista para gesti�n de datos. */
    private MoView moView;

    /** Vista de administraci�n. */
    private VistaAdmin vistaAdmin;

    /** Vista de whitelist. */
    private VistaWhitelist vistaWhitelist;

    /**
     * Constructor del controlador de whitelist.
     *
     * @param vistaWhitelist  Vista de whitelist.
     * @param modeloBaseDatos Modelo de base de datos.
     * @param moView          Modelo de vista.
     * @param vistaAdmin      Vista de administraci�n.
     */
    public ControladorWhitelist(VistaWhitelist vistaWhitelist, ModeloBaseDatos modeloBaseDatos, MoView moView,
            VistaAdmin vistaAdmin) {
        this.vistaWhitelist = vistaWhitelist;
        this.modeloBaseDatos = modeloBaseDatos;
        this.moView = moView;
        this.vistaAdmin = vistaAdmin;
    }

    /**
     * Abre la vista de gesti�n de whitelist.
     * Rellena la tabla y muestra la vista, ocultando la vista de administraci�n.
     */
    public void abrirWhitelist() {
        rellenarTablaWhitelist();
        vistaWhitelist.hacerVisible();
        vistaAdmin.setVisible(false);
    }

    /**
     * Muestra un di�logo para agregar un nuevo usuario a la whitelist.
     * Valida el email y el nombre antes de insertarlo en la base de datos.
     */
    public void anadirUsuario() {
        int result = vistaWhitelist.mostrarAgregarUsuario();

        if (result != 0) {
            return;
        }

        String email = vistaWhitelist.getTxtEmail().getText().trim();
        String nombre = vistaWhitelist.getTxtNombre().getText().trim();

        if (email.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vistaWhitelist, modelo.MoTextos.msg_fill_email_name,
                    modelo.MoTextos.msg_error_title,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(vistaWhitelist, modelo.MoTextos.msg_invalid_email,
                    modelo.MoTextos.msg_error_title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (existeEnWhitelist(email)) {
            JOptionPane.showMessageDialog(vistaWhitelist, modelo.MoTextos.msg_user_exists_whitelist,
                    modelo.MoTextos.msg_error_title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO whitelist (correo, nombre, fecha_registro) VALUES (?, ?, NOW())";
        if (modeloBaseDatos.ejecutarActualizacion(sql, new ArrayList<String>(Arrays.asList(email, nombre))) > 0) {
            JOptionPane.showMessageDialog(vistaWhitelist, modelo.MoTextos.msg_user_added_whitelist);
            vistaWhitelist.getTxtEmail().setText("");
            vistaWhitelist.getTxtNombre().setText("");
            rellenarTablaWhitelist();
        } else {
            JOptionPane.showMessageDialog(vistaWhitelist, modelo.MoTextos.msg_err_adding_user,
                    modelo.MoTextos.msg_error_title,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina los usuarios seleccionados de la whitelist tras confirmaci�n.
     */
    public void desasignarUsuarios() {
        ArrayList<String> seleccionados = moView.getCorreosWhitelist();
        if (seleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(vistaWhitelist, MoTextos.msg_no_users_selected, MoTextos.title_warning,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vistaWhitelist,
                MoTextos.msg_confirm_remove_prefix + seleccionados.size() + MoTextos.msg_confirm_remove_suffix,
                MoTextos.title_confirm_removal, JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int eliminados = 0;
            for (String email : seleccionados) {
                String sql = "DELETE FROM whitelist WHERE correo = ?";
                if (modeloBaseDatos.ejecutarActualizacion(sql, new ArrayList<String>(Arrays.asList(email))) > 0) {
                    eliminados++;
                }
            }
            JOptionPane.showMessageDialog(vistaWhitelist,
                    MoTextos.msg_removed_prefix + eliminados + MoTextos.msg_removed_suffix);
            seleccionados.clear();
            rellenarTablaWhitelist();
        }
    }

    /**
     * Rellena la tabla de la whitelist con los datos de la base de datos.
     */
    public void rellenarTablaWhitelist() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vistaWhitelist.getTabla().getTabla().setModel(modelo);
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        modelo.addColumn(MoTextos.whitelist_col_email);
        modelo.addColumn(MoTextos.whitelist_col_name);
        modelo.addColumn(MoTextos.whitelist_col_date);

        String sql = "SELECT * FROM whitelist";
        ResultSet rs = modeloBaseDatos.getConsulta(sql);
        try {
            while (rs.next()) {
                modelo.addRow(new Object[] {
                        rs.getString("correo"),
                        rs.getString("nombre"),
                        rs.getString("fecha_registro")
                });
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        moView.getCorreosWhitelist().clear();
        vistaWhitelist.getTabla().deseleccionarFilas();
    }

    /**
     * Maneja la selecci�n visual de un usuario en la tabla.
     *
     * @param email El email del usuario.
     * @param fila  El �ndice de la fila seleccionada.
     */
    public void seleccionarUsuario(String email, int fila) {
        boolean selected = moView.buscarCorreoWhitelist(email);
        vistaWhitelist.getTabla().cambiarColorFila(fila, !selected);
    }

    /**
     * Cierra la ventana de whitelist y vuelve a la vista de administraci�n.
     */
    public void volver() {
        vistaWhitelist.setVisible(false);
        vistaAdmin.setVisible(true);
    }

    /**
     * Verifica si un email ya existe en la whitelist.
     *
     * @param email El email a verificar.
     * @return true si el email existe en la whitelist, false en caso contrario.
     */
    private boolean existeEnWhitelist(String email) {
        String sql = "SELECT * FROM whitelist WHERE correo = ?";
        return modeloBaseDatos.existeRegistro(sql, new ArrayList<String>(Arrays.asList(email)));
    }

    // --- GETTERS Y SETTERS ---

    public ModeloBaseDatos getModeloBaseDatos() {
        return modeloBaseDatos;
    }

    public void setModeloBaseDatos(ModeloBaseDatos modeloBaseDatos) {
        this.modeloBaseDatos = modeloBaseDatos;
    }

    public MoView getMoView() {
        return moView;
    }

    public void setMoView(MoView moView) {
        this.moView = moView;
    }

    public VistaAdmin getVistaAdmin() {
        return vistaAdmin;
    }

    public void setVistaAdmin(VistaAdmin vistaAdmin) {
        this.vistaAdmin = vistaAdmin;
    }

    public VistaWhitelist getVistaWhitelist() {
        return vistaWhitelist;
    }

    public void setVistaWhitelist(VistaWhitelist vistaWhitelist) {
        this.vistaWhitelist = vistaWhitelist;
    }
}