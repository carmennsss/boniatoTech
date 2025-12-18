package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.ModeloClienteFTP;

public class VistaMenuPrincipal extends JFrame {
	ModeloClienteFTP client;
	JButton botonCRUD;
	JButton botonFileManager;
	JButton botonCerrarSesion;
	JButton botonAdmin;
	ViMain vista;

	public VistaMenuPrincipal(ModeloClienteFTP client, ViMain vista) {
		this.client = client;
		this.vista = vista;
		this.setTitle("Main Menu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);

		Color colorFondo = new Color(248, 245, 242);
		Color colorTexto = new Color(74, 88, 89);
		Color colorBoton = new Color(196, 164, 132);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(colorFondo);
		setContentPane(mainPanel);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		JPanel sidePanel = new JPanel() {
			private Image imagen;
			{
				URL url = getClass().getResource("/lateral_menu.jpg");
				if (url != null) {
					imagen = new ImageIcon(url).getImage();
				}
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagen != null) {
					double scale = Math.max((double) getWidth() / imagen.getWidth(this),
							(double) getHeight() / imagen.getHeight(this));
					int w = (int) (imagen.getWidth(this) * scale);
					int h = (int) (imagen.getHeight(this) * scale);
					g.drawImage(imagen, 0, 0, w, h, this);
				}
			}
		};

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.4;
		gbc.weighty = 1.0;
		mainPanel.add(sidePanel, gbc);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(colorFondo);
		contentPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.6;
		gbc.weighty = 1.0;
		mainPanel.add(contentPanel, gbc);

		JLabel text = new JLabel("Zoo Manager");
		text.setFont(new Font("Segoe UI", Font.BOLD, 42));
		text.setForeground(new Color(60, 70, 60));
		text.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel subtext = new JLabel("Select an option");
		subtext.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		subtext.setForeground(colorTexto);
		subtext.setAlignmentX(Component.CENTER_ALIGNMENT);

		botonCRUD = new JButton("Manage Data");
		botonFileManager = new JButton("File Manager");
		botonCerrarSesion = new JButton("Log out");
		botonAdmin = new JButton("Administrate");

		estilarBoton(botonCRUD, colorBoton, Color.WHITE);
		estilarBoton(botonFileManager, colorBoton, Color.WHITE);
		estilarBoton(botonAdmin, colorBoton, Color.WHITE);
		estilarBoton(botonCerrarSesion, new Color(200, 100, 100), Color.WHITE);

		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(text);
		contentPanel.add(Box.createVerticalStrut(10));
		contentPanel.add(subtext);
		contentPanel.add(Box.createVerticalStrut(60));
		contentPanel.add(botonCRUD);
		contentPanel.add(Box.createVerticalStrut(20));
		contentPanel.add(botonFileManager);
		contentPanel.add(Box.createVerticalStrut(20));
		contentPanel.add(botonAdmin);
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(botonCerrarSesion);
		contentPanel.add(Box.createVerticalStrut(20));

		accionBotonCerrarSesion(botonCerrarSesion);
	}

	private void estilarBoton(JButton btn, Color bgColor, Color fgColor) {
		btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btn.setBackground(bgColor);
		btn.setForeground(fgColor);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setMaximumSize(new Dimension(300, 50)); // Ancho fijo, altura fija
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public void accionBotonCerrarSesion(JButton boton) {
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.desconectar();
				VistaMenuPrincipal.this.setVisible(false);
				vista.setVisible(true);
				vista.getPanelLogin().getCajas().get(0).setText("");
				vista.getPanelLogin().getCajas().get(1).setText("");
				vista.mostrarLogin();
			}
		});
	}

	public JButton getBotonFileManager() {
		return botonFileManager;
	}

	public JButton getBotonAdmin() {
		return botonAdmin;
	}

	public void setBotonFileManager(JButton botonFileManager) {
		this.botonFileManager = botonFileManager;
	}

	public JButton getBotonCRUD() {
		return botonCRUD;
	}

	public void setBotonCRUD(JButton botonCRUD) {
		this.botonCRUD = botonCRUD;
	}

	public void hacerVisible() {
		setVisible(true);
	}

}
