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
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Vista del panel de administración. Proporciona una interfaz centralizada para
 * acceder a las funciones administrativas como gestión de usuarios, roles,
 * visualización de logs y mantenimiento de la whitelist.
 */
public class VistaAdmin extends JFrame {

	/** Vista del menú principal asociada para la navegación. */
	private VistaMenuPrincipal menu;

	/** Botón para acceder a la creación y gestión de nuevos usuarios. */
	private JButton botonCrearUsuario;

	/** Botón para definir y crear nuevos roles en el sistema. */
	private JButton botonCrearRoles;

	/** Botón para asignar roles existentes a los usuarios registrados. */
	private JButton botonAsignarRoles;

	/** Botón para gestionar la lista blanca (whitelist) de correos permitidos. */
	private JButton botonWhitelist;

	/** Botón para acceder a la visualización y exportación de logs del sistema. */
	private JButton botonLogs;

	/** Botón para cerrar la vista actual y regresar al menú principal. */
	private JButton botonVolver;

	/** Imagen de fondo personalizada para la ventana. */
	private Image imagenFondo;

	/** Etiqueta que muestra el título principal de la sección administrativa. */
	private JLabel titulo;

	/**
	 * Constructor que inicializa la ventana de administración. * @param menu
	 * Referencia a la vista del menú principal.
	 */
	public VistaAdmin(VistaMenuPrincipal menu) {
		this.menu = menu;
		propiedades();
	}

	/**
	 * Configura las propiedades generales, el fondo y el contenido de la ventana.
	 */
	private void propiedades() {
		configurarVentana();
		configurarFondo();
		configurarContenido();
	}

	/**
	 * Establece los parámetros básicos del JFrame (tamaño, posición y cierre).
	 */
	private void configurarVentana() {
		this.setTitle(modelo.MoTextos.admin_title);
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Configura el panel de fondo cargando una imagen escalada o un color sólido de
	 * respaldo.
	 */
	private void configurarFondo() {
		URL url = getClass().getResource("/fondo_zoo_2.png");
		if (url != null) {
			imagenFondo = new ImageIcon(url).getImage();
		}

		JPanel panelFondo = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagenFondo != null) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
				} else {
					g.setColor(new Color(40, 44, 52));
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		panelFondo.setLayout(new GridBagLayout());
		setContentPane(panelFondo);
	}

	/**
	 * Diseña y organiza los componentes visuales (títulos y botones) dentro del
	 * panel central.
	 */
	private void configurarContenido() {
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

		botonLogs = new JButton(modelo.MoTextos.logs_title);
		botonCrearUsuario = new JButton(modelo.MoTextos.btn_manage_users);
		botonCrearRoles = new JButton(modelo.MoTextos.btn_manage_roles);
		botonAsignarRoles = new JButton(modelo.MoTextos.roles_title_assign);
		botonWhitelist = new JButton(modelo.MoTextos.whitelist_title);
		botonVolver = new JButton(modelo.MoTextos.btn_main_menu);

		aniadirEstiloBoton(botonCrearUsuario, new Color(110, 137, 115));
		aniadirEstiloBoton(botonCrearRoles, new Color(110, 137, 115));
		aniadirEstiloBoton(botonAsignarRoles, new Color(110, 137, 115));
		aniadirEstiloBoton(botonLogs, new Color(110, 137, 115));
		aniadirEstiloBoton(botonWhitelist, new Color(110, 137, 115));
		aniadirEstiloBoton(botonVolver, new Color(200, 100, 100));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 0, 15, 0);
		gbc.gridx = 0;

		titulo = new JLabel(modelo.MoTextos.admin_title, SwingConstants.CENTER);
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
		panelCentral.add(botonLogs, gbc);

		gbc.gridy = 5;
		panelCentral.add(botonWhitelist, gbc);

		gbc.gridy = 6;
		gbc.insets = new Insets(30, 0, 10, 0);
		panelCentral.add(botonVolver, gbc);

		getContentPane().add(panelCentral);
	}

	/**
	 * Aplica un estilo visual estandarizado a los botones de la interfaz. * @param
	 * btn El botón al que se le aplicará el estilo.
	 * 
	 * @param color El color de fondo para el botón.
	 */
	private void aniadirEstiloBoton(JButton btn, Color color) {
		btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btn.setBackground(color);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setPreferredSize(new Dimension(280, 50));
	}

	/**
	 * Hace visible la ventana de administración en pantalla.
	 */
	public void hacerVisible() {
		this.setVisible(true);
	}

	/**
	 * Actualiza todos los textos de la interfaz según el idioma seleccionado en
	 * MoTextos.
	 */
	public void actualizarTextos() {
		this.setTitle(modelo.MoTextos.admin_title);
		titulo.setText(modelo.MoTextos.admin_title);
		botonCrearUsuario.setText(modelo.MoTextos.btn_manage_users);
		botonCrearRoles.setText(modelo.MoTextos.btn_manage_roles);
		botonAsignarRoles.setText(modelo.MoTextos.roles_title_assign);
		botonWhitelist.setText(modelo.MoTextos.whitelist_title);
		botonVolver.setText(modelo.MoTextos.btn_main_menu);
		repaint();
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el botón para acceder a la gestión de logs.
	 * 
	 * @return El objeto JButton de logs.
	 */
	public JButton getBotonLogs() {
		return botonLogs;
	}

	/**
	 * Establece el botón para acceder a la gestión de logs.
	 * 
	 * @param botonLogs El nuevo botón de logs.
	 */
	public void setBotonLogs(JButton botonLogs) {
		this.botonLogs = botonLogs;
	}

	/**
	 * Obtiene el botón para crear nuevos usuarios.
	 * 
	 * @return El objeto JButton de creación de usuarios.
	 */
	public JButton getBotonCrearUsuario() {
		return botonCrearUsuario;
	}

	/**
	 * Establece el botón para crear nuevos usuarios.
	 * 
	 * @param botonCrearUsuario El nuevo botón de gestión de usuarios.
	 */
	public void setBotonCrearUsuario(JButton botonCrearUsuario) {
		this.botonCrearUsuario = botonCrearUsuario;
	}

	/**
	 * Obtiene el botón para gestionar roles.
	 * 
	 * @return El objeto JButton de gestión de roles.
	 */
	public JButton getBotonCrearRoles() {
		return botonCrearRoles;
	}

	/**
	 * Establece el botón para gestionar roles.
	 * 
	 * @param botonCrearRoles El nuevo botón de creación de roles.
	 */
	public void setBotonCrearRoles(JButton botonCrearRoles) {
		this.botonCrearRoles = botonCrearRoles;
	}

	/**
	 * Obtiene el botón para asignar roles a usuarios.
	 * 
	 * @return El objeto JButton de asignación de roles.
	 */
	public JButton getBotonAsignarRoles() {
		return botonAsignarRoles;
	}

	/**
	 * Establece el botón para asignar roles a usuarios.
	 * 
	 * @param botonAsignarRoles El nuevo botón de asignación.
	 */
	public void setBotonAsignarRoles(JButton botonAsignarRoles) {
		this.botonAsignarRoles = botonAsignarRoles;
	}

	/**
	 * Obtiene el botón para volver al menú principal.
	 * 
	 * @return El objeto JButton de retorno.
	 */
	public JButton getBotonVolver() {
		return botonVolver;
	}

	/**
	 * Establece el botón para volver al menú principal.
	 * 
	 * @param botonVolver El nuevo botón de volver.
	 */
	public void setBotonVolver(JButton botonVolver) {
		this.botonVolver = botonVolver;
	}

	/**
	 * Obtiene el botón para acceder a la gestión de whitelist.
	 * 
	 * @return El objeto JButton de whitelist.
	 */
	public JButton getBotonWhitelist() {
		return botonWhitelist;
	}

	/**
	 * Establece el botón para acceder a la gestión de whitelist.
	 * 
	 * @param botonWhitelist El nuevo botón de whitelist.
	 */
	public void setBotonWhitelist(JButton botonWhitelist) {
		this.botonWhitelist = botonWhitelist;
	}
}