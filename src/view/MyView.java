package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;

/**
 * MyView Class implements View
 */
public class MyView implements View{

	private List<Thread> threads = new ArrayList<Thread>();
	private CLI cli;
	private HashMap<String, Command> commands;
	
	/**
	 * CTOR
	 * @param in the input stream object
	 * @param out the output stream object
	 */
	public MyView(BufferedReader in,PrintWriter out) {
		 this.cli = new CLI(in,out,this.commands);
	}
	
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

	@Override
	public void printOutput(String str) {
		cli.printOutput(str);
	}
	
	/**
	 * notify to the client that the maze is ready
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		cli.printOutput("maze " + name+ " is ready");
	}
	
	/**
	 * Prints the maze
	 * @param maze- Maze3D
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		if (maze== null) 
		{
			cli.printOutput("this maze does not exist");
		} 
		else
		{
			cli.printOutput("Start: ");
			maze.getStartPosition().printPosition();
			cli.printOutput("End: ");
			maze.getGoalPosition().printPosition();
			System.out.println(maze);
		}
		
	}

	/**
	 * setter of the commands
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
			this.commands=commands;
	}
	
	/**
	 * Prints all the files in specific folder
	 */
	public void displayFiles(File[] listOfFiles)
	{		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						System.out.println("File " + listOfFiles[i].getName());
					} else if (listOfFiles[i].isDirectory()) {
						System.out.println("Directory " + listOfFiles[i].getName());
					}
				}
			}	
		});
		thread.start();
		threads.add(thread);	
	}

}
