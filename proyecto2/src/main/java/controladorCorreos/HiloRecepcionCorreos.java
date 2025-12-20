package controladorCorreos;

import java.util.ArrayList;
import javax.swing.SwingUtilities;
import controladorLogs.GestionLogs;
import modelo.Correo;
import modelo.Log;
import vista.VistaGeneralCorreo;

/**
 * Hilo de ejecución en segundo plano para la recepción automática de
 * correos. Comprueba periódicamente si hay nuevos correos y actualiza la
 * vista.
 */
public class HiloRecepcionCorreos implements Runnable {

	/** Gestor de operaciones POP3. */
	private GestionCorreos gestionPop3;

	/** Host del servidor POP3. */
	private String host;

	/** Host del servidor IMAP. */
	private String hostImap;

	/** Correo electrónico del usuario. */
	private String correo;

	/** Contraseña de aplicación de Gmail. */
	private String PASSWORD_APLICACION;

	/** Vista general de correos. */
	private VistaGeneralCorreo vistaGeneral;

	/** Último número de correos recibidos. */
	private int ultimoNumeroCorreos = -1;

	/** Controlador de correos. */
	private ControladorCorreos controlador;

	/** Gestor de logs de correo. */
	private GestionLogs logCorreo;

	/**
	 * Constructor del hilo de recepción de correos.
	 *
	 * @param gestionPop3         Gestor de correos POP3.
	 * @param host                Host del servidor de correo.
	 * @param correo              Dirección de correo del usuario.
	 * @param PASSWORD_APLICACION Contraseña de aplicación.
	 * @param vistaGeneral        Vista general de correos.
	 * @param controlador         Controlador de correos.
	 */
	public HiloRecepcionCorreos(GestionCorreos gestionPop3, String host, String correo, String PASSWORD_APLICACION,
			VistaGeneralCorreo vistaGeneral, ControladorCorreos controlador) {

		this.gestionPop3 = gestionPop3;
		this.host = host;
		this.hostImap = "imap.gmail.com";
		this.PASSWORD_APLICACION = PASSWORD_APLICACION;
		this.correo = correo;
		this.vistaGeneral = vistaGeneral;
		this.controlador = controlador;
	}

	/**
	 * Ejecuta el bucle de recepción de correos en segundo plano. Comprueba nuevos
	 * correos cada 25 segundos y actualiza la vista si hay cambios.
	 */
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted() && vistaGeneral.isVisible()) {

				Thread.sleep(25_000);

				if (!Thread.currentThread().isInterrupted()) {

					ArrayList<Correo> nuevos = gestionPop3.recibirCorreosPOP3(host, hostImap, correo,
							PASSWORD_APLICACION);

					if (nuevos == null) {
						continue;
					}

					int cantidadActual = nuevos.size();

					if (ultimoNumeroCorreos == -1) {
						ultimoNumeroCorreos = cantidadActual;

						SwingUtilities.invokeLater(() -> {
							controlador.actualizarListaDesdeHilo(nuevos);
						});

						continue;
					}

					if (cantidadActual > ultimoNumeroCorreos) {
						GestionLogs.writeLog(new Log("MAIL_RECEIVED", correo.replaceFirst("^recent:", ""), true));
					}

					if (cantidadActual != ultimoNumeroCorreos) {
						ultimoNumeroCorreos = cantidadActual;

						SwingUtilities.invokeLater(() -> {
							controlador.actualizarListaDesdeHilo(nuevos);
						});
					}
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el gestor de correos POP3.
	 * @return El objeto GestionCorreos.
	 */
	public GestionCorreos getGestionPop3() {
		return gestionPop3;
	}

	/**
	 * Obtiene el correo electrónico configurado.
	 * @return El correo del usuario.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Obtiene el último número de correos detectado.
	 * @return Cantidad de correos en la última revisión.
	 */
	public int getUltimoNumeroCorreos() {
		return ultimoNumeroCorreos;
	}

	/**
	 * Establece manualmente el último número de correos detectado.
	 * @param ultimoNumeroCorreos La nueva cantidad.
	 */
	public void setUltimoNumeroCorreos(int ultimoNumeroCorreos) {
		this.ultimoNumeroCorreos = ultimoNumeroCorreos;
	}
}