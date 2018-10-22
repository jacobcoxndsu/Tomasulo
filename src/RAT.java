
public class RAT {

	private int[] RatArray;
	
	public RAT(int length) {
		// TODO Auto-generated constructor stub
		RatArray = new int[length];
				
		for (int i = 0; i < length; i++)
		{
			RatArray[i] = -1;
		}
	}
	
	public RAT Step()
	{
		issue();
		broadcast();
		return this;
	}
	
	public 
	

}
