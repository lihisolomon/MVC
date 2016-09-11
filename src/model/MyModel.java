package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Maze3dSearchable;
import algorithms.search.Solution;
import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * MyModel class implements Model
 *
 */
public class MyModel implements Model {
	
	private Controller controller;	
	private Map<String, Maze3d> mazes;
	private HashMap<String, Solution<Position>> solutions;
	private ExecutorService exec;

	/**
	 * CTOR
	 */
	public MyModel() {
		this.mazes = new ConcurrentHashMap<String, Maze3d>();
		this.solutions = new HashMap<>();
		this.exec=Executors.newFixedThreadPool(20);
	}
	
	/**
	 * getter of controller
	 */
	@Override
	public Controller getController() {
		return controller;
	}

	/**
	 * setter of controller
	 * @param controller 
	 */
	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * generate the maze
	 * @param name
	 * @param floors
	 * @param rows
	 * @param cols
	 */
	@Override
	public void generateMaze(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String name = args[0];
				Integer floors= Integer.parseInt(args[1]);
				Integer rows = Integer.parseInt(args[2]);
				Integer cols = Integer.parseInt(args[3]);
				if (name!=null && floors!=null && rows!=null && cols!=null){
					GrowingTreeGenerator generator = new GrowingTreeGenerator();
					Maze3d maze = generator.generate(floors,rows, cols);
					mazes.put(name, maze);
				
					controller.notifyMazeIsReady(name);
				}
				else{
					controller.output("invalid data");
				}
			}	
		});
		exec.submit(thread);
	}

	/**
	 * getter of maze
	 */
	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}

	/**
	 * save the maze into a file 
	 */
	@Override
	public void saveMaze(String[] args) {
		String mazeName=args[0];
		String fileName=args[1];
		if(mazeName!=null && fileName!=null)
		{
			try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName+".maz"));
			out.write(mazes.get(mazeName).toByteArray());			
			out.flush();
			out.close();
			}
			catch (Exception e) {
				controller.output("Error on saving the maze");
			}
		}
		else
			controller.output("invalid data");
	}
	
	/**
	 * load the maze from a file
	 */
	@Override
	public void loadMaze(String[] args) {
		String mazeName=args[0];
		String fileName=args[1];
		if(mazeName!=null && fileName!=null)
		{
			try {
				InputStream in=new MyDecompressorInputStream(new FileInputStream(fileName+".maz"));
				byte b[]=new byte[100];
				try{
					in.read(b);
					Maze3d loaded=new Maze3d(b);
					mazes.put(mazeName, loaded);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				finally {
					try {
						in.close();
					} 
					catch (IOException e) {
						controller.output("Error to close file");
					}
				}
				controller.output("Maze was loaded succesfully");
			}
			catch (Exception e) {
				controller.output("Error in loading file");
			}
		}
		else 
			controller.output("not enough data");
	}
	
	/**
	 * return the list of the files
	 */
	public File[] listFiles(String args){
		return (new File(args)).listFiles();
	}
	
	/**
	 * solve the maze
	 */
	@Override
	public void solveMaze(String[] args){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String nameMaze=args[0];
				String alg=args[1];
				Maze3d maze=getMaze(nameMaze);
				if (maze!=null && alg!=null){
					Solution<Position> solution = new Solution<>();
					Maze3dSearchable mazeAdapter = new Maze3dSearchable(maze);
				
					if(alg.toUpperCase().equals("BFS")) {
						BFS<Position> searcher = new BFS<>();
						solution = searcher.search(mazeAdapter);
						solutions.put(nameMaze, solution);
						controller.output("the solution is ready");
					}
					else if (alg.toUpperCase().equals("DFS")) {
						DFS<Position> searcher = new DFS<>();
						solution = searcher.search(mazeAdapter);
						solutions.put(nameMaze, solution);
						controller.output("the solution is ready");
					}
					else
						controller.output("you entered the wrong solution name");
				}
				else
					controller.output("invalid data");
			}
		});
		exec.submit(thread);
	}
	
	/**
	 * get the solution of the maze
	 */
	public Solution<Position> getSolution(String[] args){
		Solution<Position> sol= solutions.get(args[0]);
		if(sol!=null)
			return sol;
		else{
			controller.output("there is no solution yet");
			return null;
		}
	}
	
	/**
	 * get Cross Section
	 */
	public int[][] getCrossSection(String[] args){
		int floors= Integer.parseInt(args[0]);
		String name = args[3];
		Maze3d maze=this.mazes.get(name);
		if (maze!=null)
			return maze.getCrossSectionByX(floors);
		else{
			controller.output("the maze is not exist");
			return null;
		}
	}

	/**
	 * exit command
	 */
	@Override
	public void exit(String[] args) {
		exec.shutdown();
		controller.output("EXIT!");
	}
}
