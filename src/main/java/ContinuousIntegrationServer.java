package CIserver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);
        String method = request.getMethod();
        PrintWriter out = response.getWriter();
        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
        if (method.equals("GET")) {

        } else if (method.equals("POST")) {
            RequestParser parser = new RequestParser();
            int returncode;
            if ((returncode = parser.parse(request)) != 1) {

            } else {
                String commit_id = parser.getAfterField();
                String clone_url = parser.getClone_urlField();
                String reponame = parser.getNameField();
                String name = clone_url.split("/")[3];
                handlePost(commit_id, clone_url, name, reponame);
            }

        }
        out.println("CI job done");
    }

    /** Function for handling incoming post requests from the github webhook.
    @param commit_id the id of the latest commit on the pushed branch
    @param clone_url the url for cloning the repository
    @param name the username of the owner of the repository
    @param reponame the name of the repository
    @return 0 if the request was handled successfully, 1 if there were any errors
    in any part of execution
    */
    public int handlePost(String commit_id, String clone_url, String name, String reponame) {
        int returncode;
        Responder resp = new Responder();
        String[] request_params = new String[4];
        request_params[0] = "pending";
        request_params[1] = "http://130.237.227.78:8018";
        request_params[2] = "Waiting for results from CI server";
        request_params[3] = "CI Server of Group 18";
        String url_string = "https://api.github.com/repos/"+name+"/"+reponame+"/statuses/";
        if ((returncode = resp.git_status(url_string, commit_id, request_params)) != 0) {
            return 1;
        }
        File file = new File("history/"+name +"/"+reponame+"/"+commit_id+".log");
        try {
            if(file.getParentFile().mkdirs()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        File dir = new File(reponame+"/");
        // Run CI tests
        boolean[] commandfail = new boolean[3];
        String result = "success";
        if ((returncode = Execution.execute("git clone " + clone_url, file)) != 0) {
            // Something went wrong
            if (returncode == 4 || returncode == 5) {
                return 1;
            }
        }
        if ((returncode = Execution.execute("git checkout "+commit_id, file, dir)) != 0){
            if (returncode == 4 || returncode == 5) {
                return 1;
            }
        }
        if ((returncode = Execution.execute("ant compile", file, dir)) != 0){
            if (returncode == 4 || returncode == 5) {
                return 1;
            }
            commandfail[0] = true;
            result = "failure";
        }
        if ((returncode = Execution.execute("ant test-compile", file, dir)) != 0){
            if (returncode == 4 || returncode == 5) {
                return 1;
            }
            commandfail[1] = true;
            result = "failure";
        }
        if ((returncode = Execution.execute("ant test", file, dir)) != 0){
            if (returncode == 4 || returncode == 5) {
                return 1;
            }
            commandfail[2] = true;
            result = "failure";
        }
        if ((returncode = Execution.execute("rm -rf " + reponame, file)) != 0){
            if (returncode == 4 || returncode == 5) {
                return 1;
            }
        }

        //Set github status

        String desc = "All tests completed successfully.";
        if (commandfail[0]) {
            if (commandfail[1]) {
                desc = "Program and test compilation failed.";
            } else {
                desc = "Program compilation failed";
            }
        } else if (commandfail[1]) {
            desc = "Test compilation failed";
        } else if (commandfail[2]) {
            desc = "One or more tests failed";
        }
        request_params[0] = result;
        request_params[2] = desc;
        if (resp.git_status(url_string, commit_id, request_params) != 0){
            return 1;
        }

        //Send email
        String subject = "Build result of commit "+commit_id;
        String mailText = "All tests ran successfully. "+request_params[1];
        if (commandfail[0]) {
            if (commandfail[1]) {
                mailText = "Program and test compilation failed.\n"
                        +request_params[1]+"\n\nLog:\n"+file.toString();
            } else {
                mailText = "Program compilation failed.\n"
                        +request_params[1]+"\n\nLog:\n"+file.toString();
            }
        } else if (commandfail[1]) {
            mailText = "Test compilation failed.\n"
                    +request_params[1]+"\n\nLog:\n"+file.toString();
        } else if (commandfail[2]) {
            mailText = "One or more tests failed.\n"
                    +request_params[1]+"\n\nLog:\n"+file.toString();
        }
        if (resp.send("mailbot8080@gmail.com", subject, mailText) != 0) {
            return 1;
        }
        return 0;

    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8018);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
