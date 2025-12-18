package controladorLogs;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;

import modelo.Log;
import modelo.MoTextos;
import modelo.User;
import vista.VistaLogs;

public class ControladorLogs {

	private Connection conn;
	private VistaLogs vistaLogs;

	private GestionLogs gestionLogs;

	public ControladorLogs(Connection conn, VistaLogs vistaLogs) {
		this.conn = conn;
		this.vistaLogs = vistaLogs;
		this.gestionLogs = new GestionLogs();
		asignarOyenteBtnExport();
		asignarOyenteConsultaLogs();
	}

	public void mostrar() {
		cargarLogs("all");
		vistaLogs.hacerVisible();
	}

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

	private void asignarOyenteBtnExport() {
		vistaLogs.getBtnExport().addActionListener(new OyenteBtnExport(gestionLogs, vistaLogs));

		vistaLogs.getBtnVolver().addActionListener(new OyenteBotonVolverLogs(vistaLogs));
	}

	private void asignarOyenteConsultaLogs() {
		for (JButton boton : vistaLogs.getBotonesConsultas()) {
			boton.addActionListener(new OyenteConsultaLogs(vistaLogs, this));
		}
	}
}
