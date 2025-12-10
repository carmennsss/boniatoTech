package servidor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.Log;
import modelo.User;

public class GestionFTP {

	

	public void writeLog(User user, String action, boolean exito) {
		String fecha = LocalDateTime.now().toString();
		String resultado = exito ? "EXITO" : "DENEGADO";

		Log log = new Log(action, user.getCorreo(), fecha, resultado);

		// Conectar y registrar log en db

	}

	public ArrayList<Log> consultLogs() {
		ArrayList<Log> logs = new ArrayList<>();
		// Consultar en db los logs

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

}
