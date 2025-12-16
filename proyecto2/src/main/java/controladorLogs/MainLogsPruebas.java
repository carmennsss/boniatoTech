package controladorLogs;

import modelo.ModeloBaseDatos;

public class MainLogsPruebas {
	public static void main(String[] args) {
		ModeloBaseDatos modeloDb = new ModeloBaseDatos();
		new ControladorLogs(modeloDb.getConexion());

	}
}
