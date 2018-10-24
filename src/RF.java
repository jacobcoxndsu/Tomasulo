
public class RF {
	
	private int rf[];
	
	public RF(String[] rg){
		rf = new int[rg.length];
		for(int i = 0; i < rf.length; i++){
			rf[i] = Integer.parseInt(rg[i]);
		}
	}
	
	public int get(int i){
		return rf[i];
	}
	
	public RF Step(UD ud){
		
		return this;
	}
}
