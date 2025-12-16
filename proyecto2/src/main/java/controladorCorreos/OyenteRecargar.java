package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import vista.VistaGeneralCorreo;
import modelo.Correo;

public class OyenteRecargar implements ActionListener {
	
	private VistaGeneralCorreo vistaGeneral;
	private ArrayList<Correo> correos;
	private ControladorCorreos controladorCorreos;

	
	public OyenteRecargar(VistaGeneralCorreo vistaGeneral, ArrayList<Correo> correos, ControladorCorreos controladorCorreos) {
		this.vistaGeneral = vistaGeneral;
		this.correos = correos;
		this.controladorCorreos = controladorCorreos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		correos.clear();
		controladorCorreos.cargarCorreos();
	}

}
