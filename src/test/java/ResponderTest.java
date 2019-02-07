package CIServerTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import CIserver.Responder;

import org.json.*;
import java.io.File;
import java.io.IOException;

/**
The class ResponderTest provides test cases for the class Responder, which sets
github statuses and sends mails in response.
*/
public class ResponderTest {
    /**
    Test case for git_status in Responder.java. This test will do one successful
    HTTP POST request, which will lead to return value 0 and one request that is
    not successful and therefore produces return value 1.
    */
    @Test
    public void testGitStatus(){
        // First test case with correct parameters - should be return value 0
        String url_string = "https://api.github.com/repos/apeinot/DD2480_lab2_group18/statuses/";
        String sha_string = "273f76f5f8c01955f99a50da10c18e48519d13b5";
        String[] request_params = new String[4];
        request_params[0] = "success";
        request_params[1] = "http://130.237.227.78:8018";
        request_params[2] = "Cooking good in the neighborhood";
        request_params[3] = "CI Server of Group 18";
        int ret_code = CIserver.Responder.git_status(url_string, sha_string, request_params);
        assertEquals(ret_code, 0);

        // Second test case has an invalid sha_string and therefore produces
        // return value 1
        sha_string = "273f76f5f8c01955f99a70da10c18e48519d13b5";
        ret_code = CIserver.Responder.git_status(url_string, sha_string, request_params);
        assertEquals(ret_code, 1);
    }

    /**
    Test case for create_json in Responder.java. This test will fill data into the
    json object and check whether a string is really in stored in the json format.
    The absence of other data is also tested. Last it is tested whether the function
    correclty returns null if the parameters were not correclty provided
     */
    @Test
    public void testCreateJson(){
        String[] request_params = new String[4];
        request_params[0] = "success";
        request_params[1] = "http://130.237.227.78:8018";
        request_params[2] = "Cooking good in the neighborhood";
        request_params[3] = "CI Server of Group 18";
        JSONObject jobj = CIserver.Responder.create_json(request_params);
        // Test case 1: string 'good' should be in the object - therefore true
        assertEquals(jobj.toString().contains("good"), true);
        // Test case 2: string 'goood' should be in the object - therefore false
        assertEquals(jobj.toString().contains("goood"), false);
        request_params = null;
        jobj = CIserver.Responder.create_json(request_params);
        // Test case 3: invalid parameter request_params - therefore null
        assertEquals(jobj, null);
    }

    /**
    Testing the e-mail notification function.
    */
    @Test
    public void emailTest(){
        // The mail bot e-mails itself, should work and return 0.
        assertEquals(CIserver.Responder.send("mailbot8080@gmail.com", "Hello", "there"), 0);
        // Entering the wrong password for the bot, should not work (return code 1).
        assertEquals(CIserver.Responder.send("mailbot8080@gmail.com", "password", "mailbot8080@gmail.com", "Hello", "there"), 1);
        // Nonsensical sender address, should not work (return code 1).
        assertEquals(CIserver.Responder.send("sfjsfjtset", "sfsdfsdf", "mailbot8080@gmail.com", "Hello", "there"), 1);
        // Nonsensical recipient address, should not work (return code 1).
        assertEquals(CIserver.Responder.send("hlejsdjfosfkke", "Hello", "there"), 1);
    }
}
