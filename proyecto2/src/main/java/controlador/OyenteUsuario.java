package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.ModeloBaseDatos;
import modelo.ModeloClienteFTP;
import modelo.User;
import vista.VistaEliminarUsuarios;
import vista.VistaRegistroUsuarios;

/**
 * Oyente que gestiona los eventos relacionados con el registro y eliminación de
 * usuarios.
 */
public class OyenteUsuario implements ActionListener {
	private VistaRegistroUsuarios vistaUsuario;
	private VistaEliminarUsuarios vistaEliminarUsuarios;
	private ModeloBaseDatos bd;
	private ModeloClienteFTP client;

	public OyenteUsuario(VistaRegistroUsuarios vistaUsuario, VistaEliminarUsuarios vistaEliminarUsuarios,
			ModeloBaseDatos bd, ModeloClienteFTP client) {
		this.vistaUsuario = vistaUsuario;
		this.vistaEliminarUsuarios = vistaEliminarUsuarios;
		this.bd = bd;
		this.client = client;
	}

	/**
	 * Maneja los eventos de botón para añadir o eliminar usuarios.
	 *
	 * @param e El evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();

		if (btn == vistaUsuario.getAniadir()) {
			User usuario;
			String password;
			char[] contrasena = vistaUsuario.getTextContrasena().getPassword();
			char[] confContrasena = vistaUsuario.getTextConfContrasena().getPassword();
			if (vistaUsuario.getTextNombre().getText().trim().isEmpty()
					|| vistaUsuario.getTextCorreo().getText().trim().isEmpty()
					|| vistaUsuario.getTextClaveCorreo().getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(vistaUsuario, modelo.MoTextos.msg_fill_all_fields,
						modelo.MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
			} else if (contrasena.length == 0 || confContrasena.length == 0) {
				JOptionPane.showMessageDialog(vistaUsuario, modelo.MoTextos.msg_fill_all_fields,
						modelo.MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
			} else if (!Arrays.equals(contrasena, confContrasena)) {
				JOptionPane.showMessageDialog(vistaUsuario, modelo.MoTextos.msg_pass_mismatch,
						modelo.MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
			} else if (!vistaUsuario.getTextClaveCorreo().getText().matches("^[a-z]{4}( [a-z]{4}){3}$")) {
				JOptionPane.showMessageDialog(vistaUsuario,
						"Invalid format for Address Key, it must be: xxxx xxxx xxxx xxxx (all in lowercase)",
						modelo.MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
			} else if (!vistaUsuario.getTextCorreo().getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
				JOptionPane.showMessageDialog(vistaUsuario, modelo.MoTextos.msg_invalid_email,
						modelo.MoTextos.msg_error_title, JOptionPane.ERROR_MESSAGE);
			} else {
				password = new String(contrasena);
				usuario = new User(vistaUsuario.getTextNombre().getText(), vistaUsuario.getTextCorreo().getText(),
						vistaUsuario.getTextClaveCorreo().getText(), password);
				if (vistaUsuario.getDb().registrarUsuario(usuario.getCorreo(), usuario.getNombre(),
						usuario.getContrasena(), usuario.getClaveCorreo())) {
					vistaUsuario.getClient().aniadirUsuario(usuario.getNombre(), usuario.getContrasena());
					JOptionPane.showMessageDialog(vistaUsuario, modelo.MoTextos.msg_user_registered,
							modelo.MoTextos.msg_success_title,
							JOptionPane.INFORMATION_MESSAGE);
					vistaUsuario.getTextNombre().setText("");
					vistaUsuario.getTextCorreo().setText("");
					vistaUsuario.getTextClaveCorreo().setText("");
					vistaUsuario.getTextContrasena().setText("");
					vistaUsuario.getTextConfContrasena().setText("");
				} else {
					JOptionPane.showMessageDialog(vistaUsuario, modelo.MoTextos.msg_user_exists,
							modelo.MoTextos.msg_error_title,
							JOptionPane.ERROR_MESSAGE);
				}

			}

		} else if (btn == vistaUsuario.getEliminar()) {

			vistaUsuario.setVisible(false);
			vistaEliminarUsuarios.hacerVisible();
			rellenarTablaUsuarios();

		} else if (btn == vistaEliminarUsuarios.getBtnVolver()) {
			vistaEliminarUsuarios.setVisible(false);
			vistaUsuario.hacerVisible();
		} else if (btn == vistaEliminarUsuarios.getBtnEliminar()) {

			int filaSeleccionada = vistaEliminarUsuarios.getTabla().getTabla().getSelectedRow();

			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(vistaEliminarUsuarios, modelo.MoTextos.del_msg_select,
						modelo.MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String nombre = vistaEliminarUsuarios.getTabla().getTabla().getValueAt(filaSeleccionada, 0).toString();
			String email = vistaEliminarUsuarios.getTabla().getTabla().getValueAt(filaSeleccionada, 1).toString();

			int confirmacion = JOptionPane.showConfirmDialog(vistaEliminarUsuarios,
					modelo.MoTextos.del_msg_confirm, modelo.MoTextos.msg_confirm_title, JOptionPane.YES_NO_OPTION);

			if (confirmacion == JOptionPane.YES_OPTION) {
				if (bd.eliminarUsuario(email)) {
					client.eliminarUsuario(nombre);
					JOptionPane.showMessageDialog(vistaEliminarUsuarios, modelo.MoTextos.del_msg_success,
							modelo.MoTextos.msg_success_title,
							JOptionPane.INFORMATION_MESSAGE);
					rellenarTablaUsuarios();
				} else {
					JOptionPane.showMessageDialog(vistaEliminarUsuarios, modelo.MoTextos.del_msg_error,
							modelo.MoTextos.msg_error_title,
							JOptionPane.ERROR_MESSAGE);
				}
			}

		} else {
			vistaUsuario.setVisible(false);
			vistaUsuario.getVistaAdmin().hacerVisible();
		}
	}

	/**
	 * Rellena la tabla de eliminación de usuarios con los datos actuales de la base
	 * de datos.
	 * Excluye al usuario actual de la lista.
	 */
	public void rellenarTablaUsuarios() {
		DefaultTableModel modeloTabla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modeloTabla.addColumn(modelo.MoTextos.del_user_col_user);
		modeloTabla.addColumn(modelo.MoTextos.del_user_col_email);
		String sql = "SELECT nombre_usuario, email FROM usuarios WHERE email NOT LIKE '"
				+ bd.obtenerEmailPorUsuario(client.getUser()) + "'";
		ResultSet rs = bd.getConsulta(sql);

		try {
			while (rs.next()) {
				User usuario = new User(rs.getString("nombre_usuario"), rs.getString("email"));
				modeloTabla.addRow(new Object[] { usuario.getNombre(), usuario.getCorreo() });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		vistaEliminarUsuarios.getTabla().setModelo(modeloTabla);

	}

}
