package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Correo;
import vista.VistaCorreoBase;

public class OyenteBotonEliminar implements ActionListener {
	
	private Correo correo;
	private ControladorCorreos controladorCorreos;
	private VistaCorreoBase vistaLectura;

	public OyenteBotonEliminar(Correo correo, ControladorCorreos controlador, VistaCorreoBase vistaLectura) {
		this.controladorCorreos = controlador;
		this.correo = correo;
		this.vistaLectura = vistaLectura;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controladorCorreos.eliminarCorreoSeleccionado(correo);
		vistaLectura.dispose();
		
		
	}

}
