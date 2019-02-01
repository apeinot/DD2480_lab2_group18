//Test cases of the CI server

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServerTest {

    @Test
    public void testTrue() {
        assertEquals(true, true);
    }

    @Test
    public void executeTest() {
	String command = "ls";
	CIserver.Execution exec = new CIserver.Execution();
	int exitCode = exec.execute(command);
	assertEquals(exitCode, 0);

	command = "ls monkey";
	exitCode = exec.execute(command);
        assertEquals(exitCode, 2);

    }

}
