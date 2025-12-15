package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import vista.VistaCorreoBase;

public class OyenteEnviarCorreo implements ActionListener {

	VistaCorreoBase v;
	
	public OyenteEnviarCorreo(VistaCorreoBase v) {
		this.v = v;
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
			EnviarCorreo.enviarCorreo(remitente, asunto, cuerpoMensaje, receptor);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		v.getTextoAsunto().setText("");
		v.getTextoPara().setText("");
		v.getTextoCuerpo().setText("");
	}

}
