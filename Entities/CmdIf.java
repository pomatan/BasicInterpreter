package Entities;


public class CmdIf extends Cmd 
{
	public enum BoolOp 	{ GT, ST, GEQ, SEQ, EQ, NEQ; }
	
	private ExpVar var1;
	private ExpVar var2;
	private BoolOp op;
	private Cmd command;

	public CmdIf(cmdType type, ExpVar var1, ExpVar var2, BoolOp op, Cmd command) 
	{
		super(type);
		
		this.var1 = var1;
		this.var2 = var2;
		this.op = op;
		this.command = command;
	}
	
	public BoolOp getOp() 
	{
		return op;
	}
	
	public ExpVar getVar1() {
		return var1;
	}
	
	public ExpVar getVar2() {
		return var2;
	}
	
	public Cmd getCommand() {
		return command;
	}
	

	@Override
	public int run() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
}