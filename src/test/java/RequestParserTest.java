//Test cases of the CI server

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.json.*;

public class RequestParserTest{

    @Test
    public void testTrue() {
        assertEquals(true, true);
    }

    /**
    Tests both the getFields method AND the getter methods.
    */
    @Test
    public void getFiedsTest(){

        //Testing the field getter when fields are non existent, should:
        //1) Not crash (test of try catch) and
        //2) Not set the values.
        RequestParser rp = new RequestParser();
        String jsonString = "{\"nothing\":\"of\", \"use\": \"test\"}";
        JSONObject jo = new JSONObject(jsonString);
        rp.getFields(jo);
        assertEquals(rp.getAfterField(), null);
        assertEquals(rp.getStatuses_urlField(), null);

        //Testing the field getter when the fields exist, should set both fields correctly
        jsonString = "{\"after\":\"correct_string\",\"repository\":{\"statuses_url\":\"Correct.url/test\"}}";
        jo = new JSONObject(jsonString);
        rp.getFields(jo);
        assertEquals(rp.getAfterField(), "correct_string");
        assertEquals(rp.getStatuses_urlField(), "Correct.url/test");
    }

}
