package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.VistaCorreoBase;

public class OyenteBotonEnviar implements ActionListener{

	private VistaCorreoBase v;
	private String correo;
	
	public OyenteBotonEnviar(String correo) {
		this.correo = correo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		v = new VistaCorreoBase(correo);
		v.getBotonEnviar().addActionListener(new OyenteEnviarCorreo(v));
		v.setVisible(true);
	}

}
