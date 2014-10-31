package Entities;

public class CmdPrint extends Cmd 
{
	private Exp exp;
	
	public CmdPrint(Exp exp) 
	{
		this.exp = exp;
	}

	public Exp getExp()
	{
		return this.exp;
	}
	
	@Override
	public Utilities.result run() 
	{
		if(exp.computeVal() == null)
		//in case that exp is a non initialized var
			return Utilities.result.FAIL;

		Utilities.Print(this.exp.computeVal());
		return Utilities.result.SUCCESS;
	}
	
	

}
