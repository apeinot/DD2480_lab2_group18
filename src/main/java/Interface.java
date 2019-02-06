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
    public static String get(String path){
        String template = readFile(new File("src/main/java/template.html"));
        if(template == null){
            return null;
        }
        try{
            File file = new File(path);
            if(file.exists()){
                String title = "";
                String body = "";
                if(file.isFile()){
                    title = file.getName();
                    body = readFile(file);
                    template = template.replace("$title", title);
                    template = template.replace("$body", body);
                }else if(file.isDirectory()){

                }
            }else{
                return null;
            }
        }catch(Exception e){
            return null;
        }
        return template;
    }
}
