
public class RS {
	
	private int[][] rs;
	
	public RS(){
		// OP	Qj	Qk	Vj	Vk	Busy
		rs = new int[5][6]; // 5 Reservation Stations
	}
	
	public RS Step(IQ iq, RAT rat, RF rf){
		InstructionRecord ir = iq.getCurrentInstruction();
		
		if(ir.opcode == 0 || ir.opcode == 1){
			for(int i = 0; i < rs.length - 2; i++){
				if(rs[i][6] == 0){
					//put instruction into the add/subtract slot RS
					rs[i][0] = ir.opcode;
					
					if(rat.get(ir.sourceOp1) != -1){
						rs[i][1] = rat.get(ir.sourceOp1);
					} else {
						rs[i][3] = rf.get(ir.sourceOp1);
					}
					
					if(rat.get(ir.sourceOp2) != -1){
						rs[i][2] = rat.get(ir.sourceOp1);
					} else {
						rs[i][4] = rf.get(ir.sourceOp2);
					}
				    
				    rs[i][5] = 1;
					
					//increment IQ
					iq.Increment();
				}
			}
		}
		
		if(ir.opcode == 2 || ir.opcode == 3){
			for(int i = 3; i < rs.length; i++){
				if(rs[i][6] == 0){
					//put instruction into the multiply/divide slot RS
					
					
					//increment IQ
					iq.Increment();
				}
			}
		}
		return this;
	}
}
