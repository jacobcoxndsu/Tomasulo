
public class Simulator {
	
	private int cycles;
	private InstructionRecord[] instructionRecords;
	private int[] registerFile;
	private int[] RAT;
	private ReservationStation[] addSubRS;
	private ReservationStation[] multDivRS;

	public Simulator(int cy, String[] ir, String[] rg) {
		
		//cycles
		cycles = cy;
		
		//instructionRecords
		instructionRecords = new InstructionRecord[ir.length];
		for(int i = 0; i < ir.length; i++){
			int opcode = ir[i].charAt(0);
			int destOp = ir[i].charAt(1);
			int sourceOp1 = ir[i].charAt(2);
			int sourceOp2 = ir[i].charAt(3);
			InstructionRecord inst = new InstructionRecord(opcode, destOp, sourceOp1, sourceOp2);
			instructionRecords[i] = inst;
		}
		
		//registerFile
		
		//RAT
		
		//addSubRS
		
		//multDivRS
	}
	
	public String toString(){
		String s = "";
		for(int i = 0; i < instructionRecords.length; i++){
			s += instructionRecords[i].toString() + "\n";
		}
		
		return s;
	}

}
