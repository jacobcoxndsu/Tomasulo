package com.current;
public class Simulator {

	private int cycle = 0;
	private int numCycles;
	private int head;
	
	//Components
	private int[] rf;
	private int[] rat;
	private int[][] rs; //OP	Qj	Qk	Vj	Vk	Busy
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
		for(int i = 0; i < eu.length; i++){
			
		}
		
		return rat;
	}
	
	private int[][] rs_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq){
		rs = rs.clone();
	//Issue
		
		//Take next inst from IQ
		int currentRS;
		int endOfRS;
		//Is it an add/sub or mult/div?
		if(iq[head].opcode == 0 || iq[head].opcode == 1)
		{
			currentRS = 0;
			endOfRS = 2;
		}
		else
		{
			currentRS = 3;
			endOfRS = 4;
		}
		
		while(currentRS <= endOfRS)
		{
			//check the busy bit
			if(rs[currentRS][5] == 0)
			{
				//Take the instruction from IQ - set busy bit
				rs[currentRS][5] = 1;
				
				//Determine where inputs come from
				if(rat[iq[head].sourceOp1] != -1)
				{
					//Use the value tag in RS
					rs[currentRS][1] = rat[iq[head].sourceOp1];
					rs[currentRS][3] = -1;
				}
				else
				{
					//use the value from rf
					rs[currentRS][3] = rf[iq[head].sourceOp1];
					rs[currentRS][1] = -1;
				}
				
				//Do the same for second source
				if(rat[iq[head].sourceOp2] != -1)
				{
					//Use the value tag in RS
					rs[currentRS][2] = rat[iq[head].sourceOp2];
					rs[currentRS][4] = -1;
				}
				else
				{
					//use the value from rf
					rs[currentRS][4] = rf[iq[head].sourceOp2];
					rs[currentRS][2] = -1;
				}
				//set the destination register
				rs[currentRS][6] = iq[head].destOp;

				break;

			}
			else
			{
				//Go to the next RS
				currentRS++;
			}
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
	
	private InstructionRecord[] iq_step(int[][] rs, InstructionRecord[] iq){
		//get temp variables
		rs = rs.clone();
		iq = iq.clone();

		boolean canIssue = false;
		int tempOpcode = iq[head].opcode;
		
		if(tempOpcode == 0 || tempOpcode == 1){
			for(int i = 0; i < rs.length - 2; i++){
				if(rs[i][0] != 1){
					canIssue = true;
				}
			}
		} else if(tempOpcode == 2 || tempOpcode == 3){
			for(int i = 2; i < rs.length; i++){
				if(rs[i][0] != 1){
					canIssue = true;
				}
			}
		}
		
		if(canIssue){
			head++;
		}
		
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
		for(int i = iq.length - 1; i > head; i--){
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
	
	//public int get

	public void Run()
	{
		while(cycle < numCycles){
			
			//Components Step
			int[] tempRf = rf_step(rf, rat, rs, eu, iq);
			int[] tempRat = rat_step(rf, rat, rs, eu, iq);
			int[][] tempRs = rs_step(rf, rat, rs, eu, iq);
			int[][] tempEu = eu_step(rf, rat, rs, eu, iq);
			InstructionRecord[] tempIq = iq_step(rs, iq);
			
			//Components remade
			rf = tempRf;
			rat = tempRat;
			rs = tempRs;
			eu = tempEu;
			iq = tempIq;
			
			//Print results
			Print();
			
			//Increments Cycle
			cycle++;
		}
		
		Print();
	
		//System.out.println("Finished");
	}
}
