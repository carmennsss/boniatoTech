package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.User;
import vista.VistaRegistroUsuarios;

public class OyenteUsuario implements ActionListener {
	private VistaRegistroUsuarios vistaUsuario;

	public OyenteUsuario(VistaRegistroUsuarios vistaUsuario) {
		this.vistaUsuario = vistaUsuario;
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

		} else {
			vistaUsuario.setVisible(false);
			vistaUsuario.getVistaAdmin().hacerVisible();

		}

	}
}
