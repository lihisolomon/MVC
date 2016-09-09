package view;

import java.io.File;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;

/**
 * View Interface
 */
public interface View {
	public void printOutput(String str);
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void setCommands(HashMap<String, Command> commands);
	void displayFiles(File[] listOfFiles);
}
