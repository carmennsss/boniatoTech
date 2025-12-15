package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;

public class OyenteBotonEliminar implements ActionListener {
	
	private Correo correo;

	public OyenteBotonEliminar(Correo correo) {
		this.correo = correo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ControladorCorreos.eliminarCorreo(correo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
