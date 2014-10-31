package Entities;

public class CmdIf extends Cmd 
{
	public enum BoolOp 	{ GT, ST, GEQ, SEQ, EQ, NEQ; }
	
	private Exp var1;
	private Exp var2;
	private BoolOp op;
	private Cmd command;

	public CmdIf(Exp var1, Exp var2, BoolOp op, Cmd command) 
	{		
		this.var1 = var1;
		this.var2 = var2;
		this.op = op;
		this.command = command;
	}
	
	public BoolOp getOp() 
	{
		return op;
	}
	
	public Exp getVar1() {
		return var1;
	}
	
	public Exp getVar2() {
		return var2;
	}
	
	public Cmd getCommand() {
		return command;
	}
	

	@Override
	public Utilities.result run() 
	{
		if(var1.computeVal() == null || var2.computeVal() == null)
			//in case one of the vars is not initialized
			return Utilities.result.FAIL;
		
		if(computeBoolVal()) //the boolean phrase it True
			return this.command.run();
		else
			return Utilities.result.SUCCESS;
	}
	
	boolean computeBoolVal()
	{
		switch (this.op)
		{
		case GT:
			if(this.var1.computeVal() > this.var2.computeVal())
				return true;
			else
				return false;
			
		case ST:
			if(this.var1.computeVal() < this.var2.computeVal())
				return true;
			else
				return false;
			
		case GEQ:
			if(this.var1.computeVal() >= this.var2.computeVal())
				return true;
			else
				return false;
			
		case SEQ:
			if(this.var1.computeVal() <= this.var2.computeVal())
				return true;
			else
				return false;

		case EQ:
			if(this.var1.computeVal() == this.var2.computeVal())
				return true;
			else
				return false;
			
		case NEQ:
			if(this.var1.computeVal() != this.var2.computeVal())
				return true;
			else
				return false;
		}
		return false;

	}
	
}
