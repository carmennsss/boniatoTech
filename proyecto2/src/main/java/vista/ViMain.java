package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class ViMain {
	private VistaLogin vistaLogin;
	private VistaCRUD vistaCRUD;
	private ViFormulario ventanaFormulario;
	private ViCrearRol viCrearRol;
	private VistaAsignarRol viAsignarRol;

	public ViMain() {
		viCrearRol = new ViCrearRol();
		vistaLogin = new VistaLogin();
		viAsignarRol = new VistaAsignarRol();
		vistaCRUD = new VistaCRUD();
		ventanaFormulario = new ViFormulario();
	}

	public VistaAsignarRol getViAsignarRol() {
		return viAsignarRol;
	}

	public void hacerVisible() {
		mostrarLogin();
	}

	public ViCrearRol getViCrearRol() {
		return viCrearRol;
	}

	public void mostrarLogin() {
		vistaCRUD.setVisible(false);
		ventanaFormulario.setVisible(false);
		vistaLogin.setVisible(true);
	}

	public void mostrarCRUD() {
		vistaLogin.setVisible(false);
		vistaCRUD.setVisible(true);
	}

	public void setVisible(boolean b) {
		if (!b) {
			vistaLogin.setVisible(false);
			vistaCRUD.setVisible(false);
			ventanaFormulario.setVisible(false);
		} else {
			mostrarLogin();
		}
	}

	public ViBotones getPanelMenu() {
		return vistaCRUD.getPanelMenu();
	}

	public ViTabla getPanelTabla() {
		return vistaCRUD.getPanelTabla();
	}

	public ViBotones getPanelAcciones() {
		return vistaCRUD.getPanelAcciones();
	}

	public ViFormulario getVentanaFormulario() {
		return ventanaFormulario;
	}

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

	public void mostrarMensajeExito(String mensaje) {
		JOptionPane.showMessageDialog(getVentanaActual(), mensaje);
	}

	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(getVentanaActual(), mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public boolean mostrarConfirmacion(String mensaje) {
		int confirmacion = JOptionPane.showConfirmDialog(getVentanaActual(), mensaje, "Confirmar",
				JOptionPane.YES_NO_OPTION);
		return confirmacion == JOptionPane.YES_OPTION;
	}

	public int mostrarOpcionesTabla() {
		final JDialog dialog = new JDialog(
				(JFrame) SwingUtilities.getWindowAncestor(getVentanaActual()),
				"Options",
				true);

		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 245, 242));
		panel.setBorder(BorderFactory.createLineBorder(new Color(74, 88, 89), 2));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JLabel lblTitulo = new JLabel("Select Action");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblTitulo.setForeground(new Color(74, 88, 89));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblTitulo);
		panel.add(Box.createVerticalStrut(20));

		final int[] result = { -1 };

		Color colorBtn = new Color(110, 137, 115);
		Color colorCancel = new Color(200, 100, 100);

		JButton btnNew = crearBotonDialogo("Create New", colorBtn);
		JButton btnUpdate = crearBotonDialogo("Update", colorBtn);
		JButton btnDelete = crearBotonDialogo("Delete", colorCancel);
		JButton btnCancel = crearBotonDialogo("Cancel", Color.GRAY);

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
