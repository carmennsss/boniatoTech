package controladorCorreos;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;

import modelo.Correo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

public class GestionPOP3 {

    public ArrayList<Correo> recibirCorreosPOP3(String host, String user, String password) {
        ArrayList<Correo> listaCorreos = new ArrayList<>();

        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            
            // Obtener Sesi�n
            Session emailSession = Session.getDefaultInstance(properties);

            // Crear el Store y conectar
            Store store = emailSession.getStore("pop3s");
            store.connect(host, user, password);

            // Abrir la carpeta INBOX
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY); // READ_ONLY evita borrar correos accidentalmente

            // Obtener mensajes
            Message[] messages = emailFolder.getMessages();
            System.out.println("Total de mensajes encontrados: " + messages.length);

            // Recorrer mensajes y extraer datos
            for (Message message : messages) {
                
                String remitente = message.getFrom()[0].toString();
                String asunto = message.getSubject();
                java.util.Date fecha = message.getSentDate();
                String cuerpo = getTextFromMessage(message); 

                // A�adir al ArrayList
                listaCorreos.add(new Correo(remitente, asunto, fecha, cuerpo));
            }

            // Cerrar conexiones
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
    
    public void marcarLeidoPOP3(String host, String user, String password, int indice) {
        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");

            Session session = Session.getInstance(properties);
            Store store = session.getStore("pop3s");
            store.connect(host, user, password);

            Folder inbox = store.getFolder("INBOX");
            // Abrir en READ_WRITE para poder modificar estados
            inbox.open(Folder.READ_WRITE);

            // Obtenemos el mensaje por su número (1-based index)
            Message mensaje = inbox.getMessage(indice + 1);
            
            // Aplicamos el flag SEEN
            mensaje.setFlag(Flags.Flag.SEEN, true);

            // Al cerrar con true, se intentan persistir los cambios en la carpeta
            inbox.close(true);
            store.close();
            System.out.println("Flag SEEN aplicado al mensaje " + (indice + 1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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