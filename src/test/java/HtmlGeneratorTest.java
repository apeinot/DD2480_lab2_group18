import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import org.json.*;

import java.io.File;

public class HtmlGeneratorTest{
    @Test
    public void testReadFile(){
        String str = CIserver.HtmlGenerator.readFile(new File("history/test/test.txt"), true);
        // Should not be null, since this file exists.
        assertNotEquals(str, null);
        // This file should contain the string README.md
        assertEquals(str.contains("README.md"), true);
        str = CIserver.HtmlGenerator.readFile(new File("history/test/test1.txt"), true);
        // This file doesn't exist, therefore the string returned should be null.
        assertEquals(str, null);
    }

    @Test
    public void testGet(){
        String str = CIserver.HtmlGenerator.get("/test/test.txt");
        // Should not be null since there is a directory called test/test.log.
        assertNotEquals(str, null);
        // The function should return the contents of the file, which is testingtesting.
        assertEquals(str.contains("README.md"), true);

        str = CIserver.HtmlGenerator.get("/test/test1.log");
        // There is no file called test1.log, should return null.
        assertEquals(str, null);

        str = CIserver.HtmlGenerator.get("/test");
        // There contains a link to test.log. Should be true, since test/ contains a test.log file.
        assertEquals(str.contains("<a href=\"" + CIserver.HtmlGenerator.DOMAIN + "/test/test.txt\">test.txt</a><br>"), true);
        // There contains a URL to the parent directory, which is just /. Should be true.
        assertEquals(str.contains("<a href=\"" + CIserver.HtmlGenerator.DOMAIN +"/"+"\">..</a><br>"), true);

        str = CIserver.HtmlGenerator.get("/");
        // Should exist and not be null
        assertNotEquals(str, null);
        // Empty path, should not have a back link.
        assertEquals(str.contains("<a href=\"" + CIserver.HtmlGenerator.DOMAIN + "\">..</a><br>"), false);
        // But should contain a link to the test folder, among others.
        assertEquals(str.contains("<a href=\"" + CIserver.HtmlGenerator.DOMAIN + "/test\">test</a><br>"), true);

        // Null string as parameter, should return null.
        str = CIserver.HtmlGenerator.get(null);
        assertEquals(str, null);
    }
}
