package model;

import algorithms.mazeGenerators.Maze3d;

/**
 * Model Interface
 */
public interface Model {

	public void generateMaze(String name, int floors ,int rows, int cols);
	public Maze3d getMaze(String name);
	public void saveMaze(String[] args);
	public void loadMaze(String[] args);
	public void exit(String[] args);
}
