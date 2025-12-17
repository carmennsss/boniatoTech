package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;

import modelo.Correo;
import vista.VistaCorreoBase;

public class OyenteExportarCorreo implements ActionListener {

	private Correo correo;
	private VistaCorreoBase vista;
	private String correoUsuario;
	
	public OyenteExportarCorreo(Correo correo, VistaCorreoBase vistaLectura, String correoUsuario) {
		this.correo = correo;
		this.vista = vistaLectura;
		this.correoUsuario = correoUsuario;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nombreSugerido = correo.getAsunto().replaceAll("[^a-zA-Z0-9]", "_");
	    File archivo = vista.exportarCorreo(nombreSugerido);

	    if (archivo != null) {
	        try {
	            // 1. Crear una sesión de correo vacía
	            javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());
	            
	            // 2. Crear el mensaje MIME
	            javax.mail.internet.MimeMessage mensajeEml = new javax.mail.internet.MimeMessage(session);
	            
	            // 3. Rellenar cabeceras estándar
	            mensajeEml.setFrom(new javax.mail.internet.InternetAddress(correo.getRemitente()));
	            mensajeEml.setRecipient(javax.mail.Message.RecipientType.TO, 
	                new javax.mail.internet.InternetAddress(correoUsuario)); // O el dato que tengas
	            mensajeEml.setSubject(correo.getAsunto());
	            mensajeEml.setSentDate(correo.getFecha() != null ? correo.getFecha() : new java.util.Date());
	            
	            mensajeEml.setText(correo.getCuerpo());

	            try (java.io.FileOutputStream os = new java.io.FileOutputStream(archivo)) {
	                mensajeEml.writeTo(os);
	            }

	            vista.mostrarMensaje("Correo exportado correctamente como .eml", false);

	        } catch (Exception ex) {
	            ex.printStackTrace();
	            vista.mostrarMensaje("Error al generar el formato EML: " + ex.getMessage(), true);
	        }
	    }
	}

}
