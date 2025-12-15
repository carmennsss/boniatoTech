package controladorCorreos;

import java.util.ArrayList;
import java.util.Date;

import modelo.Correo;
import vista.VistaGeneralCorreo;

public class ControladorCorreos {
	
	private static String CORREO = "pablo.pruebas.mail@gmail.com";
	private static final String PASSWORD_APLICACION = "bqas bwag dasl kcjj";
	private static final String HOST = "pop.gmail.com";
	private ArrayList<Correo> correos = new ArrayList<>();
	private VistaGeneralCorreo vistaGeneral;

	
	public ControladorCorreos() {
		configurarVistaGeneral();
		
		new Thread(() -> {
	        System.out.println("Conectando con Gmail...");
	        correos = obtenerCorreos();
	        
	        // 3. Una vez descargados, actualizamos la tabla en el hilo de Swing
	        javax.swing.SwingUtilities.invokeLater(() -> {
	            vistaGeneral.cargarCorreos(correos);
	            // Re-asignamos el oyente porque 'correos' ahora tiene datos
	            // O mejor: pasa la lista 'correos' al oyente al principio y solo llénala aquí.
	            vistaGeneral.getEmailTabla().addMouseListener(new OyenteTabla(vistaGeneral.getEmailTabla(), correos));
	            System.out.println("Correos cargados.");
	        });
	    }).start();
		
		
		
	}




	private ArrayList<Correo> obtenerCorreos() {
		ReceptorCorreo receptor = new ReceptorCorreo();
		ArrayList<Correo> listaCorreos = receptor.recibirCorreosPOP3(HOST, "recent:" + CORREO, PASSWORD_APLICACION);		
		
		return listaCorreos;
	}


	private void configurarVistaGeneral() {
		vistaGeneral = new VistaGeneralCorreo(CORREO);
		vistaGeneral.setVisible(true);
		vistaGeneral.getBotonEnviarCorreo().addActionListener(new OyenteBotonEnviar(vistaGeneral.getCorreo()));
		vistaGeneral.getEmailTabla().addMouseListener(new OyenteTabla(vistaGeneral.getEmailTabla(), correos));
	}




	public static String getPasswordAplicacion() {
		return PASSWORD_APLICACION;
	}
	
	
}
