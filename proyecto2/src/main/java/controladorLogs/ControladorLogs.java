package controladorLogs;

import java.sql.Connection;
import java.util.ArrayList;

import modelo.Log;
import modelo.User;
import vista.VistaLogs;

public class ControladorLogs {
	
	private Connection conn;
	private VistaLogs vistaLogs;
	private GestionLogs gestionLogs;

	public ControladorLogs(Connection conn, VistaLogs vistaLogs) {
		this.conn = conn;
		this.vistaLogs=vistaLogs;
		cargarLogs();
		vistaLogs.setVisible(true);
		asignarOyenteBtnExport();
	}

	private void cargarLogs() {
		gestionLogs = new GestionLogs(conn);

		ArrayList<Log> logs = new ArrayList<>();
		
		logs = gestionLogs.consultLogs();
		
		vistaLogs.cargarLogs(logs);
		
	}

	private void asignarOyenteBtnExport() {
		vistaLogs.getBtnExport().addActionListener(new OyenteBtnExport(gestionLogs, vistaLogs));
	}
}
