package CIserver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.File;
import java.net.URL;

/**

*/
public class Interface{
    public String html =   "<!DOCTYPE html>
                            <html>
                                <head>
                                    <title>$title</title>
                                    <meta http-equiv=""Content-Type"" content=""text/html; charset=UTF-8""/>
                                </head>
                                <body>
                                    $body
                                </body>
                            </html>";
    /**

    */
    public int get(HttpServletRequest request) throws IOException{
        URL url = new URL(request.getServletPath());
        File log = new File(url.getPath());
        String title = "";
        String body = "";
        htmlString = htmlString.replace("$title", title);
        htmlString = htmlString.replace("$body", body);
    }
}
