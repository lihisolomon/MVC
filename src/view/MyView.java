package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;

public class MyView implements View{

	private List<Thread> threads = new ArrayList<Thread>();
	
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
	public void notifyMazeIsReady(String name) {
		
		
	}
	/**
	 * Prints the maze
	 * @param1 - maze is type Maze3d
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		System.out.println(maze);
		
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
			
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
