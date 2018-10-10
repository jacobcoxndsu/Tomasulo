import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Input {
	
	private String fileName;
	
	public Input(String name){
		fileName = name;
	}
	
	public String[] readFile() throws IOException{
		ArrayList<String> lines = new ArrayList<String>();
		FileReader in = new FileReader(fileName);
	    BufferedReader br = new BufferedReader(in);
	    String line = br.readLine();
	    while (line!=null) {
	    	line = line.replaceAll("\\s", "");
	        lines.add(line);
	        line = br.readLine();
	    }
	    in.close();
	    
	    String[] n = new String[lines.size()];
	    for(int i = 0; i < lines.size(); i++){
	    	String l = lines.get(i);
	    	n[i] = l;
	    }
	    
	    return n;
	}
	
	public Simulator decode(String[] file){
		int instructionAmount = Integer.parseInt(file[0]);
		int numberOfCycles = Integer.parseInt(file[1]);
		String[] instructions = new String[instructionAmount];
		String[] registerValues = new String[file.length - instructionAmount - 2];
		
		for(int i = 2; i < instructionAmount + 2; i++){
			instructions[i - 2] = file[i];
		}
		
		for(int i = instructionAmount + 2; i < file.length; i++){
			registerValues[i - instructionAmount - 2] = file[i];
		}
		
		return new Simulator(instructionAmount, instructions, registerValues);
	}
}
