package vista;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

public class VistaLogin extends JPanel {

	private ArrayList<JLabel> textos;
	private ArrayList<JTextField> cajas;
	private ArrayList<JButton> botones;
	private JPanel panelCentral;

	public VistaLogin() {
		this.textos = new ArrayList<>();
		this.cajas = new ArrayList<>();
		this.botones = new ArrayList<>();

		configurarLogin();
	}

	private void configurarLogin() {
		panelCentral = new JPanel(new GridBagLayout());
		panelCentral.setBorder(new EmptyBorder(40, 60, 40, 60));
		panelCentral.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel titulo = new JLabel("Zoo Manager", SwingConstants.CENTER);
		titulo.setFont(Estilos.FONT_TITULO.deriveFont(28f));
		titulo.setForeground(Estilos.COLOR_TITULO_APP);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelCentral.add(titulo, gbc);

		gbc.gridwidth = 1;

		JLabel lblUser = new JLabel("Usuario:");
		lblUser.setFont(Estilos.FONT_TEXTO);
		lblUser.setForeground(Estilos.COLOR_LABEL);
		textos.add(lblUser);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelCentral.add(lblUser, gbc);

		JTextField txtUser = new JTextField(20);
		txtUser.setFont(Estilos.FONT_TEXTO);
		cajas.add(txtUser);

		gbc.gridx = 1;
		gbc.gridy = 1;
		panelCentral.add(txtUser, gbc);

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setFont(Estilos.FONT_TEXTO);
		lblPass.setForeground(Estilos.COLOR_LABEL);
		textos.add(lblPass);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelCentral.add(lblPass, gbc);

		JPasswordField txtPass = new JPasswordField(20);
		txtPass.setFont(Estilos.FONT_TEXTO);
		cajas.add(txtPass);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panelCentral.add(txtPass, gbc);

		JButton btnLogin = new JButton("Entrar");
		btnLogin.setFont(Estilos.FONT_BOTON);
		btnLogin.setBackground(Estilos.DARK_SPRUCE);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFocusPainted(false);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botones.add(btnLogin);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panelCentral.add(btnLogin, gbc);

		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		this.add(panelCentral);
	}

	private Image imagenFondo;

	{
		URL url = getClass().getResource("/fondo_login.jpg");
		if (url != null) {
			imagenFondo = new ImageIcon(url).getImage();
		}
	}

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
