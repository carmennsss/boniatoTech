package controladorCorreos;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Pruebas {

    public static void main(String[] args) throws Exception {

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
                        "miguelroblesp.sanjosemlg@fundacionloyola.net",
                        "bzzk xbqc jzko nogk"  // Contraseña de aplicación correcta
                );
            }
        });

        // Crear mensaje
        Message mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress("miguelroblesp.sanjosemlg@fundacionloyola.net"));
        mensaje.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse("pablo.pruebas.mail@gmail.com")
        );
        mensaje.setSubject("Prueba desde Java");
        mensaje.setText("Hola, este mensaje se envió usando JavaMail.");

        // Enviar
        Transport.send(mensaje);

        System.out.println("Correo enviado correctamente.");
    }
}
