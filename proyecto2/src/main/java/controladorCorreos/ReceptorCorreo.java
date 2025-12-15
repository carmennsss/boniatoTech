package controladorCorreos;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;

import modelo.Correo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ReceptorCorreo {

    public ArrayList<Correo> recibirCorreosPOP3(String host, String user, String password) {
        ArrayList<Correo> listaCorreos = new ArrayList<>();

        try {
            // 1. Configurar Propiedades para POP3S (POP3 Seguro con SSL)
            Properties properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            
            // 2. Obtener Sesión
            Session emailSession = Session.getDefaultInstance(properties);

            // 3. Crear el Store y conectar
            // Usamos "pop3s" para SSL. Si tu servidor no usa SSL (raro hoy en día), usa "pop3"
            Store store = emailSession.getStore("pop3s");
            store.connect(host, user, password);

            // 4. Abrir la carpeta INBOX
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY); // READ_ONLY evita borrar correos accidentalmente

            // 5. Obtener mensajes
            Message[] messages = emailFolder.getMessages();
            System.out.println("Total de mensajes encontrados: " + messages.length);

            // 6. Recorrer mensajes y extraer datos
            // NOTA: En producción, limita este bucle (ej. últimos 10) para no saturar la memoria
            for (Message message : messages) {
                
                String remitente = message.getFrom()[0].toString();
                String asunto = message.getSubject();
                java.util.Date fecha = message.getSentDate();
                String cuerpo = getTextFromMessage(message); // Método auxiliar mágico

                // Añadir al ArrayList
                listaCorreos.add(new Correo(remitente, asunto, fecha, cuerpo));
            }

            // 7. Cerrar conexiones
            emailFolder.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCorreos;
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
                break; // Si encontramos texto plano, solemos preferirlo sobre el HTML
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                // Opcional: Usar Jsoup para limpiar tags HTML si solo quieres texto
                result.append("\n").append(html); 
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result.append(getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}