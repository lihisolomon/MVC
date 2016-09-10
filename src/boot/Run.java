package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import controller.*;
import model.*;
import view.*;

public class Run {

	public static void main(String[] args) {
		Model model=new MyModel();
		View view= new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		Controller controller=new MyController(view, model);
		view.setController(controller);
		model.setController(controller);
		view.start();
	}	

}