package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controladorLogs.GestionLogs;
import modelo.Log;
import modelo.MoTextos;

import vista.VistaCorreoBase;

/**
 * Oyente del bot�n "Enviar" en la vista de redacci�n de correos.
 * Valida los datos del formulario y env�a el correo electr�nico.
 */
public class OyenteEnviarCorreo implements ActionListener {

	/** Contrase�a de aplicaci�n. */
	private String passwordAplicacion;

	/** Vista de redacci�n de correo. */
	private VistaCorreoBase v;

	/** Controlador de correos. */
	private ControladorCorreos controlador;

	/**
	 * Constructor del oyente.
	 *
	 * @param v                  Vista de redacci�n de correo.
	 * @param passwordAplicacion Contrase�a de aplicaci�n.
	 * @param controlador        Controlador de correos.
	 */
	public OyenteEnviarCorreo(VistaCorreoBase v, String passwordAplicacion, ControladorCorreos controlador) {
		this.v = v;
		this.passwordAplicacion = passwordAplicacion;
		this.controlador = controlador;
	}

	/**
	 * Recopila los datos del formulario (destinatario, asunto, cuerpo) y env�a el
	 * correo.
	 * Valida que los campos no est�n vac�os y que el destinatario est� permitido.
	 *
	 * @param e Evento de acci�n.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String asunto = v.getTextoAsunto().getText();
		String receptor = v.getTextoPara().getText();
		String remitente = v.getRemitente();
		String cuerpoMensaje = v.getTextoCuerpo().getText();

		if (receptor == "" || receptor.isEmpty() || cuerpoMensaje.isEmpty()) {
			JOptionPane.showMessageDialog(v, MoTextos.mail_msg_recipient_obligatory);
			return;
		}

		try {
			if (!comprobarReceptorWhiteList(receptor)) {
				return;
			}
			EnviarCorreo.enviarCorreo(remitente, asunto, cuerpoMensaje, receptor, passwordAplicacion, v.getAdjuntos());
			JOptionPane.showMessageDialog(v, MoTextos.mail_msg_sent_prefix + receptor);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(v,
					MoTextos.mail_msg_sent_error_recipient);
			e1.printStackTrace();
		}

		v.dispose();
	}

	/**
	 * Verifica si el destinatario est� en la lista blanca (whitelist) o es un
	 * usuario registrado.
	 *
	 * @param receptor Direcci�n de correo del destinatario.
	 * @return true si el destinatario es v�lido, false en caso contrario.
	 */
	private boolean comprobarReceptorWhiteList(String receptor) {
		boolean estaEnWhiteList;
		estaEnWhiteList = controlador.comprobarReceptorWhiteList(receptor);

		if (!estaEnWhiteList) {
			v.mostrarMensaje("The address is not in the whitelist", true);
			Log log = new Log("MAIL_SEND", controlador.getCORREO(), false);
			GestionLogs.writeLog(log);
			return false;
		}

		Log log = new Log("MAIL_SEND", controlador.getCORREO(), false);
		GestionLogs.writeLog(log);
		return true;
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene la contrase�a de aplicaci�n.
	 * @return La contrase�a configurada.
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
	 * Obtiene la vista de redacci�n asociada.
	 * @return La vista de correo base.
	 */
	public VistaCorreoBase getV() {
		return v;
	}

	/**
	 * Establece la vista de redacci�n.
	 * @param v La nueva vista.
	 */
	public void setV(VistaCorreoBase v) {
		this.v = v;
	}

	/**
	 * Obtiene el controlador de correos asociado.
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