package CIserver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;

/**
Used for creating a HTML document for the web interface so that you can traverse
the log file system and display the contents of the log files.
*/
public class Interface{
    public static final String DOMAIN = "http://130.237.227.78:8018/";

    /**
    Returns the contents of a file in a string.
    @param file - A file.
    @return a string that contains the contents of the file, or if there is a
            FileNotFoundException, NullPointerException or another exception,
            return null.
    */
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
    Used for reading log files and creating the HTML that is to be rendered
    on the web interface. If the current path is a directory, output all of the
    links to all the subdirectories and files, as well as a link for getting back
    to the previous directory. If the current path is a file, output its contents.
    @param path - the (relative) path of the current directory.
    @return a string containing an HTML document that is to be put on the web
            interface, or if there were any errors null is returned.
    */
    public static String get(String path){
        // The HTML template file that is to be filled with a body and a title.
        String template = readFile(new File("src/main/java/template.html"));
        // Template file wasn't found.
        if(template == null){
            return null;
        }
        try{
            File file = new File(path);
            // If the current file or directory exists
            if(file.exists()){
                String title = file.getName();
                String body = "";
                // If it's a file
                if(file.isFile()){
                    body = readFile(file);
                }
                // If it's a directory.
                else if(file.isDirectory()){
                    String[] parts = path.split("/");
                    String newPath = "";
                    for(int i=0; i<parts.length - 1; i++){
                        newPath += parts[i] + "/";
                    }
                    body += "<a href=" + DOMAIN + newPath + ">..</a><br>";
                    for(File entry : file.listFiles()){
                        body += "<a href=" + DOMAIN + path + entry.getName() + ">" + entry.getName() + "</a><br>";
                    }
                }
                // Add title and body to HTML document.
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
