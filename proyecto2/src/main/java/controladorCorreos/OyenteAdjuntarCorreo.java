package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import modelo.MoTextos;
import vista.VistaCorreoBase;

/**
 * Oyente del botón "Adjuntar" en la vista de redacción de correos.
 * Permite seleccionar archivos para adjuntar al correo.
 */
public class OyenteAdjuntarCorreo implements ActionListener {

	private VistaCorreoBase v;

	/**
	 * Constructor del oyente.
	 *
	 * @param v Vista de redacción de correo.
	 */
	public OyenteAdjuntarCorreo(VistaCorreoBase v) {
		this.v = v;
	}

	/**
	 * Abre el selector de archivos para adjuntar un documento al correo.
	 *
	 * @param e Evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		File archivo = v.mostrarSelectorAdjuntos();
		if (archivo != null) {
			v.agregarAdjunto(archivo);
			JOptionPane.showMessageDialog(v, MoTextos.mail_msg_attached_prefix + archivo.getName());
		}
	}

}