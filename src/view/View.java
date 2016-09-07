package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

public interface View {

	 public void start(BufferedReader in, PrintWriter out,HashMap<String, Command> commands);
}
