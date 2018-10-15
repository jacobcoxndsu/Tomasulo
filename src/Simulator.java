
public class Simulator {

	private boolean RUNNING = true;
	private int cycle = 0;
	private int numCycles;
	private InstructionRecord[] instructionRecords;
	private int[] registerFile;
	private int[] RAT;
	private ReservationStation[] addSubRS;
	private ReservationStation[] multDivRS;

	public Simulator(int cy, String[] ir, String[] rg)
	{

		// cycles
		numCycles = cy;

		// instructionRecords
		instructionRecords = new InstructionRecord[ir.length];
		for (int i = 0; i < ir.length; i++)
		{
			int opcode = Character.getNumericValue(ir[i].charAt(0));
			int destOp = Character.getNumericValue(ir[i].charAt(1));
			int sourceOp1 = Character.getNumericValue(ir[i].charAt(2));
			int sourceOp2 = Character.getNumericValue(ir[i].charAt(3));
			InstructionRecord inst = new InstructionRecord(opcode, destOp, sourceOp1, sourceOp2);
			instructionRecords[i] = inst;
		}

		// registerFile
		registerFile = new int[rg.length];
		for (int i = 0; i < rg.length; i++)
		{
			registerFile[i] = Integer.parseInt(rg[i]);
		}

		// RAT
		RAT = new int[8];
		for (int i = 0; i < 8; i++)
		{
			RAT[i] = -1;
		}

		// addSubRS
		addSubRS = new ReservationStation[3];
		addSubRS[0] = new ReservationStation();
		addSubRS[1] = new ReservationStation();
		addSubRS[2] = new ReservationStation();

		// multDivRS
		multDivRS = new ReservationStation[2];
		multDivRS[0] = new ReservationStation();
		multDivRS[1] = new ReservationStation();

		Run();
	}

	public void Run()
	{
		while (RUNNING)
		{

			Issue();
			Dispatch();
			Broadcast();
			Commit();

			if (cycle > numCycles)
			{
				RUNNING = false;
			}

			// If you need to wait, then dont increment...
			cycle++;
		}

		System.out.println("Finished");
	}

	public void Issue()
	{
		InstructionRecord r = instructionRecords[cycle];
		int opcode = r.opcode;
		if (opcode == 0 || opcode == 1)
		{
			for (int i = 0; i < addSubRS.length; i++)
			{
				if (!addSubRS[i].isEmpty())
				{
					// DO something
					addSubRS[i].busy = true;
					addSubRS[i].op = opcode;
					// check RAT to see if it is tagged and set q values
					for (int j = 0; j < RAT.length; j++)
					{
						boolean foundFirstTag = false;
						boolean foundSecondTag = false;
						//The RAT entry has the RS tag
						if (RAT[j] == i)
						{
							addSubRS[i].qj = j;
							foundFirstTag = true;
						}
						
						if(foundFirstTag && RAT[j] == i)
						{
							addSubRS[i].qk = j;
							foundSecondTag = true;
							break;
						}
					}
					
					//set v values if q values aren't used
					if(addSubRS[i].qj == -1)
					{
						addSubRS[i].vj = registerFile[r.sourceOp1];
					}
					if(addSubRS[i].qk == -1)
					{
						addSubRS[i].vk = registerFile[r.sourceOp2];
					}
					
					addSubRS[i].op = opcode;
					addSubRS[i].op = opcode;
					addSubRS[i].op = opcode;
					addSubRS[i].op = opcode;

					break;
				}
			}
		}
	}

	public void Dispatch()
	{

	}

	public void Broadcast()
	{

	}

	public void Commit()
	{

	}

	public int getCycle()
	{
		return cycle;
	}

	public String toString()
	{
		String s = "";
		for (int i = 0; i < instructionRecords.length; i++)
		{
			s += instructionRecords[i].toString() + "\n";
		}

		return s;
	}

}
