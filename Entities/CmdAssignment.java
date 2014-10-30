package Entities;


public class CmdAssignment extends Cmd 
{
	private ExpVar var;
	private Exp exp;
	
	public CmdAssignment(cmdType type, ExpVar var, Exp exp) 
	{
		super(type);
	
		this.var = var;
		this.exp = exp;
	}
	
	@Override
	public int run() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ExpVar getVar() {
		return var;
	}
	
	public Exp getExp() {
		return exp;
	}
}
