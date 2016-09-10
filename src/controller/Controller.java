package controller;

import model.Model;
import view.View;

/**
 * Controller Interface
 *
 */
public interface Controller {
	public void notifyMazeIsReady(String name);
	public void output(String str);
	public View getView();
	public void setView(View view);
	public Model getModel();
	public void setModel(Model model);
}
