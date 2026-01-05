package controladorLogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.VistaLogs;

/**
 * Oyente para el botón "Volver" en la vista de logs.
 * Cierra la ventana actual y reabre el menú de administrador.
 */
public class OyenteBotonVolverLogs implements ActionListener {

	/** Vista de logs. */
	private VistaLogs vistaLogs;

	/**
	 * Constructor del oyente.
	 *
	 * @param vistaLogs Vista de logs.
	 */
	public OyenteBotonVolverLogs(VistaLogs vistaLogs) {
		this.vistaLogs = vistaLogs;
	}

	/**
	 * Cierra la vista de logs y hace visible la vista de administrador.
	 *
	 * @param e Evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		vistaLogs.dispose();
		if (vistaLogs.getVistaAdmin() != null) {
			vistaLogs.getVistaAdmin().setVisible(true);
		}
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene la vista de logs asociada.
	 * @return El objeto VistaLogs.
	 */
	public VistaLogs getVistaLogs() {
		return vistaLogs;
	}

	/**
	 * Establece la vista de logs asociada.
	 * @param vistaLogs La nueva vista de logs.
	 */
	public void setVistaLogs(VistaLogs vistaLogs) {
		this.vistaLogs = vistaLogs;
	}

}