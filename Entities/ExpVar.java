package Entities;

public class ExpVar extends Exp
{
	private Character var;
	
	public ExpVar(char var)
	{
		this.var = var;
	}
	
	public char getVar()
	{
		return this.var;
	}
	
	@Override
	public Integer computeVal()
	{
		if(!Utilities.IsInitVar(var))
			return null;
		return Utilities.getVarContent(var);
	}

}
