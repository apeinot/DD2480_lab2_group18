package CIserver;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.BufferedReader;

import org.json.*;

public class RequestParser{
     String after = null;
     String statuses_url = null;
     String clone_url = null;
     String name = null;
     int statusCode;

    /**
    Parse request JSON and get the correct fields
    @param request The http(s) POST request
    @return The status code. 1 for success, 0 for connection/response issues and -1 for parsing issues.
    */
    public int parse(HttpServletRequest request) throws IOException{
        statusCode = 1;
        try{
            JSONObject request_data = requestToJSON(request); // Get request data as JSON object
            getFields(request_data);
        }catch(Error e){
            statusCode = 0;
            throw e;
        }catch(JSONException je){ //new JSONObject() could not parse the request.
            System.out.println("Bad data from webhook, all fields set to null: \n " + je.toString());
        }catch(IOException ioe){ //BufferedReader failsed somehow
            System.out.println("Something went wrong with POST request reader, all fields set to null: \n " + ioe.toString());
        }
        return statusCode;
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
    public  void getFields(JSONObject request_data){
        JSONObject repository = null;
        try {
            repository = (JSONObject) request_data.get("repository"); //Nested JSON
        } catch(JSONException e) {
            System.out.println("Warning, field: 'repository' not found, children will be set to null");
        }
        after = getField("after", request_data);
        statuses_url = getField("statuses_url", repository);
        clone_url = getField("clone_url", repository);
        name = getField("name", repository);
    }

    private String getField(String field, JSONObject request_data){
        try{
            return request_data.get(field).toString();
        }
        catch(Error e){
            statusCode = -1;
            throw e;
        }
        catch(JSONException e){
            System.out.println("Warning '" + field + "' not found, set to null: \n " + e.toString());
        }
        catch(NullPointerException e){
            System.out.println("Warning parent to '" + field + "' not found, set to null: \n " + e.toString());
        }
        return null;
    }

    /**
    @return The 'after' field of the request
    */
    public  String getAfterField(){
        return after;
    }

    /**
    @return The 'statuses_url' field of the request
    */
    public  String getStatuses_urlField(){
        return statuses_url;
    }

    /**
    @return The 'clone_url' field of the request
    */
    public  String getClone_urlField(){
        return clone_url;
    }

    /**
    @return The 'name' field of the request
    */
    public  String getNameField(){
        return name;
    }
}
