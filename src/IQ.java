
public class IQ {
	
	private InstructionRecord[] iq;
	private int currentInstruction;
	
	public IQ(String[] ir){
		currentInstruction = 0;
		
		iq = new InstructionRecord[ir.length];
		for (int i = 0; i < ir.length; i++)
		{
			int opcode = Character.getNumericValue(ir[i].charAt(0));
			int destOp = Character.getNumericValue(ir[i].charAt(1));
			int sourceOp1 = Character.getNumericValue(ir[i].charAt(2));
			int sourceOp2 = Character.getNumericValue(ir[i].charAt(3));
			InstructionRecord inst = new InstructionRecord(opcode, destOp, sourceOp1, sourceOp2);
			iq[i] = inst;
		}
	}
	
	public IQ Step(){
		
		return this;
	}
	
	public void Increment()
	{
		currentInstruction++;
	}
	
	public InstructionRecord getCurrentInstruction(){
		if(currentInstruction < iq.length && currentInstruction > -1){
			return iq[currentInstruction];
		}
		
		System.out.println("In IQ: Instruction asked for does not exist...");
		return null;
		
	}
	
	
}
