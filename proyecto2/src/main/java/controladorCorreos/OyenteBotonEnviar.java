package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VistaCorreoBase;

/**
 * Oyente del bot�n "Enviar" en la vista de correos. Abre la ventana de
 * redacci�n de un nuevo correo.
 */
public class OyenteBotonEnviar implements ActionListener {

	/** Vista de redacci�n de correo. */
	private VistaCorreoBase v;

	/** Correo electr�nico del remitente. */
	private String correo;

	/** Contrase�a de aplicaci�n. */
	private String passwordAplicacion;

	/** Controlador de correos. */
	private ControladorCorreos controlador;

	/**
	 * Constructor del oyente.
	 *
	 * @param correo             Direcci�n de correo del remitente.
	 * @param passwordAplicacion Contrase�a de aplicaci�n.
	 * @param controladorCorreos Controlador de correos.
	 */
	public OyenteBotonEnviar(String correo, String passwordAplicacion, ControladorCorreos controladorCorreos) {
		this.correo = correo;
		this.passwordAplicacion = passwordAplicacion;
		this.controlador = controladorCorreos;
	}

	/**
	 * Abre la ventana de redacci�n de nuevo correo.
	 *
	 * @param e Evento de acci�n.
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
	 * Obtiene la vista de redacci�n de correo.
	 * @return La vista de correo base.
	 */
	public VistaCorreoBase getV() {
		return v;
	}

	/**
	 * Establece la vista de redacci�n de correo.
	 * @param v La nueva vista.
	 */
	public void setV(VistaCorreoBase v) {
		this.v = v;
	}

	/**
	 * Obtiene el correo del remitente.
	 * @return El correo electr�nico.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo del remitente.
	 * @param correo El nuevo correo electr�nico.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene la contrase�a de aplicaci�n.
	 * @return La contrase�a de aplicaci�n.
	 */
	public String getPasswordAplicacion() {
		return passwordAplicacion;
	}

	/**
	 * Establece la contrase�a de aplicaci�n.
	 * @param passwordAplicacion La nueva contrase�a.
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