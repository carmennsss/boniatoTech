package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class VistaLogs extends JFrame {

	private JPanel panel;
	private JTable tabla;
	private DefaultTableModel tablaModelo;

	public VistaLogs() {

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
		this.setTitle("Logs");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(630, 500);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(630, 500));

	}

	public void mostrarTabla() {

	}

	private void inicializarTabla() {
		JPanel panelTabla = new JPanel(new BorderLayout());
//Consultar y exportar registros de operaciones como subidas/descargas, cambios en archivos y accesos no autorizados.
		String[] nombresColumnas = { "Action", "User", "Date", "Result" };

		tablaModelo = new DefaultTableModel(nombresColumnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Esto evita la edición, pero permite la selección
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

		panel.add(panelTabla, BorderLayout.NORTH);
		JButton botExport = new JButton("Export");
		panel.add(botExport, BorderLayout.EAST);

	}

	public void cargarLogs() {
	}

}
