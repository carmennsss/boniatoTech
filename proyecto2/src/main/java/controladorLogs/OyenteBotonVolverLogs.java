package controladorLogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.VistaLogs;

/**
 * Oyente para el botón "Volver" en la vista de logs.
 * Cierra la ventana actual y reabre el menú de administrador.
 */
/**
 * Oyente para el botón "Volver" en la vista de logs.
 * Cierra la ventana actual y reabre el menú de administrador.
 */
public class OyenteBotonVolverLogs implements ActionListener {

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

}
