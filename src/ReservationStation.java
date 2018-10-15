
public class ReservationStation {
	
	boolean busy;
	int op;
	int vj;
	int vk;
	int qj;
	int qk;
	int disp;

	public ReservationStation() {
		this.busy = false;
		this.op = -1;
		this.vj = -1;
		this.vk = -1;
		this.qj = -1;
		this.qk = -1;
		this.disp = -1;
	}
	
	public boolean isEmpty(){
		return busy;
	}
}
