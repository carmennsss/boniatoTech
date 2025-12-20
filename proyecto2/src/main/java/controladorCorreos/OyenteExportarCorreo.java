package controladorCorreos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;

import modelo.Correo;
import vista.VistaCorreoBase;

/**
 * Oyente del botón "Exportar" en la vista de lectura de correos.
 * Exporta el correo seleccionado a formato .eml.
 */
public class OyenteExportarCorreo implements ActionListener {

	/** Correo a exportar. */
	private Correo correo;

	/** Vista de lectura del correo. */
	private VistaCorreoBase vista;

	/** Dirección de correo del usuario. */
	private String correoUsuario;

	/**
	 * Constructor del oyente.
	 *
	 * @param correo        Correo a exportar.
	 * @param vistaLectura  Vista de lectura del correo.
	 * @param correoUsuario Dirección de correo del usuario.
	 */
	public OyenteExportarCorreo(Correo correo, VistaCorreoBase vistaLectura, String correoUsuario) {
		this.correo = correo;
		this.vista = vistaLectura;
		this.correoUsuario = correoUsuario;
	}

	/**
	 * Exporta el correo seleccionado a un archivo .eml en la ubicación elegida por
	 * el usuario.
	 *
	 * @param e Evento de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String nombreSugerido = correo.getAsunto().replaceAll("[^a-zA-Z0-9]", "_");
		File archivo = vista.exportarCorreo(nombreSugerido);

		if (archivo != null) {
			try {
				javax.mail.Session session = javax.mail.Session.getDefaultInstance(new java.util.Properties());

				javax.mail.internet.MimeMessage mensajeEml = new javax.mail.internet.MimeMessage(session);

				mensajeEml.setFrom(new javax.mail.internet.InternetAddress(correo.getRemitente()));
				mensajeEml.setRecipient(javax.mail.Message.RecipientType.TO,
						new javax.mail.internet.InternetAddress(correoUsuario));
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
