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

import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.ModeloClienteFTP;

import modelo.MoTextos;

public class VistaMenuPrincipal extends JFrame {
	ModeloClienteFTP client;
	JButton botonCRUD;
	JButton botonFileManager;
	JButton botonCerrarSesion;
	JButton botonAdmin;
	JButton botonCorreo;
	ViMain vista;

	private JComboBox<ImageIcon> comboIdiomas;

	JLabel text;
	JLabel subtext;

	public VistaMenuPrincipal(ModeloClienteFTP client, ViMain vista) {
		this.client = client;
		this.vista = vista;
		this.setTitle(MoTextos.menu_title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);

		Color colorFondo = new Color(248, 245, 242);
		Color colorTexto = new Color(74, 88, 89);
		Color colorBoton = new Color(196, 164, 132);

		// Main Container (LayeredPane for absolute positioning of Combo)
		JLayeredPane layeredPane = new JLayeredPane();
		setContentPane(layeredPane);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(colorFondo);
		// mainPanel will be added to layeredPane in componentResized or setBounds

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

		text = new JLabel(MoTextos.app_title);
		text.setFont(new Font("Segoe UI", Font.BOLD, 42));
		text.setForeground(new Color(60, 70, 60));
		text.setAlignmentX(Component.CENTER_ALIGNMENT);

		subtext = new JLabel(MoTextos.lbl_select_option);
		subtext.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		subtext.setForeground(colorTexto);
		subtext.setAlignmentX(Component.CENTER_ALIGNMENT);

		botonCRUD = new JButton(MoTextos.btn_manage_data);
		botonFileManager = new JButton(MoTextos.btn_file_manager);
		botonCerrarSesion = new JButton(MoTextos.btn_logout);
		botonAdmin = new JButton(MoTextos.btn_administrate);
		botonCorreo = new JButton(MoTextos.btn_mail_controller);

		estilarBoton(botonCRUD, colorBoton, Color.WHITE);
		estilarBoton(botonFileManager, colorBoton, Color.WHITE);
		estilarBoton(botonAdmin, colorBoton, Color.WHITE);
		estilarBoton(botonCorreo, colorBoton, Color.WHITE);
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
		contentPanel.add(botonCorreo);
		contentPanel.add(Box.createVerticalStrut(20));
		contentPanel.add(botonAdmin);
		contentPanel.add(Box.createVerticalStrut(20));
		contentPanel.add(botonCerrarSesion);
		contentPanel.add(Box.createVerticalStrut(20));

		// Language Combo
		ImageIcon iconEng = null;
		ImageIcon iconEsp = null;
		try {
			java.net.URL urlEng = getClass().getResource("/eng.png");
			java.net.URL urlEsp = getClass().getResource("/esp.png");
			if (urlEng != null)
				iconEng = new ImageIcon(new ImageIcon(urlEng).getImage().getScaledInstance(30, 20, Image.SCALE_SMOOTH));
			if (urlEsp != null)
				iconEsp = new ImageIcon(new ImageIcon(urlEsp).getImage().getScaledInstance(30, 20, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			e.printStackTrace();
		}

		comboIdiomas = new JComboBox<>();
		if (iconEng != null)
			comboIdiomas.addItem(iconEng);
		if (iconEsp != null)
			comboIdiomas.addItem(iconEsp);

		// Set initial selection based on current language
		if (MoTextos.getIdioma() == 0 && iconEng != null) {
			comboIdiomas.setSelectedItem(iconEng);
		} else if (MoTextos.getIdioma() == 1 && iconEsp != null) {
			comboIdiomas.setSelectedItem(iconEsp);
		}

		// Local listener removed - now handled by OyenteIdioma

		// Local listener removed - now handled by OyenteIdioma

		// Add components to layered pane
		layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(comboIdiomas, JLayeredPane.PALETTE_LAYER);

		layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				int width = layeredPane.getWidth();
				int height = layeredPane.getHeight();

				// Main Panel covers everything
				mainPanel.setBounds(0, 0, width, height);

				// Position Combo at Top Right
				int comboW = 80;
				int comboH = 40;
				comboIdiomas.setBounds(width - comboW - 20, 20, comboW, comboH);
			}
		});

	}

	public JButton getBotonCorreo() {
		return botonCorreo;
	}

	public void setBotonCorreo(JButton botonCorreo) {
		this.botonCorreo = botonCorreo;
	}

	public JButton getBotonCerrarSesion() {
		return botonCerrarSesion;
	}

	public void setBotonCerrarSesion(JButton botonCerrarSesion) {
		this.botonCerrarSesion = botonCerrarSesion;
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

	public void actualizarTextos() {
		if (comboIdiomas != null) {
			comboIdiomas.setSelectedIndex(MoTextos.getIdioma());
		}
		setTitle(MoTextos.menu_title);
		text.setText(MoTextos.app_title);
		subtext.setText(MoTextos.lbl_select_option);
		botonCRUD.setText(MoTextos.btn_manage_data);
		botonFileManager.setText(MoTextos.btn_file_manager);
		botonCerrarSesion.setText(MoTextos.btn_logout);
		botonAdmin.setText(MoTextos.btn_administrate);
		botonCorreo.setText(MoTextos.btn_mail_controller);
		repaint();
	}

	public JComboBox<ImageIcon> getComboIdiomas() {
		return comboIdiomas;
	}
}
