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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VistaAdmin extends JFrame {

	VistaMenuPrincipal menu;
	JButton botonCrearUsuario;
	JButton botonCrearRoles;
	JButton botonAsignarRoles;
	JButton botonVolver;
	private Image imagenFondo;

	public VistaAdmin(VistaMenuPrincipal menu) {
		this.menu = menu;
		this.setTitle("Administrator Menu");
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		URL url = getClass().getResource("/fondo_zoo_2.png");
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
					g.setColor(new Color(40, 44, 52));
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
				g2.setColor(new Color(255, 255, 255, 220));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
				g2.dispose();
			}
		};
		panelCentral.setOpaque(false);
		panelCentral.setBorder(new EmptyBorder(40, 60, 40, 60));

		botonCrearUsuario = new JButton("Manage Users");
		botonCrearRoles = new JButton("Manage Roles");
		botonAsignarRoles = new JButton("Assign Roles");
		botonVolver = new JButton("Main Menu");

		estilarBoton(botonCrearUsuario, new Color(110, 137, 115));
		estilarBoton(botonCrearRoles, new Color(110, 137, 115));
		estilarBoton(botonAsignarRoles, new Color(110, 137, 115));
		estilarBoton(botonVolver, new Color(200, 100, 100));

		accionBotonVolver(botonVolver);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 0, 15, 0);
		gbc.gridx = 0;

		JLabel titulo = new JLabel("Administration", SwingConstants.CENTER);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titulo.setForeground(new Color(60, 70, 60));

		gbc.gridy = 0;
		panelCentral.add(titulo, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 10, 0);
		panelCentral.add(botonCrearUsuario, gbc);

		gbc.gridy = 2;
		panelCentral.add(botonCrearRoles, gbc);

		gbc.gridy = 3;
		panelCentral.add(botonAsignarRoles, gbc);

		gbc.gridy = 4;
		gbc.insets = new Insets(30, 0, 10, 0);
		panelCentral.add(botonVolver, gbc);

		panelFondo.add(panelCentral);
	}

	private void estilarBoton(JButton btn, Color color) {
		btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btn.setBackground(color);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setPreferredSize(new Dimension(280, 50));
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
