package controller;

import model.Model;
import view.View;

public abstract class CommonController implements Controller {
	protected View view;
	protected Model model;
	protected CommandsManager commandsManager;
	
	/**
	 * Controller CTOR
	 * @param view
	 * @param model
	 */
	public CommonController(View view, Model model) {
		this.view = view;
		this.model = model;
		
		commandsManager = new CommandsManager(model, view);
		view.setCommands(commandsManager.getCommandsMap());
	}

	@Override
	public abstract void notifyMazeIsReady(String name);

	@Override
	public abstract void output(String str);

	@Override
	public abstract View getView();

	@Override
	public abstract void setView(View view) ;

	@Override
	public abstract Model getModel();

	@Override
	public abstract void setModel(Model model);
}
