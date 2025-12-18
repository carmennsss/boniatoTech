package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.MoTextos;

import vista.VistaCorreoBase;

public class OyenteEnviarCorreo implements ActionListener {

	private String passwordAplicacion;
	private VistaCorreoBase v;

	public OyenteEnviarCorreo(VistaCorreoBase v, String passwordAplicacion) {
		this.v = v;
		this.passwordAplicacion = passwordAplicacion;
	}

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
			EnviarCorreo.enviarCorreo(remitente, asunto, cuerpoMensaje, receptor, passwordAplicacion, v.getAdjuntos());
			JOptionPane.showMessageDialog(v, MoTextos.mail_msg_sent_prefix + receptor);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(v,
					MoTextos.mail_msg_sent_error_recipient);
			e1.printStackTrace();
		}

		v.dispose();
	}

}
