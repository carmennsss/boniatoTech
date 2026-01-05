package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.MoTextos;

/**
 * Vista encargada de la gestión de la Whitelist (lista blanca) de correos
 * permitidos. Esta interfaz permite a los administradores del sistema añadir
 * nuevas direcciones autorizadas o eliminar las existentes a través de una
 * tabla dinámica.
 */
public class VistaWhitelist extends JFrame {
	/** Lista que almacena las instancias de los botones de acción de la vista. */
	private ArrayList<JButton> botones;

	/** Panel personalizado que contiene la tabla de datos y su modelo. */
	private ViTabla tabla;

	/** Campo de texto utilizado en el diálogo modal para capturar el email. */
	private JTextField txtEmail;

	/**
	 * Campo de texto utilizado en el diálogo modal para capturar el nombre del
	 * usuario.
	 */
	private JTextField txtNombre;

	/** Imagen de fondo para la personalización estética de la ventana. */
	private Image imagenFondo;

	/** Botón para disparar el flujo de adición de un nuevo registro. */
	private JButton btnAnadir;

	/** Botón para eliminar un registro seleccionado de la lista de confianza. */
	private JButton btnDesasignar;

	/** Botón para regresar a la vista de administración principal. */
	private JButton btnVolver;

	/** Etiqueta que muestra el título principal de la sección. */
	private JLabel titulo;

	/**
	 * Constructor de la clase. Inicializa todos los componentes y aplica las
	 * configuraciones visuales base.
	 */
	public VistaWhitelist() {
		propiedades();
	}

	/**
	 * Orquesta la configuración de la ventana invocando los métodos de diseño y
	 * disposición de componentes.
	 */
	private void propiedades() {
		configurarVentana();
		configurarPanelFondo();
		configurarTitulo();
		configurarTabla();
		configurarBotones();
	}

	/**
	 * Establece los parámetros básicos del JFrame como tamaño, posición inicial y
	 * comportamiento de cierre.
	 */
	private void configurarVentana() {
		this.botones = new ArrayList<>();
		this.tabla = new ViTabla();

		this.setTitle(MoTextos.whitelist_title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 650);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
	}

	/**
	 * Carga el recurso de imagen y configura el panel principal con un renderizado
	 * de alta calidad para el fondo.
	 */
	private void configurarPanelFondo() {
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
	 * Crea y posiciona el título de la ventana dentro de un panel con bordes
	 * redondeados.
	 */
	private void configurarTitulo() {
		titulo = new JLabel(MoTextos.whitelist_title);
		titulo.setFont(Estilos.FONT_TITULO);
		titulo.setForeground(Estilos.DARK_SPRUCE);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);

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
	 * Inicializa y coloca el contenedor de la tabla en la zona central de la vista.
	 */
	private void configurarTabla() {
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
		panelTablaContenedor.add(this.tabla, BorderLayout.CENTER);
		getContentPane().add(panelTablaContenedor, BorderLayout.CENTER);
	}

	/**
	 * Instancia los botones de acción y los organiza en la parte inferior (Sur) de
	 * la ventana.
	 */
	private void configurarBotones() {
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelSur.setOpaque(false);

		btnAnadir = new JButton(MoTextos.btn_add);
		btnDesasignar = new JButton(MoTextos.btn_unassign);
		btnVolver = new JButton(MoTextos.btn_back_whitelist);

		estilarBoton(btnAnadir, Estilos.COLOR_BOTON_MENU);
		estilarBoton(btnDesasignar, Estilos.BLUE_SLATE);
		estilarBoton(btnVolver, new Color(200, 100, 100));

		this.botones.add(btnAnadir);
		this.botones.add(btnDesasignar);
		this.botones.add(btnVolver);

		panelSur.add(btnAnadir);
		panelSur.add(btnDesasignar);
		panelSur.add(btnVolver);

		getContentPane().add(panelSur, BorderLayout.SOUTH);
	}

	/**
	 * Aplica el esquema de colores, fuentes y cursores unificado a un botón.
	 * 
	 * @param btn     El botón a procesar.
	 * @param bgColor El color de fondo para el botón.
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
	 * Aplica estilos visuales (fuente, borde, fondo) a los campos de entrada de
	 * datos.
	 * 
	 * @param input El componente de entrada a estilar.
	 */
	private void estilarInput(JComponent input) {
		input.setFont(Estilos.FONT_TEXTO);
		input.setBackground(Estilos.COLOR_INPUT_BG);
		input.setForeground(Estilos.COLOR_INPUT_TEXT);
		input.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Estilos.BLUE_SLATE, 1),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
	}

	/**
	 * Crea un botón configurado específicamente para ser usado en ventanas
	 * emergentes.
	 * 
	 * @param texto Texto que mostrará el botón.
	 * @param color Color de fondo del botón.
	 * @return El botón configurado.
	 */
	private JButton crearBotonDialogo(String texto, Color color) {
		JButton btn = new JButton(texto);
		btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		btn.setBackground(color);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setMaximumSize(new Dimension(200, 40));
		return btn;
	}

	/**
	 * Despliega un diálogo modal para solicitar los datos del nuevo correo a
	 * añadir.
	 * 
	 * @return 0 si el usuario confirma la acción ("Añadir"), 1 si la cancela.
	 */
	public int mostrarAgregarUsuario() {
		final JDialog dialog = new JDialog(this, MoTextos.whitelist_dialog_title, true);
		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(Estilos.BEIGE_CANVAS);
		panel.setBorder(BorderFactory.createLineBorder(Estilos.DARK_SPRUCE, 2));
		panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JLabel lblTitulo = new JLabel(MoTextos.whitelist_lbl_title);
		lblTitulo.setFont(Estilos.FONT_TITULO);
		lblTitulo.setForeground(Estilos.COLOR_TITULO_APP);
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblTitulo);
		panel.add(javax.swing.Box.createVerticalStrut(20));

		txtEmail = new JTextField();
		estilarInput(txtEmail);
		panel.add(new JLabel(MoTextos.whitelist_lbl_email));
		panel.add(txtEmail);
		panel.add(javax.swing.Box.createVerticalStrut(20));

		txtNombre = new JTextField();
		estilarInput(txtNombre);
		panel.add(new JLabel(MoTextos.whitelist_lbl_name));
		panel.add(txtNombre);
		panel.add(javax.swing.Box.createVerticalStrut(20));

		final int[] result = { -1 };
		JButton btnNew = crearBotonDialogo(MoTextos.btn_dialog_add, Estilos.COLOR_BOTON_MENU);
		JButton btnCancel = crearBotonDialogo(MoTextos.btn_dialog_cancel, new Color(200, 100, 100));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setOpaque(false);
		buttonPanel.add(btnNew);
		buttonPanel.add(btnCancel);
		panel.add(buttonPanel);

		btnNew.addActionListener(e -> {
			result[0] = 0;
			dialog.dispose();
		});
		btnCancel.addActionListener(e -> {
			result[0] = 1;
			dialog.dispose();
		});

		dialog.add(panel);
		dialog.pack();
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);

		return result[0];
	}

	/**
	 * Actualiza dinámicamente los textos de la interfaz y las cabeceras de la tabla
	 * según el idioma configurado en MoTextos.
	 */
	public void actualizarTextos() {
		this.setTitle(MoTextos.whitelist_title);
		titulo.setText(MoTextos.whitelist_title);
		btnAnadir.setText(MoTextos.btn_add);
		btnDesasignar.setText(MoTextos.btn_unassign);
		btnVolver.setText(MoTextos.btn_back_whitelist);

		javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tabla.getTabla().getModel();
		String[] header = { MoTextos.whitelist_col_email, MoTextos.whitelist_col_name, MoTextos.whitelist_col_date };
		model.setColumnIdentifiers(header);

		repaint();
	}

	/**
	 * Hace visible la ventana principal.
	 */
	public void hacerVisible() {
		this.setVisible(true);
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el panel que contiene la tabla de datos.
	 * 
	 * @return El componente ViTabla de la vista.
	 */
	public ViTabla getTabla() {
		return this.tabla;
	}

	/**
	 * Establece un nuevo panel de tabla para la vista.
	 * 
	 * @param tabla El objeto ViTabla a asignar.
	 */
	public void setTabla(ViTabla tabla) {
		this.tabla = tabla;
	}

	/**
	 * Obtiene la lista completa de botones de acción.
	 * 
	 * @return Un ArrayList con los objetos JButton.
	 */
	public ArrayList<JButton> getBotones() {
		return botones;
	}

	/**
	 * Obtiene el campo de entrada para el correo electrónico.
	 * 
	 * @return El objeto JTextField de email.
	 */
	public JTextField getTxtEmail() {
		return txtEmail;
	}

	/**
	 * Obtiene el campo de entrada para el nombre de usuario.
	 * 
	 * @return El objeto JTextField de nombre.
	 */
	public JTextField getTxtNombre() {
		return txtNombre;
	}

	/**
	 * Obtiene el botón encargado de la acción de añadir.
	 * 
	 * @return La instancia de JButton correspondiente.
	 */
	public JButton getBtnAnadir() {
		return btnAnadir;
	}

	/**
	 * Obtiene el botón encargado de la acción de desasignar/quitar.
	 * 
	 * @return La instancia de JButton correspondiente.
	 */
	public JButton getBtnDesasignar() {
		return btnDesasignar;
	}

	/**
	 * Obtiene el botón encargado de la navegación de retorno.
	 * 
	 * @return La instancia de JButton correspondiente.
	 */
	public JButton getBtnVolver() {
		return btnVolver;
	}
}