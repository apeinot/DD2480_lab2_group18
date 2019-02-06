package CIserver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;

/**

*/
public class Interface{
    public static String readFile(File file){
        try{
            String str = "";
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null){
                str += line;
            }
            return str;
        }catch(Exception e){
            return null;
        }
    }

    /**
    Måste skrivas.
    @param request -
    @return 0 if all went well, anything else means something went wrong.
    */
    public static String get(String path, String url){
        String template = readFile(new File("src/main/java/template.html"));
        if(template == null){
            return null;
        }
        try{
            File file = new File(path);
            if(file.exists()){
                String title = file.getName();
                String body = "";
                if(file.isFile()){
                    body = readFile(file);
                }else if(file.isDirectory()){
                    String[] parts = path.split("/");
                    String newPath = "";
                    for(int i=0; i<parts.length - 1; i++){
                        newPath += parts[i] + "/";
                    }
                    body += "<a href=" + url + newPath + ">..</a><br>";
                    for(File entry : file.listFiles()){
                        body += "<a href=" + url + path + entry.getName() + ">" + entry.getName() + "</a><br>";
                    }
                }
                template = template.replace("$title", title);
                template = template.replace("$body", body);
            }else{
                return null;
            }
        }catch(Exception e){
            return null;
        }
        return template;
    }
}
