package com.current;

public class InstructionRecord {

	int opcode;
	int destOp;
	int sourceOp1;
	int sourceOp2;
	
	public InstructionRecord(int opcode, int destOp, int sourceOp1, int sourceOp2) {
		this.opcode = opcode;
		this.destOp = destOp;
		this.sourceOp1 = sourceOp1;
		this.sourceOp2 = sourceOp2;
	}
	
	public String toString(){
		String returnString = "R[";
		returnString += this.destOp + "] = ";
		returnString += "R[" + this.sourceOp1 + "] ";
		if(this.opcode == 0){
			returnString += "+ ";
		} else if(this.opcode == 1){
			returnString += "- ";
		} else if(this.opcode == 2){
			returnString += "* ";
		} else if(this.opcode == 3){
			returnString += "/ ";
		}
		returnString += "R[" + this.sourceOp2 + "]";
		
		return returnString;
	}
	
}
