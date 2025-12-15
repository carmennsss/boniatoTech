package controladorCorreos;

import java.util.Properties;
import java.util.Date;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Pruebas {

    private static final String EMAIL = "miguelroblesp.sanjosemlg@fundacionloyola.net";
    private static final String PASSWORD = "bzzk xbqc jzko nogk";

    public static void main(String[] args) throws Exception {

        Session session = crearSession();

        enviarCorreo(session);
        leerCorreos(session);
    }

    // -----------------------------
    // 1. Crear Session
    // -----------------------------
    private static Session crearSession() {

        Properties props = new Properties();

        // SMTP (envio)
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // IMAP (lectura)
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });
    }

    // -----------------------------
    // 2. Enviar correo
    // -----------------------------
    private static void enviarCorreo(Session session) throws Exception {

        Message mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(EMAIL));
        mensaje.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse("pablo.pruebas.mail@gmail.com")
        );
        mensaje.setSubject("Prueba desde Java");
        mensaje.setText("Hola, este mensaje se envió usando JavaMail.");

        Transport.send(mensaje);
        System.out.println("Correo enviado correctamente.");
    }

    // -----------------------------
    // 3. Leer correos
    // -----------------------------
    private static void leerCorreos(Session session) throws Exception {

        Store store = session.getStore("imap");
        store.connect(EMAIL, PASSWORD);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] mensajes = inbox.getMessages();

        for (Message m : mensajes) {

            String remitente = m.getFrom() != null ? m.getFrom()[0].toString() : "(desconocido)";
            String asunto = m.getSubject();
            Date fecha = m.getReceivedDate();
            boolean leido = m.isSet(Flags.Flag.SEEN);
            String cuerpo = obtenerCuerpo(m);

            System.out.println("De: " + remitente);
            System.out.println("Asunto: " + asunto);
            System.out.println("Fecha: " + fecha);
            System.out.println("Leido: " + leido);
            System.out.println("Cuerpo:");
            System.out.println(cuerpo);
            System.out.println("-------------------------------------------");
        }

        inbox.close(false);
        store.close();
    }

    // -----------------------------
    // 4. Obtener cuerpo del mensaje
    // -----------------------------
    private static String obtenerCuerpo(Message message) throws Exception {

        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        }

        if (message.isMimeType("text/html")) {
            return message.getContent().toString();
        }

        if (message.isMimeType("multipart/*")) {

            Multipart multipart = (Multipart) message.getContent();

            for (int i = 0; i < multipart.getCount(); i++) {

                BodyPart part = multipart.getBodyPart(i);

                if (part.isMimeType("text/html")) {
                    return part.getContent().toString();
                }

                if (part.isMimeType("text/plain")) {
                    return part.getContent().toString();
                }
            }
        }

        return "";
    }
}
