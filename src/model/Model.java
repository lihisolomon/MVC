package model;

import java.io.File;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;

/**
 * Model Interface
 */
public interface Model {
	public Controller getController() ;
	public void setController(Controller controller);
	public void generateMaze(String[] args);
	public Maze3d getMaze(String name);
	public void saveMaze(String[] args);
	public void loadMaze(String[] args);
	public void exit(String[] args);
	public File[] listFiles(String[] args);
	public void solveMaze(String[] args);
	public Solution<Position> getSolution(String[] args);
	public int[][] getCrossSection(String[] args);
}
