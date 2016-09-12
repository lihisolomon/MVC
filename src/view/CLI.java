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
                	do{
                		printMenu();
                		printOutput("please enter your choice: ");
                        userString=in.readLine();
                        String command=(userString.split(" "))[0];
                        if ( commands.containsKey(command)) {
                           	//convert the string to an array and removes the first characters until space
                           	 commands.get(command).doCommand(userString.substring(userString.indexOf(" ")+1,userString.length()).split(" "));
                        }
                        else
                            printOutput("You type wrong command");

                    } while (!userString.equals("exit"));
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
 
    /**
     * Print a menu to the viewer
     */
    public void printMenu(){
    		  System.out.println("\n\n********************************");
              System.out.println("what do want to do? ");
              System.out.println("		dir <path>");
              System.out.println("		generate_maze <mazeName> <x> <y> <z>");
              System.out.println("		display <name>");
              System.out.println("		display_cross_section <axle> <index> <mazeName>");
              System.out.println("		save_maze mazeName <fileName>");
              System.out.println("		load_maze mazeName <fileName>");
              System.out.println("		solve mazeName <algorithm>");
              System.out.println("		display_solution <mazeName>");
              System.out.println("		exit");
              System.out.println("********************************\n");
       }
}
 
