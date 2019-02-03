package CIserver;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Responder{
    public static void git(){

    }

    /**
    Sends an e-mail from our mailbot.
    @param to - E-mail address of recipient.
    @param subject - The title (subject) of the e-mail.
    @param text - The e-mail content.
    @return - Returns 0 if an e-mail was sent successfully, 1 if there was an
              authentication error or some other error when creating the e-mail.
    */
    public static int send(final String to, final String subject, final String text){
        return send("mailbot8080@gmail.com", "bot12345", to, subject, text);
    }

    /**
    Sends an e-mail through Gmail's SMTP server. The sender needs to allow less
    secure apps to access their account.
    @param from - E-mail address of the sender.
    @param password - E-mail password of the sender.
    @param to - E-mail address of recipient.
    @param subject - The title (subject) of the e-mail.
    @param text - The e-mail content.
    @return - Returns 0 if an e-mail was sent successfully, 1 if there was an
              authentication error or some other error when creating the e-mail.
    */
    public static int send(final String from, final String password, final String to, final String subject, final String text){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        // Authenticating the mail session
        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(from, password);
            }
        });
        try{
            // Create e-mail.
            MimeMessage message = new MimeMessage(session);

            // Set sender
            message.setFrom(new InternetAddress(from));
            // Set recipient
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // The subject of the e-mail
            message.setSubject(subject);
            // The actual e-mail content
            message.setText(text);

            // Send e-mail
            Transport.send(message);
        }catch(MessagingException e){
            //e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
