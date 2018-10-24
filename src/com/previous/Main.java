package com.previous;
import java.io.IOException;

public class Main {
	public static void main(String args[]){
		Input in = new Input("Test.txt");
		String[] numbers = new String[0];
		try {
			numbers = in.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Simulator simulator;
		simulator = in.decode(numbers);
	}
}
