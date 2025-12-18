package controladorCorreos;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladorLogs.ControladorLogs;
import controladorLogs.GestionLogs;
import modelo.Correo;
import vista.VistaGeneralCorreo;

public class HiloRecepcionCorreos implements Runnable {

	private GestionCorreos gestionPop3;
	private String host,hostImap, correo, PASSWORD_APLICACION;
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
	        while (!Thread.currentThread().isInterrupted() && vistaGeneral.isVisible()) {

	            Thread.sleep(25_000); 

	            if (!Thread.currentThread().isInterrupted()) {
	                ArrayList<Correo> nuevos = gestionPop3.recibirCorreosPOP3(host, hostImap, correo, PASSWORD_APLICACION);

	                if (nuevos != null && nuevos.size() != ultimoNumeroCorreos) {
	                    ultimoNumeroCorreos = nuevos.size();
	                    SwingUtilities.invokeLater(() -> {
	                        controlador.actualizarListaDesdeHilo(nuevos);
	                    });
	                }
	            }
	        }
	    } catch (InterruptedException e) {
	    } finally {
	    }

	}

}
