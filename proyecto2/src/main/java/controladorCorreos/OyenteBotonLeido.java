package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;

public class OyenteBotonLeido implements ActionListener {

	private Correo correo;
	private ControladorCorreos controladorCorreos;
	
	public OyenteBotonLeido(Correo correo, ControladorCorreos controladorCorreos) {
		this.controladorCorreos = controladorCorreos;
		this.correo = correo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			controladorCorreos.marcarCorreoLeido(correo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
