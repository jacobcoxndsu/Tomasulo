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
	
	public int[] readFile() throws IOException{
		ArrayList<String> lines = new ArrayList<String>();
		FileReader in = new FileReader(fileName);
	    BufferedReader br = new BufferedReader(in);
	    String line = br.readLine();
	    while (line!=null) {
	    	line = line.replaceAll(" ", "");
	        lines.add(line);
	        line = br.readLine();
	    }
	    in.close();
	    
	    int[] n = new int[lines.size()];
	    for(int i = 0; i < lines.size(); i++){
	    	String l = lines.get(i);
	    	int number = Integer.parseInt(l);
	    	n[i] = number;
	    }
	    
	    return n;
	}
	
	public void decode(){
		
	}
}
