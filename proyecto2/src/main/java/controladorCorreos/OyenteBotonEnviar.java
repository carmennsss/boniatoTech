package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.ModeloBaseDatos;
import vista.VistaCorreoBase;

public class OyenteBotonEnviar implements ActionListener {

	private VistaCorreoBase v;
	private String correo;
	private String passwordAplicacion;
	private ControladorCorreos controlador;

	public OyenteBotonEnviar(String correo, String passwordAplicacion, ControladorCorreos controladorCorreos) {
		this.correo = correo;
		this.passwordAplicacion = passwordAplicacion;
		this.controlador = controladorCorreos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		v = new VistaCorreoBase(correo);
		v.getBotonEnviar().addActionListener(new OyenteEnviarCorreo(v, passwordAplicacion, controlador));
		v.getBotonAdjuntar().addActionListener(new OyenteAdjuntarCorreo(v));
		v.setVisible(true);
	}

}
