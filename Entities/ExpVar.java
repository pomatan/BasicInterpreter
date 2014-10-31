package Entities;

public class ExpVar extends Exp
{
	String var;
	
	public ExpVar(String var)
	{
		this.var = var;
	}
	
	public String getVar()
	{
		return this.var;
	}
	
	@Override
	public int computeVal() {
		// TODO Auto-generated method stub
		return 0;
	}

}
