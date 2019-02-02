//Test cases of the CI server

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServerTest {

    @Test
    public void testTrue() {
        assertEquals(true, true);
    }

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
