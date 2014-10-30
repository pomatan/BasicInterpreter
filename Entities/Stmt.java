package Entities;

public class Stmt 
{
	private int num;
	private Cmd command;
	
	public Stmt(int num, Cmd command)
	{
		this.command = command;
		this.num = num;
	}
	
	public int GetNum()
	{
		return(this.num);
	}
	
	public Cmd GetCommand()
	{
		return(this.command);
	}
	
	public int run() 
	{
		// Todo, check
		return (command.run());
	}

}
