package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

/**
 * MyView Class implements View
 */
public class MyView implements View{

	private List<Thread> threads = new ArrayList<Thread>();
	private CLI cli;
	private HashMap<String, Command> commands;
	private Controller controller;
	
	/**
	 * CTOR
	 * @param in the input stream object
	 * @param out the output stream object
	 */
	public MyView(BufferedReader in,PrintWriter out) {
		 this.cli = new CLI(in,out,this.commands);
	}

	/**
	 * setter of controller
	 * @param controller 
	 */
	@Override
	public void setController(Controller controller) {
		this.controller = controller;
		cli.setCommands(this.commands);
	}
	
	/**
	 * getter of controller
	 */
	@Override
	public Controller getController() {
		return controller;
	}
	
	/**
	 * setter of the commands
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
			this.commands=commands;
	}
	
	/**
	 * print a message to the user
	 */
	@Override
	public void printOutput(String str) {
		cli.printOutput(str);
	}
	
	
	/**
	 * Start the program 
	 * @param1 in 		- input stream
	 * @param2 out 		- output stream
	 * @param3 command 	- command to run in cli class
	 */
	@Override
	public void start()
	 {
		cli.start();
	 }
	
	/**
	 * notify to the client that the maze is ready
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		System.out.println("maze " + name+ " is ready");
	}
	
	/**
	 * Prints the maze
	 * @param maze- Maze3D
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		if (maze== null) 
		{
			System.out.println("this maze does not exist");
		} 
		else
		{
			System.out.println("Start: ");
			maze.getStartPosition().printPosition();
			System.out.println("End: ");
			maze.getGoalPosition().printPosition();
			System.out.println(maze);
		}
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
	
	@Override
	public void displaySolution(Solution<Position> solution) {
		System.out.println("the solution is: ");
		System.out.println(solution);
	}

}
