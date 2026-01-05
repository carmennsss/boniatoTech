package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.Rol;
import modelo.MoTextos;

/**
 * Vista encargada de la interfaz para la asignación y desasignación de roles a
 * los usuarios. Permite visualizar los usuarios registrados en una tabla y
 * aplicar cambios de permisos mediante un selector de roles y botones de
 * acción.
 */
public class VistaAsignarRol extends JFrame {
	/** Lista de etiquetas de texto para la gestión de internacionalización. */
	private ArrayList<JLabel> textos;

	/** Lista de botones de la interfaz (Asignar, Quitar, Volver). */
	private ArrayList<JButton> botones;

	/**
	 * Panel personalizado que contiene la tabla de usuarios y sus roles actuales.
	 */
	private ViTabla tabla;

	/** Selector desplegable para elegir el rol que se desea procesar. */
	private JComboBox<Rol> comboRoles = new JComboBox<>();

	/** Imagen de fondo para la personalización visual de la ventana. */
	private Image imagenFondo;

	/** Etiqueta descriptiva para el componente selector de rol. */
	private JLabel lblRol;

	/** Etiqueta que muestra el título principal de la ventana. */
	private JLabel titulo;

	/**
	 * Constructor que inicializa la vista y configura todos los componentes
	 * gráficos.
	 */
	public VistaAsignarRol() {
		propiedades();
	}

	/**
	 * Configura las propiedades generales de la ventana y orquesta la creación de
	 * componentes.
	 */
	private void propiedades() {
		configurarVentana();
		configurarFondo();
		configurarTitulo();
		configurarTabla();
		configurarBotones();
	}

	/**
	 * Establece los parámetros básicos del JFrame (tamaño, cierre, posición).
	 */
	private void configurarVentana() {
		this.textos = new ArrayList<>();
		this.botones = new ArrayList<>();

		this.setTitle(MoTextos.roles_title_assign);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 650);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
	}

	/**
	 * Carga la imagen de fondo y configura el panel principal con renderizado de
	 * alta calidad.
	 */
	private void configurarFondo() {
		URL url = getClass().getResource("/fondo_abstracto_2.png");
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
					g.setColor(Estilos.FONDO_PRINCIPAL);
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		panelFondo.setLayout(new BorderLayout(20, 20));
		panelFondo.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setContentPane(panelFondo);
	}

	/**
	 * Configura la parte superior de la ventana con el título estilizado.
	 */
	private void configurarTitulo() {
		titulo = new JLabel(MoTextos.roles_title_assign);
		titulo.setFont(Estilos.FONT_TITULO);
		titulo.setForeground(Estilos.DARK_SPRUCE);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		this.textos.add(titulo);

		JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(255, 255, 255, 200));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
				g2.dispose();
				super.paintComponent(g);
			}
		};
		panelTitulo.setOpaque(false);
		panelTitulo.setBorder(new EmptyBorder(10, 20, 10, 20));
		panelTitulo.add(titulo);
		getContentPane().add(panelTitulo, BorderLayout.NORTH);
	}

	/**
	 * Configura la sección central donde se ubica la tabla de datos de usuarios.
	 */
	private void configurarTabla() {
		this.tabla = new ViTabla();

		JPanel panelTablaContenedor = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(255, 255, 255, 180));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
				g2.dispose();
			}
		};
		panelTablaContenedor.setOpaque(false);
		panelTablaContenedor.setBorder(new EmptyBorder(20, 20, 20, 20));

		this.tabla.setOpaque(false);
		panelTablaContenedor.add(this.tabla, BorderLayout.CENTER);
		getContentPane().add(panelTablaContenedor, BorderLayout.CENTER);
	}

	/**
	 * Configura la barra inferior de herramientas con el combo de roles y botones
	 * de acción.
	 */
	private void configurarBotones() {
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelSur.setOpaque(false);

		this.comboRoles.setFont(Estilos.FONT_TEXTO);
		this.comboRoles.setPreferredSize(new Dimension(200, 35));

		JButton btnAsignar = new JButton(MoTextos.roles_btn_assign);
		JButton btnDesasignar = new JButton(MoTextos.btn_unassign);
		JButton btnVolver = new JButton(MoTextos.btn_back_whitelist);

		estilarBoton(btnAsignar, Estilos.COLOR_BOTON_MENU);
		estilarBoton(btnDesasignar, Estilos.BLUE_SLATE);
		estilarBoton(btnVolver, new Color(200, 100, 100));

		this.botones.add(btnAsignar);
		this.botones.add(btnDesasignar);
		this.botones.add(btnVolver);

		lblRol = new JLabel(MoTextos.roles_lbl_role);
		lblRol.setFont(Estilos.FONT_BOTON);
		lblRol.setForeground(Estilos.COLOR_LABEL);

		panelSur.add(lblRol);
		panelSur.add(this.comboRoles);
		panelSur.add(btnAsignar);
		panelSur.add(btnDesasignar);
		panelSur.add(btnVolver);

		getContentPane().add(panelSur, BorderLayout.SOUTH);
	}

	/**
	 * Aplica el diseño unificado a los botones de la interfaz.
	 * 
	 * @param btn     El botón a estilar.
	 * @param bgColor El color de fondo del botón.
	 */
	private void estilarBoton(JButton btn, Color bgColor) {
		btn.setFont(Estilos.FONT_BOTON);
		btn.setBackground(bgColor);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setPreferredSize(new Dimension(150, 40));
	}

	/**
	 * Actualiza los textos de la interfaz según el idioma seleccionado en la
	 * aplicación.
	 */
	public void actualizarTextos() {
		this.setTitle(MoTextos.roles_title_assign);
		titulo.setText(MoTextos.roles_title_assign);
		botones.get(0).setText(MoTextos.roles_btn_assign);
		botones.get(1).setText(MoTextos.btn_unassign);
		botones.get(2).setText(MoTextos.btn_back_whitelist);
		lblRol.setText(MoTextos.roles_lbl_role);
		repaint();
	}

	// --- GETTERS Y SETTERS AL FINAL ---

	/**
	 * Obtiene el componente de tabla personalizado.
	 * 
	 * @return El objeto ViTabla.
	 */
	public ViTabla getTabla() {
		return this.tabla;
	}

	/**
	 * Establece un nuevo componente de tabla.
	 * 
	 * @param tabla El objeto ViTabla a asignar.
	 */
	public void setTabla(ViTabla tabla) {
		this.tabla = tabla;
	}

	/**
	 * Obtiene la lista de botones de acción.
	 * 
	 * @return ArrayList de botones.
	 */
	public ArrayList<JButton> getBotones() {
		return botones;
	}

	/**
	 * Obtiene el selector desplegable de roles.
	 * 
	 * @return El objeto JComboBox con objetos de tipo Rol.
	 */
	public JComboBox<Rol> getComboRoles() {
		return this.comboRoles;
	}

	/**
	 * Muestra la ventana y asegura que sus componentes internos sean visibles.
	 */
	public void hacerVisible() {
		this.setVisible(true);
	}
}