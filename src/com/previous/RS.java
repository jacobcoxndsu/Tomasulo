package com.previous;

public class RS {
	
	private int[][] rs;
	private int indexOfInstruction;
	
	public RS(){
		// OP	Qj	Qk	Vj	Vk	Busy
		rs = new int[5][7]; // 5 Reservation Stations
	}
	
	public RS Step(IQ iq, RAT rat, RF rf){
		InstructionRecord ir = iq.getCurrentInstruction();
		indexOfInstruction = -1;
		
		if(ir.opcode == 0 || ir.opcode == 1){
			for(int i = 0; i < rs.length - 2; i++){
				if(rs[i][5] == 0){
					//put instruction into the add/subtract slot RS
					rs[i][0] = ir.opcode;
					
					if(rat.get(ir.sourceOp1) != -1){
						rs[i][1] = rat.get(ir.sourceOp1);
						indexOfInstruction = i;
					} else {
						rs[i][3] = rf.get(ir.sourceOp1);
						indexOfInstruction = i;
					}
					
					if(rat.get(ir.sourceOp2) != -1){
						rs[i][2] = rat.get(ir.sourceOp2);
						indexOfInstruction = i;
					} else {
						rs[i][4] = rf.get(ir.sourceOp2);
						indexOfInstruction = i;
					}
				    
				    rs[i][5] = 1;
				    rs[i][6] = 0;
					
					//increment IQ
					iq.Increment();
				}
				
			}
		}
		
		if(ir.opcode == 2 || ir.opcode == 3){
			for(int i = 3; i < rs.length; i++){
				if(rs[i][5] == 0){
					//put instruction into the multiply/divide slot RS
					
					if(rat.get(ir.sourceOp1) != -1){
						rs[i][1] = rat.get(ir.sourceOp1);
						indexOfInstruction = i;
					} else {
						rs[i][3] = rf.get(ir.sourceOp1);
						indexOfInstruction = i;
					}
					
					if(rat.get(ir.sourceOp2) != -1){
						rs[i][2] = rat.get(ir.sourceOp2);
						indexOfInstruction = i;
					} else {
						rs[i][4] = rf.get(ir.sourceOp2);
						indexOfInstruction = i;
					}
				    
				    rs[i][5] = 1;
				    rs[i][6] = 0;
					
					//increment IQ
					iq.Increment();
				}
				
			}
		}
		
		//Increment the CC for each one. 
		for(int i = 0; i < rs.length; i++){
			for(int j = 0; j < rs[i].length; j++){
				if(rs[i][5] == 1){
					rs[i][6]++;
				}
			}
		}
		
		return this;
	}
	
	public int getCurrentRSIndex()
	{
		return indexOfInstruction;
	}
}
