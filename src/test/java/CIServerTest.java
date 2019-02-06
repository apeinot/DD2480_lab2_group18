import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.json.*;

public class CIServerTest{


    /**
    Test for handling of post requests coming in from the github webhook.
    Contains both a test with a forged correct request as well as one where
    the data is invalid.
    */
    @Test
    public void handlePostTest(){
        CIserver.ContinuousIntegrationServer cis = new CIserver.ContinuousIntegrationServer();
        String clone_url = "https://github.com/apeinot/DD2480_lab2_group18.git";
        String sha_string = "273f76f5f8c01955f99a50da10c18e48519d13b5";
        String name = "apeinot";
        String reponame = "DD2480_lab2_group18";
        // A test for a valid post request for a commit in a repository
        int result = cis.handlePost(sha_string, clone_url, name, reponame);
        assertEquals(result, 0);

        // A test where the username does not match the owner of the given repo
        name = "jonagoo";
        result = cis.handlePost(sha_string, clone_url, name, reponame);
        assertEquals(result, 1);
    }
}
