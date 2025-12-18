package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controladorLogs.GestionLogs;
import modelo.Log;
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
			JOptionPane.showMessageDialog(v, "The recipient and the message body are obligatory");
			return;
		}

		try {
			if (!comprobarReceptorWhiteList(receptor)) {
				return;
			}
			EnviarCorreo.enviarCorreo(remitente, asunto, cuerpoMensaje, receptor, passwordAplicacion, v.getAdjuntos());
			JOptionPane.showMessageDialog(v, "Email successfully sent to" + receptor);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(v,
					"The message could not be sent; please check the recipient's email address.");
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
		
		Log log = new Log("MAIL_SEND", controlador.getCORREO(), true);
		GestionLogs.writeLog(log);
		return true;
	}

}
