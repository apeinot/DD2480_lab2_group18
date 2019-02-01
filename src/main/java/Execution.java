package CIserver;

import java.lang.Process;
import java.lang.Runtime;
import java.lang.InterruptedException;
import java.io.IOException;


public class Execution{

    public int execute(String command){
	Process p;
	int exitStatus;
        try{
            p = Runtime.getRuntime().exec(command);
        } catch (IOException e){
            p = null;
	    exitStatus = 4;
        }
        try{
            exitStatus = p.waitFor();
        } catch (InterruptedException e){
            exitStatus = 5;
        }
    	System.out.println(exitStatus);    
    	return exitStatus;
    }
}
