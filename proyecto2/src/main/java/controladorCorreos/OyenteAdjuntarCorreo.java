package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import modelo.MoTextos;
import vista.VistaCorreoBase;

/**
 * Oyente del bot�n "Adjuntar" en la vista de redacci�n de correos.
 * Permite seleccionar archivos para adjuntar al correo.
 */
public class OyenteAdjuntarCorreo implements ActionListener {

	/** Vista de redacci�n de correo. */
	private VistaCorreoBase v;

	/**
	 * Constructor del oyente.
	 *
	 * @param v Vista de redacci�n de correo.
	 */
	public OyenteAdjuntarCorreo(VistaCorreoBase v) {
		this.v = v;
	}

	/**
	 * Abre el selector de archivos para adjuntar un documento al correo.
	 *
	 * @param e Evento de acci�n.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		File archivo = v.mostrarSelectorAdjuntos();
		if (archivo != null) {
			v.agregarAdjunto(archivo);
			JOptionPane.showMessageDialog(v, MoTextos.mail_msg_attached_prefix + archivo.getName());
		}
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene la vista de correo base asociada al oyente.
	 * * @return La vista de redacci�n.
	 */
	public VistaCorreoBase getV() {
		return v;
	}

	/**
	 * Establece la vista de correo base asociada al oyente.
	 * * @param v La nueva vista de redacci�n.
	 */
	public void setV(VistaCorreoBase v) {
		this.v = v;
	}

}