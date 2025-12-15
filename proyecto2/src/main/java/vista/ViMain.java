package vista;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class ViMain extends JFrame {
	private CardLayout cardLayout;
	private JPanel panelPrincipal;

	private JPanel panelCRUD;
	private ViBotones panelMenu;
	private ViTabla panelTabla;
	private ViBotones panelAcciones;
	private ViFormulario ventanaFormulario;

	private VistaLogin panelLogin;

	public ViMain() {
		super("Gestión Serwo - Animales");
		setSize(900, 600);
		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		cardLayout = new CardLayout();
		panelPrincipal = new PanelFondo();
		panelPrincipal.setLayout(cardLayout);

		panelLogin = new VistaLogin();

		panelCRUD = new JPanel(new BorderLayout());
		panelCRUD.setOpaque(false);

		ArrayList<String> textosMenu = new ArrayList<>(Arrays.asList("ESPECIES", "RECINTOS", "CUIDADORES", "ANIMALES"));
		panelMenu = new ViBotones(textosMenu);

		panelTabla = new ViTabla();

		ArrayList<String> textosAcciones = new ArrayList<>(Arrays.asList("Nuevo", "Cerrar Sesión"));
		panelAcciones = new ViBotones(textosAcciones);

		panelCRUD.add(panelMenu, BorderLayout.NORTH);
		panelCRUD.add(panelTabla, BorderLayout.CENTER);
		panelCRUD.add(panelAcciones, BorderLayout.SOUTH);

		ventanaFormulario = new ViFormulario();

		panelPrincipal.add(panelLogin, "LOGIN");
		panelPrincipal.add(panelCRUD, "CRUD");

		add(panelPrincipal);
	}

	public void hacerVisible() {
		setVisible(true);
	}

	public void mostrarLogin() {
		cardLayout.show(panelPrincipal, "LOGIN");
	}

	public void mostrarCRUD() {
		cardLayout.show(panelPrincipal, "CRUD");
	}

	public ViBotones getPanelMenu() {
		return panelMenu;
	}

	public ViTabla getPanelTabla() {
		return panelTabla;
	}

	public ViBotones getPanelAcciones() {
		return panelAcciones;
	}

	public ViFormulario getVentanaFormulario() {
		return ventanaFormulario;
	}

	public VistaLogin getPanelLogin() {
		return panelLogin;
	}

	public void mostrarMensajeExito(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}

	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public boolean mostrarConfirmacion(String mensaje) {
		int confirmacion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar", JOptionPane.YES_NO_OPTION);
		return confirmacion == JOptionPane.YES_OPTION;
	}

	public int mostrarOpcionesTabla() {
		String[] opciones = { "Nuevo", "Actualizar", "Eliminar", "Cancelar" };
		return JOptionPane.showOptionDialog(this, "Seleccione una acción", "Opciones", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
	}
}
