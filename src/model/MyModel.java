package model;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.*;
import algorithms.search.*;
import controller.Controller;
import io.*;

/**
 * MyModel class implements Model
 *
 */
public class MyModel implements Model {
	
	private Controller controller;	
	private Map<String, Maze3d> mazes;
	private HashMap<String, Solution<Position>> solutions;
	private List<Thread> threads;

	/**
	 * CTOR
	 */
	public MyModel() {
		this.mazes = new ConcurrentHashMap<String, Maze3d>();
		this.solutions = new HashMap<>();
		this.threads=new ArrayList<Thread>();
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
	 * @param args
	 */
	@Override
	public void generateMaze(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				if(args.length==4){
					String name = args[0];
					Integer floors= Integer.parseInt(args[1]);
					Integer rows = Integer.parseInt(args[2]);
					Integer cols = Integer.parseInt(args[3]);
					if (!mazes.containsKey(name) && floors>0 && rows>0 && cols>0){
						GrowingTreeGenerator generator = new GrowingTreeGenerator();
						Maze3d maze = generator.generate(floors,rows, cols);
						mazes.put(name, maze);
				
						controller.notifyMazeIsReady(name);
					}
					else{
						controller.output("ERROR: invalid data");
					}
				}
				else{
					controller.output("ERROR: Please enter 4 arguments");
				}
			}	
		});
		thread.start();
		threads.add(thread);
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
		if (args.length==2){
			String mazeName=args[0];
			String fileName=args[1];
			if(mazes.containsKey(mazeName))
			{
				try {
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName+".maz"));
				byte[] arr=mazes.get(mazeName).toByteArray();
				int counter=arr.length;
				while(counter>=255)
				{
					out.write(255);
					counter-=255;
				}
				out.write(counter);
				out.write(arr);			
				out.flush();
				out.close();
				controller.output("the Maze was saved");
				}
				catch (Exception e) {
					controller.output("Error on saving the maze");
				}
			}
			else
				controller.output("Error: maze doesn't exist");
		}
		else
			controller.output("Error: please enter 2 args");
	}
	
	/**
	 * load the maze from a file
	 */
	@Override
	public void loadMaze(String[] args) {
	if (args.length==2){
			String mazeName=args[0];
			String fileName=args[1];
			if(!mazes.containsKey(mazeName))
			{
				try {
					InputStream in=new MyDecompressorInputStream(new FileInputStream(fileName+".maz"));
					int size=in.read();
					int sum=0;
					while(size==255)
					{	sum+=size;
						size=in.read();
					}
					sum+=size;
					byte b[]=new byte[sum];
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
				controller.output("ERROR: maze name is incorrect ");
		}
		else {
			controller.output("ERROR: please enter 2 args");
		}
	}
	
	
	/**
	 * return the list of the files
	 */
	public File[] listFiles(String[] args){
		if (args.length==1)
		{
			File f = new File(args[0]);
			if (f.exists() && f.isDirectory()) {
				return new File(args[0]).listFiles();
			}
			else{
				System.out.println("ERROR: directory :" + args[0] +" dosent exist");
				return null;
			}
		}
		else {
			System.out.println("ERROR: please enter 1 arg");
			return null;
		}
	}
	
	/**
	 * solve the maze
	 */
	@Override
	public void solveMaze(String[] args){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				if(args.length==2){
					String nameMaze=args[0];
					String alg=args[1];
					if (mazes.containsKey(nameMaze)){
						Maze3d maze=getMaze(nameMaze);
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
							controller.output("ERROR: you entered the wrong solution name");
					}
					else
						controller.output("ERROR: maze doesn't exist");
				}
				else 
					controller.output("ERROR: please enter 2 args");
			}
		});
		thread.start();
		threads.add(thread);
	}
	
	/**
	 * get the solution of the maze
	 */
	public Solution<Position> getSolution(String[] args){
		if ( args.length==1 )
		{
			if (mazes.containsKey(args[0]))
				{
					Solution<Position> sol= solutions.get(args[0]);
					if(sol!=null)
						return sol;
					else{
						controller.output("ERROR: there is no solution yet");
						return null;
					}
				}
			else{
				controller.output("ERROR: the maze doesn't exits");
				return null;
			}
		}
		else{
			controller.output("ERROR: please enter 1 arg");
			return null;
		}
	}
	
	/**
	 * get Cross Section
	 * axle index maze name
	 */
	public int[][] getCrossSection(String[] args){
		if (args.length==3){
			int floors= Integer.parseInt(args[1]);
			String name = args[2];
			Maze3d maze=this.mazes.get(name);
			if (maze!=null)
				if(args[0].toUpperCase().equals("X"))
					return maze.getCrossSectionByX(floors);
				else if(args[0].toUpperCase().equals("Y"))
					return maze.getCrossSectionByY(floors);
				else if(args[0].toUpperCase().equals("Z"))
					return maze.getCrossSectionByZ(floors);
				else{
					controller.output("ERROR: the axle dosen't exist");
					return null;
				}
			else{
				controller.output("ERROR: the maze is not exist");
				return null;
			}
		}
		else{
			controller.output("ERROR: please enter 3 args");
			return null;
		}
		
	}

	/**
	 * exit command
	 */
	@Override
	public void exit(String[] args) {
		for (Thread t: threads){
			t.interrupt();
		}
	}
}
