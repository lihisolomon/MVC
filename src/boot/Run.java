package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.CommandsManager;
import controller.Controller;
import controller.MyController;
import model.MyModel;
import view.CLI;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		HashMap<String, Command> commands=new HashMap<>();
		//Model model=new MyModel(controller);
		Controller controller=new MyController(view, model)
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		PrintWriter out = new PrintWriter(System.out);
		CLI cli=new CLI(in,out,commands);
		cli.start();
	}	

}