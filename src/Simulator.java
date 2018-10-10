
public class Simulator {
	
	private int cycles;
	private InstructionRecord[] instructionRecords;
	private int[] registerFile;
	private int[] RAT;
	private ReservationStation[] addSubRS;
	private ReservationStation[] multDivRS;

	public Simulator(int cy, int[] ir, int[] rg) {
		
		//cycles
		cycles = cy;
		
		//instructionRecords
		instructionRecords = new InstructionRecord[ir.length];
		for(int i = 0; i < ir.length; i++){
			int opcode = Integer.toString(ir[i]).charAt(0);
			int destOp = Integer.toString(ir[i]).charAt(1);
			int sourceOp1 = Integer.toString(ir[i]).charAt(2);
			int sourceOp2 = Integer.toString(ir[i]).charAt(3);
			InstructionRecord inst = new InstructionRecord(opcode, destOp, sourceOp1, sourceOp2);
			instructionRecords[i] = inst;
		}
		
		//registerFile
		
		//RAT
		
		//addSubRS
		
		//multDivRS
	}

}
