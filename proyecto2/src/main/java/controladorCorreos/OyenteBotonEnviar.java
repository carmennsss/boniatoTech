package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.ModeloBaseDatos;
import vista.VistaCorreoBase;

/**
 * Oyente del botón "Enviar" en la vista de correos.
 * Abre la ventana de redacción de un nuevo correo.
 */
public class OyenteBotonEnviar implements ActionListener {

	/** Vista de redacción de correo. */
	private VistaCorreoBase v;

	/** Correo electrónico del remitente. */
	private String correo;

	/** Contraseña de aplicación. */
	private String passwordAplicacion;

	/** Controlador de correos. */
	private ControladorCorreos controlador;

	/**
	 * Constructor del oyente.
	 *
	 * @param correo             Dirección de correo del remitente.
	 * @param passwordAplicacion Contraseña de aplicación.
	 * @param controladorCorreos Controlador de correos.
	 */
	public OyenteBotonEnviar(String correo, String passwordAplicacion, ControladorCorreos controladorCorreos) {
		this.correo = correo;
		this.passwordAplicacion = passwordAplicacion;
		this.controlador = controladorCorreos;
	}

	/**
	 * Abre la ventana de redacción de nuevo correo.
	 *
	 * @param e Evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		v = new VistaCorreoBase(correo);
		v.getBotonEnviar().addActionListener(new OyenteEnviarCorreo(v, passwordAplicacion, controlador));
		v.getBotonAdjuntar().addActionListener(new OyenteAdjuntarCorreo(v));
		v.setVisible(true);
	}

}
