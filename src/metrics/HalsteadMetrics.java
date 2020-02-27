package metrics;

/**
 * @author  Ahmed Metwally
 */


// This class is intended to calculate all the halstead complexity metrics   
@SuppressWarnings("WeakerAccess")
public final class HalsteadMetrics {
	
	private int DistOperators, DistOperands, TotOperators, TotOperands;

	public int getDistOperators() {
		return DistOperators;
	}

	public int getDistOperands() {
		return DistOperands;
	}

	public int getTotOperators() {
		return TotOperators;
	}

	public int getTotOperands() {
		return TotOperands;
	}

	// Initialize the variables in the constructor
	public HalsteadMetrics() {
		DistOperators=0;
		DistOperands=0;
		TotOperators=0;
		TotOperands=0;
	}
	
	
	
	// set number of DistOperators, DistOperands, TotOperators, and TotOperands
	void setParameters(int DistOprt, int DistOper, int TotOprt, int TotOper)
	{
		DistOperators=DistOprt;
		DistOperands=DistOper;
		TotOperators=TotOprt;
		TotOperands=TotOper;
	}
	
	
	
	// calculate the Program vocabulary
	public int getVocabulary()
	{
		return DistOperators+DistOperands;
	}
	
	
	
	// calculate the Program length
	public int getProglen()
	{
		return TotOperators+TotOperands;
	}
	
	
	
	// calculate the Calculated program length
	public double getCalcProgLen()
	{
		return DistOperators*(Math.log(DistOperators) / Math.log(2)) + DistOperands*(Math.log(DistOperands) / Math.log(2));
	}
	
	
	
	// calculate the Volume
	public double getVolume()
	{
		return (TotOperators+TotOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2));
	}
	
	
	
	// calculate the Difficulty
	public double getDifficulty()
	{
		return (DistOperators/2)*(TotOperands/(double)DistOperands);
	}
	
	
	
	// calculate the Effort
	public double getEffort()
	{
		return ((DistOperators/2)*(TotOperands/(double)DistOperands)) * ((TotOperators+TotOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2)));
	}
	
	
	
	// calculate the Time required to program
	public double getTimeReqProg()
	{
		return (((DistOperators/2)*(TotOperands/(double)DistOperands)) * ((TotOperators+TotOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2)))) /18;
	}
	
	
	
	// calculate the Number of delivered bugs
	public double getTimeDelBugs()
	{
		return ((TotOperators+TotOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2))) / 3000;
	}	
}