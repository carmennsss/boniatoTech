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
import modelo.ModeloBaseDatos;
import modelo.User;
import vista.VistaLogs;

/**
 * Clase para realizar operaciones de base de datos relacionadas con los Logs.
 * Permite registrar acciones, consultar logs con filtros y exportar a CSV.
 */
public class GestionLogs {
	private static Connection conexion;

	public GestionLogs() {
		this.conexion = ModeloBaseDatos.getConexion();
	}

	/**
	 * Registra un nuevo log en la base de datos.
	 *
	 * @param log Objeto Log con la información de la acción, usuario y resultado.
	 */
	public static void writeLog(Log log) {
		if (conexion == null) {
			conexion = ModeloBaseDatos.getConexion();
		}

		String sql = "INSERT INTO logs (accion, fecha, resultado, email_usuario) VALUES (?,CURRENT_TIMESTAMP,?,?)";

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {
			ps.setString(1, log.getAction());
			ps.setString(2, log.getResult());

			if (log.getCorreo() == null || log.getCorreo().isEmpty()) {
				ps.setNull(3, java.sql.Types.VARCHAR);
			} else {
				ps.setString(3, log.getCorreo());
			}
			ps.executeUpdate();

			System.out.println("Log registrado correctamente.");

		} catch (SQLException e) {
			System.err.println("Error al insertar log: " + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Consulta los logs de la base de datos aplicando un criterio de ordenación.
	 *
	 * @param consulta Criterio de ordenación ("actions", "users", "dates",
	 *                 "results").
	 * @return Lista de logs recuperados.
	 */
	public static ArrayList<Log> consultLogs(String consulta) {
		if (conexion == null) {
			conexion = ModeloBaseDatos.getConexion();
			return null;
		}
		ArrayList<Log> logs = new ArrayList<>();

		String sql = "SELECT id_logs, accion, fecha, resultado, email_usuario FROM logs";
		if (consulta.equals("actions")) {
			sql += " ORDER BY accion ASC";
		} else if (consulta.equals("users")) {
			sql += " ORDER BY email_usuario ASC";
		} else if (consulta.equals("dates")) {
			sql += " ORDER BY fecha ASC";
		} else if (consulta.equals("results")) {
			sql += " ORDER BY resultado ASC";
		}

		try (
				PreparedStatement ps = conexion.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				int id = rs.getInt("id_logs");
				String accion = rs.getString("accion");
				String fecha = rs.getString("fecha");
				String resultado = rs.getString("resultado");
				String email = rs.getString("email_usuario");

				Log log = new Log(id, accion, email, fecha, resultado);
				logs.add(log);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return logs;
	}

	/**
	 * Exporta todos los logs a un archivo CSV.
	 *
	 * @param file El archivo destino.
	 * @return true si la exportación fue exitosa, false en caso contrario.
	 */
	public boolean exportLogs(File file) {
		if (conexion == null) {
			conexion = ModeloBaseDatos.getConexion();
			return false;
		}

		ArrayList<Log> logs = consultLogs("all");

		try (FileWriter fw = new FileWriter(file)) {

			if (logs.isEmpty()) {
				fw.write("There aren't logs registered in the database.\n");
				return true;
			}

			// Cabecera CSV
			fw.write("Date,User,Action,Result\n");

			for (Log log : logs) {
				fw.write(log.toString());
				fw.write("\n");
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void mostrarLogs() {
		ArrayList<Log> logs = new ArrayList<>();

	}

}