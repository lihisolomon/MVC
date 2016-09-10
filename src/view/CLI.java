package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.*;
 
/**
* CLI Class
*/
public class CLI {
    private BufferedReader in;
    private PrintWriter out;
    private HashMap<String, Command> commands;

 
    public CLI(BufferedReader in, PrintWriter out,HashMap<String, Command> commands){
       this.in = in;
       this.out = out;
       this.commands = commands;
       
    }
 
    
    public HashMap<String, Command> getCommands() {
		return commands;
	}


	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}


	public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    do {
                    	String userString;
                        printOutput("please enter your string: ");
                        userString=in.readLine();
                        if ( commands.containsKey(userString)) {
                        	printOutput("please enter parameters: ");
                            String parameters=in.readLine();
                        	//convert the string to an array and removes the first characters until space
                        	 String[] paramArray=parameters.split(" ");
                            commands.get(userString).doCommand(paramArray);
                        }
                        else
                            printOutput("You type wrong command");
 
                    } while (!(in.readLine()).equals("exit"));
               }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * print using the out object
     * @param str the string we want to print
     */
    public void printOutput(String str) {
    	out.println(str);
        out.flush();
    }
 
}
 
