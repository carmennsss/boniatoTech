package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VistaRegistroUsuarios extends JFrame {

	VistaAdmin vistaAdmin;

	public VistaRegistroUsuarios(VistaAdmin vistaAdmin) {
		this.vistaAdmin = vistaAdmin;

		this.setTitle("User Register");
		this.setSize(500, 450); // Adjusted size
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// Main content with BorderLayout
		JPanel mainContent = new JPanel(new java.awt.BorderLayout());
		this.setContentPane(mainContent);

		// Side Panel with Image
		JPanel sidePanel = new JPanel() {
			private java.awt.Image imagen;
			{
				java.net.URL url = getClass().getResource("/lateral_files.jpg");
				if (url != null) {
					imagen = new javax.swing.ImageIcon(url).getImage();
				}
			}

			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				if (imagen != null) {
					// Scale image to fill height, keep aspect ratio or simple fill
					g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
				} else {
					g.setColor(Estilos.COLOR_BOTON_MENU);
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		sidePanel.setPreferredSize(new java.awt.Dimension(200, 0));
		mainContent.add(sidePanel, java.awt.BorderLayout.WEST);

		// Center Panel for Form (Clean background)
		JPanel panelFondo = new JPanel(new java.awt.GridBagLayout());
		panelFondo.setBackground(Estilos.BEIGE_CANVAS);
		mainContent.add(panelFondo, java.awt.BorderLayout.CENTER);

		JLabel nombre = new JLabel("Name: ");
		JTextField textNombre = new JTextField(20);

		JLabel correo = new JLabel("Address: ");
		JTextField textCorreo = new JTextField(20);

		JLabel claveCorreo = new JLabel("Address Key: ");
		JTextField textClaveCorreo = new JTextField(20);

		JLabel contrasena = new JLabel("Password: ");
		JPasswordField textContrasena = new JPasswordField(20);

		JLabel confContrasena = new JLabel("Confirm password: ");
		JPasswordField textConfContrasena = new JPasswordField(20);

		JButton anadir = new JButton("Register user");
		JButton volver = new JButton("Admin Menu");

		// Estilo
		estilarLabel(nombre);
		estilarLabel(correo);
		estilarLabel(claveCorreo);
		estilarLabel(contrasena);
		estilarLabel(confContrasena);
		estilarInput(textNombre);
		estilarInput(textCorreo);
		estilarInput(textClaveCorreo);
		estilarInput(textContrasena);
		estilarInput(textConfContrasena);
		estilarBoton(anadir, Estilos.COLOR_TITULO_APP);
		estilarBoton(volver, new java.awt.Color(200, 100, 100));

		JPanel contenedorForm = new JPanel(new java.awt.GridBagLayout());
		contenedorForm.setOpaque(true);
		contenedorForm.setBackground(new java.awt.Color(255, 255, 255, 200)); // Semi-transparent white
		contenedorForm.setBorder(new EmptyBorder(20, 30, 20, 30));

		java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
		gbc.insets = new java.awt.Insets(5, 5, 5, 5);
		gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

		agregarCampo(contenedorForm, nombre, textNombre, gbc, 0);
		agregarCampo(contenedorForm, correo, textCorreo, gbc, 1);
		agregarCampo(contenedorForm, claveCorreo, textClaveCorreo, gbc, 2);
		agregarCampo(contenedorForm, contrasena, textContrasena, gbc, 3);
		agregarCampo(contenedorForm, confContrasena, textConfContrasena, gbc, 4);

		accionBotonVolver(volver);

		JPanel panelBotones = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));
		panelBotones.setOpaque(false);
		panelBotones.add(anadir);
		panelBotones.add(volver);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.insets = new java.awt.Insets(20, 5, 5, 5);
		contenedorForm.add(panelBotones, gbc);

		panelFondo.add(contenedorForm);
	}

	private void agregarCampo(JPanel panel, JLabel label, javax.swing.JComponent campo, java.awt.GridBagConstraints gbc,
			int row) {
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 1;
		gbc.weightx = 0.3;
		panel.add(label, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		panel.add(campo, gbc);
	}

	private void estilarLabel(JLabel lbl) {
		lbl.setFont(Estilos.FONT_BOTON);
		lbl.setForeground(Estilos.COLOR_LABEL);
	}

	private void estilarInput(javax.swing.JComponent input) {
		input.setFont(Estilos.FONT_TEXTO);
		input.setBackground(Estilos.COLOR_INPUT_BG);
		input.setForeground(Estilos.COLOR_INPUT_TEXT);
	}

	private void estilarBoton(JButton btn, java.awt.Color bg) {
		btn.setFont(Estilos.FONT_BOTON);
		btn.setBackground(bg);
		btn.setForeground(java.awt.Color.WHITE);
		btn.setFocusPainted(false);
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btn.setPreferredSize(new java.awt.Dimension(140, 35));
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

}
