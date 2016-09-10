package controller;

import model.Model;
import view.View;

public class MyController implements Controller{
	private View view;
	private Model model;
	private CommandsManager commandsManager;
	
	/**
	 * Controller CTOR
	 * @param view
	 * @param model
	 */
	public MyController(View view, Model model) {
		this.view = view;
		this.model = model;
		
		commandsManager = new CommandsManager(model, view);
		view.setCommands(commandsManager.getCommandsMap());
	}
	/**
	 * sending a message to the view
	 * @param string the message we want
	 */
	public void output(String string) 
	{
		view.printOutput(string);
	}
	
	@Override
	public void notifyMazeIsReady(String name) {
		view.notifyMazeIsReady(name);
	}
	public View getView() {
		return view;
	}
	public void setView(View view) {
		this.view = view;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	
}
