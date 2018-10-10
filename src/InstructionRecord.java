
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
		return "Instruction: op: " + this.opcode + " destOp: " + this.destOp + " sourceOp1: " + this.sourceOp1 + " sourceOp2: " + this.sourceOp2;
	}
	
}
