package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;

import controller.Command;
 
/**
* CLI Class
*/
public class CLI {
    private BufferedReader in;
    private PrintWriter out;
    private HashMap<String, Command> commands;
 
    public CLI(BufferedReader in, PrintWriter out,HashMap<String, Command> commands){
        in=new BufferedReader(null);
        out = new PrintWriter(System.out);
        this.commands=commands;
    }
 
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userString;
                try {
                    do {
                        printOutput("please enter your string: ");
                        userString=in.readLine();
                        if ( commands.containsKey(userString)) {
                            commands.get(userString).doCommand();
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
 
