package Entities;


public class CmdPrint extends Cmd 
{
	private Exp exp;
	
	public CmdPrint(cmdType type, Exp exp) 
	{
		super(type);

		this.exp = exp;
	}

	public Exp getExp()
	{
		return this.exp;
	}
	
	@Override
	public int run() 
	{
		Utilities.Print(this.exp.computeVal());
		
		return (Utilities.result.SUCCESS.ordinal());
	}
	
	

}
