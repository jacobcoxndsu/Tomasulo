
public class RS {
	
	private int[][] rs;
	
	public RS(){
		// OP	Qj	Qk	Vj	Vk	Busy
		rs = new int[5][6]; // 5 Reservation Stations
	}
	
	public RS Step(){
		return new RS();
	}
}
