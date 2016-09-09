package controller;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

public class CommandsManager {
	
	private Model model;
	private View view;
		
	/**
	 * C'tor
	 * @param model
	 * @param view
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	
	/**
	 * Creates a hash map of all the commands
	 * @return commands
	 */
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("generate_3d_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("dir", new DisplayFilesInPathCommand());
		
		return commands;
	}
	
	/**
	 * 
	 * Creates maze 
	 *
	 */
	public class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int floors 	= Integer.parseInt(args[1]);
			int rows 	= Integer.parseInt(args[2]);
			int cols 	= Integer.parseInt(args[3]);
			model.generateMaze(name,floors, rows, cols);
		}		
	}
	
	/**
	 * 
	 * Displays the maze 
	 *
	 */
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
		
	}
	
	/**
	 * 
	 * Displays the files in a specific folder
	 *
	 */
	public class DisplayFilesInPathCommand implements Command {

		@Override
		public void doCommand(String[] args) {
				String folderPath=args[0];
				File[] listOfFiles=(new File(folderPath)).listFiles();
				view.displayFiles(listOfFiles);
		}
		
	}
	
	
}
