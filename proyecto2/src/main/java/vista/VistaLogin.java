package vista;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

public class VistaLogin extends JFrame {

	private ArrayList<JLabel> textos;
	private ArrayList<JTextField> cajas;
	private ArrayList<JButton> botones;
	private Image imagenFondo;
	private URL logoUrl;

	public VistaLogin() {
		super("Login");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		textos = new ArrayList<>();
		cajas = new ArrayList<>();
		botones = new ArrayList<>();

		// URL url = getClass().getResource("/fondo_login.jpg");
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

		Color colorFondoPanel = new Color(255, 255, 255, 245);
		Color colorBoton = new Color(74, 88, 89);
		Font fontTitulo = new Font("Segoe UI", Font.BOLD, 28);
		Font fontLabel = new Font("Segoe UI", Font.PLAIN, 14);

		JPanel panelCentral = new JPanel(new GridBagLayout()) {
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

		JLabel titulo = new JLabel("Zoo Manager", SwingConstants.CENTER);
		titulo.setFont(fontTitulo);
		titulo.setForeground(Estilos.COLOR_TITULO_APP);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelCentral.add(titulo, gbc);

		gbc.gridwidth = 1;

		JLabel lblUser = new JLabel("User:");
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

		JLabel lblPass = new JLabel("Password:");
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

		JButton btnLogin = new JButton("Enter");
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
		layeredPane.add(lblLogo, JLayeredPane.PALETTE_LAYER);

		layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				int width = layeredPane.getWidth();
				int height = layeredPane.getHeight();

				Dimension sizePanel = panelCentral.getPreferredSize();
				int xPanel = (width - sizePanel.width) / 2;
				int yPanel = (height - sizePanel.height) / 2;
				panelCentral.setBounds(xPanel, yPanel, sizePanel.width, sizePanel.height);

				Dimension sizeLogo = lblLogo.getSize();
				int xLogo = xPanel + sizePanel.width - sizeLogo.width + 40;
				int overlap = 160;
				int yLogo = yPanel - sizeLogo.height + overlap;

				lblLogo.setBounds(xLogo, yLogo, sizeLogo.width, sizeLogo.height);
			}
		});
	}

	public ArrayList<JLabel> getTextos() {
		return textos;
	}

	public ArrayList<JTextField> getCajas() {
		return cajas;
	}

	public ArrayList<JButton> getBotones() {
		return botones;
	}
}
