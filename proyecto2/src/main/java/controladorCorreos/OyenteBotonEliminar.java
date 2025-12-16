package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;

public class OyenteBotonEliminar implements ActionListener {
	
	private Correo correo;
	private ControladorCorreos controladorCorreos;

	public OyenteBotonEliminar(Correo correo, ControladorCorreos controlador) {
		this.controladorCorreos = controlador;
		this.correo = correo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controladorCorreos.eliminarCorreoSeleccionado(correo);
		
	}

}
