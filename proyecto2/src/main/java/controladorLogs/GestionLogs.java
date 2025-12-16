package controladorLogs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.Log;
import modelo.User;
import vista.VistaLogs;

public class GestionLogs {

	private static Connection conn;

	public GestionLogs(Connection conn) {
		super();
		this.conn = conn;
	}

	public void writeLog(User user, String action, boolean exito) {
		String fecha = LocalDateTime.now().toString();
		String resultado = exito ? "EXITO" : "DENEGADO";

		Log log = new Log(action, user.getCorreo(), fecha, resultado);

		// Conectar y registrar log en db

	}

	public static ArrayList<Log> consultLogs() {
		ArrayList<Log> logs = new ArrayList<>();

		String sql = "SELECT id_logs, accion, fecha, resultado, email_usuario FROM logs";

		try (Connection conn = java.sql.DriverManager.getConnection(URL, USUARIO, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id_logs");
				String accion = rs.getString("accion");
				String fecha = rs.getString("fecha");
				String resultado = rs.getString("resultado");
				String email = rs.getString("email_usuario");

				Log log = new Log(id, accion, fecha, resultado, email);
				logs.add(log);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return logs;
	}

	public void exportLogs() {
		ArrayList<Log> logs = new ArrayList<>();
		File file = new File("logs.csv");

		logs = consultLogs();

		try (FileWriter fw = new FileWriter(file, true)) {
			for (Log log : logs) {
				fw.write(log.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void mostrarLogs() {
		ArrayList<Log> logs = new ArrayList<>();

	}

}