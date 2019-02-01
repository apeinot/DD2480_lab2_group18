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
	int exitCode = CIserver.Execution.execute(command);
	assertEquals(exitCode, 0);

	command = "ls monkey";
	exitCode = CIserver.Execution.execute(command);
        assertEquals(exitCode, 2);

    }

}
