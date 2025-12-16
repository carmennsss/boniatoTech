package controladorCorreos;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import modelo.Correo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class GestionPOP3 {

    // --- MÉTODOS POP3 (RECIBIR Y ELIMINAR) ---

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

            for (Message message : messages) {
                String remitente = message.getFrom()[0].toString();
                String asunto = message.getSubject();
                java.util.Date fecha = message.getSentDate();
                String cuerpo = getTextFromMessage(message); 

                listaCorreos.add(new Correo(remitente, asunto, fecha, cuerpo));
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
    
    // --- MÉTODO IMAP (SOLO PARA MARCAR LEÍDO) ---

    /**
     * IMPORTANTE: El parametro 'host' aquí debe ser el servidor IMAP 
     * (ej: imap.gmail.com), NO el servidor POP3.
     */
    public void marcarLeidoIMAP(String imapHost, String user, String password, int indice) {
        try {
            Properties properties = new Properties();
            // Configuración específica para IMAP SSL
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

            // Obtenemos el mensaje. 
            // NOTA: Asegúrate de que el índice IMAP coincida con el POP3. 
            // Si el buzón cambia mucho, los índices podrían desincronizarse.
            Message mensaje = inbox.getMessage(indice + 1);
            
            // Aplicamos el flag SEEN (Leído)
            // IMAP sí permite sincronizar esto con el servidor permanentemente
            mensaje.setFlag(Flags.Flag.SEEN, true);

            // Cerramos guardando cambios
            inbox.close(true);
            store.close();
            System.out.println("Correo marcado como LEÍDO usando IMAP (índice " + (indice + 1) + ")");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al marcar como leído vía IMAP: " + e.getMessage());
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