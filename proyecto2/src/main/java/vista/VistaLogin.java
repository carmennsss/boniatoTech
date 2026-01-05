package vista;

import java.awt.image.BufferedImage;
import java.awt.*;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

import modelo.MoTextos;

/**
 * Vista de inicio de sesi�n de la aplicaci�n.
 * Proporciona una interfaz gr�fica con soporte multiidioma para que el u
 * uario
 * se autentique mediante su nombre de usuario y contrase�a.
 */
public class VistaLogin extends JFrame {

	/** Lista de etiquetas de texto de la interfaz para gesti�n de idiomas. */
	private ArrayList<JLabel> textos;

	/** Lista de campos de entrada (usuario y contrase�a). */
	private ArrayList<JTextField> cajas;

	/** Lista de botones de la interfaz. */
	private ArrayList<JButton> botones;

	/** Imagen de fondo de la ventana (Panda background). */
	private Image imagenFondo;

	/** URL del recurso del logo de la aplicaci�n. */
	private URL logoUrl;

	/** Etiqueta que muestra el t�tulo de la aplicaci�n en el panel central. */
	private JLabel titulo;

	/** Etiqueta para el campo de identificaci�n de usuario. */
	private JLabel lblUser;

	/** Etiqueta para el campo de clave de acceso. */
	private JLabel lblPass;

	/** Bot�n que dispara la acci�n de validaci�n de credenciales. */
	private JButton btnLogin;

	/** Selector de idioma con representaci�n visual mediante banderas. */
	private JComboBox<ImageIcon> comboIdiomas;

	/** Panel central que contiene el formulario de acceso. */
	private JPanel panelCentral;

	/**
	 * Constructor de la vista. Inicializa la ventana y sus componentes.
	 */
	public VistaLogin() {
		super(MoTextos.login_title);
		propiedades();
	}

	/**
	 * Orquesta la configuraci�n de listas, ventana y paneles.
	 */
	private void propiedades() {
		inicializarListas();
		configurarVentana();
		configurarPaneles();
	}

	/**
	 * Crea las instancias de las listas para el almacenamiento de componentes.
	 */
	private void inicializarListas() {
		textos = new ArrayList<>();
		cajas = new ArrayList<>();
		botones = new ArrayList<>();
	}

	/**
	 * Establece los par�metros b�sicos del JFrame.
	 */
	private void configurarVentana() {
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	/**
	 * Configura el panel de contenido con imagen de fondo reactiva y capas.
	 */
	private void configurarPaneles() {

		URL url = getClass().getResource("/panditas.png");
		if (url != null) {
			imagenFondo = new ImageIcon(url).getImage();
		}
		logoUrl = getClass().getResource("/panda_logo.png");

		JPanel contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagenFondo != null) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					int imgW = imagenFondo.getWidth(this);
					int imgH = imagenFondo.getHeight(this);
					if (imgW > 0 && imgH > 0) {
						int panelW = getWidth();
						int panelH = getHeight();
						double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);
						int newW = (int) (imgW * scale);
						int newH = (int) (imgH * scale);
						int x = (panelW - newW) / 2;
						int y = (panelH - newH) / 2;
						g.drawImage(imagenFondo, x, y, newW, newH, this);
					}
				}
			}
		};
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);

		JLabel lblLogo = new JLabel();
		if (logoUrl != null) {
			ImageIcon icon = new ImageIcon(logoUrl);
			float ratio = (float) icon.getIconWidth() / icon.getIconHeight();
			int logoW = 280;
			int logoH = (int) (logoW / ratio);
			Image img = icon.getImage().getScaledInstance(logoW, logoH, Image.SCALE_SMOOTH);
			lblLogo.setIcon(new ImageIcon(img));
			lblLogo.setSize(logoW, logoH);
		}

		configurarPanelCentral(layeredPane);
		configurarIdioma(layeredPane);

		layeredPane.add(lblLogo, JLayeredPane.PALETTE_LAYER);

		layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				int width = layeredPane.getWidth();
				int height = layeredPane.getHeight();

				if (panelCentral != null) {
					Dimension sizePanel = panelCentral.getPreferredSize();
					int xPanel = (width - sizePanel.width) / 2;
					int yPanel = (height - sizePanel.height) / 2;
					panelCentral.setBounds(xPanel, yPanel, sizePanel.width, sizePanel.height);
				}

				Dimension sizeLogo = lblLogo.getSize();
				if (panelCentral != null) {
					Dimension sizePanel = panelCentral.getPreferredSize();
					int xPanel = (width - sizePanel.width) / 2;
					int yPanel = (height - sizePanel.height) / 2;

					int xLogo = xPanel + sizePanel.width - sizeLogo.width + 40;
					int overlap = 160;
					int yLogo = yPanel - sizeLogo.height + overlap;
					lblLogo.setBounds(xLogo, yLogo, sizeLogo.width, sizeLogo.height);
				}

				if (comboIdiomas != null) {
					int comboW = 80;
					int comboH = 40;
					comboIdiomas.setBounds(width - comboW - 20, 20, comboW, comboH);
				}
			}
		});
	}

	/**
	 * Configura el dise�o y los componentes internos del formulario de acceso.
	 * 
	 * @param layeredPane Capa donde se a�adir� el panel.
	 */
	private void configurarPanelCentral(JLayeredPane layeredPane) {
		Color colorFondoPanel = new Color(255, 255, 255, 245);
		Color colorBoton = new Color(74, 88, 89);
		Font fontTitulo = new Font("Segoe UI", Font.BOLD, 28);
		Font fontLabel = new Font("Segoe UI", Font.PLAIN, 14);

		panelCentral = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(getBackground());
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
				g2.dispose();
			}
		};
		panelCentral.setOpaque(false);
		panelCentral.setBorder(new EmptyBorder(40, 50, 40, 50));
		panelCentral.setBackground(colorFondoPanel);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		titulo = new JLabel(MoTextos.app_title, SwingConstants.CENTER);
		titulo.setFont(fontTitulo);
		titulo.setForeground(Estilos.COLOR_TITULO_APP);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelCentral.add(titulo, gbc);

		gbc.gridwidth = 1;

		lblUser = new JLabel(MoTextos.lbl_user);
		lblUser.setFont(fontLabel);
		lblUser.setForeground(Estilos.COLOR_LABEL);
		textos.add(lblUser);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelCentral.add(lblUser, gbc);

		JTextField txtUser = new JTextField(20);
		txtUser.setFont(fontLabel);
		cajas.add(txtUser);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelCentral.add(txtUser, gbc);

		lblPass = new JLabel(MoTextos.lbl_password);
		lblPass.setFont(fontLabel);
		lblPass.setForeground(Estilos.COLOR_LABEL);
		textos.add(lblPass);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelCentral.add(lblPass, gbc);

		JPasswordField txtPass = new JPasswordField(20);
		txtPass.setFont(fontLabel);
		cajas.add(txtPass);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelCentral.add(txtPass, gbc);

		btnLogin = new JButton(MoTextos.btn_login);
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnLogin.setBackground(colorBoton);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFocusPainted(false);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botones.add(btnLogin);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panelCentral.add(btnLogin, gbc);

		layeredPane.add(panelCentral, JLayeredPane.DEFAULT_LAYER);
	}

	/**
	 * Configura el selector de idioma en la parte superior derecha de la ventana.
	 * 
	 * @param layeredPane Capa donde se a�adir� el componente.
	 */
	private void configurarIdioma(JLayeredPane layeredPane) {

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
		comboIdiomas.setRenderer(new RenderComboIdioma());
		comboIdiomas.setBackground(Color.WHITE);
		comboIdiomas.setFocusable(false);

		if (iconEng != null)
			comboIdiomas.addItem(iconEng);
		if (iconEsp != null)
			comboIdiomas.addItem(iconEsp);

		if (MoTextos.getIdioma() == 0 && iconEng != null) {
			comboIdiomas.setSelectedItem(iconEng);
		} else if (MoTextos.getIdioma() == 1 && iconEsp != null) {
			comboIdiomas.setSelectedItem(iconEsp);
		}

		layeredPane.add(comboIdiomas, JLayeredPane.PALETTE_LAYER);
	}
	
	/**
	 * Actualiza los textos de la interfaz en base al idioma seleccionado.
	 */
	public void actualizarTextos() {
		if (comboIdiomas != null) {
			comboIdiomas.setSelectedIndex(MoTextos.getIdioma());
		}
		setTitle(MoTextos.login_title);
		titulo.setText(MoTextos.app_title);
		lblUser.setText(MoTextos.lbl_user);
		lblPass.setText(MoTextos.lbl_password);
		btnLogin.setText(MoTextos.btn_login);
		repaint();
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el selector de idioma configurado en la ventana.
	 * 
	 * @return Selector de idioma.
	 */
	public JComboBox<ImageIcon> getComboIdiomas() {
		return comboIdiomas;
	}

	/**
	 * Obtiene la lista de etiquetas de texto de la interfaz.
	 * 
	 * @return Lista de etiquetas de texto.
	 */
	public ArrayList<JLabel> getTextos() {
		return textos;
	}

	/**
	 * Obtiene la lista de campos de texto de la interfaz.
	 * 
	 * @return Lista de campos de texto.
	 */
	public ArrayList<JTextField> getCajas() {
		return cajas;
	}

	/**
	 * Obtiene la lista de botones de la interfaz.
	 * 
	 * @return Lista de botones.
	 */
	public ArrayList<JButton> getBotones() {
		return botones;
	}
}
