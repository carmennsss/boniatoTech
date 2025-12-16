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
		this.conn = conn;
	}

	public static void writeLog(Log log) {

		// Conectar y registrar log en db

		String sql = "INSERT INTO logs (accion, fecha, resultado, email_usuario) VALUES (?,CURRENT_TIMESTAMP,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, log.getAction());
			ps.setString(2, log.getResult());
			ps.setString(3, log.getUser().getCorreo());
			ps.executeUpdate();

			System.out.println("Log registrado correctamente.");

		} catch (SQLException e) {
			System.err.println("Error al insertar log: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static ArrayList<Log> consultLogs() {
		ArrayList<Log> logs = new ArrayList<>();

		String sql = "SELECT id_logs, accion, fecha, resultado, email_usuario FROM logs";

		try (
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();)
		{
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

	public boolean exportLogs(File file) {
        ArrayList<Log> logs = consultLogs();
        
        
        // Usamos try-with-resources para cerrar el FileWriter automáticamente
        try (FileWriter fw = new FileWriter(file)) {
            
        	if (logs.isEmpty()) {
            	fw.write("There isn´t logs registered in the database.");
            }
            fw.write("Date,User,Action,Result\n");
            
           
            
            for (Log log : logs) {
                fw.write(log.toString());
            }
            return true; // Éxito
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error
        }
    }

	public void mostrarLogs() {
		ArrayList<Log> logs = new ArrayList<>();

	}

}