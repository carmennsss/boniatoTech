package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.ModeloBaseDatos;
import modelo.ModeloClienteFTP;
import modelo.User;

public class VistaRegistroUsuarios extends JFrame {

	ModeloClienteFTP client;
	ModeloBaseDatos db;
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

	private Image imagenFondo;

	public VistaRegistroUsuarios(VistaAdmin vistaAdmin, ModeloClienteFTP client, ModeloBaseDatos db) {
		this.vistaAdmin = vistaAdmin;
		this.client = client;
		this.db = db;

		this.setTitle("User Register");
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		URL url = getClass().getResource("/fondo_abstracto_1.png");
		if (url != null) {
			imagenFondo = new ImageIcon(url).getImage();
		}

		JPanel panelFondo = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagenFondo != null) {
					int width = getWidth();
					int height = getHeight();
					g.drawImage(imagenFondo, 0, 0, width, height, this);
				} else {
					g.setColor(new Color(248, 245, 242));
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		panelFondo.setLayout(new GridBagLayout());
		setContentPane(panelFondo);

		JPanel panelCentral = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(255, 255, 255, 230));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
				g2.dispose();
			}
		};
		panelCentral.setOpaque(false);
		panelCentral.setBorder(new EmptyBorder(30, 40, 30, 40));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel titulo = new JLabel("Register User", SwingConstants.CENTER);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		titulo.setForeground(new Color(74, 88, 89));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelCentral.add(titulo, gbc);

		gbc.gridwidth = 1;

		Font fontLabel = new Font("Segoe UI", Font.BOLD, 14);
		Font fontText = new Font("Segoe UI", Font.PLAIN, 14);
		Color colorLabel = new Color(74, 88, 89);

		nombre = new JLabel("Name: ");
		nombre.setFont(fontLabel);
		nombre.setForeground(colorLabel);
		textNombre = new JTextField(20);
		textNombre.setFont(fontText);

		correo = new JLabel("Email Address: ");
		correo.setFont(fontLabel);
		correo.setForeground(colorLabel);
		textCorreo = new JTextField(20);
		textCorreo.setFont(fontText);

		claveCorreo = new JLabel("Address Key: ");
		claveCorreo.setFont(fontLabel);
		claveCorreo.setForeground(colorLabel);
		textClaveCorreo = new JTextField(20);
		textClaveCorreo.setFont(fontText);

		contrasena = new JLabel("Password: ");
		contrasena.setFont(fontLabel);
		contrasena.setForeground(colorLabel);
		textContrasena = new JPasswordField(20);
		textContrasena.setFont(fontText);

		confContrasena = new JLabel("Confirm Password: ");
		confContrasena.setFont(fontLabel);
		confContrasena.setForeground(colorLabel);
		textConfContrasena = new JPasswordField(20);
		textConfContrasena.setFont(fontText);

		agregarCampo(panelCentral, gbc, 1, nombre, textNombre);
		agregarCampo(panelCentral, gbc, 2, correo, textCorreo);
		agregarCampo(panelCentral, gbc, 3, claveCorreo, textClaveCorreo);
		agregarCampo(panelCentral, gbc, 4, contrasena, textContrasena);
		agregarCampo(panelCentral, gbc, 5, confContrasena, textConfContrasena);

		JButton aniadir = new JButton("Register");
		JButton volver = new JButton("Back");

		estilarBoton(aniadir, new Color(110, 137, 115));
		estilarBoton(volver, new Color(200, 100, 100));

		accionBotonVolver(volver);
		accionBotonAniadir(aniadir);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;

		JPanel panelBotones = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 0));
		panelBotones.setOpaque(false);
		panelBotones.add(aniadir);
		panelBotones.add(volver);

		panelCentral.add(panelBotones, gbc);

		panelFondo.add(panelCentral);
	}

	private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, JLabel label, JTextField campo) {
		gbc.gridx = 0;
		gbc.gridy = fila;
		gbc.weightx = 0.3;
		panel.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = fila;
		gbc.weightx = 0.7;
		panel.add(campo, gbc);
	}

	private void estilarBoton(JButton btn, Color bgColor) {
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btn.setBackground(bgColor);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setPreferredSize(new Dimension(150, 40));
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
				String password;
				char[] contrasena = textContrasena.getPassword();
				char[] confContrasena = textConfContrasena.getPassword();
				if (textNombre.getText().trim().isEmpty() || textCorreo.getText().trim().isEmpty()
						|| textClaveCorreo.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(VistaRegistroUsuarios.this, "All fields must be filled", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (contrasena.length == 0 || confContrasena.length == 0) {
					JOptionPane.showMessageDialog(VistaRegistroUsuarios.this, "All fields must be filled", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (!Arrays.equals(contrasena, confContrasena)) {
					JOptionPane.showMessageDialog(VistaRegistroUsuarios.this, "Password doesn't match", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (!textClaveCorreo.getText().matches("^[a-z]{4}( [a-z]{4}){3}$")) {
					JOptionPane.showMessageDialog(VistaRegistroUsuarios.this,
							"Invalid format for Address Key, it must be: xxxx xxxx xxxx xxxx (all in lowercase)",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else if (!textCorreo.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
					JOptionPane.showMessageDialog(VistaRegistroUsuarios.this, "Invalid email format", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					password = new String(contrasena);
					usuario = new User(textNombre.getText(), textCorreo.getText(), textClaveCorreo.getText(), password);
					if (db.registrarUsuario(usuario.getCorreo(), usuario.getNombre(), usuario.getContrasena(),
							usuario.getClaveCorreo())) {
						client.aniadirUsuario(usuario.getNombre(), usuario.getContrasena());
						JOptionPane.showMessageDialog(VistaRegistroUsuarios.this, "User registered correctly", "",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(VistaRegistroUsuarios.this,
								"User with this name or email already exists", "Error", JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});

	}

}
