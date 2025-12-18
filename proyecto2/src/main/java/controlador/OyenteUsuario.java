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
import modelo.User;
import vista.VistaEliminarUsuarios;
import vista.VistaRegistroUsuarios;

public class OyenteUsuario implements ActionListener {
	private VistaRegistroUsuarios vistaUsuario;
	private VistaEliminarUsuarios vistaEliminarUsuarios;
	private ModeloBaseDatos bd;

	public OyenteUsuario(VistaRegistroUsuarios vistaUsuario, VistaEliminarUsuarios vistaEliminarUsuarios, ModeloBaseDatos bd) {
		this.vistaUsuario = vistaUsuario;
		this.vistaEliminarUsuarios = vistaEliminarUsuarios;
		this.bd=bd;
	}

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
				JOptionPane.showMessageDialog(vistaUsuario, "All fields must be filled", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (contrasena.length == 0 || confContrasena.length == 0) {
				JOptionPane.showMessageDialog(vistaUsuario, "All fields must be filled", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (!Arrays.equals(contrasena, confContrasena)) {
				JOptionPane.showMessageDialog(vistaUsuario, "Password doesn't match", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (!vistaUsuario.getTextClaveCorreo().getText().matches("^[a-z]{4}( [a-z]{4}){3}$")) {
				JOptionPane.showMessageDialog(vistaUsuario,
						"Invalid format for Address Key, it must be: xxxx xxxx xxxx xxxx (all in lowercase)", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (!vistaUsuario.getTextCorreo().getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
				JOptionPane.showMessageDialog(vistaUsuario, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				password = new String(contrasena);
				usuario = new User(vistaUsuario.getTextNombre().getText(), vistaUsuario.getTextCorreo().getText(),
						vistaUsuario.getTextClaveCorreo().getText(), password);
				if (vistaUsuario.getDb().registrarUsuario(usuario.getCorreo(), usuario.getNombre(),
						usuario.getContrasena(), usuario.getClaveCorreo())) {
					vistaUsuario.getClient().aniadirUsuario(usuario.getNombre(), usuario.getContrasena());
					JOptionPane.showMessageDialog(vistaUsuario, "User registered correctly", "",
							JOptionPane.INFORMATION_MESSAGE);
					vistaUsuario.getTextNombre().setText("");
					vistaUsuario.getTextCorreo().setText("");
					vistaUsuario.getTextClaveCorreo().setText("");
					vistaUsuario.getTextContrasena().setText("");
					vistaUsuario.getTextConfContrasena().setText("");
				} else {
					JOptionPane.showMessageDialog(vistaUsuario, "User with this name or email already exists", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		} else if (btn == vistaUsuario.getEliminar()) {
			
			rellenarTablaUsuarios();
			vistaUsuario.setVisible(false);
			vistaEliminarUsuarios.hacerVisible();

		} else {
			vistaUsuario.setVisible(false);
			vistaUsuario.getVistaAdmin().hacerVisible();
		}
	}
	
	public void rellenarTablaUsuarios() {
	        DefaultTableModel modeloTabla = new DefaultTableModel() {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        modeloTabla.addColumn("Usuario");
	        modeloTabla.addColumn("Correo");
	        String sql = "SELECT nombre_usuario, email FROM usuarios;";
	        ResultSet rs = bd.getConsulta(sql);

	        try {
	            while (rs.next()) {
	                User usuario = new User(rs.getString("nombre_usuario"), rs.getString("email"));
	                modeloTabla.addRow(new Object[] { usuario.getNombre(), usuario.getCorreo()});
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        vistaEliminarUsuarios.getTabla().setModelo(modeloTabla);

	    
	}

}
