package controladorCorreos;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladorLogs.ControladorLogs;
import controladorLogs.GestionLogs;
import modelo.Correo;
import modelo.Log;
import vista.VistaGeneralCorreo;

public class HiloRecepcionCorreos implements Runnable {

	private GestionCorreos gestionPop3;
	private String host, hostImap, correo, PASSWORD_APLICACION;
	private VistaGeneralCorreo vistaGeneral;
	private int ultimoNumeroCorreos = -1;
	private ControladorCorreos controlador;
	private GestionLogs logCorreo;

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

}
