package controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;
/**
 * CommandsManager Class
 *
 */
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
			commands.put("dir", new displayFilesInPathCommand());
			commands.put("generate_maze", new generateMazeCommand());
			commands.put("display", new displayMazeCommand());
			commands.put("display_cross_section", new displayCrossSectionCommand());
			commands.put("save", new saveMazeCommand());
			commands.put("load", new loadMazeCommand());
			commands.put("solve", new solveMazeCommand());
			commands.put("display_solution", new displaySolutionCommand());
			commands.put("exit", new exitCommand());
			return commands;
		}
	
	/**
	 * Creates maze 
	 */
	public class generateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.generateMaze(args);
		}		
	}
	
	/**
	 * Displays the maze 
	 */
	public class displayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
	}
	
	/**
	 * Displays the files in a specific folder
	 */
	public class displayFilesInPathCommand implements Command {

		@Override
		public void doCommand(String[] args) {
				String folderPath=args[0];
				view.displayFiles(model.listFiles(folderPath));
		}
		
	}

	/**
	 * save the maze into a file
	 *
	 */
	public class saveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
				model.saveMaze(args);
		}
	}
	/**
	 * load a maze from file
	 *
	 */
	public class loadMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
				model.loadMaze(args);
		}
		
	}
	
	/**
	 * exit
	 */
	public class exitCommand implements Command {
		@Override
		public void doCommand(String[] args) {
			model.exit(args);
			
		}
	}
	
	/**
	 * display Cross Section of the maze
	 *
	 */
	public class displayCrossSectionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			view.displayCrossSection(model.getCrossSection(args));
		}
	}
	
	/**
	 * solve the Maze
	 *
	 */
	public class solveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.solveMaze(args);
		}
		
	}
	
	/**
	 * display the solution of the maze
	 *
	 */
	public class displaySolutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
				view.displaySolution(model.getSolution(args));
		}
		
	}
}
