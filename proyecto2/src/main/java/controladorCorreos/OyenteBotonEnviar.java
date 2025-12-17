package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.VistaCorreoBase;

public class OyenteBotonEnviar implements ActionListener {

	private VistaCorreoBase v;
	private String correo;
	private String passwordAplicacion;

	public OyenteBotonEnviar(String correo, String passwordAplicacion) {
		this.correo = correo;
		this.passwordAplicacion = passwordAplicacion;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		v = new VistaCorreoBase(correo);
		v.getBotonEnviar().addActionListener(new OyenteEnviarCorreo(v, passwordAplicacion));
		v.getBotonAdjuntar().addActionListener(new OyenteAdjuntarCorreo(v));
		v.setVisible(true);
	}

}
