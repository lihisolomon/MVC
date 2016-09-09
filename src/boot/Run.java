package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import view.CLI;

public class Run {

	public static void main(String[] args) {
		HashMap<String, Command> commands=new HashMap<>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		PrintWriter out = new PrintWriter(System.out);
		CLI cli=new CLI(in,out,commands);
		cli.start();
		

	}	

}