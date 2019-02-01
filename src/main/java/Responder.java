package CIserver;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Responder{
    public static void git(){

    }

    /**
    Sends an e-mail.
    @param from - E-mail address of sender.
    @param to - E-mail address of recipient.
    @param subject - The title (subject) of the e-mail.
    @param text - The e-mail content.
    */
    public static void email(String from, String to, String subject, String text){
        String host = "localhost";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);
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
            e.printStackTrace();
        }
    }
}
