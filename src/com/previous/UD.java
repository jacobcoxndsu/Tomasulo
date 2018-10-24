package com.previous;

public class UD {
	
	private int opCode;
	private int cc;
	
	public UD(){
		opCode = -1;
		cc = -1;
	}
	
	public boolean setInstruction(int op, int c){
		if(opCode == -1 || cc == -1){
			return false;
		}
		
		opCode = op;
		cc = c;
		
		return true;
	}
	
	public UD Step(RS rs, RF rf){
		if(opCode == -1 || cc == -1){
			
		}
		
		return this;
	}
}
