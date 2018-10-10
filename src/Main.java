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
		
		if(numbers.length > 0){
			for(int i = 0; i < numbers.length; i++){
				//System.out.println(numbers[i]);
			}
		}
		
		Simulator simulator;
		simulator = in.decode(numbers);
		
		System.out.println(simulator);
	}
}
