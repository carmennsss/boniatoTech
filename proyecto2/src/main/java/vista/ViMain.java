package vista;

import java.awt.Component;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViMain {
	private VistaLogin vistaLogin;
	private VistaCRUD vistaCRUD;
	private ViFormulario ventanaFormulario;

	public ViMain() {
		vistaLogin = new VistaLogin();
		vistaCRUD = new VistaCRUD();
		ventanaFormulario = new ViFormulario();
	}

	public void hacerVisible() {
		mostrarLogin();
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
		final javax.swing.JDialog dialog = new javax.swing.JDialog(
				(javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(getVentanaActual()),
				"Options",
				true);

		dialog.setUndecorated(true);
		dialog.setLayout(new java.awt.BorderLayout());

		javax.swing.JPanel panel = new javax.swing.JPanel();
		panel.setBackground(new java.awt.Color(248, 245, 242));
		panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(74, 88, 89), 2));
		panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
		panel.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

		javax.swing.JLabel lblTitulo = new javax.swing.JLabel("Select Action");
		lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
		lblTitulo.setForeground(new java.awt.Color(74, 88, 89));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblTitulo);
		panel.add(javax.swing.Box.createVerticalStrut(20));

		final int[] result = { -1 };

		java.awt.Color colorBtn = new java.awt.Color(110, 137, 115);
		java.awt.Color colorCancel = new java.awt.Color(200, 100, 100);

		javax.swing.JButton btnNew = crearBotonDialogo("Create New", colorBtn);
		javax.swing.JButton btnUpdate = crearBotonDialogo("Update", colorBtn);
		javax.swing.JButton btnDelete = crearBotonDialogo("Delete", colorCancel);
		javax.swing.JButton btnCancel = crearBotonDialogo("Cancel", java.awt.Color.GRAY);

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
		panel.add(javax.swing.Box.createVerticalStrut(10));
		panel.add(btnUpdate);
		panel.add(javax.swing.Box.createVerticalStrut(10));
		panel.add(btnDelete);
		panel.add(javax.swing.Box.createVerticalStrut(20));
		panel.add(btnCancel);

		dialog.add(panel);
		dialog.pack();
		dialog.setLocationRelativeTo(getVentanaActual());
		dialog.setVisible(true);

		return result[0];
	}

	private javax.swing.JButton crearBotonDialogo(String texto, java.awt.Color color) {
		javax.swing.JButton btn = new javax.swing.JButton(texto);
		btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		btn.setBackground(color);
		btn.setForeground(java.awt.Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setMaximumSize(new java.awt.Dimension(200, 40));
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		return btn;
	}
}
