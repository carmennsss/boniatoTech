package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modelo.Correo;

public class VistaGeneralCorreo extends JFrame {

	private JPanel panel;
	private String correo;
	private JButton botonEnviarCorreo;
	private DefaultTableModel tablaModelo;
	private JTable emailTabla;
	private JButton btnVolver;

	public VistaGeneralCorreo(String correo) {
		this.correo = correo;
		inicializarPanel();
		propiedadesVentana();
		inicializarVista();
		inicializarTabla();

	}

	private void inicializarTabla() {
		JPanel panelTabla = new JPanel(new BorderLayout());
		panelTabla.setOpaque(false);
		panelTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 40));

		String[] nombresColumnas = { "Subject", "From", "Date" };

		tablaModelo = new DefaultTableModel(nombresColumnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		emailTabla = new JTable(tablaModelo);
		emailTabla.setRowHeight(35);
		emailTabla.getTableHeader().setFont(Estilos.FONT_BOTON);
		emailTabla.getTableHeader().setBackground(Estilos.COLOR_TABLA_HEADER);
		emailTabla.getTableHeader().setForeground(java.awt.Color.WHITE);
		emailTabla.setFont(Estilos.FONT_TEXTO);
		emailTabla.setSelectionBackground(Estilos.COLOR_TABLA_SELECCION);
		emailTabla.setSelectionForeground(java.awt.Color.WHITE);
		emailTabla.setShowGrid(false);
		emailTabla.setIntercellSpacing(new Dimension(0, 0));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < 3; i++)
			emailTabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(emailTabla);
		scrollPane.getViewport().setBackground(java.awt.Color.WHITE);
		scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(Estilos.COLOR_TABLA_HEADER, 1));

		panelTabla.add(scrollPane, BorderLayout.CENTER);
		panel.add(panelTabla, BorderLayout.CENTER);
	}

	private void propiedadesVentana() {
		this.setTitle("Inbox");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(630, 500);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(630, 500));

	}

	private void inicializarPanel() {
		panel = new JPanel(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
	}

	private void inicializarVista() {
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 10));

		JLabel etiquetaCorreo = new JLabel(correo + " inbox");
		etiquetaCorreo.setPreferredSize(new Dimension(600, 20));
		etiquetaCorreo.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
		etiquetaCorreo.setAlignmentX(SwingConstants.CENTER);

		panelSuperior.add(etiquetaCorreo);

		panel.add(panelSuperior, BorderLayout.NORTH);

		JPanel panelMedio = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));

		botonEnviarCorreo = new JButton("Send Mail");
		botonEnviarCorreo.setPreferredSize(new Dimension(150, 30));
		btnVolver = new JButton("Back");
		btnVolver.setPreferredSize(new Dimension(150, 30));

		panelMedio.add(btnVolver);
		panelMedio.add(botonEnviarCorreo);

		panel.add(panelMedio, BorderLayout.SOUTH);
	}

	public void cargarCorreos(ArrayList<Correo> correos) {
		tablaModelo.setRowCount(0);
		// Ordenar el array por fecha
		Collections.sort(
				correos,
				Comparator.comparing(Correo::getFecha).reversed());
		for (Correo c : correos) {
			Object[] fila = new Object[3];
			fila[0] = c.getAsunto();
			fila[1] = c.getRemitente();

			fila[2] = c.getFecha();

			tablaModelo.addRow(fila);
		}
	}

	public JButton getBotonEnviarCorreo() {
		return botonEnviarCorreo;
	}

	public void setBotonEnviarCorreo(JButton botonEnviarCorreo) {
		this.botonEnviarCorreo = botonEnviarCorreo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public JTable getEmailTabla() {
		return emailTabla;
	}

	public void setEmailTabla(JTable emailTabla) {
		this.emailTabla = emailTabla;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}

}
