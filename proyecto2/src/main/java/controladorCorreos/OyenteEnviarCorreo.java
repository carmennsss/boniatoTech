package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.MoTextos;

import vista.VistaCorreoBase;

public class OyenteEnviarCorreo implements ActionListener {

	private String passwordAplicacion;
	private VistaCorreoBase v;
	private ControladorCorreos controlador;

	public OyenteEnviarCorreo(VistaCorreoBase v, String passwordAplicacion, ControladorCorreos controlador) {
		this.v = v;
		this.passwordAplicacion = passwordAplicacion;
		this.controlador = controlador;
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

	private boolean comprobarReceptorWhiteList(String receptor) {	
		boolean estaEnWhiteList;
		estaEnWhiteList = controlador.comprobarReceptorWhiteList(receptor);
		
		if (!estaEnWhiteList) {
			v.mostrarMensaje("The address is not in the whitelist", true);
			return false;
		}
		return true;
	}

}
