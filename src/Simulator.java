
public class Simulator {
	
	private boolean RUNNING = true;
	private int cycle = 0;
	private int numCycles;
	private InstructionRecord[] instructionRecords;
	private int[] registerFile;
	private int[] RAT;
	private ReservationStation[] addSubRS;
	private ReservationStation[] multDivRS;

	public Simulator(int cy, String[] ir, String[] rg) {
		
		//cycles
		numCycles = cy;
		
		//instructionRecords
		instructionRecords = new InstructionRecord[ir.length];
		for(int i = 0; i < ir.length; i++){
			int opcode = Character.getNumericValue(ir[i].charAt(0));
			int destOp = Character.getNumericValue(ir[i].charAt(1));
			int sourceOp1 = Character.getNumericValue(ir[i].charAt(2));
			int sourceOp2 = Character.getNumericValue(ir[i].charAt(3));
			InstructionRecord inst = new InstructionRecord(opcode, destOp, sourceOp1, sourceOp2);
			instructionRecords[i] = inst;
		}
		
		//registerFile
		registerFile = new int[rg.length];
		for(int i = 0; i < rg.length; i++){
			registerFile[i] = Integer.parseInt(rg[i]);
		}
		
		//RAT
		RAT = new int[8];
		for(int i = 0; i < 8; i++){
			RAT[i] = -1;
		}
		
		//addSubRS
		addSubRS = new ReservationStation[3];
		
		//multDivRS
		multDivRS = new ReservationStation[2];
		
		Run();
	}
	
	public void Run(){
		while(RUNNING){
			
			Issue();
			Dispatch();
			Broadcast();
			Commit();
			
			if(cycle > numCycles){
				RUNNING = false;
			}
			
			cycle++;
		}
		
		System.out.println("Finished");
	}
	
	public void Issue(){
		
	}
	
	public void Dispatch(){
		
	}
	
	public void Broadcast(){
		
	}
	
	public void Commit(){
		
	}
	
	public int getCycle(){
		return cycle;
	}
	
	public String toString(){
		String s = "";
		for(int i = 0; i < instructionRecords.length; i++){
			s += instructionRecords[i].toString() + "\n";
		}
		
		return s;
	}

}
