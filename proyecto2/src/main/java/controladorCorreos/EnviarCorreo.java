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

/**
 * Clase utilitaria para el envío de correos electrónicos vía SMTP.
 * Configurada para usar el servidor SMTP de Gmail.
 */
public class EnviarCorreo {

    /**
     * Envía un correo electrónico con soporte para archivos adjuntos.
     *
     * @param miCorreo           Dirección de correo del remitente.
     * @param asunto             Asunto del correo.
     * @param mensaje            Cuerpo del mensaje.
     * @param receptor           Dirección de correo del destinatario.
     * @param passwordAplicacion Contraseña de aplicación del remitente.
     * @param archivos           Lista de archivos a adjuntar (puede ser null).
     * @throws Exception Si ocurre un error durante la autenticación o el envío.
     */
    public static void enviarCorreo(String miCorreo, String asunto, String mensaje, String receptor,
            String passwordAplicacion, List<File> archivos) throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        miCorreo,
                        passwordAplicacion // Contrasenna de aplicacion correcta
                );
            }
        });

        // Crear mensaje
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(miCorreo));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
        msg.setSubject(asunto);

        Multipart multipart = new MimeMultipart();

        MimeBodyPart textoParte = new MimeBodyPart();
        textoParte.setText(mensaje);
        multipart.addBodyPart(textoParte);

        if (archivos != null) {
            for (File archivo : archivos) {
                MimeBodyPart adjuntoParte = new MimeBodyPart();
                adjuntoParte.attachFile(archivo);
                multipart.addBodyPart(adjuntoParte);
            }
        }

        msg.setContent(multipart);
        Transport.send(msg);

    }
}
