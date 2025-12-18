package controladorCorreos;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Multipart;

public class EnviarCorreo {

    public static void enviarCorreo(String miCorreo, String asunto, String mensaje, String receptor,
            String passwordAplicacion, List<File> archivos) throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticaci�n CORRECTA
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        miCorreo,
                        passwordAplicacion // Contrase�a de aplicaci�n correcta
                );
            }
        });

        // Crear mensaje
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(miCorreo));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
        msg.setSubject(asunto);

        // --- LÓGICA PARA ADJUNTOS ---
        Multipart multipart = new MimeMultipart();

        // 1. Parte del texto
        MimeBodyPart textoParte = new MimeBodyPart();
        textoParte.setText(mensaje);
        multipart.addBodyPart(textoParte);

        // 2. Partes de archivos
        if (archivos != null) {
            for (File archivo : archivos) {
                MimeBodyPart adjuntoParte = new MimeBodyPart();
                adjuntoParte.attachFile(archivo);
                multipart.addBodyPart(adjuntoParte);
            }
        }

        msg.setContent(multipart);
        Transport.send(msg);
        ;
    }
}
