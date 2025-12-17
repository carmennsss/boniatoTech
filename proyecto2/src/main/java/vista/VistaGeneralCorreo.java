package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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

		String[] nombresColumnas = { "Subject", "From", "Date" };

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
		//Ordenar el array por fecha
		Collections.sort(
			    correos,
			    Comparator.comparing(Correo::getFecha).reversed()
			);
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
