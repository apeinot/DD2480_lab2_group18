package CIserver;

// imports for setting github statuses
import java.nio.charset.StandardCharsets;
import org.json.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
// imports for sending e-mails
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Responder{

    /**
    git_status does a HTTP POST request to the given URL and includes the specified
    data in the JSON format as a payload
    @param url_string the URL of the status to be set
    @param sha_string the sha sum of the commit to be set
    @param request_params the parameters in string form as requested by doJSON
    @return 0 if the request was successful, otherwise 1
    */
    public static int git_status(String url_string, String sha_string, String request_params[]){
        // hard-coded token of the CI server account
        String token_string = "?access_token=6c0a05b0fe8126566";
        String token_string_2 = "287afec0de70ae841fd8856";
        HttpURLConnection con = null;
        OutputStream os = null;
        try{
            URL url = new URL(url_string + sha_string + token_string + token_string_2);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setUseCaches(false);
            byte[] output = create_json(request_params).toString().getBytes(StandardCharsets.UTF_8);
            con.setFixedLengthStreamingMode(output.length);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.connect();
            os = con.getOutputStream();
            os.write(output);
            // response code 201 means 'created', all other codes indicate an error
            int res_code = con.getResponseCode();
            if(res_code != 201){
                return 1;
            }
            con.disconnect();
        }
        catch(Exception e){
            //e.printStackTrace();
            return 1;
        }
        finally{
            if(con != null){
                con.disconnect();
            }
        }
        return 0;
    }

    /**
    create_json converts an array of strings into the JSON format required by github
    @param request_params an array with the arguments in form of strings in the
                          following order: state, target_url, description, context
    @return the json object containing the information
    */
    public static JSONObject create_json(String request_params[]){
        // use the org.json library
        if(request_params == null){
            return null;
        }
        JSONObject jobj = new JSONObject();
        jobj.put("state", request_params[0]);
        jobj.put("target_url", request_params[1]);
        jobj.put("description", request_params[2]);
        jobj.put("context", request_params[3]);
        return jobj;
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
