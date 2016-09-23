package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

/**
 * MyView Class implements View
 */
public class MyView extends CommonView{

	private CLI cli;
	
	/**
	 * CTOR
	 * @param in the input stream object
	 * @param out the output stream object
	 */
	public MyView(BufferedReader in,PrintWriter out) {
		 this.cli = new CLI(in,out,commands);
	}

	/**
	 * setter of controller
	 * @param controller 
	 */
	@Override
	public void setController(Controller controller) {
		super.controller = controller;
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
			super.commands=commands;
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
		printOutput("maze " + name+ " is ready");
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
			printOutput("Start: ");
			maze.getStartPosition().printPosition();
			printOutput("End: ");
			maze.getGoalPosition().printPosition();
			System.out.println(maze);
		}
	}
	
	/**
	 * Prints all the files in specific folder
	 */
	@Override
	public void displayFiles(File[] listOfFiles)
	{			
		if (listOfFiles!=null)
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println("File " + listOfFiles[i].getName());
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory " + listOfFiles[i].getName());
				}
			}
	}
	
	/**
	 * display the solution to the user
	 */
	@Override
	public void displaySolution(Solution<Position> solution) {
		System.out.println("the solution is: ");
		System.out.println(solution);
	}
	
	/**
	 * display Cross Section
	 */
	@Override
	public void displayCrossSection(int[][] maze2d){
		System.out.println("{");
		for (int i=0;i<maze2d.length;i++)
		{
			for (int j=0;j<maze2d[0].length;j++)
			{
				System.out.print(maze2d[i][j]+ ",");
			}System.out.println("");
		}System.out.println("}");
	}
}
