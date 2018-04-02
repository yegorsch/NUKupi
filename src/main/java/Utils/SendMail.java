package Utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// TODO: Check if it works everywhere
public class SendMail {

    public SendMail() {}

    public static void send(String recipientEmail, String tempPassword, boolean newUser) {

        final String username = "serviceofnukupi@gmail.com";
        final String password = "!NuKup1!";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            if(newUser) {
                message.setSubject("Welcome to NUKupi!");
                message.setText("Use this temporary password to login to our service " + tempPassword);
            } else {
                message.setSubject("Password reset");
                message.setText("Your password is " + tempPassword);
            }

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}