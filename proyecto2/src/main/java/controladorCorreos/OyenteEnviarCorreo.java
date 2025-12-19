package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controladorLogs.GestionLogs;
import modelo.Log;
import modelo.MoTextos;

import vista.VistaCorreoBase;

/**
 * Oyente del botón "Enviar" en la vista de redacción de correos.
 * Valida los datos del formulario y envía el correo electrónico.
 */
public class OyenteEnviarCorreo implements ActionListener {

	private String passwordAplicacion;
	private VistaCorreoBase v;
	private ControladorCorreos controlador;

	/**
	 * Constructor del oyente.
	 *
	 * @param v                  Vista de redacción de correo.
	 * @param passwordAplicacion Contraseña de aplicación.
	 * @param controlador        Controlador de correos.
	 */
	public OyenteEnviarCorreo(VistaCorreoBase v, String passwordAplicacion, ControladorCorreos controlador) {
		this.v = v;
		this.passwordAplicacion = passwordAplicacion;
		this.controlador = controlador;
	}

	/**
	 * Recopila los datos del formulario (destinatario, asunto, cuerpo) y envía el
	 * correo.
	 * Valida que los campos no estén vacíos y que el destinatario esté permitido.
	 *
	 * @param e Evento de acción.
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
	 * Verifica si el destinatario está en la lista blanca (whitelist) o es un
	 * usuario registrado.
	 *
	 * @param receptor Dirección de correo del destinatario.
	 * @return true si el destinatario es válido, false en caso contrario.
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

		Log log = new Log("MAIL_SEND", controlador.getCORREO(), true);
		GestionLogs.writeLog(log);
		return true;
	}

}
