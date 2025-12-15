package controladorCorreos;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.Flags;

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
		
		cargarCorreos();
		
		asignarBotonRecargar();
		
	}








	protected void cargarCorreos() {
		new Thread(() -> {
			vistaGeneral.getBotonRecargar().setEnabled(false);

	        System.out.println("Conectando con Gmail...");
	        correos.clear();
	        correos.addAll(obtenerCorreos());
	        
	        // Una vez descargados, actualizamos la tabla en el hilo de Swing
	        javax.swing.SwingUtilities.invokeLater(() -> {
	            vistaGeneral.cargarCorreos(correos);

	            System.out.println("Correos cargados.");
				vistaGeneral.getBotonRecargar().setEnabled(true);

	        });
	    }).start();
	}




	private void asignarBotonRecargar() {
		vistaGeneral.getBotonRecargar().addActionListener(new OyenteRecargar(vistaGeneral, correos, this));
	}




	public ArrayList<Correo> obtenerCorreos() {
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


	public static void eliminarCorreo(Correo correo) throws Exception {
		correo.getMessage().setFlag(Flags.Flag.DELETED, true);
		
	}
	
	public static void marcarCorreoLeido(Correo correo) throws Exception {
		correo.getMessage().setFlag(Flags.Flag.SEEN, true);
	}
	
	


	public static String getPasswordAplicacion() {
		return PASSWORD_APLICACION;
	}
	
	
}
