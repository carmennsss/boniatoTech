package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;

/**
 * Oyente del bot�n "Marcar como no le�do" en la vista de correos.
 * Cambia el estado de lectura del correo seleccionado.
 */
public class OyenteBotonNoLeido implements ActionListener {

	/** Correo a marcar como no le�do. */
	private Correo correo;

	/** Controlador de correos. */
	private ControladorCorreos controladorCorreos;

	/**
	 * Constructor del oyente.
	 *
	 * @param correo             Correo a marcar como no le�do.
	 * @param controladorCorreos Controlador de correos.
	 */
	public OyenteBotonNoLeido(Correo correo, ControladorCorreos controladorCorreos) {
		this.controladorCorreos = controladorCorreos;
		this.correo = correo;
	}

	/**
	 * Marca el correo seleccionado como no le�do.
	 *
	 * @param e Evento de acci�n.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			controladorCorreos.marcarCorreoNoLeido(correo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el correo asociado a este oyente.
	 * @return El objeto Correo.
	 */
	public Correo getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo a modificar.
	 * @param correo El nuevo objeto Correo.
	 */
	public void setCorreo(Correo correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el controlador de correos asociado.
	 * @return El controlador de correos.
	 */
	public ControladorCorreos getControladorCorreos() {
		return controladorCorreos;
	}

	/**
	 * Establece el controlador de correos asociado.
	 * @param controladorCorreos El nuevo controlador.
	 */
	public void setControladorCorreos(ControladorCorreos controladorCorreos) {
		this.controladorCorreos = controladorCorreos;
	}
}