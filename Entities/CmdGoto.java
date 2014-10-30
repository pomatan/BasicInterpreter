package Entities;


public class CmdGoto extends Cmd 
{
	private int num;
	
	public CmdGoto(cmdType type, int num)
	{
		super(type);
		
		this.num = num;
	}

	public int getNum()
	{
		return (this.num);
	}
	
	@Override
	public int run() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
