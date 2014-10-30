package Entities;

public abstract class Cmd 
{
	public enum cmdType{ASSIGN, IF, GOTO, PRINT};
	
	public cmdType type;
	public abstract int run();

	public Cmd(cmdType type)
	{
		this.type = type;
	}
}
