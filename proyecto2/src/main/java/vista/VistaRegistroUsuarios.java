package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.ModeloClienteFTP;
import modelo.User;

public class VistaRegistroUsuarios extends JFrame {

	ModeloClienteFTP client;
	VistaAdmin vistaAdmin;
	JLabel nombre;
	JTextField textNombre;
	JLabel correo;
	JTextField textCorreo;
	JLabel claveCorreo;
	JTextField textClaveCorreo;
	JLabel contrasena;
	JPasswordField textContrasena;
	JLabel confContrasena;
	JPasswordField textConfContrasena;

	public VistaRegistroUsuarios(VistaAdmin vistaAdmin, ModeloClienteFTP client) {
		this.vistaAdmin = vistaAdmin;
		this.client = client;
		
		this.setTitle("User Register");
		this.setLayout(new BorderLayout());

		nombre = new JLabel("Name: ");
		textNombre = new JTextField(20);

		correo = new JLabel("Address: ");
		textCorreo = new JTextField(20);

		claveCorreo = new JLabel("Address Key: ");
		textClaveCorreo = new JTextField(20);

		contrasena = new JLabel("Password: ");
		textContrasena = new JPasswordField(20);

		confContrasena = new JLabel("Confirm password: ");
		textConfContrasena = new JPasswordField(20);

		JButton aniadir = new JButton("Register user");
		JButton volver = new JButton("Admin Menu");

		JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel layout = new JPanel(new GridLayout(5, 2, 10, 10));
		layout.setBorder(new EmptyBorder(20, 20, 20, 20));

		layout.add(nombre);
		layout.add(textNombre);

		layout.add(correo);
		layout.add(textCorreo);

		layout.add(claveCorreo);
		layout.add(textClaveCorreo);

		layout.add(contrasena);
		layout.add(textContrasena);

		layout.add(confContrasena);
		layout.add(textConfContrasena);

		accionBotonVolver(volver);
		accionBotonAniadir(aniadir);
		
		contenedor.add(layout);
		contenedor.add(aniadir);
		contenedor.add(volver);

		this.add(contenedor, BorderLayout.CENTER);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void hacerVisible() {
		this.setVisible(true);
	}

	public void accionBotonVolver(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VistaRegistroUsuarios.this.setVisible(false);
				vistaAdmin.hacerVisible();
			}
		});
	}

	public void accionBotonAniadir(JButton boton) {

		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User usuario;
				char[] contrasena = textContrasena.getPassword();
	            char[] confContrasena = textConfContrasena.getPassword();

	            if (!java.util.Arrays.equals(contrasena, confContrasena)) {
					JOptionPane.showMessageDialog(VistaRegistroUsuarios.this, "Password doesn't match", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					usuario = new User(textNombre.getText(), textCorreo.getText(), textClaveCorreo.getText(),
							textContrasena.getPassword().toString());
					client.aniadirUsuario(usuario.getNombre(),usuario.getContrasena());
					JOptionPane.showMessageDialog(VistaRegistroUsuarios.this, "User registered correctly", "",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

	}

}
