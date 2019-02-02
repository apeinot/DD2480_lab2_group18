//Test cases of the CI server

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import CIserver.RequestTest;

public class ServerTest {

    @Test
    public void testTrue() {
        assertEquals(true, true);
    }

    @Test
    public void testRequest(){
        RequestTest.doRequest();
        assertEquals(true, true);
    }

    /**@Test
    public void testJSON(){
        RequestTest.doJSON();
        assertEquals(true, true);
    }*/

}
