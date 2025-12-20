package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;

/**
 * Oyente del botón "Marcar como no leído" en la vista de correos.
 * Cambia el estado de lectura del correo seleccionado.
 */
public class OyenteBotonNoLeido implements ActionListener {

	/** Correo a marcar como no leído. */
	private Correo correo;

	/** Controlador de correos. */
	private ControladorCorreos controladorCorreos;

	/**
	 * Constructor del oyente.
	 *
	 * @param correo             Correo a marcar como no leído.
	 * @param controladorCorreos Controlador de correos.
	 */
	public OyenteBotonNoLeido(Correo correo, ControladorCorreos controladorCorreos) {
		this.controladorCorreos = controladorCorreos;
		this.correo = correo;
	}

	/**
	 * Marca el correo seleccionado como no leído.
	 *
	 * @param e Evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			controladorCorreos.marcarCorreoNoLeido(correo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}