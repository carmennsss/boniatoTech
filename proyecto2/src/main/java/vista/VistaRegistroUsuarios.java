package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelo.ModeloBaseDatos;
import modelo.ModeloClienteFTP;

/**
 * Vista para el registro de nuevos usuarios en el sistema. Proporciona un
 * formulario detallado para capturar la información necesaria, permitiendo la
 * creación de cuentas con credenciales de acceso y de correo.
 */
public class VistaRegistroUsuarios extends JFrame {

	/** Modelo del cliente FTP asociado. */
	private ModeloClienteFTP client;

	/** Modelo de base de datos asociado. */
	private ModeloBaseDatos db;

	/** Referencia a la vista de administración para la navegación. */
	private VistaAdmin vistaAdmin;

	/** Etiqueta para el campo de nombre. */
	private JLabel nombre;

	/** Campo de texto para el nombre de usuario. */
	private JTextField textNombre;

	/** Etiqueta para el campo de correo electrónico. */
	private JLabel correo;

	/** Campo de texto para la dirección de correo. */
	private JTextField textCorreo;

	/** Etiqueta para el campo de clave de correo. */
	private JLabel claveCorreo;

	/** Campo de texto para la clave de aplicación del correo. */
	private JTextField textClaveCorreo;

	/** Etiqueta para the campo de contraseña. */
	private JLabel contrasena;

	/** Campo de texto oculto para la contraseña de acceso. */
	private JPasswordField textContrasena;

	/** Etiqueta para la confirmación de la contraseña. */
	private JLabel confContrasena;

	/** Campo de texto oculto para confirmar la contraseña. */
	private JPasswordField textConfContrasena;

	/** Botón para registrar el usuario. */
	private JButton aniadir;

	/** Botón para acceder a la funcionalidad de borrado. */
	private JButton eliminar;

	/** Botón para regresar a la vista administrativa. */
	private JButton volver;

	/** Imagen de fondo de la ventana. */
	private Image imagenFondo;

	/** Etiqueta del título principal en el panel. */
	private JLabel titulo;

	/** Panel central redondeado que contiene el formulario. */
	private JPanel panelCentral;

	/**
	 * Constructor que inicializa la vista de registro y vincula sus dependencias.
	 * * @param vistaAdmin Instancia de la vista administrativa padre.
	 * @param client     Instancia del modelo de cliente FTP.
	 * @param db         Instancia del modelo de base de datos.
	 */
	public VistaRegistroUsuarios(VistaAdmin vistaAdmin, ModeloClienteFTP client, ModeloBaseDatos db) {
		this.vistaAdmin = vistaAdmin;
		this.client = client;
		this.db = db;
		propiedades();
	}

	/**
	 * Orquesta la configuración visual de la ventana y sus componentes internos.
	 */
	private void propiedades() {
		configurarVentana();
		configurarFondo();
		configurarFormulario();
		configurarBotones();
	}

	/**
	 * Establece las propiedades básicas del marco de la ventana (JFrame).
	 */
	private void configurarVentana() {
		this.setTitle(modelo.MoTextos.reg_title_window);
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Configura el panel de fondo con imagen decorativa escalada.
	 */
	private void configurarFondo() {
		URL url = getClass().getResource("/fondo_abstracto_1.png");
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
					g.setColor(new Color(248, 245, 242));
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		panelFondo.setLayout(new GridBagLayout());
		setContentPane(panelFondo);
	}

	/**
	 * Diseña el panel central redondeado y distribuye los campos de entrada.
	 */
	private void configurarFormulario() {
		panelCentral = new JPanel(new GridBagLayout()) {
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

		titulo = new JLabel(modelo.MoTextos.reg_title_main, SwingConstants.CENTER);
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

		nombre = new JLabel(modelo.MoTextos.reg_lbl_name);
		nombre.setFont(fontLabel);
		nombre.setForeground(colorLabel);
		textNombre = new JTextField(20);
		textNombre.setFont(fontText);

		correo = new JLabel(modelo.MoTextos.reg_lbl_email);
		correo.setFont(fontLabel);
		correo.setForeground(colorLabel);
		textCorreo = new JTextField(20);
		textCorreo.setFont(fontText);

		claveCorreo = new JLabel(modelo.MoTextos.reg_lbl_key);
		claveCorreo.setFont(fontLabel);
		claveCorreo.setForeground(colorLabel);
		textClaveCorreo = new JTextField(20);
		textClaveCorreo.setFont(fontText);

		contrasena = new JLabel(modelo.MoTextos.reg_lbl_pass);
		contrasena.setFont(fontLabel);
		contrasena.setForeground(colorLabel);
		textContrasena = new JPasswordField(20);
		textContrasena.setFont(fontText);

		confContrasena = new JLabel(modelo.MoTextos.reg_lbl_conf_pass);
		confContrasena.setFont(fontLabel);
		confContrasena.setForeground(colorLabel);
		textConfContrasena = new JPasswordField(20);
		textConfContrasena.setFont(fontText);

		agregarCampo(panelCentral, gbc, 1, nombre, textNombre);
		agregarCampo(panelCentral, gbc, 2, correo, textCorreo);
		agregarCampo(panelCentral, gbc, 3, claveCorreo, textClaveCorreo);
		agregarCampo(panelCentral, gbc, 4, contrasena, textContrasena);
		agregarCampo(panelCentral, gbc, 5, confContrasena, textConfContrasena);

		getContentPane().add(panelCentral);
	}

	/**
	 * Configura los botones de acción principal y los posiciona en el panel
	 * central.
	 */
	private void configurarBotones() {
		aniadir = new JButton(modelo.MoTextos.reg_btn_register);
		eliminar = new JButton(modelo.MoTextos.btn_sys_delete);
		volver = new JButton(modelo.MoTextos.btn_back);

		aniadirEstiloBoton(aniadir, new Color(110, 137, 115));
		aniadirEstiloBoton(eliminar, new Color(110, 137, 115));
		aniadirEstiloBoton(volver, new Color(200, 100, 100));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		panelBotones.setOpaque(false);
		panelBotones.add(aniadir);
		panelBotones.add(eliminar);
		panelBotones.add(volver);

		if (panelCentral != null) {
			panelCentral.add(panelBotones, gbc);
		}
	}

	/**
	 * Método auxiliar para alinear y añadir una etiqueta con su respectivo campo.
	 */
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

	/**
	 * Aplica el estilo visual unificado de la aplicación a un botón.
	 */
	private void aniadirEstiloBoton(JButton btn, Color bgColor) {
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btn.setBackground(bgColor);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setPreferredSize(new Dimension(150, 40));
	}

	/**
	 * Hace visible la ventana de registro en pantalla.
	 */
	public void hacerVisible() {
		this.setVisible(true);
	}

	/**
	 * Actualiza dinámicamente los textos de las etiquetas y botones según el
	 * idioma.
	 */
	public void actualizarTextos() {
		this.setTitle(modelo.MoTextos.reg_title_window);
		titulo.setText(modelo.MoTextos.reg_title_main);
		nombre.setText(modelo.MoTextos.reg_lbl_name);
		correo.setText(modelo.MoTextos.reg_lbl_email);
		claveCorreo.setText(modelo.MoTextos.reg_lbl_key);
		contrasena.setText(modelo.MoTextos.reg_lbl_pass);
		confContrasena.setText(modelo.MoTextos.reg_lbl_conf_pass);
		aniadir.setText(modelo.MoTextos.reg_btn_register);
		eliminar.setText(modelo.MoTextos.btn_sys_delete);
		volver.setText(modelo.MoTextos.btn_back);
	}

	// --- SECCIÓN DE GETTERS Y SETTERS ---

	/**
	 * Obtiene el botón encargado de la eliminación de usuarios.
	 * * @return El objeto JButton de eliminar.
	 */
	public JButton getEliminar() {
		return eliminar;
	}

	/**
	 * Establece el botón encargado de la eliminación de usuarios.
	 * * @param eliminar La nueva instancia de JButton.
	 */
	public void setEliminar(JButton eliminar) {
		this.eliminar = eliminar;
	}

	/**
	 * Obtiene el botón encargado de procesar el registro (añadir).
	 * * @return El objeto JButton de añadir.
	 */
	public JButton getAniadir() {
		return aniadir;
	}

	/**
	 * Establece el botón encargado de procesar el registro.
	 * * @param aniadir La nueva instancia de JButton.
	 */
	public void setAniadir(JButton aniadir) {
		this.aniadir = aniadir;
	}

	/**
	 * Obtiene el botón para regresar a la vista anterior.
	 * * @return El objeto JButton de volver.
	 */
	public JButton getVolver() {
		return volver;
	}

	/**
	 * Establece el botón para regresar a la vista anterior.
	 * * @param volver La nueva instancia de JButton.
	 */
	public void setVolver(JButton volver) {
		this.volver = volver;
	}

	/**
	 * Obtiene el modelo del cliente FTP configurado.
	 * * @return El objeto ModeloClienteFTP.
	 */
	public ModeloClienteFTP getClient() {
		return client;
	}

	/**
	 * Establece el modelo del cliente FTP.
	 * * @param client El objeto ModeloClienteFTP a asignar.
	 */
	public void setClient(ModeloClienteFTP client) {
		this.client = client;
	}

	/**
	 * Obtiene el modelo de acceso a la base de datos.
	 * * @return El objeto ModeloBaseDatos.
	 */
	public ModeloBaseDatos getDb() {
		return db;
	}

	/**
	 * Establece el modelo de acceso a la base de datos.
	 * * @param db El objeto ModeloBaseDatos a asignar.
	 */
	public void setDb(ModeloBaseDatos db) {
		this.db = db;
	}

	/**
	 * Obtiene la referencia a la vista de administración principal.
	 * * @return El objeto VistaAdmin.
	 */
	public VistaAdmin getVistaAdmin() {
		return vistaAdmin;
	}

	/**
	 * Establece la referencia a la vista de administración principal.
	 * * @param vistaAdmin La instancia de VistaAdmin a asignar.
	 */
	public void setVistaAdmin(VistaAdmin vistaAdmin) {
		this.vistaAdmin = vistaAdmin;
	}

	/**
	 * Obtiene la etiqueta del campo Nombre.
	 * * @return El objeto JLabel.
	 */
	public JLabel getNombre() {
		return nombre;
	}

	/**
	 * Establece la etiqueta del campo Nombre.
	 * * @param nombre La nueva etiqueta JLabel.
	 */
	public void setNombre(JLabel nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el campo de texto donde se ingresa el nombre.
	 * * @return El objeto JTextField.
	 */
	public JTextField getTextNombre() {
		return textNombre;
	}

	/**
	 * Establece el campo de texto para el nombre.
	 * * @param textNombre El nuevo campo JTextField.
	 */
	public void setTextNombre(JTextField textNombre) {
		this.textNombre = textNombre;
	}

	/**
	 * Obtiene la etiqueta del campo Correo.
	 * * @return El objeto JLabel.
	 */
	public JLabel getCorreo() {
		return correo;
	}

	/**
	 * Establece la etiqueta del campo Correo.
	 * * @param correo La nueva etiqueta JLabel.
	 */
	public void setCorreo(JLabel correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el campo de texto donde se ingresa el correo electrónico.
	 * * @return El objeto JTextField.
	 */
	public JTextField getTextCorreo() {
		return textCorreo;
	}

	/**
	 * Establece el campo de texto para el correo electrónico.
	 * * @param textCorreo El nuevo campo JTextField.
	 */
	public void setTextCorreo(JTextField textCorreo) {
		this.textCorreo = textCorreo;
	}

	/**
	 * Obtiene la etiqueta del campo Clave de Correo.
	 * * @return El objeto JLabel.
	 */
	public JLabel getClaveCorreo() {
		return claveCorreo;
	}

	/**
	 * Establece la etiqueta del campo Clave de Correo.
	 * * @param claveCorreo La nueva etiqueta JLabel.
	 */
	public void setClaveCorreo(JLabel claveCorreo) {
		this.claveCorreo = claveCorreo;
	}

	/**
	 * Obtiene el campo de texto para la clave de aplicación del correo.
	 * * @return El objeto JTextField.
	 */
	public JTextField getTextClaveCorreo() {
		return textClaveCorreo;
	}

	/**
	 * Establece el campo de texto para la clave de aplicación del correo.
	 * * @param textClaveCorreo El nuevo campo JTextField.
	 */
	public void setTextClaveCorreo(JTextField textClaveCorreo) {
		this.textClaveCorreo = textClaveCorreo;
	}

	/**
	 * Obtiene la etiqueta del campo Contraseña.
	 * * @return El objeto JLabel.
	 */
	public JLabel getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la etiqueta del campo Contraseña.
	 * * @param contrasena La nueva etiqueta JLabel.
	 */
	public void setContrasena(JLabel contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Obtiene el campo de contraseña de acceso al sistema.
	 * * @return El objeto JPasswordField.
	 */
	public JPasswordField getTextContrasena() {
		return textContrasena;
	}

	/**
	 * Establece el campo de contraseña de acceso.
	 * * @param textContrasena El nuevo campo JPasswordField.
	 */
	public void setTextContrasena(JPasswordField textContrasena) {
		this.textContrasena = textContrasena;
	}

	/**
	 * Obtiene la etiqueta del campo Confirmar Contraseña.
	 * * @return El objeto JLabel.
	 */
	public JLabel getConfContrasena() {
		return confContrasena;
	}

	/**
	 * Establece la etiqueta del campo Confirmar Contraseña.
	 * * @param confContrasena La nueva etiqueta JLabel.
	 */
	public void setConfContrasena(JLabel confContrasena) {
		this.confContrasena = confContrasena;
	}

	/**
	 * Obtiene el campo para validar la contraseña de acceso.
	 * * @return El objeto JPasswordField.
	 */
	public JPasswordField getTextConfContrasena() {
		return textConfContrasena;
	}

	/**
	 * Establece el campo para validar la contraseña de acceso.
	 * * @param textConfContrasena El nuevo campo JPasswordField.
	 */
	public void setTextConfContrasena(JPasswordField textConfContrasena) {
		this.textConfContrasena = textConfContrasena;
	}

	/**
	 * Obtiene la imagen de fondo utilizada en la ventana.
	 * * @return El objeto Image.
	 */
	public Image getImagenFondo() {
		return imagenFondo;
	}

	/**
	 * Establece la imagen de fondo para la ventana.
	 * * @param imagenFondo La nueva imagen Image.
	 */
	public void setImagenFondo(Image imagenFondo) {
		this.imagenFondo = imagenFondo;
	}
}