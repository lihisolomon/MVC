package view;

import java.io.File;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import controller.Controller;
import controller.MyController;

/**
 * View Interface
 */
public interface View {
	public void start();
	public Controller getController();
	public void setController(Controller controller);
	public void printOutput(String str);
	public void notifyMazeIsReady(String name);
	public void displayMaze(Maze3d maze);
	public void setCommands(HashMap<String, Command> commands);
	public void displayFiles(File[] listOfFiles);
}
