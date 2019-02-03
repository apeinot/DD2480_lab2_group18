package CIserver;

import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.json.*;

public class ResponderGithub{

    /**
    doRequest does a HTTP POST request to the given URL and includes the specified
    data in the JSON format as a payload
    @param url_string the URL of the status to be set
    @param sha_string the sha sum of the commit to be set
    @param request_params the parameters in string form as requested by doJSON
    @return 0 if the request was successful, otherwise 1
    */
    public static int doRequest(String url_string, String sha_string, String request_params[]){
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
            byte[] output = doJSON(request_params).toString().getBytes(StandardCharsets.UTF_8);
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
            e.printStackTrace();
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
    doJSON converts an array of strings into the JSON format required by github
    @param request_params an array with the arguments in form of strings in the
                          following order: state, target_url, description, context
    @return the json object containing the information
    */
    public static JSONObject doJSON(String request_params[]){
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

}