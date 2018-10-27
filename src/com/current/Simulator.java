package com.current;
public class Simulator {

	private int cycle = 0;
	private int numCycles;
	private int head;
	
	//Components
	private int[] rf;
	private int[] rat;
	private int[][] rs; //busy op Vj Vk Qj Qk Dest Disp
	private int[][] eu;
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
		rs = new int[5][8];
		//Create ud
		eu = new int[2][2];
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
	
	private int[] rf_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq){
		//Issue
		
		//Dispatch
		
		//Broadcast
			//Update the RAT
			
		return rf;
	}
	
	private int[] rat_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq){
		//Issue
			
		//Dispatch
		
		//Broadcast
		
		return rat;
	}
	
	private int[][] rs_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq){
		//Issue
		//busy op Vj Vk Qj Qk Dest Disp
		//Take next inst from IQ
		int currentRS;
		if(iq[cycle].opcode == 0 || iq[cycle].opcode == 1)
		{
			currentRS = 0;
			if(rs[currentRS][0] != 1)//check the busy bit
			{
				//Take the instruction from IQ
				rs[currentRS][0] = 1;
				//op dest src1 src2
				//Determine where inputs come from
				if(rat[iq[cycle]] != -1)
				{
					//Use the value tag in RS
					rs[currentRS][3] = rat[iq[cycle]];
				}
				
			}
			else
			{
				//Go to the next RS
				currentRS++;
			}
			
		}
		else
		{
			
		}
		
		//Dispatch
		
		//Broadcast
		
		return rs;
	}
	
	private int[][] eu_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq){
		//Issue
		
		//Dispatch
		
		//Broadcast
		if(eu[1][1] == 0)//mult/div is ready
		{
			
			int euLocation = eu[1][0];
			if(rs[euLocation][0] == 2)
			{
				rf[rs[euLocation][6]] = rs[euLocation][3] * rs[euLocation][4];
			}
			else
			{
				if(rs[euLocation][4] != 0){
					rf[rs[euLocation][6]] = rs[euLocation][3] / rs[euLocation][4];
				}
			}
				
		}
		else if(eu[0][1] == 0)//add/sub is ready
		{
			int euLocation = eu[0][0];
			if(rs[euLocation][0] == 0)
			{
				rf[rs[euLocation][6]] = rs[euLocation][3] + rs[euLocation][4];
			}
			else
			{
				rf[rs[euLocation][6]] = rs[euLocation][3] - rs[euLocation][4];
			}
		}
		return eu;
	}
	
	private InstructionRecord[] iq_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq){
		//Issue

		boolean canIssue = false;
		int tempOpcode = iq[head].opcode;
		
		//if()
		
		for(int i = 0; i < rs.length; i++){
			for(int j = 0; j < rs[i].length; i++){
				
			}
		}
		
		//Dispatch
		
		//Broadcast
		
		return iq;
	}
	
	public void Print(){
		
		PrintInstructionQueue();
		
		System.out.println();
		
		PrintReservationStation();
		
		System.out.println();
		
		PrintRFandRAT();
		
	}
	
	public void PrintReservationStation(){
		System.out.println("                                  --ReservationStation--");
		String[] info1 = {"          ","Busy", "Op", "Vj", "Vk", "Qj", "Qk", "Dest", "Disp"};
		
		System.out.format("%-11s%-11s%-11s%-11s%-11s%-11s%-11s%-11s%-11s\n", info1);
		System.out.println("--------------------------------------------------------------------------------------------");
		
		for(int i = 0; i < rs.length; i++){
			String[] info2 = new String[9];
			info2[0] = "    RS" + (i + 1) + "  ";
			info2[1] = String.valueOf(rs[i][0]);
			info2[2] = String.valueOf(rs[i][1]);
			info2[3] = String.valueOf(rs[i][2]);
			info2[4] = String.valueOf(rs[i][3]);
			info2[5] = String.valueOf(rs[i][4]);
			info2[6] = String.valueOf(rs[i][5]);
			info2[7] = String.valueOf(rs[i][6]);
			info2[8] = String.valueOf(rs[i][7]);
			
			System.out.format("%-11s%-11s%-11s%-11s%-11s%-11s%-11s%-11s%-11s\n", info2);
		}

	}
	
	public void PrintInstructionQueue(){
		System.out.println("--Instruction Queue--");
		for(int i = 0; i < iq.length; i++){
			System.out.println(" " + iq[i]);
		}
	}
	
	public void PrintRFandRAT(){
		System.out.println(" --RF--       --RAT--");
		
		for(int i = 0; i < rf.length; i++){
			System.out.print(i + ":    " + rf[i]);
			if(rat[i] != -1){
				if(rf[i] > 9){
					System.out.print("        RS" + rat[i]);
				} else {
					System.out.print("         RS" + rat[i]);
				}
			}
			
			System.out.println();
		}
	}

	public void Run()
	{
		while(cycle < numCycles){
			
			//Components Step
			int[] tempRf = rf_step(rf, rat, rs, eu, iq);
			int[] tempRat = rat_step(rf, rat, rs, eu, iq);
			int[][] tempRs = rs_step(rf, rat, rs, eu, iq);
			int[][] tempEu = eu_step(rf, rat, rs, eu, iq);
			InstructionRecord[] tempIq = iq_step(rf, rat, rs, eu, iq);
			
			//Components remade
			rf = tempRf;
			rat = tempRat;
			rs = tempRs;
			eu = tempEu;
			iq = tempIq;
			
			//Print results
			//Print();
			
			//Increments Cycle
			cycle++;
		}
		
		Print();
	
		//System.out.println("Finished");
	}
}
