/**
 * Clase panel tabla
 * 
 * @author Grupo 1
 * 10/12/2025
 */

package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.MoCliente;
import modelo.MoVentana;
import java.awt.*;

public class ViPanelTabla extends JPanel {
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private MoVentana modelo;

	// Constructor
	public ViPanelTabla(MoVentana modelo) {
		this.modelo = modelo;
		setLayout(new BorderLayout()); // Mejor organización del espacio

		crearTabla();
	}

	// Crea el JTable
	public void crearTabla() {
		/*
		 * Crear el modelo de la tabla con los títulos de MoVentana, y deshabilitar la
		 * edicion de las celdas de la tabla
		 */
		modeloTabla = new DefaultTableModel(modelo.getTitulos(), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabla = new JTable(modeloTabla);
		tabla.setFillsViewportHeight(true);

		// Colocar la tabla dentro de un JScrollPane para permitir el desplazamiento
		JScrollPane scrollPane = new JScrollPane(tabla);
		add(scrollPane, BorderLayout.CENTER);
	}

	// Metodo para agregar nuevas filas con los datos de cliente
	public void agregarFilaPersonal(MoCliente cliente) {
		Object[] data = { cliente.getNombre(), cliente.getCorreo(), cliente.getButacasCompradas(),
				cliente.getSesion() };
		modeloTabla.addRow(data); // Agregar la fila al modelo
	}
}
