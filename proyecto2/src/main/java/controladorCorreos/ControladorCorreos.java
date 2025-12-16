package controladorCorreos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.mail.Flags;

import modelo.Correo;
import vista.VistaGeneralCorreo;

public class ControladorCorreos {
	
	private String CORREO;
	private String PASSWORD_APLICACION;
	private static final String HOST = "pop.gmail.com";
	private ArrayList<Correo> correos = new ArrayList<>();
	private VistaGeneralCorreo vistaGeneral;
	private static GestionPOP3 gestion;
	private Thread hiloRecepcion;

	
	public ControladorCorreos(String CORREO, String PASSWORD_APLICACION) {
		this.CORREO = CORREO;
		this.PASSWORD_APLICACION = PASSWORD_APLICACION;
		gestion = new GestionPOP3();
		configurarVistaGeneral();
		
		cargarCorreos();
				
	}

	protected void cargarCorreos() {
		new Thread(() -> {

	        System.out.println("Conectando con Gmail...");
	        correos.clear();
	        correos.addAll(obtenerCorreos());
	        
	        // Una vez descargados, actualizamos la tabla en el hilo de Swing
	        javax.swing.SwingUtilities.invokeLater(() -> {
	            vistaGeneral.cargarCorreos(correos);
	            
	            if (hiloRecepcion == null || !hiloRecepcion.isAlive()) {
	                HiloRecepcionCorreos hilo = new HiloRecepcionCorreos(
	                        gestion, HOST, "recent:" + CORREO, PASSWORD_APLICACION, vistaGeneral
	                );
	                hiloRecepcion = new Thread(hilo, "Hilo-Recepcion-Correos");
	                hiloRecepcion.start();
	            }


	        });
	    }).start();
	}
	
	// Metodo para detener el hilo cuando se cierre la ventana
	public void detenerHiloRecepcion() {
	    if (hiloRecepcion != null && hiloRecepcion.isAlive()) {
	        hiloRecepcion.interrupt();
	    }
	}


	public ArrayList<Correo> obtenerCorreos() {
		ArrayList<Correo> listaCorreos = gestion.recibirCorreosPOP3(HOST, "recent:" + CORREO, PASSWORD_APLICACION);		
		
		return listaCorreos;
	}


	private void configurarVistaGeneral() {
		vistaGeneral = new VistaGeneralCorreo(CORREO);
		vistaGeneral.setVisible(true);
		vistaGeneral.getBotonEnviarCorreo().addActionListener(new OyenteBotonEnviar(vistaGeneral.getCorreo(), PASSWORD_APLICACION));
		vistaGeneral.getEmailTabla().addMouseListener(new OyenteTabla(vistaGeneral.getEmailTabla(), correos, this));
	}


	public void eliminarCorreoSeleccionado(Correo correo) {
	    try {
	    	
	    	int indiceEnLista = correos.indexOf(correo);
	        if (indiceEnLista == -1) return;
	        
	        int totalCorreos = correos.size();
	        int indiceServidor = totalCorreos - indiceEnLista; 

	        gestion.eliminarCorreoPOP3(
	                HOST,
	                "recent:" + CORREO,
	                PASSWORD_APLICACION,
	                indiceServidor - 1 
	        );

	        correos.remove(indiceEnLista);
	        vistaGeneral.cargarCorreos(correos);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public void marcarCorreoLeido(Correo correo) throws Exception {
		try {
			    	
	    	int indiceEnLista = correos.indexOf(correo);
	        if (indiceEnLista == -1) return;
	        
	        int totalCorreos = correos.size();
	        int indiceServidor = totalCorreos - indiceEnLista; 

	        gestion.marcarLeidoPOP3(
	                HOST,
	                "recent:" + CORREO,
	                PASSWORD_APLICACION,
	                indiceServidor - 1 
	        );

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	


	public String getPasswordAplicacion() {
		return PASSWORD_APLICACION;
	}
	
	
}
