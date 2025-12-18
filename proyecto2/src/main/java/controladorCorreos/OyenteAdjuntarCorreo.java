package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import modelo.MoTextos;
import vista.VistaCorreoBase;

public class OyenteAdjuntarCorreo implements ActionListener {

	private VistaCorreoBase v;

	public OyenteAdjuntarCorreo(VistaCorreoBase v) {
		this.v = v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File archivo = v.mostrarSelectorAdjuntos();
		if (archivo != null) {
			v.agregarAdjunto(archivo);
			JOptionPane.showMessageDialog(v, MoTextos.mail_msg_attached_prefix + archivo.getName());
		}
	}

}
