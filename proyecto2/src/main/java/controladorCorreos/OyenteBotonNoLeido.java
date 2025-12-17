package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;

public class OyenteBotonNoLeido implements ActionListener {

	private Correo correo;
	private ControladorCorreos controladorCorreos;

	public OyenteBotonNoLeido(Correo correo, ControladorCorreos controladorCorreos) {
		this.controladorCorreos = controladorCorreos;
		this.correo = correo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			controladorCorreos.marcarCorreoNoLeido(correo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}