package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;
import vista.VistaCorreoBase;

/**
 * Oyente del botón "Eliminar" en la vista de lectura de correos.
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
	 * @param correo       Correo a eliminar.
	 * @param controlador  Controlador de correos.
	 * @param vistaLectura Vista de lectura del correo.
	 */
	public OyenteBotonEliminar(Correo correo, ControladorCorreos controlador, VistaCorreoBase vistaLectura) {
		this.controladorCorreos = controlador;
		this.correo = correo;
		this.vistaLectura = vistaLectura;
	}

	/**
	 * Elimina el correo seleccionado tanto de la base de datos como de la vista.
	 *
	 * @param e Evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controladorCorreos.eliminarCorreoSeleccionado(correo);
		vistaLectura.dispose();

	}

}
