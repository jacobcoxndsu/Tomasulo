
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
	
	public RAT Step(RF rf, RAT rat, RS rs, IQ iq, UD ud)
	{
		issue(rf,rat,rs,iq,ud);
		broadcast(rf,rat,rs,iq,ud);
		return this;
	}
	
	public void issue(RF rf, RAT rat, RS rs, IQ iq, UD ud)
	{
		//Tag dest reg
		int ratDestIndex = iq.getCurrentInstruction().destOp;
		rat[ratDestIndex] = rs.getCurrentRSIndex();
	}
	

	public void broadcast(RF rf, RAT rat, RS rs, IQ iq, UD ud)
	{
		int ratDestIndex = iq.getCurrentInstruction().destOp;
		rat[ratDestIndex] = -1;
	}
}
