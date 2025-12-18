package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Oyente del botón "Refrescar" en la vista de correos.
 * Recarga manualmente la lista de correos desde el servidor.
 */
public class OyenteRefrescarCorreo implements ActionListener {

	private ControladorCorreos controlador;

	/**
	 * Constructor del oyente.
	 *
	 * @param controladorCorreos Controlador de correos.
	 */
	public OyenteRefrescarCorreo(ControladorCorreos controladorCorreos) {
		this.controlador = controladorCorreos;
	}

	/**
	 * Fuerza una recarga manual de la lista de correos.
	 *
	 * @param e Evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controlador.cargarCorreos();
	}

}
