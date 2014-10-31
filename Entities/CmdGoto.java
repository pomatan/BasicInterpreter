package Entities;


public class CmdGoto extends Cmd 
{
	private int num;
	
	public CmdGoto(int num)
	{
		this.num = num;
	}

	public int getNum()
	{
		return (this.num);
	}
	
	@Override
	public Utilities.result run() 
	{
		Utilities.nextLine = this.num;
		return Utilities.result.SUCCESS;
	}

}
