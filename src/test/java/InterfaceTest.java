import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import org.json.*;

public class InterfaceTest{
    @Test
    public void testGet(){
        String str = CIserver.Interface.get("test/test.log");
        // Should not be null since there is a directory called test/test.log.
        assertNotEquals(str, null);
        // The function should return the contents of the file, which is testingtesting.
        assertEquals(str.contains("testingtesting"), true);
        str = CIserver.Interface.get("test/test1.log");
        // There is no file called test1.log, should return null.
        assertEquals(str, null);
        str = CIserver.Interface.get("test/");
        // There contains a link to test.log. Should be true, since test/ contains a test.log file.
        assertEquals(str.contains("test.log"), true);
        // There contains a URL to the parent directory, which is just /. Should be true.
        assertEquals(str.contains("<a href=" + CIserver.Interface.DOMAIN + ">..</a><br>"), true);

    }
}
