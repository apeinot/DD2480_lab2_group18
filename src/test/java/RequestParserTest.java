import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.json.*;

public class RequestParserTest{

    @Test
    public void testTrue() {
        assertEquals(true, true);
    }

    /**
    Testing the field setters/getters when fields are non existent, should:
    1) Not crash (test of try catch) and
    2) Not set the values.
    */
    @Test
    public void badDataTest(){
        CIserver.RequestParser rp = new CIserver.RequestParser();
        String jsonString = "{\"nothing\":\"of\", \"use\": \"test\"}";
        JSONObject jo = new JSONObject(jsonString);
        rp.getFields(jo);
        assertEquals(rp.getAfterField(), null);
        assertEquals(rp.getStatuses_urlField(), null);
        assertEquals(rp.getClone_urlField(), null);
        assertEquals(rp.getNameField(), null);
        assertEquals(rp.getEmailField(), null);
        System.out.println("REQUESTPARSER: DONE TESTING BAD DATA ^^^^^^^^^^^^\n\n\n");


    }

    /**
    Testing the field setters/getters when the fields exist, should:
    1) Not crash and
    2) Set all values correctly
    */
    @Test
    public void validDataTest(){
        CIserver.RequestParser rp = new CIserver.RequestParser();
        String jsonString = "{\"after\":\"correct_string\",\"repository\":{\"statuses_url\":\"Correct.url/test\",\"clone_url\":\"Correct.url/clone/test\",\"name\":\"correct_name\"}, \"pusher\":{\"email\": \"correct_email.com\"}}";
        JSONObject   jo = new JSONObject(jsonString);
        rp.getFields(jo);
        assertEquals(rp.getAfterField(), "correct_string");
        assertEquals(rp.getStatuses_urlField(), "Correct.url/test");
        assertEquals(rp.getClone_urlField(), "Correct.url/clone/test");
        assertEquals(rp.getNameField(), "correct_name");
        assertEquals(rp.getEmailField(), "correct_email.com");
    }

    /**
    Testing the field setters/getters when some fields exist, should:
    1) Not crash and
    2) Set the 'after' & name values correctly
    */
    @Test
    public void partialDataTest(){
        CIserver.RequestParser rp = new CIserver.RequestParser();
        String jsonString = "{\"after\":\"correct_string\",\"repository\":{\"name\":\"correct_name\"}}";
        JSONObject jo = new JSONObject(jsonString);
        rp.getFields(jo);
        assertEquals(rp.getAfterField(), "correct_string");
        assertEquals(rp.getStatuses_urlField(), null);
        assertEquals(rp.getClone_urlField(), null);
        assertEquals(rp.getNameField(), "correct_name");
        assertEquals(rp.getEmailField(), null);
        System.out.println("REQUESTPARSER: DONE TESTING PARTIAL DATA ^^^^^^^^\n\n\n");
    }

}
