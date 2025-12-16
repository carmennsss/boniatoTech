package controladorCorreos;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.Correo;
import vista.VistaGeneralCorreo;

public class HiloRecepcionCorreos implements Runnable {

	private GestionPOP3 gestionPop3;
	private String host, correo, PASSWORD_APLICACION;
	private VistaGeneralCorreo vistaGeneral;
	private int ultimoNumeroCorreos = -1;
	
	public HiloRecepcionCorreos(GestionPOP3 gestionPop3, String host, String correo, String PASSWORD_APLICACION, VistaGeneralCorreo vistaGeneral) {
		this.gestionPop3 = gestionPop3;
		this.host = host;
		this.PASSWORD_APLICACION = PASSWORD_APLICACION;
		this.correo = correo;
		this.vistaGeneral = vistaGeneral;
	}
	
	@Override
	public void run() {
		try {
	        while (!Thread.currentThread().isInterrupted()) {

	        	ArrayList<Correo> nuevos =
	        	        gestionPop3.recibirCorreosPOP3(host, correo, PASSWORD_APLICACION);

	        	if (nuevos.size() != ultimoNumeroCorreos) {
	        	    ultimoNumeroCorreos = nuevos.size();
	        	    SwingUtilities.invokeLater(() -> vistaGeneral.cargarCorreos(nuevos));
	        	}

	            Thread.sleep(1_000); // 10 segundos
	        }
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }

	}

}
