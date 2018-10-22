
public class UD {
	
	private int[][] ud;
	
	public UD(){
		ud = new int[10][2];
		
		for(int i = 0; i < ud.length; i++){
			for(int j = 0; j < ud[i].length; j++){
				ud[i][j] = -1;
			}
		}
	}
	
	public UD Step(){
		return this;
	}
}
