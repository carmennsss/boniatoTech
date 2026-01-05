package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;
import vista.VistaCorreoBase;

/**
 * Oyente del bot�n "Eliminar" en la vista de lectura de correos.
 * Elimina el correo seleccionado de la base de datos y cierra la ventana.
 */
public class OyenteBotonEliminar implements ActionListener {

	/** Correo a eliminar. */
	private Correo correo;

	/** Controlador de correos. */
	private ControladorCorreos controladorCorreos;

	/** Vista de lectura del correo. */
	private VistaCorreoBase vistaLectura;

	/**
	 * Constructor del oyente.
	 *
	 * @param correo             Correo a eliminar.
	 * @param controlador        Controlador de correos.
	 * @param vistaLectura       Vista de lectura del correo.
	 */
	public OyenteBotonEliminar(Correo correo, ControladorCorreos controlador, VistaCorreoBase vistaLectura) {
		this.controladorCorreos = controlador;
		this.correo = correo;
		this.vistaLectura = vistaLectura;
	}

	/**
	 * Elimina el correo seleccionado tanto de la base de datos como de la vista.
	 *
	 * @param e Evento de acci�n.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controladorCorreos.eliminarCorreoSeleccionado(correo);
		vistaLectura.dispose();
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
	 * Establece el correo a eliminar.
	 * @param correo El nuevo objeto Correo.
	 */
	public void setCorreo(Correo correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el controlador de correos asociado.
	 * @return El controlador.
	 */
	public ControladorCorreos getControladorCorreos() {
		return controladorCorreos;
	}

	/**
	 * Establece el controlador de correos.
	 * @param controladorCorreos El nuevo controlador.
	 */
	public void setControladorCorreos(ControladorCorreos controladorCorreos) {
		this.controladorCorreos = controladorCorreos;
	}

	/**
	 * Obtiene la vista de lectura asociada.
	 * @return La vista de lectura.
	 */
	public VistaCorreoBase getVistaLectura() {
		return vistaLectura;
	}

	/**
	 * Establece la vista de lectura.
	 * @param vistaLectura La nueva vista.
	 */
	public void setVistaLectura(VistaCorreoBase vistaLectura) {
		this.vistaLectura = vistaLectura;
	}
}