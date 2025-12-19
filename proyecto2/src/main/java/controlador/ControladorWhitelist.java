package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.MoTextos;
import modelo.MoView;
import modelo.ModeloBaseDatos;
import vista.ViMain;
import vista.VistaAdmin;
import vista.VistaWhitelist;

/**
 * Controlador para la gestión de la lista blanca (Whitelist) de correos.
 * Permite añadir y eliminar usuarios de la whitelist.
 */
public class ControladorWhitelist {

    private ViMain viMain;
    private ModeloBaseDatos modeloBaseDatos;
    private MoView moView;
    private VistaAdmin vistaAdmin;

    public ControladorWhitelist(ViMain viMain, ModeloBaseDatos modeloBaseDatos, MoView moView, VistaAdmin vistaAdmin) {
        this.viMain = viMain;
        this.modeloBaseDatos = modeloBaseDatos;
        this.moView = moView;
        this.vistaAdmin = vistaAdmin;
    }

    /**
     * Muestra un diálogo para agregar un nuevo usuario a la whitelist.
     * Valida el email y el nombre antes de insertarlo en la base de datos.
     */
    public void anadirUsuario() {
        VistaWhitelist vista = viMain.getViWhitelist();
        int result = vista.mostrarAgregarUsuario();

        if (result != 0) {
            return;
        }

        String email = vista.getTxtEmail().getText().trim();
        String nombre = vista.getTxtNombre().getText().trim();

        if (email.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, modelo.MoTextos.msg_fill_email_name, modelo.MoTextos.msg_error_title,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(vista, modelo.MoTextos.msg_invalid_email, modelo.MoTextos.msg_error_title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (existeEnWhitelist(email)) {
            JOptionPane.showMessageDialog(vista, modelo.MoTextos.msg_user_exists_whitelist,
                    modelo.MoTextos.msg_error_title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO whitelist (correo, nombre, fecha_registro) VALUES (?, ?, NOW())";
        if (modeloBaseDatos.ejecutarActualizacion(sql, new ArrayList<String>(Arrays.asList(email, nombre))) > 0) {
            JOptionPane.showMessageDialog(vista, modelo.MoTextos.msg_user_added_whitelist);
            vista.getTxtEmail().setText("");
            vista.getTxtNombre().setText("");
            rellenarTablaWhitelist();
        } else {
            JOptionPane.showMessageDialog(vista, modelo.MoTextos.msg_err_adding_user, modelo.MoTextos.msg_error_title,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean existeEnWhitelist(String email) {
        String sql = "SELECT * FROM whitelist WHERE correo = ?";
        return modeloBaseDatos.existeRegistro(sql, new ArrayList<String>(Arrays.asList(email)));
    }

    /**
     * Elimina los usuarios seleccionados de la whitelist tras confirmación.
     */
    public void desasignarUsuarios() {
        ArrayList<String> seleccionados = moView.getCorreosWhitelist();
        if (seleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(viMain.getViWhitelist(), "No users selected to remove.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(viMain.getViWhitelist(),
                "Are you sure you want to remove " + seleccionados.size() + " user(s) from Whitelist?",
                "Confirm Removal", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int eliminados = 0;
            for (String email : seleccionados) {
                String sql = "DELETE FROM whitelist WHERE correo = ?";
                if (modeloBaseDatos.ejecutarActualizacion(sql, new ArrayList<String>(Arrays.asList(email))) > 0) {
                    eliminados++;
                }
            }
            JOptionPane.showMessageDialog(viMain.getViWhitelist(),
                    MoTextos.msg_removed_prefix + eliminados + MoTextos.msg_removed_suffix);
            seleccionados.clear();
            rellenarTablaWhitelist();
        }
    }

    /**
     * Cierra la ventana de whitelist y vuelve a la vista de administración.
     */
    public void volver() {
        viMain.getViWhitelist().setVisible(false);
        vistaAdmin.setVisible(true);
    }

    /**
     * Rellena la tabla de la whitelist con los datos de la base de datos.
     */
    public void rellenarTablaWhitelist() {
        DefaultTableModel modelo = (DefaultTableModel) viMain.getViWhitelist().getTabla().getTabla().getModel();
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
        viMain.getViWhitelist().getTabla().deseleccionarFilas();
    }

    /**
     * Maneja la selección visual de un usuario en la tabla.
     *
     * @param email El email del usuario.
     * @param fila  El índice de la fila seleccionada.
     */
    public void seleccionarUsuario(String email, int fila) {
        boolean selected = moView.buscarCorreoWhitelist(email);
        viMain.getViWhitelist().getTabla().cambiarColorFila(fila, !selected);
    }
}
