package CIserver;

import java.lang.Process;
import java.lang.Runtime;
import java.lang.InterruptedException;
import java.io.IOException;
import java.io.File;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class Execution{

    /**Function to execute a command and write the output of the command in a file
    @param String command - command to be executed
    @param File file - file in which write the output from the command
    @return int exitStatus - the exit status of the command (0: success, not 0: error)
    */
    public static int execute(String command, File file){
	Process p;
	int exitStatus;
	String s = null;
        try{
            //creating the process
	    p = Runtime.getRuntime().exec(command);

	    //creating the buffer to write in the file
            Writer output = new BufferedWriter(new FileWriter(file, true));

	    //buffer to read the stdout and the stderr 
            BufferedReader stdOut = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));

	    //begin of writing
	    output.write(">>> " + command);
            output.write(System.getProperty("line.separator"));

            //write the output from the command
	    output.write("----------------------------");
	    output.write(System.getProperty("line.separator"));
	    output.write("STANDARD OUTPUT:");
	    output.write(System.getProperty("line.separator"));
	    output.write("----------------------------");
            output.write(System.getProperty("line.separator"));

            while ((s = stdOut.readLine()) != null) {
                output.write(s);
                output.write(System.getProperty("line.separator"));
            }

            //write the potential errors from the command
	    output.write("----------------------------");
            output.write(System.getProperty("line.separator"));
            output.write("STANDARD ERROR:");
            output.write(System.getProperty("line.separator"));
            output.write("----------------------------");
            output.write(System.getProperty("line.separator"));

            while ((s = stdError.readLine()) != null) {
		output.write(s);
		output.write(System.getProperty("line.separator"));
            }
	    output.write(System.getProperty("line.separator"));

            output.close(); //end of writing and closing the buffer
        } catch (IOException e){
            p = null;
	    exitStatus = 4;
        }
        try{
            exitStatus = p.waitFor(); //get the exitStatus
        } catch (InterruptedException e){
            exitStatus = 5;
        }
    	//System.out.println(exitStatus);    
    	return exitStatus;
    }

    /**Function to execute a command and write the output of the command in a file
    @param String command - command to be executed
    @param File file - file in which write the output from the command
    @param File dir - directory where the command should be executed
    @return int exitStatus - the exit status of the command (0: success, not 0: error)
    */
    public static int execute(String command, File file, File dir){
        Process p;
        int exitStatus;
        String s = null;
        try{
            //creating the process
            p = Runtime.getRuntime().exec(command, null, dir);

            //creating the buffer to write in the file
            Writer output = new BufferedWriter(new FileWriter(file, true));

            //buffer to read the stdout and the stderr
            BufferedReader stdOut = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));

            //begin of writing
            output.write(">>> " + command);
            output.write(System.getProperty("line.separator"));

            //write the output from the command
            output.write("----------------------------");
            output.write(System.getProperty("line.separator"));
            output.write("STANDARD OUTPUT:");
            output.write(System.getProperty("line.separator"));
            output.write("----------------------------");
            output.write(System.getProperty("line.separator"));

            while ((s = stdOut.readLine()) != null) {
                output.write(s);
                output.write(System.getProperty("line.separator"));
            }

            //write the potential errors from the command
            output.write("----------------------------");
            output.write(System.getProperty("line.separator"));
            output.write("STANDARD ERROR:");
	    output.write(System.getProperty("line.separator"));
            output.write("----------------------------");
            output.write(System.getProperty("line.separator"));

            while ((s = stdError.readLine()) != null) {
                output.write(s);
                output.write(System.getProperty("line.separator"));
            }
            output.write(System.getProperty("line.separator"));

            output.close(); //end of writing and closing the buffer
        } catch (IOException e){
            p = null;
            exitStatus = 4;
        }
        try{
            exitStatus = p.waitFor(); //get the exitStatus
        } catch (InterruptedException e){
            exitStatus = 5;
        }
        //System.out.println(exitStatus);
        return exitStatus;
    }
}
