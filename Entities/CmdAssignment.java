package Entities;

public class CmdAssignment extends Cmd 
{
	private ExpVar var;
	private Exp exp;
	
	public CmdAssignment(ExpVar var, Exp exp) 
	{
		this.var = var;
		this.exp = exp;
	}
	
	@Override
	public Utilities.result run() 
	{
		if(exp.computeVal() == null)
			//in case that exp is a non initialized var
			return Utilities.result.FAIL;
		
		Utilities.vars.put(var.getVar(), exp.computeVal());
		return Utilities.result.SUCCESS;
	}

	public ExpVar getVar() {
		return var;
	}
	
	public Exp getExp() {
		return exp;
	}
}
