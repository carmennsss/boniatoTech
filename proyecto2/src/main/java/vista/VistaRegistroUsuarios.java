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
 * Vista para el registro de nuevos usuarios en el sistema.
 * Contiene un formulario con campos para nombre, correo, contraseñas, etc.
 */
public class VistaRegistroUsuarios extends JFrame {

	/** Modelo del cliente FTP. */
	ModeloClienteFTP client;

	/** Modelo de base de datos. */
	ModeloBaseDatos db;

	/** Vista de administración asociada. */
	VistaAdmin vistaAdmin;

	/** Etiqueta del campo nombre. */
	JLabel nombre;

	/** Campo de texto para el nombre. */
	JTextField textNombre;

	/** Etiqueta del campo correo. */
	JLabel correo;

	/** Campo de texto para el correo. */
	JTextField textCorreo;

	/** Etiqueta del campo clave de correo. */
	JLabel claveCorreo;

	/** Campo de texto para la clave de correo. */
	JTextField textClaveCorreo;

	/** Etiqueta del campo contraseña. */
	JLabel contrasena;

	/** Campo de texto para la contraseña. */
	JPasswordField textContrasena;

	/** Etiqueta del campo confirmación de contraseña. */
	JLabel confContrasena;

	/** Campo de texto para confirmar la contraseña. */
	JPasswordField textConfContrasena;

	/** Botón para añadir un nuevo usuario. */
	JButton aniadir;

	/** Botón para eliminar usuarios. */
	JButton eliminar;

	/** Botón para volver a la vista anterior. */
	JButton volver;

	/** Imagen de fondo de la ventana. */
	private Image imagenFondo;

	/** Etiqueta del título de la ventana. */
	private JLabel titulo;

	/** Panel central que contiene el formulario. */
	private JPanel panelCentral;

	public VistaRegistroUsuarios(VistaAdmin vistaAdmin, ModeloClienteFTP client, ModeloBaseDatos db) {
		this.vistaAdmin = vistaAdmin;
		this.client = client;
		this.db = db;
		propiedades();
	}

	private void propiedades() {
		configurarVentana();
		configurarFondo();
		configurarFormulario();
		configurarBotones();
	}

	private void configurarVentana() {
		this.setTitle(modelo.MoTextos.reg_title_window);
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

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
	}

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

	public JButton getEliminar() {
		return eliminar;
	}

	public void setEliminar(JButton eliminar) {
		this.eliminar = eliminar;
	}

	public JButton getAniadir() {
		return aniadir;
	}

	public void setAniadir(JButton aniadir) {
		this.aniadir = aniadir;
	}

	public JButton getVolver() {
		return volver;
	}

	public void setVolver(JButton volver) {
		this.volver = volver;
	}

	public ModeloClienteFTP getClient() {
		return client;
	}

	public void setClient(ModeloClienteFTP client) {
		this.client = client;
	}

	public ModeloBaseDatos getDb() {
		return db;
	}

	public void setDb(ModeloBaseDatos db) {
		this.db = db;
	}

	public VistaAdmin getVistaAdmin() {
		return vistaAdmin;
	}

	public void setVistaAdmin(VistaAdmin vistaAdmin) {
		this.vistaAdmin = vistaAdmin;
	}

	public JLabel getNombre() {
		return nombre;
	}

	public void setNombre(JLabel nombre) {
		this.nombre = nombre;
	}

	public JTextField getTextNombre() {
		return textNombre;
	}

	public void setTextNombre(JTextField textNombre) {
		this.textNombre = textNombre;
	}

	public JLabel getCorreo() {
		return correo;
	}

	public void setCorreo(JLabel correo) {
		this.correo = correo;
	}

	public JTextField getTextCorreo() {
		return textCorreo;
	}

	public void setTextCorreo(JTextField textCorreo) {
		this.textCorreo = textCorreo;
	}

	public JLabel getClaveCorreo() {
		return claveCorreo;
	}

	public void setClaveCorreo(JLabel claveCorreo) {
		this.claveCorreo = claveCorreo;
	}

	public JTextField getTextClaveCorreo() {
		return textClaveCorreo;
	}

	public void setTextClaveCorreo(JTextField textClaveCorreo) {
		this.textClaveCorreo = textClaveCorreo;
	}

	public JLabel getContrasena() {
		return contrasena;
	}

	public void setContrasena(JLabel contrasena) {
		this.contrasena = contrasena;
	}

	public JPasswordField getTextContrasena() {
		return textContrasena;
	}

	public void setTextContrasena(JPasswordField textContrasena) {
		this.textContrasena = textContrasena;
	}

	public JLabel getConfContrasena() {
		return confContrasena;
	}

	public void setConfContrasena(JLabel confContrasena) {
		this.confContrasena = confContrasena;
	}

	public JPasswordField getTextConfContrasena() {
		return textConfContrasena;
	}

	public void setTextConfContrasena(JPasswordField textConfContrasena) {
		this.textConfContrasena = textConfContrasena;
	}

	public Image getImagenFondo() {
		return imagenFondo;
	}

	public void setImagenFondo(Image imagenFondo) {
		this.imagenFondo = imagenFondo;
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

	private void aniadirEstiloBoton(JButton btn, Color bgColor) {
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

	/**
	 * Actualiza los textos de la interfaz según el idioma seleccionado.
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

}
