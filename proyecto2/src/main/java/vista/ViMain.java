package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.MoTextos;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Gestor principal de la interfaz gráfica de usuario.
 * Inicializa y controla la visibilidad de todas las vistas principales de la
 * aplicación.
 */
public class ViMain {
	private VistaLogin vistaLogin;
	private VistaCRUD vistaCRUD;
	private ViFormulario ventanaFormulario;
	private ViCrearRol viCrearRol;
	private VistaAsignarRol viAsignarRol;
	private VistaWhitelist viWhitelist;

	public ViMain() {
		propiedades();
	}

	private void propiedades() {
		viCrearRol = new ViCrearRol();
		vistaLogin = new VistaLogin();
		viAsignarRol = new VistaAsignarRol();
		viWhitelist = new VistaWhitelist();
		vistaCRUD = new VistaCRUD();
		ventanaFormulario = new ViFormulario();
	}

	/**
	 * Obtiene la vista de gestión CRUD.
	 * 
	 * @return Vista CRUD.
	 */
	public VistaCRUD getVistaCRUD() {
		return vistaCRUD;
	}

	/**
	 * Obtiene la vista de asignación de roles.
	 * 
	 * @return Vista de asignar rol.
	 */
	public VistaAsignarRol getViAsignarRol() {
		return viAsignarRol;
	}

	/**
	 * Obtiene la vista de gestión de whitelist.
	 * 
	 * @return Vista de whitelist.
	 */
	public VistaWhitelist getViWhitelist() {
		return viWhitelist;
	}

	/**
	 * Muestra la vista de inicio de sesión.
	 */
	public void hacerVisible() {
		mostrarLogin();
	}

	/**
	 * Obtiene la vista de creación de roles.
	 * 
	 * @return Vista de crear rol.
	 */
	public ViCrearRol getViCrearRol() {
		return viCrearRol;
	}

	/**
	 * Oculta otras vistas y activa la pantalla de login.
	 */
	public void mostrarLogin() {
		vistaCRUD.setVisible(false);
		ventanaFormulario.setVisible(false);
		vistaLogin.setVisible(true);
	}

	/**
	 * Oculta el login y muestra la vista principal de gestión (CRUD).
	 */
	public void mostrarCRUD() {
		vistaLogin.setVisible(false);
		vistaCRUD.setVisible(true);
	}

	/**
	 * Controla la visibilidad de todas las ventanas.
	 * 
	 * @param b true para mostrar login, false para ocultar todo.
	 */
	public void setVisible(boolean b) {
		if (!b) {
			vistaLogin.setVisible(false);
			vistaCRUD.setVisible(false);
			ventanaFormulario.setVisible(false);
		} else {
			mostrarLogin();
		}
	}

	/**
	 * Obtiene el panel de botones del menú CRUD.
	 * 
	 * @return Panel de botones del menú.
	 */
	public ViBotones getPanelMenu() {
		return vistaCRUD.getPanelMenu();
	}

	/**
	 * Obtiene el panel de tabla del CRUD.
	 * 
	 * @return Panel de tabla.
	 */
	public ViTabla getPanelTabla() {
		return vistaCRUD.getPanelTabla();
	}

	/**
	 * Obtiene el panel de acciones del CRUD.
	 * 
	 * @return Panel de acciones.
	 */
	public ViBotones getPanelAcciones() {
		return vistaCRUD.getPanelAcciones();
	}

	/**
	 * Obtiene la ventana de formulario.
	 * 
	 * @return Ventana de formulario.
	 */
	public ViFormulario getVentanaFormulario() {
		return ventanaFormulario;
	}

	/**
	 * Obtiene la vista de formulario.
	 * 
	 * @return Vista de formulario.
	 */
	public ViFormulario getViFormulario() {
		return ventanaFormulario;
	}

	/**
	 * Obtiene el panel de login.
	 * 
	 * @return Panel de login.
	 */
	public VistaLogin getPanelLogin() {
		return vistaLogin;
	}

	private Component getVentanaActual() {
		if (vistaLogin.isVisible())
			return vistaLogin;
		if (vistaCRUD.isVisible())
			return vistaCRUD;
		if (ventanaFormulario.isVisible())
			return ventanaFormulario;
		return null;
	}

	/**
	 * Muestra un mensaje de éxito al usuario.
	 * 
	 * @param mensaje Texto del mensaje.
	 */
	public void mostrarMensajeExito(String mensaje) {
		JOptionPane.showMessageDialog(getVentanaActual(), mensaje);
	}

	/**
	 * Muestra un mensaje de error al usuario.
	 * 
	 * @param mensaje Texto del mensaje de error.
	 */
	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(getVentanaActual(), mensaje, MoTextos.msg_error_title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Muestra un cuadro de diálogo de confirmación con Sí/No.
	 *
	 * @param mensaje Texto de la pregunta.
	 * @return true si el usuario selecciona "Sí", false si selecciona "No".
	 */
	public boolean mostrarConfirmacion(String mensaje) {
		int confirmacion = JOptionPane.showConfirmDialog(getVentanaActual(), mensaje, MoTextos.msg_confirm_title,
				JOptionPane.YES_NO_OPTION);
		return confirmacion == JOptionPane.YES_OPTION;
	}

	/**
	 * Muestra un diálogo personalizado para seleccionar una acción sobre una fila.
	 * Ofrece opciones para Crear Nuevo, Actualizar, Borrar o Cancelar.
	 *
	 * @return 0 para Nuevo, 1 para Actualizar, 2 para Borrar, 3 para Cancelar.
	 */
	public int mostrarOpcionesTabla() {
		final JDialog dialog = new JDialog(
				(JFrame) SwingUtilities.getWindowAncestor(getVentanaActual()),
				MoTextos.dialog_select_action,
				true);

		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 245, 242));
		panel.setBorder(BorderFactory.createLineBorder(new Color(74, 88, 89), 2));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JLabel lblTitulo = new JLabel(MoTextos.dialog_select_action);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitulo.setForeground(new Color(74, 88, 89));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblTitulo);
		panel.add(Box.createVerticalStrut(20));

		final int[] result = { -1 };

		Color colorBtn = new Color(110, 137, 115);
		Color colorCancel = new Color(200, 100, 100);

		JButton btnNew = crearBotonDialogo(MoTextos.btn_create_new, colorBtn);
		JButton btnUpdate = crearBotonDialogo(MoTextos.btn_sys_update, colorBtn);
		JButton btnDelete = crearBotonDialogo(MoTextos.btn_sys_delete, colorCancel);
		JButton btnCancel = crearBotonDialogo(MoTextos.btn_sys_cancel, Color.GRAY);

		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result[0] = 0;
				dialog.dispose();
			}
		});

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result[0] = 1;
				dialog.dispose();
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result[0] = 2;
				dialog.dispose();
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result[0] = 3;
				dialog.dispose();
			}
		});

		panel.add(btnNew);
		panel.add(Box.createVerticalStrut(10));
		panel.add(btnUpdate);
		panel.add(Box.createVerticalStrut(10));
		panel.add(btnDelete);
		panel.add(Box.createVerticalStrut(20));
		panel.add(btnCancel);

		dialog.add(panel);
		dialog.pack();
		dialog.setLocationRelativeTo(getVentanaActual());
		dialog.setVisible(true);

		return result[0];
	}

	private JButton crearBotonDialogo(String texto, Color color) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btn.setBackground(color);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setMaximumSize(new Dimension(200, 40));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
}
