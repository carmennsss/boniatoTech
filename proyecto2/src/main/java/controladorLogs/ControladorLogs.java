package controladorLogs;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;

import modelo.Log;
import modelo.MoTextos;

import vista.VistaLogs;

/**
 * Controlador de la vista de Logs.
 * Gestiona la visualización, filtros y exportación de logs del sistema.
 */
public class ControladorLogs {

	/** Conexión a la base de datos. */
	private Connection conn;

	/** Vista de logs. */
	private VistaLogs vistaLogs;

	/** Gestor de logs para operaciones de base de datos. */
	private GestionLogs gestionLogs;

	/**
	 * Constructor del controlador de logs.
	 *
	 * @param conn      Conexión a la base de datos.
	 * @param vistaLogs Vista de logs.
	 */
	public ControladorLogs(Connection conn, VistaLogs vistaLogs) {
		this.vistaLogs = vistaLogs;
		this.gestionLogs = new GestionLogs();
		asignarOyenteBtnExport();
		asignarOyenteConsultaLogs();
	}

	/**
	 * Carga y filtra los logs en la vista según el tipo de consulta.
	 * Actualiza el título de la vista según el filtro aplicado.
	 *
	 * @param consulta Tipo de filtro ("actions", "users", "dates", "results", o
	 *                 "all").
	 */
	public void cargarLogs(String consulta) {
		ArrayList<Log> logs = new ArrayList<>();

		if (consulta.equals("actions")) {
			vistaLogs.setTituloTexto(MoTextos.logs_title + " - Acciones");
		} else if (consulta.equals("users")) {
			vistaLogs.setTituloTexto(MoTextos.logs_title + " - Usuarios");
		} else if (consulta.equals("dates")) {
			vistaLogs.setTituloTexto(MoTextos.logs_title + " - Fechas");
		} else if (consulta.equals("results")) {
			vistaLogs.setTituloTexto(MoTextos.logs_title + " - Resultados");
		} else {
			vistaLogs.setTituloTexto(MoTextos.logs_title);
		}

		logs = GestionLogs.consultLogs(consulta);

		vistaLogs.cargarLogs(logs);

	}

	/**
	 * Muestra la ventana de logs y carga todos los registros por defecto.
	 */
	public void mostrar() {
		cargarLogs("all");
		vistaLogs.hacerVisible();
	}

	/**
	 * Asigna los oyentes a los botones de consulta de logs.
	 */
	private void asignarOyenteConsultaLogs() {
		for (JButton boton : vistaLogs.getBotonesConsultas()) {
			boton.addActionListener(new OyenteConsultaLogs(vistaLogs, this));
		}
	}

	/**
	 * Asigna los oyentes a los botones de exportación y volver.
	 */
	private void asignarOyenteBtnExport() {
		vistaLogs.getBtnExport().addActionListener(new OyenteBtnExport(gestionLogs, vistaLogs));

		vistaLogs.getBtnVolver().addActionListener(new OyenteBotonVolverLogs(vistaLogs));
	}
}
