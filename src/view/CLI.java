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

    /**
     * CTOR
     * @param in
     * @param out
     * @param commands
     */
    public CLI(BufferedReader in, PrintWriter out,HashMap<String, Command> commands){
       this.in = in;
       this.out = out;
       this.commands = commands;
    }
 
    /**
     * getters the commands
     * @return
     */
    public HashMap<String, Command> getCommands() {
		return commands;
	}

    /**
     * setter of the commands
     * @param commands
     */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	/**
	 * start the cli
	 */
	public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                	String userString;
                    printOutput("please enter your string: ");
                    userString=in.readLine();
                	while (!userString.equals("exit")){
                    	
                        String command=(userString.split(" "))[0];
                        //String keyRef=userString.substring(0, userString.indexOf(" "));
                        if ( commands.containsKey(command)) {
                           	//convert the string to an array and removes the first characters until space
                           	 commands.get(command).doCommand(userString.substring(userString.indexOf(" ")+1,userString.length()).split(" "));
                        }
                        else
                            printOutput("You type wrong command");
                        
                        printOutput("please enter your string: ");
                        userString=in.readLine();
                    } 
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
 
