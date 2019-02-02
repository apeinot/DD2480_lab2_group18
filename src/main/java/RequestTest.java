package CIserver;

import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.json.*;

public class RequestTest{

    public static void doRequest(){
        String url_strig = "https://api.github.com/repos/francislaus/WordReminder/statuses/";
        String sha_string = "bbfe965fbbd961ab54412c5cdd8dbdb72fcf2e94";
        HttpURLConnection con = null;
        OutputStream os = null;
        String state = "success";
        String target_url = "https://127.0.0.1";
        String description = "We did it";
        String context = "KTH bois";
        try{
            URL url = new URL(url_strig + sha_string);
            System.out.println("URL: "+url.getPath());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setUseCaches(false);
            byte[] output = doJSON(state, target_url, description, context).toString().getBytes(StandardCharsets.UTF_8);
            con.setFixedLengthStreamingMode(output.length);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.connect();
            os = con.getOutputStream();
            os.write(output);
            int res_code = con.getResponseCode();
            String res_mess = con.getResponseMessage();
            System.out.println("Code: " + res_code);
            System.out.println("Message: " + res_mess);
            con.disconnect();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(con != null){
                con.disconnect();
            }
        }
    }

    private static JSONObject doJSON(String state, String target_url, String description, String context){
        JSONObject jobj = new JSONObject();
        jobj.put("state", state);
        jobj.put("target_url", target_url);
        jobj.put("description", description);
        jobj.put("context", context);
        System.out.println(jobj.toString());
        return jobj;
    }

}