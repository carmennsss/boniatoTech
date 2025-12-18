package controladorCorreos;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.HeaderTerm;

import modelo.Correo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GestionCorreos {

	private Map<String, Boolean> estadosLocales = new HashMap<>();

	public ArrayList<Correo> recibirCorreosPOP3(String pop3Host, String imapHost, String user, String password) {

		ArrayList<Correo> listaCorreos = new ArrayList<>();

		try {
			Properties props = new Properties();
			props.put("mail.pop3.host", pop3Host);
			props.put("mail.pop3.port", "995");
			props.put("mail.pop3.ssl.enable", "true");

			Session session = Session.getInstance(props);
			Store store = session.getStore("pop3s");
			store.connect(pop3Host, user, password);

			System.out.println("[POP3] Conexion establecida");

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			Message[] messages = inbox.getMessages();
			System.out.println("[POP3] Total mensajes: " + messages.length);

			String usuarioIMAP = user.startsWith("recent:") ? user.substring(7) : user;

			Map<String, Boolean> estadosIMAP = obtenerEstadosIMAP(imapHost, usuarioIMAP, password);

			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];

				String remitente = message.getFrom()[0].toString();
				String emailLimpio = user.replace("recent:", "");
				if (remitente.toLowerCase().contains(emailLimpio.toLowerCase()))
					continue;
				String asunto = message.getSubject();
				java.util.Date fecha = message.getSentDate();
				String cuerpo = getTextFromMessage(message);

				String messageId = "";
				String[] headers = message.getHeader("Message-ID");
				if (headers != null && headers.length > 0 && headers[0] != null) {
					messageId = headers[0];
				}

				Correo correo = new Correo(remitente, asunto, fecha, cuerpo, messageId);

				Boolean estadoIMAP = estadosIMAP.get(messageId);

				if (estadoIMAP != null) {
					correo.setLeido(estadoIMAP);
					estadosLocales.put(messageId, estadoIMAP);

				} else {
					Boolean estadoLocal = estadosLocales.get(messageId);
					if (estadoLocal != null) {
						correo.setLeido(estadoLocal);

					} else {

					}
				}

				listaCorreos.add(correo);
			}

			inbox.close(false);
			store.close();

			System.out.println("[POP3] Correos cargados correctamente");

		} catch (Exception e) {
			System.err.println("[ERROR POP3]: " + e.getMessage());
		}

		return listaCorreos;
	}

	public Map<String, Boolean> obtenerEstadosIMAP(String imapHost, String user, String password) {

		Map<String, Boolean> estados = new HashMap<>();

		if (imapHost == null || imapHost.isEmpty()) {
			System.out.println("[IMAP] Host IMAP invalido");
			return estados;
		}

		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");
			props.put("mail.imaps.host", imapHost);
			props.put("mail.imaps.port", "993");
			props.put("mail.imaps.ssl.enable", "true");

			Session session = Session.getInstance(props);
			Store store = session.getStore("imaps");
			store.connect(imapHost, user, password);

			System.out.println("[IMAP] Conexion establecida");

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			Message[] messages = inbox.getMessages();
			System.out.println("[IMAP] Total mensajes: " + messages.length);

			for (Message msg : messages) {
				String[] headers = msg.getHeader("Message-ID");
				if (headers != null && headers.length > 0 && headers[0] != null && !headers[0].isEmpty()) {
					boolean leido = msg.isSet(Flags.Flag.SEEN);
					estados.put(headers[0], leido);
				}
			}

			inbox.close(false);
			store.close();

		} catch (Exception e) {
			System.err.println("[ERROR IMAP]");
			e.printStackTrace();
		}

		return estados;
	}

	public void eliminarCorreoPOP3(String pop3Host, String user, String password, Correo correoABorrar) throws Exception {
		
		Properties props = new Properties();
	    props.put("mail.pop3.host", pop3Host);
	    props.put("mail.pop3.port", "995");
	    props.put("mail.pop3.ssl.enable", "true");

	    Session session = Session.getInstance(props);
	    Store store = session.getStore("pop3s");
	    store.connect(pop3Host, user, password);

	    Folder inbox = store.getFolder("INBOX");
	    inbox.open(Folder.READ_WRITE);

	    Message[] mensajes = inbox.getMessages();
	    boolean encontrado = false;

	    for (int i = 0; i < mensajes.length; i++) {
	        String[] headers = mensajes[i].getHeader("Message-ID");
	        if (headers != null && headers.length > 0) {
	            if (headers[0].equals(correoABorrar.getMessageId())) {
	                mensajes[i].setFlag(Flags.Flag.DELETED, true);
	                encontrado = true;
	                System.out.println("[POP3] Mensaje identificado y marcado para borrar.");
	                break;
	            }
	        }
	    }

	    if (!encontrado) {
	        System.out.println("[POP3] No se encontró el mensaje en el servidor para borrar.");
	    }

	    // Al cerrar con 'true', se ejecuta el EXPUNGE (borrado físico)
	    inbox.close(true); 
	    store.close();

		System.out.println("[POP3] Correo eliminado");
	}

	public void marcarLeidoIMAP(String imapHost, String user, String password, String messageId) {

		System.out.println("[IMAP] Marcar LEIDO -> " + messageId);

		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");
			props.put("mail.imaps.host", imapHost);
			props.put("mail.imaps.port", "993");
			props.put("mail.imaps.ssl.enable", "true");

			Session session = Session.getInstance(props);
			Store store = session.getStore("imaps");
			store.connect(imapHost, user, password);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			Message[] encontrados = inbox.search(new HeaderTerm("Message-ID", messageId));
			if (encontrados.length > 0) {
				encontrados[0].setFlag(Flags.Flag.SEEN, true);
				estadosLocales.put(messageId, true);
			}

			inbox.close(true);
			store.close();

		} catch (Exception e) {
			System.err.println("[ERROR MARCAR LEIDO]");
			e.printStackTrace();
		}
	}

	public void marcarNoLeidoIMAP(String imapHost, String user, String password, String messageId) {

		System.out.println("[IMAP] Marcar NO LEIDO -> " + messageId);

		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");
			props.put("mail.imaps.host", imapHost);
			props.put("mail.imaps.port", "993");
			props.put("mail.imaps.ssl.enable", "true");

			Session session = Session.getInstance(props);
			Store store = session.getStore("imaps");
			store.connect(imapHost, user, password);

			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			Message[] encontrados = inbox.search(new HeaderTerm("Message-ID", messageId));
			if (encontrados.length > 0) {
				inbox.setFlags(encontrados, new Flags(Flags.Flag.SEEN), false);
				estadosLocales.put(messageId, false);
			}

			inbox.close(true);
			store.close();

		} catch (Exception e) {
			System.err.println("[ERROR MARCAR NO LEIDO]");
			e.printStackTrace();
		}
	}

	private String getTextFromMessage(Message message) throws MessagingException, IOException {

		if (message.isMimeType("text/plain")) {
			return message.getContent().toString();
		}

		if (message.isMimeType("multipart/*")) {
			return getTextFromMimeMultipart((MimeMultipart) message.getContent());
		}

		return "";
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < mimeMultipart.getCount(); i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);

			if (bodyPart.isMimeType("text/plain")) {
				result.append(bodyPart.getContent());
				break;
			}

			if (bodyPart.isMimeType("text/html")) {
				result.append(bodyPart.getContent());
			}

			if (bodyPart.getContent() instanceof MimeMultipart) {
				result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
			}
		}

		return result.toString();
	}
}