package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
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
	private List<Thread> threads;
	private ExecutorService exec;

	/**
	 * CTOR
	 */
	public MyModel() {
		this.threads = new ArrayList<Thread>();
		this.mazes = new ConcurrentHashMap<String, Maze3d>();
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
	public void generateMaze(String name, int floors, int rows, int cols) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				GrowingTreeGenerator generator = new GrowingTreeGenerator();
				Maze3d maze = generator.generate(floors,rows, cols);
				mazes.put(name, maze);
				
				controller.notifyMazeIsReady(name);				
			}	
		});
		thread.start();
		threads.add(thread);
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
		if(mazes.containsKey(args[0]))
		{
			try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(args[1]+".maz"));
			out.write(mazes.get(args[0]).toByteArray());			
			out.flush();
			out.close();
			}
			catch (Exception e) {
				controller.output("Couldn't save the maze - fatal error");
			}
		}
		else
		{
			controller.output("not enough data");
		}
	}
	
	/**
	 * load the maze from a file
	 */
	@Override
	public void loadMaze(String[] args) {
		if(!mazes.containsKey(args[1]))
		{
			try {
				InputStream in=new MyDecompressorInputStream(new FileInputStream(args[0]+".maz"));
				byte b[]=new byte[(int)(new File(args[0]+".maz")).length()];
				in.read(b);
				in.close();
				Maze3d loaded=new Maze3d(b);
				mazes.put(args[1], loaded);
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
	 * exit command
	 */
	@Override
	public void exit(String[] args) {
		exec.shutdown();
		controller.output("EXIT!");
	}
	
	/**
	 * return the list of the files
	 */
	public File[] listFiles(String args){
		return (new File(args)).listFiles();
	}
}
