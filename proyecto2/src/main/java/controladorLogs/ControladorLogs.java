package controladorLogs;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;

import modelo.Log;
import modelo.User;
import vista.VistaLogs;

public class ControladorLogs {

	private Connection conn;
	private VistaLogs vistaLogs;
	private GestionLogs gestionLogs;

	public ControladorLogs(Connection conn, VistaLogs vistaLogs) {
		this.conn = conn;
		this.vistaLogs = vistaLogs;
		asignarOyenteBtnExport();
		asignarOyenteConsultaLogs();
	}

	public void mostrar() {
		cargarLogs("all");
		vistaLogs.hacerVisible();
	}

	public void cargarLogs(String consulta) {
		if (gestionLogs == null) {
			gestionLogs = new GestionLogs(conn);
		}

		ArrayList<Log> logs = new ArrayList<>();

		logs = gestionLogs.consultLogs(consulta);

		vistaLogs.cargarLogs(logs);

	}

	private void asignarOyenteBtnExport() {
		vistaLogs.getBtnExport().addActionListener(new OyenteBtnExport(gestionLogs, vistaLogs));

		vistaLogs.getBtnVolver().addActionListener(new OyenteBotonVolverLogs());
	}

	private void asignarOyenteConsultaLogs() {
		for (JButton boton : vistaLogs.getBotonesConsultas()) {
			boton.addActionListener(new OyenteConsultaLogs(vistaLogs, this));
		}
	}
}
