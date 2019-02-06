import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import org.json.*;

public class InterfaceTest{
    @Test
    public void testGet(){
        String str = CIserver.Interface.get("history/test.log");
        assertNotEquals(str, null);
        assertEquals(str.contains("testingtesting"), true);
        str = CIserver.Interface.get("history/test1.log");
        assertEquals(str, null);
    }
}
