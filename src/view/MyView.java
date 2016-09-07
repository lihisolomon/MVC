package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

public class MyView implements View{

	/**
	 * Start the program 
	 * @param1 in 		- input stream
	 * @param2 out 		- output stream
	 * @param3 command 	- command to run in cli class
	 */
	public void start(BufferedReader in, PrintWriter out,HashMap<String, Command> commands)
	 {
		CLI cli=new CLI(in,out,commands);
		cli.start();
	 }

}
