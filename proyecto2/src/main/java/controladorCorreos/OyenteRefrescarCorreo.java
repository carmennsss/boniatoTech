package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OyenteRefrescarCorreo implements ActionListener {
	
	private ControladorCorreos controlador;

	public OyenteRefrescarCorreo(ControladorCorreos controladorCorreos) {
		this.controlador = controladorCorreos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controlador.cargarCorreos();
	}

}
