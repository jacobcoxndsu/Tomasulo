package com.current;
public class Simulator {

	private int cycle = 0;
	private int numCycles;
	private int head;
	
	//Components
	private int[] rf;
	private int[] rat;
	private int[][] rs; //op qj qk vj vk busy dest disp
	private int[][] eu;
	private InstructionRecord[] iq;
	
	//Structures


	public Simulator(int cy, String[] ir, String[] rg)
	{
		//Create cycles
		numCycles = cy;
		head = 0;
		
		
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
		
		int rsLocation = getEUBroadcast(eu, rs);
		
		
		//Broadcast

			if(rs[rsLocation][0] == 2)
			{
				rf[rs[rsLocation][6]] = rs[rsLocation][3] * rs[rsLocation][4];
			}
			else if (rs[rsLocation][0] == 3)
			{
				if(rs[rsLocation][4] != 0){
					rf[rs[rsLocation][6]] = rs[rsLocation][3] / rs[rsLocation][4];
				} else {
					System.out.println("Attempted to divide by zero...");
				}
			} else if(rs[rsLocation][0] == 0) {
				rf[rs[rsLocation][6]] = rs[rsLocation][3] + rs[rsLocation][4];
			} else if(rs[rsLocation][0] == 1) {
				rf[rs[rsLocation][6]] = rs[rsLocation][3] - rs[rsLocation][4];
			}
				
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
				rs[currentRS][0] = iq[head].opcode;
				
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
		
		System.out.println("\n\n\n");
		
		PrintInstructionQueue();
		
		System.out.println();
		
		PrintReservationStation();
		
		System.out.println();
		
		PrintRFandRAT();
		
	}
	
	//op qj qk vj vk busy dest disp
	public void PrintReservationStation(){
		System.out.println("                                  --ReservationStation--");
		String[] info1 = {"          ","Op", "Qj", "Qk", "Vj", "Vk", "Busy", "Dest", "Disp"};
		
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
		for(int i = iq.length - 1; i >= head; i--){
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
	
	public int getEUBroadcast(int[][] eu, int[][] rs){
		//Check Multiply/Divide first
		for(int i = 0; i < 2; i++){
			int cc = eu[i][1];
			int location = eu[i][0];
			
			int type = rs[location][0];
			
			if(type == 2){
				if(cc > 10){
					return location;
				}
			} else if(type == 2){
				if(cc > 40){
					return location;
				}
			} else if(type == 0 || type == 1){
				if(cc > 2){
					return location;
				}
			} 
		}
		 return -1;
	}

	public void Run()
	{
		Print();
		
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
