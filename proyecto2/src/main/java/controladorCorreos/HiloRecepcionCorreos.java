package controladorCorreos;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.Correo;
import vista.VistaGeneralCorreo;

public class HiloRecepcionCorreos implements Runnable {

	private GestionCorreos gestionPop3;
	private String host, correo, PASSWORD_APLICACION;
	private VistaGeneralCorreo vistaGeneral;
	private int ultimoNumeroCorreos = -1;
	private ControladorCorreos controlador;
	
	public HiloRecepcionCorreos(GestionCorreos gestionPop3, String host, String correo, String PASSWORD_APLICACION, VistaGeneralCorreo vistaGeneral, ControladorCorreos controlador) {
		this.gestionPop3 = gestionPop3;
		this.host = host;
		this.PASSWORD_APLICACION = PASSWORD_APLICACION;
		this.correo = correo;
		this.vistaGeneral = vistaGeneral;
		this.controlador = controlador;
	}
	
	@Override
	public void run() {
		try {
	        while (!Thread.currentThread().isInterrupted()) {

	        	ArrayList<Correo> nuevos =
	        	        gestionPop3.recibirCorreosPOP3(host, correo, PASSWORD_APLICACION);

	        	if (nuevos.size() != ultimoNumeroCorreos) {
	        	    ultimoNumeroCorreos = nuevos.size();
	        	    SwingUtilities.invokeLater(() -> {
	        	    	controlador.actualizarListaDesdeHilo(nuevos);
	        	    	});
	        	}

	            Thread.sleep(5_000); // 5 segundos
	        }
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }

	}

}
