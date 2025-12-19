package controladorCorreos;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladorLogs.ControladorLogs;
import controladorLogs.GestionLogs;
import modelo.Correo;
import vista.VistaGeneralCorreo;

/**
 * Hilo de ejecución en segundo plano para la recepción automática de correos.
 * Comprueba periódicamente si hay nuevos correos y actualiza la vista.
 */
public class HiloRecepcionCorreos implements Runnable {

	private GestionCorreos gestionPop3;
	private String host, hostImap, correo, PASSWORD_APLICACION;
	private VistaGeneralCorreo vistaGeneral;
	private int ultimoNumeroCorreos = -1;
	private ControladorCorreos controlador;
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
		this.PASSWORD_APLICACION = PASSWORD_APLICACION;
		this.correo = correo;
		this.vistaGeneral = vistaGeneral;
		this.controlador = controlador;
	}

	/**
	 * Ejecuta el bucle de recepción de correos en segundo plano.
	 * Comprueba nuevos correos cada 25 segundos y actualiza la vista si hay
	 * cambios.
	 */
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted() && vistaGeneral.isVisible()) {

				Thread.sleep(25_000);

				if (!Thread.currentThread().isInterrupted()) {
					ArrayList<Correo> nuevos = gestionPop3.recibirCorreosPOP3(host, hostImap, correo,
							PASSWORD_APLICACION);

					if (nuevos != null && nuevos.size() != ultimoNumeroCorreos) {
						ultimoNumeroCorreos = nuevos.size();
						SwingUtilities.invokeLater(() -> {
							controlador.actualizarListaDesdeHilo(nuevos);
						});
					}
				}
			}
		} catch (InterruptedException e) {
		} finally {
		}
	}
}
