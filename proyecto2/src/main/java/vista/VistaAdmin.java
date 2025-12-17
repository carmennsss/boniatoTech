package vista;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.ModeloClienteFTP;

public class VistaAdmin extends JFrame {

	VistaMenuPrincipal menu;
	JButton botonCrearUsuario;
	JButton botonCrearRoles;
	JButton botonAsignarRoles;
	JButton botonVolver;

	public VistaAdmin(VistaMenuPrincipal menu) {
		this.menu = menu;
		this.setTitle("Administrator menu");
		this.setSize(500, 400); // Increased size for better layout
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// Panel with background image
		JPanel panelFondo = new JPanel() {
			private java.awt.Image imagen;
			{
				java.net.URL url = getClass().getResource("/fondo_login.jpg");
				if (url != null) {
					imagen = new javax.swing.ImageIcon(url).getImage();
				}
			}

			@Override
			protected void paintComponent(java.awt.Graphics g) {
				super.paintComponent(g);
				if (imagen != null) {
					int width = getWidth();
					int height = getHeight();
					g.drawImage(imagen, 0, 0, width, height, this);
				} else {
					g.setColor(Estilos.FONDO_PRINCIPAL);
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		panelFondo.setLayout(new java.awt.GridBagLayout());
		setContentPane(panelFondo);

		botonCrearUsuario = new JButton("Administrate users");
		botonCrearRoles = new JButton("Administrate roles");
		botonAsignarRoles = new JButton("Asign roles");
		botonVolver = new JButton("Main Menu");

		estilarBoton(botonCrearUsuario);
		estilarBoton(botonCrearRoles);
		estilarBoton(botonAsignarRoles);
		estilarBoton(botonVolver);
		// Special color for back button
		botonVolver.setBackground(new java.awt.Color(200, 100, 100));

		JPanel layout = new JPanel(new GridLayout(4, 1, 10, 15));
		layout.setOpaque(false); // Transparent to show background
		layout.setBorder(new EmptyBorder(20, 20, 20, 20));

		accionBotonVolver(botonVolver);

		layout.add(botonCrearUsuario);
		layout.add(botonCrearRoles);
		layout.add(botonAsignarRoles);
		layout.add(botonVolver);

		panelFondo.add(layout);
	}

	private void estilarBoton(JButton btn) {
		btn.setFont(Estilos.FONT_BOTON);
		btn.setBackground(Estilos.COLOR_BOTON_MENU);
		btn.setForeground(java.awt.Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btn.setPreferredSize(new java.awt.Dimension(250, 40));
	}

	public JButton getBotonCrearUsuario() {
		return botonCrearUsuario;
	}

	public void setBotonCrearUsuario(JButton botonCrearUsuario) {
		this.botonCrearUsuario = botonCrearUsuario;
	}

	public JButton getBotonCrearRoles() {
		return botonCrearRoles;
	}

	public void setBotonCrearRoles(JButton botonCrearRoles) {
		this.botonCrearRoles = botonCrearRoles;
	}

	public JButton getBotonAsignarRoles() {
		return botonAsignarRoles;
	}

	public void setBotonAsignarRoles(JButton botonAsignarRoles) {
		this.botonAsignarRoles = botonAsignarRoles;
	}

	public JButton getBotonVolver() {
		return botonVolver;
	}

	public void setBotonVolver(JButton botonVolver) {
		this.botonVolver = botonVolver;
	}

	public void hacerVisible() {
		this.setVisible(true);
	}

	public void accionBotonVolver(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.hacerVisible();
				VistaAdmin.this.setVisible(false);
			}
		});

	}

}
