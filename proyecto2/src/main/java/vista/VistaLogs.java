package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import modelo.MoTextos;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modelo.Correo;
import modelo.Log;

public class VistaLogs extends JFrame {

	VistaAdmin vistaAdmin;
	private JPanel panel;
	private JTable tabla;
	private DefaultTableModel tablaModelo;
	private JButton btnExport;

	public VistaLogs(VistaAdmin vistaAdmin) {
		this.vistaAdmin=vistaAdmin;
		mostrarVentana();
		propiedadesVentana();
		mostrarTabla();
		inicializarTabla();
	}

	private void mostrarVentana() {

		panel = new JPanel(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);

	}

	public void propiedadesVentana() {
		this.setTitle(MoTextos.logs_title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(630, 500);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(630, 500));

	}

	public void mostrarTabla() {

	}

	private void inicializarTabla() {
		JPanel panelTabla = new JPanel(new BorderLayout());
		// Consultar y exportar registros de operaciones como subidas/descargas, cambios
		// en archivos y accesos no autorizados.
		String[] nombresColumnas = { MoTextos.logs_col_action, MoTextos.logs_col_user, MoTextos.logs_col_date,
				MoTextos.logs_col_result };

		tablaModelo = new DefaultTableModel(nombresColumnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Esto evita la edici�n, pero permite la selecci�n
			}
		};

		tabla = new JTable(tablaModelo);
		tabla.setRowHeight(35);
		// tabla.setEnabled(false);
		tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(tabla);

		panelTabla.add(scrollPane, BorderLayout.CENTER);
		//
		panel.add(panelTabla, BorderLayout.NORTH);
		btnExport = new JButton(MoTextos.logs_btn_export);
		btnExport.setPreferredSize(new Dimension(100, 8));
		panel.add(btnExport, BorderLayout.EAST);

	}

	public File seleccionarArchivoGuardar() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(MoTextos.logs_dialog_save_title);
		fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

		int userSelection = fileChooser.showSaveDialog(this);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			// Asegurar extensión .csv
			if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
				fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
			}
			return fileToSave;
		}
		return null;
	}

	public void cargarLogs(ArrayList<Log> logs) {
		tablaModelo.setRowCount(0);
		// Ordenar el array por fecha

		for (Log log : logs) {
			Object[] fila = new Object[3];
			fila[0] = log.getAction();
			fila[1] = log.getUser().toString();

			fila[2] = log.getDate();
			fila[3] = log.getResult();

			tablaModelo.addRow(fila);
		}
	}
	
	public void hacerVisible() {
		this.setVisible(true);
	}
	

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}

	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, MoTextos.msg_error_title, JOptionPane.ERROR_MESSAGE);
	}

	public JButton getBtnExport() {
		return btnExport;
	}

	public void setBtnExport(JButton btnExport) {
		this.btnExport = btnExport;
	}

}
