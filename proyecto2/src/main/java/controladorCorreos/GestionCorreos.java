package controladorCorreos;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.HeaderTerm;
import javax.mail.search.SearchTerm;
import modelo.Correo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class GestionCorreos {

    // --- MÃTODOS POP3 (RECIBIR Y ELIMINAR) ---

    public ArrayList<Correo> recibirCorreosPOP3(String host, String user, String password) {
        ArrayList<Correo> listaCorreos = new ArrayList<>();

        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            
            Session emailSession = Session.getDefaultInstance(properties);

            // Store pop3s
            Store store = emailSession.getStore("pop3s");
            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY); 

            Message[] messages = emailFolder.getMessages();
            System.out.println("Total de mensajes (POP3): " + messages.length);

            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];
                
                String remitente = message.getFrom()[0].toString();
                String asunto = message.getSubject();
                java.util.Date fecha = message.getSentDate();
                String cuerpo = getTextFromMessage(message);
                
                // --- CAMBIO: OBTENER MESSAGE-ID ---
                String messageId = "";
                String[] headers = message.getHeader("Message-ID");
                if (headers != null && headers.length > 0) {
                    messageId = headers[0];
                }
                
                // Pasamos el ID al constructor
                listaCorreos.add(new Correo(remitente, asunto, fecha, cuerpo, messageId));
            }

            emailFolder.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCorreos;
    }
    
    public void eliminarCorreoPOP3(String host, String user, String password, int indice) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");

        Session session = Session.getInstance(properties);
        Store store = session.getStore("pop3s");
        store.connect(host, user, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        Message mensaje = inbox.getMessage(indice + 1);
        mensaje.setFlag(Flags.Flag.DELETED, true);

        inbox.close(true);
        store.close();
    }
    
    // --- MÃTODO IMAP (SOLO PARA MARCAR LEÃDO) ---

    public void marcarLeidoIMAP(String imapHost, String user, String password, String messageId) {
        try {
            Properties properties = new Properties();
            // ConfiguraciÃ³n especÃ­fica para IMAP SSL
            properties.put("mail.store.protocol", "imaps"); 
            properties.put("mail.imap.host", imapHost);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");

            Session session = Session.getInstance(properties);
            
            // Usamos 'imaps' en lugar de 'pop3s'
            Store store = session.getStore("imaps");
            store.connect(imapHost, user, password);

            Folder inbox = store.getFolder("INBOX");
            // READ_WRITE es necesario para cambiar Flags
            inbox.open(Folder.READ_WRITE);

            if (messageId != null && !messageId.isEmpty()) {
                // Creamos un termino de busqueda para el Header "Message-ID"
                SearchTerm searchTerm = new HeaderTerm("Message-ID", messageId);
                Message[] foundMessages = inbox.search(searchTerm);

                if (foundMessages.length > 0) {
                    // Si lo encontramos, marcamos el primero (debera ser unico)
                    Message mensaje = foundMessages[0];
                    mensaje.setFlag(Flags.Flag.SEEN, true);
                    System.out.println("Correo marcado como LEÃDO (ID: " + messageId + ")");
                } else {
                    System.out.println("No se encontrÃ³ el mensaje con ese ID en IMAP.");
                }
            }

            // Cerramos guardando cambios
            inbox.close(true);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al marcar como leÃ­do vÃ­a IMAP: " + e.getMessage());
        }
    }
    
    public void marcarNoLeidoIMAP(String imapHost, String user, String password, String messageId) {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", imapHost);
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.ssl.enable", "true");

            Session session = Session.getInstance(properties);

            Store store = session.getStore("imaps");
            store.connect(imapHost, user, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            if (messageId != null && !messageId.isEmpty()) {

                SearchTerm searchTerm = new HeaderTerm("Message-ID", messageId);
                Message[] foundMessages = inbox.search(searchTerm);

                if (foundMessages.length > 0) {
                    Message mensaje = foundMessages[0];

            
                    inbox.setFlags(
                        new Message[]{ mensaje },
                        new Flags(Flags.Flag.SEEN),
                        false
                    );

                    System.out.println("Correo marcado como NO LEÍDO (ID: " + messageId + ")");
                } else {
                    System.out.println("No se encontró el mensaje con ese ID en IMAP.");
                }
            }

            inbox.close(true);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al marcar como NO leído vía IMAP: " + e.getMessage());
        }
    }

    // --- UTILIDADES ---

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
                break; 
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append("\n").append(html); 
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result.append(getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}