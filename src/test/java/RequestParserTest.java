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
        RequestParser rp = new RequestParser();
        
        //Testing the field getter when fields are non existent, should:
        //1) Not crash (test of try catch) and
        //2) Not set the values.
        String jsonString = "{\"nothing\":\"of\", \"use\": \"test\"}";
        JSONObject jo = new JSONObject(jsonString);
        rp.getFields(jo);
        assertEquals(rp.getAfterField(), null);
        assertEquals(rp.getStatuses_urlField(), null);

        //Testing the field getter when the fields exist, should:
        //1) Not crash and
        //2) Set all values correctly
        jsonString = "{\"after\":\"correct_string\",\"repository\":{\"statuses_url\":\"Correct.url/test\",\"clone_url\":\"Correct.url/clone/test\",\"name\":\"correct_name\"}}";
        jo = new JSONObject(jsonString);
        rp.getFields(jo);
        assertEquals(rp.getAfterField(), "correct_string");
        assertEquals(rp.getStatuses_urlField(), "Correct.url/test");
        assertEquals(rp.getClone_urlField(), "Correct.url/clone/test");
        assertEquals(rp.getNameField(), "correct_name");

        //Testing the field getter when some fields exist, should:
        //1) Not crash and
        //2) Set the 'after' & name values correctly
        jsonString = "{\"after\":\"correct_string\",\"repository\":{\"name\":\"correct_name\"}}";
        jo = new JSONObject(jsonString);
        rp.getFields(jo);
        assertEquals(rp.getAfterField(), "correct_string");
        assertEquals(rp.getStatuses_urlField(), null);
        assertEquals(rp.getClone_urlField(), null);
        assertEquals(rp.getNameField(), "correct_name");
    }

}
