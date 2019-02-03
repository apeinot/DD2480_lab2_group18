//Test cases of the CI server

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ServerTest {

    @Test
    public void testTrue() {
        assertEquals(true, true);
    }

    /**Test cases of the execute method from the class Execution
    First test - try to execute the command 'ls' which should work and return 0 as exit code
    Second test - try to execute the command 'ls monkey' which should result
                  in an error (the monkey directory doesn't exist) and return 2 as exit code
    Third test - try to execute the command 'ls' in the directory 'src/' which should work
                 and return 0 as exit code
    (The output of both commands is stored in 'history/test/test.txt')
    */
    @Test
    public void executeTest() {
	File file = new File("history/test/test.txt");
	try{
	    if (file.getParentFile().mkdirs()){
                file.createNewFile();
	    }
	}catch (IOException e){
	    System.out.println(e);
	}

	//Test 1
	String command = "ls";
	int exitCode = CIserver.Execution.execute(command, file);
	assertEquals(exitCode, 0);

	//Test 2
	command = "ls monkey";
	exitCode = CIserver.Execution.execute(command, file);
        assertEquals(exitCode, 2);

	//Test 3
	File dir = new File("src/");
        command = "ls";
        exitCode = CIserver.Execution.execute(command, file, dir);
        assertEquals(exitCode, 0);
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
