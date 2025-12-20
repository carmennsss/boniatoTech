package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Oyente del botón "Refrescar" en la vista de correos.
 * Recarga manualmente la lista de correos desde el servidor.
 */
public class OyenteRefrescarCorreo implements ActionListener {

	/** Controlador de correos. */
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

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el controlador de correos asociado.
	 * @return El controlador de correos.
	 */
	public ControladorCorreos getControlador() {
		return controlador;
	}

	/**
	 * Establece el controlador de correos asociado.
	 * @param controlador El nuevo controlador de correos.
	 */
	public void setControlador(ControladorCorreos controlador) {
		this.controlador = controlador;
	}
}