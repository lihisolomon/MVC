package view;

import java.io.File;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
import controller.Controller;

/**
 * View Interface
 */
public interface View {
	public void start();
	public void setController(Controller controller);
	public Controller getController();
	public void printOutput(String str);
	public void notifyMazeIsReady(String name);
	public void displayMaze(Maze3d maze);
	public void setCommands(HashMap<String, Command> commands);
	public void displayFiles(File[] listOfFiles);
	public void displaySolution(Solution<Position> solution);
	public void displayCrossSection(int[][] maze2d);
	public void exit(String[] args);
}
