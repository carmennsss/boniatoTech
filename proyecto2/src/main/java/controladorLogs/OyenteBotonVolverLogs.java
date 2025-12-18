package controladorLogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.VistaLogs;

public class OyenteBotonVolverLogs implements ActionListener {

	private VistaLogs vistaLogs;

	public OyenteBotonVolverLogs(VistaLogs vistaLogs) {
		this.vistaLogs = vistaLogs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		vistaLogs.dispose();
		if (vistaLogs.getVistaAdmin() != null) {
			vistaLogs.getVistaAdmin().setVisible(true);
		}
	}

}
