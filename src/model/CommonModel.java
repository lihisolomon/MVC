package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Controller;

public abstract class CommonModel implements Model {
	protected List<Thread> threads;
	protected Controller controller;	
	
	public CommonModel() {
		this.threads=new ArrayList<Thread>();
	}
	@Override
	public abstract Controller getController();

	@Override
	public abstract void setController(Controller controller);

	@Override
	public abstract void generateMaze(String[] args) ;

	@Override
	public abstract Maze3d getMaze(String name);
	@Override
	public abstract void saveMaze(String[] args) ;

	@Override
	public abstract void loadMaze(String[] args);

	@Override
	public abstract void exit(String[] args) ;

	@Override
	public abstract File[] listFiles(String[] args);

	@Override
	public abstract void solveMaze(String[] args) ;

	@Override
	public abstract Solution<Position> getSolution(String[] args) ;

	@Override
	public abstract int[][] getCrossSection(String[] args);
}
