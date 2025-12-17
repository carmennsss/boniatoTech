package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

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
            JOptionPane.showMessageDialog(v, "Archivo adjuntado: " + archivo.getName());
        }
	}

}
