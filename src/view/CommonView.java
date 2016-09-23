package view;

import java.io.File;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

public abstract class CommonView implements View {
	
	protected HashMap<String, Command> commands;
	protected Controller controller;
	
	@Override
	public abstract void start();

	@Override
	public abstract void setController(Controller controller);

	@Override
	public abstract Controller getController() ;

	@Override
	public abstract void printOutput(String str);

	@Override
	public abstract void notifyMazeIsReady(String name);

	@Override
	public abstract void displayMaze(Maze3d maze);

	@Override
	public abstract void setCommands(HashMap<String, Command> commands) ;

	@Override
	public abstract void displayFiles(File[] listOfFiles);

	@Override
	public abstract void displaySolution(Solution<Position> solution);
	
	@Override
	public abstract void displayCrossSection(int[][] maze2d) ;

}
