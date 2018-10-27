package com.previous;

public class RS {
	
	private int[][] rs;
	private int indexOfInstruction;
	
	public RS(){
		// OP	Qj	Qk	Vj	Vk	Busy Dest Disp
		rs = new int[5][7]; // 5 Reservation Stations
	}
	
	public int getCurrentRSIndex()
	{
		return indexOfInstruction;
	}
}
