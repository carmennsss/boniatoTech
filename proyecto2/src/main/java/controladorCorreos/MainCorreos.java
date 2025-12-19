package controladorCorreos;

import java.util.ArrayList;
import java.util.Date;

import modelo.Correo;
import vista.VistaGeneralCorreo;

/**
 * Clase principal para la ejecución aislada del módulo de correos (pruebas
 * manuales).
 */
public class MainCorreos {

	private static String CORREO = "pablo.pruebas.mail@gmail.com";
	private static final String PASSWORD_APLICACION = "bqas bwag dasl kcjj";

	/**
	 * Método principal para pruebas. INicia el controlador de correos con
	 * credenciales de prueba.
	 *
	 * @param args Argumentos de consola.
	 */
	public static void main(String[] args) {
		new ControladorCorreos(CORREO, null, null, null);
	}

}
