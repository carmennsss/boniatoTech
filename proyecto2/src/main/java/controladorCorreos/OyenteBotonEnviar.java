package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaCorreoBase;

/**
 * Oyente del botón "Enviar" en la vista de correos. Abre la ventana de
 * redacción de un nuevo correo.
 */
public class OyenteBotonEnviar implements ActionListener {

	/** Vista de redacción de correo. */
	private VistaCorreoBase v;

	/** Correo electrónico del remitente. */
	private String correo;

	/** Contraseña de aplicación. */
	private String passwordAplicacion;

	/** Controlador de correos. */
	private ControladorCorreos controlador;

	/**
	 * Constructor del oyente.
	 *
	 * @param correo             Dirección de correo del remitente.
	 * @param passwordAplicacion Contraseña de aplicación.
	 * @param controladorCorreos Controlador de correos.
	 */
	public OyenteBotonEnviar(String correo, String passwordAplicacion, ControladorCorreos controladorCorreos) {
		this.correo = correo;
		this.passwordAplicacion = passwordAplicacion;
		this.controlador = controladorCorreos;
	}

	/**
	 * Abre la ventana de redacción de nuevo correo.
	 *
	 * @param e Evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		v = new VistaCorreoBase(correo);
		v.getBotonEnviar().addActionListener(new OyenteEnviarCorreo(v, passwordAplicacion, controlador));
		v.getBotonAdjuntar().addActionListener(new OyenteAdjuntarCorreo(v));
		v.setVisible(true);
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene la vista de redacción de correo.
	 * @return La vista de correo base.
	 */
	public VistaCorreoBase getV() {
		return v;
	}

	/**
	 * Establece la vista de redacción de correo.
	 * @param v La nueva vista.
	 */
	public void setV(VistaCorreoBase v) {
		this.v = v;
	}

	/**
	 * Obtiene el correo del remitente.
	 * @return El correo electrónico.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo del remitente.
	 * @param correo El nuevo correo electrónico.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene la contraseña de aplicación.
	 * @return La contraseña de aplicación.
	 */
	public String getPasswordAplicacion() {
		return passwordAplicacion;
	}

	/**
	 * Establece la contraseña de aplicación.
	 * @param passwordAplicacion La nueva contraseña.
	 */
	public void setPasswordAplicacion(String passwordAplicacion) {
		this.passwordAplicacion = passwordAplicacion;
	}

	/**
	 * Obtiene el controlador de correos.
	 * @return El controlador.
	 */
	public ControladorCorreos getControlador() {
		return controlador;
	}

	/**
	 * Establece el controlador de correos.
	 * @param controlador El nuevo controlador.
	 */
	public void setControlador(ControladorCorreos controlador) {
		this.controlador = controlador;
	}
}