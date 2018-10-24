package com.current;
public class Simulator {

	private int cycle = 0;
	private int numCycles;
	
	//Components
	private int[] rf;
	private int[] rat;
	private int[][] rs = new int[5][6];
	private int[][] ud;
	private InstructionRecord[] iq;
	
	//Structures


	public Simulator(int cy, String[] ir, String[] rg)
	{
		//Create cycles
		numCycles = cy;
		
		//Create rf
		rf = new int[rg.length];
		for(int i = 0; i < rf.length; i++){
			rf[i] = Integer.parseInt(rg[i]);
		}
		//Create rat
		rat = new int[rg.length];
		for(int i = 0; i < rat.length; i++){
			rat[i] = -1;
		}
		//Create rs
		rs = new int[5][6];
		//Create ud
		ud = new int[2][2];
		//Fill in Instructions
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
		
		//Finished creating now step
		Run();
	}
	
	private int[] rf_step(){
		//Issue
		
		//Dispatch
		
		//Broadcast
		
	}
	
	private int[] rat_step(){
		//Issue
		
		//Dispatch
		
		//Broadcast
		
	}
	
	private int[][] rs_step(){
		//Issue
		
		//Dispatch
		
		//Broadcast
		
	}
	
	private int[][] ud_step(){
		//Issue
		
		//Dispatch
		
		//Broadcast
		
	}
	
	private int[] iq_step(){
		//Issue
		
		//Dispatch
		
		//Broadcast
		
	}
	
	
	public void Print(){
		for(int i = 0; i < iq.length; i++){
			System.out.println(iq[i]);
		}
	}

	public void Run()
	{
		while(cycle < numCycles){
			
			//Components Step
			int[] tempRf = rf_step();
			int[] tempRf = rf_step();
			int[] tempRf = rf_step();
			int[] tempRf = rf_step();
			int[] tempRf = rf_step();
			
			//Components remade
			rf = tempRf;
			
			//Print results
			Print();
			
			//Increments Cycle
			cycle++;
		}
	
		System.out.println("Finished");
	}
}
