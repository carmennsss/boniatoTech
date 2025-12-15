package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

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

	public VistaGeneralCorreo(String correo) {
		this.correo = correo;
		inicializarPanel();
		propiedadesVentana();
		inicializarVista();
		inicializarTabla();

	}

	private void inicializarTabla() {
		JPanel panelTabla = new JPanel(new BorderLayout());

		String[] nombresColumnas = { "De", "Asunto", "Fecha" };

		tablaModelo = new DefaultTableModel(nombresColumnas, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // Esto evita la edición, pero permite la selección
	        }
	    };

		emailTabla = new JTable(tablaModelo);
		emailTabla.setRowHeight(35);
		//emailTabla.setEnabled(false);
		emailTabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		emailTabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		emailTabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		emailTabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(emailTabla);

		panelTabla.add(scrollPane, BorderLayout.CENTER);

		panel.add(panelTabla, BorderLayout.CENTER);
	}

	private void propiedadesVentana() {
		this.setTitle("Correos");
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
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));

		JLabel etiquetaCorreo = new JLabel("Bandeja de entrada de " + correo);
		etiquetaCorreo.setPreferredSize(new Dimension(400, 40));
		etiquetaCorreo.setFont(new Font("Arial", Font.ITALIC, 12));

		botonEnviarCorreo = new JButton("Enviar correo");
		botonEnviarCorreo.setPreferredSize(new Dimension(120, 30));

		panelSuperior.add(etiquetaCorreo);
		panelSuperior.add(botonEnviarCorreo);

		panel.add(panelSuperior, BorderLayout.NORTH);
	}

	public void cargarCorreos(ArrayList<Correo> correos) {
		tablaModelo.setRowCount(0);

		for (Correo c : correos) {
			Object[] fila = new Object[3];
			fila[0] = c.getRemitente();
			fila[1] = c.getAsunto();
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

}
