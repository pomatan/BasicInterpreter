package Entities;

public class ExpBinOp extends Exp 
{
	public enum BinOp { PLUS, MUL, MIN, DIV; }

	private BinOp operation;
	private Exp exp1;
	private Exp exp2;
	
	public ExpBinOp(BinOp operation, Exp exp1, Exp exp2)
	{
		this.operation = operation;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	
	
	public BinOp getOperation() 
	{
		return operation;
	}



	public Exp getExp1() 
	{
		return exp1;
	}



	public Exp getExp2() 
	{
		return exp2;
	}



	@Override
	public int computeVal() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
