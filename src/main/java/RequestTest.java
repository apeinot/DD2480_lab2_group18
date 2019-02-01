package CIserver;

import java.net.*;
import org.json.*;

public class RequestTest{

    public static void doRequest(){
        String url_strig = "https://api.github.com/repos/francislaus/FoxCrossOS/statuses/";
        String sha_string = "058d93cb1ff394528c38bc11cfe72cfa06a153cb";
        HttpURLConnection con = null;
        try{
            URL url = new URL(url_strig + sha_string);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
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

    public static void doJSON(){
        JSONObject jobj = new JSONObject();
        jobj.put("franz", "alex");
        System.out.println(jobj.toString());
    }

}