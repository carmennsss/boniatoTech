package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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
		
		if (receptor == "" || receptor.isEmpty() || cuerpoMensaje == "" || cuerpoMensaje.isEmpty()) {
			JOptionPane.showMessageDialog(v, "El receptor y el cuerpo del mensaje es obligatorio");
			return;
		}
		
		try {
			EnviarCorreo.enviarCorreo(remitente, asunto, cuerpoMensaje, receptor, passwordAplicacion, v.getAdjuntos());		} catch (Exception e1) {
			JOptionPane.showMessageDialog(v, "El mensaje no se pudo enviar, verifica la existencia del correo del receptor");
			e1.printStackTrace();
		}
		
		v.dispose();
	}

}
