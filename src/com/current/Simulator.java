package com.current;

import java.util.Scanner;

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
	private int[][] rob;
	private int issuePointer;
	private int commitPointer;
	
	//Structures

	//DOUBLE CHECK
	public Simulator(int cy, int ns, String[] ir, String[] rg)
	{
		//Create cycles
		numCycles = ns;
		head = 0;
		
		
		//Create rf
		rf = new int[rg.length];
		for(int i = 0; i < rf.length; i++){
			rf[i] = Integer.parseInt(rg[i]) + (int)(Math.random());
		}
		//Create rat
		rat = new int[rg.length];
		for(int i = 0; i < rat.length; i++){
			rat[i] = -1;
		}
		//Create rs
		rs = new int[5][8];
		for(int i = 0; i < rs.length; i++){
			rs[i][0] = -1;//op
			rs[i][1] = -1;// rob tag 1
			rs[i][2] = -1;// rob tag 2
			rs[i][3] = -1;// value 1
			rs[i][4] = -1;//value 2
			rs[i][5] = -1;// busy flag
			rs[i][6] = -1;//dest rob tag
			rs[i][7] = -1;//dispatch ready tag
	
		}
		//Create ud
		eu = new int[2][7];
		for(int i = 0; i < eu.length; i++)
		{
			eu[i][0] = -1;//op
			eu[i][1] = -1;//rob tag
			eu[i][2] = -1;//val1
			eu[i][3] = -1;//val2
			eu[i][4] = -1;//number of cc
			eu[i][5] = -1;//Broadcast flag
			eu[i][6] = -1;//Exception flag
		}
		
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
		
		//Create ROB
		rob = new int[6][4];
		for(int i = 0; i < rob.length; i++){
			rob[i][0] = -1; //REG
			rob[i][1] = -1; //VAL
			rob[i][2] = -1; //Done
			rob[i][3] = -1; //Exception
		}
		
		//Create pointers
		issuePointer = 0;
		commitPointer = 0;
		
		
		//Finished creating now step
		Run();
	}
	
	//NEED TO UPDATE
	private int[] rf_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq, int[][] rob){
		rf = rf.clone();
		//Issue
		
		//Dispatch
		
		//Commit
			
		int register = rob[commitPointer][0];
		if(rob[commitPointer][2] == 1){
			rf[register] = rob[commitPointer][1];//Rob value goes to rf
		}
			
		return rf;
	}
	
	//DONE
	private int[] rat_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq, int[][] rob){
		rat = rat.clone();
		//Issue
		if(head < iq.length && (getFreeRob(rob) != -1))
		{	
			rat[iq[head].destOp] = issuePointer;
		} 
		
		//Commit
		 //need the row of the rob (commit pointer)
		if(rob[commitPointer][2] != -1)//When we are ready to commit - if it has broadcasted and commitPointer
		{
			rat[rob[commitPointer][0]] = -1;
		}
		
		return rat;
	}
	//DONE
	private int[][] rs_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq, int[][] rob){
		rs = rs.clone();
		
		//Dispatch - free the RS - done before issue to avoid issue and dispatch in same cycle.
		
		//Set the dispatch flags
		for(int rsRow = 0; rsRow < rs.length; rsRow++)
		{
			if(rsDispatchReady(rs, rsRow))
			{
				rs[rsRow][7] = 1;
			}
		}
		//Find the next one that will be dispatched
		int rsLocation = getNextRSDispatch(rs);//which RS entries are supposed to be freed?
		int euLocation = getFreeEU(eu,rs);
		if((rsLocation != -1) && (euLocation != -1))//if the values are ready
		{
			rs[rsLocation][0] = -1;
			rs[rsLocation][1] = -1;
			rs[rsLocation][2] = -1;
			rs[rsLocation][3] = -1;
			rs[rsLocation][4] = -1;
			rs[rsLocation][5] = -1;
			rs[rsLocation][6] = -1;
		}
		
		//Issue

			//Instruction in the queue, ROB is ready, RS is empty
			if(head < iq.length /*&& (getFreeRob(rob) != -1)*/){
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
					if(rs[currentRS][5] == -1)
					{
						rs[currentRS][0] = iq[head].opcode;
						
						//Take the instruction from IQ - set busy bit
						rs[currentRS][5] = 1;
						
						//Determine where inputs come from
						if(rat[iq[head].sourceOp1] != -1) //changed from rat
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
						if(rat[iq[head].sourceOp2] != -1) //changed from rat
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
						//set the destination ROB Tag
						rs[currentRS][6] = issuePointer;

						head++;//Point to  next instruction
						break;

					}
					else
					{
						//Go to the next RS
						currentRS++;
					}
				}//Exit while loop - never put the instruction into an RS		
			}
		
		return rs;
	}
	
	private void setEU(int[][] eu, int[][] rs, int location, int euLocation) {
		eu[euLocation][0] = rs[location][0]; // rs pcode -> eu opcode
		eu[euLocation][1] = rs[location][6]; // rs destination -> eu robtag
		eu[euLocation][2] = rs[location][3]; // rs Vj -> eu Val1
		eu[euLocation][3] = rs[location][4]; // rs Vk -> eu Val2
		
		int opcode = rs[location][0];
		if(opcode == 0) {
			eu[euLocation][4] = 2;
		} else if(opcode == 1) {
			eu[euLocation][4] = 2;
		} else if(opcode == 2) {
			eu[euLocation][4] = 10;
		} else if(opcode == 3) {
			eu[euLocation][4] = 40;
		}
		
		eu[euLocation][5] = -1;
		eu[euLocation][6] = -1;
	}
	
	//NEED TO UPDATE
	private int[][] eu_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq){
		eu = eu.clone();
		//Issue
		
		//What is this doing?
		//Dispatch
			//Receive the instruction from the RS
		
		for(int i = 0; i < rs.length; i++) {
			if(rs[i][7] == 1) {
				int opcode = rs[i][0];
				//Add/Subtract
				if(opcode == 0 || opcode == 1) {
					//If the add/subtract space is free
					if(eu[0][1] != -1) {
						setEU(eu, rs, i, 0);
					} 
				} else if(opcode == 2 || opcode == 3) {
					//If the multiply/divide space is free
					if(eu[1][1] != -1) {
						setEU(eu, rs, i, 1);
					} 
				}
			}
		}
		
		if(eu[1][0] == 3 && eu[1][3] == 0) {
			eu[1][6] = 1;
		}

		
		//Broadcast
		//Check the broadcast flag
		//Clear the first entry found (and only the first one)
		

		
		//Executing
			//Check if cycles equal 1 
			//Set the broadcast flag (make a function)
		for(int euRows = 0; euRows < eu.length; euRows++)	
		{
			if(eu[euRows][4] == 1)
			{
				eu[euRows][5] = 1;
			}
			//decrement the cc for each 
			eu[euRows][4]--;
		}
			
			
		
		
		return eu;
	}
	//NEED TO CHECK
	private int[][] rob_step(int[] rf, int[] rat, int[][] rs, int[][]eu, InstructionRecord[] iq, int[][] rob){
		rob = rob.clone();
		//Issue - put instruction into the ROB if available
		if(head < iq.length && (getFreeRob(rob) != -1))
		{
			rob[issuePointer][0] = iq[head].destOp;
			moveRobIssuePointerToFree(rob);
			return rob;
		}
		
		//Broadcast - capture the result into ROB, set DONE
		int euLocation = getEUIndexForBroadcast(eu);
		if(euLocation != -1)//ready to receive a broadcast
		{
			int robLocation = eu[euLocation][2];
			//go to entry of rob with dst tag
			rob[robLocation][1] = calculate(eu,rs, robLocation);//Value
			rob[robLocation][2] = 1; //Done
			rob[robLocation][3] = eu[euLocation][6];//Exception
			return rob;//Exit before the commit
		}
		
		//Commit - clear ROB entry
		//Exception - need to add
		if((rob[commitPointer][2] == 1) && rob[commitPointer][3] == 1)//ROB entry is ready to commit and has an exception
		{
			for(int nextRobEntry = commitPointer + 1; nextRobEntry < rob.length; nextRobEntry++)
			{
				for(int j = 0; j < 4; j++)
				{
					rob[nextRobEntry][j] = -1;
				}
			}
		}
		
		//Clear the rob entry after a commit
		if(rob[commitPointer][2] == 1) 
		{
			for(int j = 0; j < 4; j++)
			{
				rob[commitPointer][j] = -1;
			}
			commitPointer++;
			if(commitPointer > rob.length - 1)
			{
				commitPointer = rob.length - 1;
			}
		}
		
		return rob;
	}
	
	//DONE
	private InstructionRecord[] iq_step(int[][] rs, InstructionRecord[] iq){
		//get temp variables
		rs = rs.clone();
		iq = iq.clone();
			
		return iq;
	}
	
	//NEED TO UPDATE
	public void Print(){
		
		System.out.println("\n\n\n");
		
		System.out.println("Cycle: " + cycle);
		
		PrintInstructionQueue();
		
		System.out.println();
		
		PrintReservationStation();
		
		System.out.println();
		
		PrintExecutionUnit();
		
		System.out.println();
		
		PrintRFandRATandROB();
		
	}
	
	//op qj qk vj vk busy dest disp
	public void PrintReservationStation(){
		System.out.println("                                  --ReservationStation--");
		String[] info1 = {"          ","Op", "Qj", "Qk", "Vj", "Vk", "Busy", "Dest"};
		
		System.out.format("%-11s%-11s%-11s%-11s%-11s%-11s%-11s%-11s\n", info1);
		System.out.println("---------------------------------------------------------------------------------");
		
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
			
			System.out.format("%-11s%-11s%-11s%-11s%-11s%-11s%-11s%-11s\n", info2);
		}

	}
	
	public void PrintExecutionUnit() {
		System.out.println("                                  --Execution unit--");
		String[] info1 = {"          ","Op", "RobTag", "Val1", "Val2", "cc", "BCReady", "Exception"};
		
		System.out.format("%-11s%-11s%-11s%-11s%-11s%-9s%-11s%-11s\n", info1);
		System.out.println("------------------------------------------------------------------------------------");
		
		for(int i = 0; i < eu.length; i++){
			String[] info2 = new String[9];
			info2[0] = "    RS" + (i + 1) + "  ";
			info2[1] = String.valueOf(eu[i][0]);
			info2[2] = String.valueOf(eu[i][1]);
			info2[3] = String.valueOf(eu[i][2]);
			info2[4] = String.valueOf(eu[i][3]);
			info2[5] = String.valueOf(eu[i][4]);
			info2[6] = String.valueOf(eu[i][5]);
			info2[7] = String.valueOf(eu[i][6]);
			
			System.out.format("%-11s%-11s%-11s%-11s%-11s%-11s%-11s%-11s\n", info2);
		}
	}
	
	public void PrintInstructionQueue(){
		System.out.println("--Instruction Queue--");
		for(int i = iq.length - 1; i >= head; i--){
			System.out.println(" " + iq[i]);
		}
	}
	
	public void PrintRFandRATandROB(){
		System.out.println(" --RF--       --RAT--               --Reg--  --Value--  --Done--  --Exception--");
		
		for(int i = 0; i < rf.length; i++) {
			if(i < rf.length && i < rat.length && i < rob.length) 
			{
				System.out.format(" " + i + ": %-10dRS" + i + ": %-9d  RB" + (i + 1) + " :    %-10d%-10d%-13d%-5d", rf[i], rat[i], rob[i][0], rob[i][1], rob[i][2], rob[i][3]);
				
			} else if(i < rf.length && i < rat.length) 
			{
				System.out.format(" " + i + ": %-10dRS" + i + ": %-9d", rf[i], rat[i]);
				
			} else if(i < rf.length) 
			{
				System.out.format(" " + i + ": %d", rf[i]);
			}
			
			System.out.println();
		}
	}
	
	//NEED TO UPDATE
	public int getEUIndexForBroadcast(int[][] eu){
		//Check Multiply/Divide first
		for(int euRow = 0; euRow < 2; euRow++)
		{
			int cc = eu[euRow][4];
			int robTag = eu[euRow][1];
			int opType = eu[euRow][0];
			int broadcastFlag = eu[euRow][5];
			
			if(robTag != -1)//There is an entry in the EU
			{
				if((opType == 2 || opType == 3) && (broadcastFlag == 1))
				{
					return euRow;
				} 
				else if(broadcastFlag == 1)
				{
					return euRow;
				} 
			}
		}
		 return -1;
	}
	
	//NEED TO UPDATE
	public int getEUBroadcastInEU(int[][] eu){
		//Check Multiply/Divide first
		for(int i = 0; i < 2; i++){
			int cc = eu[i][1];
			int location = eu[i][2];
			
			if(location != -1){
				int operationType = eu[location][3];
				
				/*switch(operationType)
				{
					case 0:
					case 1:
						if(cc > 2)
						{
							return i;
						}
					case 2:
					case 3:
				}*/
				if(operationType == 2){
					if(cc > 10){
						return i;
					}
				} else if(operationType == 3){
					if(cc > 40){
						return i;
					}
				} else if(operationType == 0 || operationType == 1){
					if(cc > 2){
						return i;
					}
				} 
			}
		}
		 return -1;
	}
	
	//NEED TO UPDATE - depends on getEUBroadcastInEU
	public int calculate(int[][]eu, int[][] rs, int rbLocation) {
		
		for(int i = 0; i < eu.length; i++) {
			if(eu[i][1] == rbLocation) {
				if(eu[i][5] == 1) {
					//Ready to BroadCast and has a location
					int opcode = eu[i][0];
					
					//Add
					if(opcode == 0) {
						return eu[i][2] + eu[i][3];
					//Subtract
					} else if(opcode == 1) {
						return eu[i][2] - eu[i][3];
					//Multiply
					} else if(opcode == 2) {
						return eu[i][2] * eu[i][3];
					//DIvide
					} else if(opcode == 3) {
						if(eu[i][3] != 0) {
							return eu[i][2] / eu[i][3];
						}
					}
				}
			}
		}
		
		return -1;
	}

	//DONE
	public void Run()
	{
		PrintInstructionQueue();
		System.out.println("\n\n");
		
		while(cycle < numCycles){
			
			//Components Step
			int[] tempRf = rf_step(rf, rat, rs, eu, iq, rob);
			int[] tempRat = rat_step(rf, rat, rs, eu, iq, rob);
			int[][] tempRs = rs_step(rf, rat, rs, eu, iq, rob);
			int[][] tempEu = eu_step(rf, rat, rs, eu, iq);
			int[][] tempRob = rob_step(rf, rat, rs, eu, iq, rob);
			InstructionRecord[] tempIq = iq_step(rs, iq);
			
			//Components remade
			rf = tempRf;
			rat = tempRat;
			rs = tempRs;
			eu = tempEu;
			iq = tempIq;
			rob = tempRob;
			
			//Print results
			Print();
			
			//Increments Cycle
			cycle++;
		}
		
		Print();		
		
	}
	//DONE
	public boolean rsDispatchReady(int[][] rs, int rsEntry)
	{
		if((rs[rsEntry][3] != -1) && (rs[rsEntry][4] != -1))
		{
			return true;
		}
		
		return false;
	}
	
	//DONE
	public int getNextRSDispatch(int[][] rs)
	{
		for(int rsRow = 0; rsRow < rs.length; rsRow++)
		{
			if(rs[rsRow][7] == 1)
			{
				return rsRow;
			}
		}
		return -1;
	}
	//DONE
	public int getFreeRob(int[][] rob)
	{
		//issue pointer is at free ROB entry
		if(rob[issuePointer][0] == -1)
		{
			return issuePointer;
		}
		return -1;
	
	}
	//DONE
	public void moveRobIssuePointerToFree(int[][] rob)
	{
		if(issuePointer < rob.length - 1)
		{
			issuePointer++;
			if(rob[issuePointer][0] != -1)
			{
				issuePointer++;
			}
		}
		else
		{
			for(int robRow = 0; robRow < rob.length; robRow++)
			{
				issuePointer = robRow;
			}
		}
	}
	//NEED TO WRITE
	public boolean divideException(int[][] eu)
	{
		return false;
	}
	//NEED TO WRITE
	public int getFreeEU(int[][]eu, int [][] rs)
	{
		return -1;
	}
}
