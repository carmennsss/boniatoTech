package controladorCorreos;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarCorreo {
	

    public static void enviarCorreo(String miCorreo, String asunto, String mensaje, String receptor) throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticación CORRECTA
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        miCorreo,
                        ControladorCorreos.getPasswordAplicacion()  // Contraseña de aplicación correcta
                );
            }
        });

        // Crear mensaje
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(miCorreo));
        msg.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receptor)
        );
        msg.setSubject(asunto);
        msg.setText(mensaje);

        // Enviar
        Transport.send(msg);

        System.out.println("Correo enviado correctamente.");
    }
}
