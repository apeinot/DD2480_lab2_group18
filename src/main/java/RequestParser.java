import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.BufferedReader;

import org.json.*;

public class RequestParser{
    String after = null;
    String statuses_url = null;

    /**
    Parse request JSON and get the correct fields
    @param request The http(s) POST request
    */
    public void parse(HttpServletRequest request) throws IOException{
        try{
            JSONObject request_data = requestToJSON(request); // Get request data as JSON object
            getFields(request_data);
        }catch(JSONException je){ //new JSONObject() could not parse the request.
            System.out.println("Bad data from webhook, 'after' & 'statuses_url' set to null: \n " + je.toString());
        }catch(IOException ioe){ //BufferedReader failsed somehow
            System.out.println("Something went wrong with POST request reader, 'after' & 'statuses_url' set to null: \n " + ioe.toString());
        }
    }

    /**
    This method converts a post requests data to a JSON object.
    @param request The post request from the github webhook
    @return The JSON object
    */
    public JSONObject requestToJSON(HttpServletRequest request) throws IOException, JSONException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();

        String str;
        while( (str = br.readLine()) != null ){ //Read request and build string
            sb.append(str);
        }
        return new JSONObject(sb.toString());//Parse built string as JSON
    }

    /**
    This method reads the correct fields out of a JSON.
    @param request_data the JSON with the POST request data.
    */
    public void getFields(JSONObject request_data){
        try{
            after = request_data.get("after").toString();
        }catch(JSONException e){ //after field missing
            System.out.println("Warning, 'after' not found, set to null: \n " + e.toString());
        }
        try{
            JSONObject repository = (JSONObject) request_data.get("repository"); //Nested JSON
            statuses_url = repository.get("statuses_url").toString();
        }catch(JSONException e){ //statuses_url field missing
            System.out.println("Warning 'repository.statuses_url' not found, set to null: \n " + e.toString());
        }
    }

    /**
    @return The 'after' field of the request
    */
    public String getAfterField(){
        return after;
    }

    /**
    @return The 'statuses_url' field of the request
    */
    public String getStatuses_urlField(){
        return statuses_url;
    }
}
