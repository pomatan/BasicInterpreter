package Entities;

public class ExpNum extends Exp
{
	private int num;
	
	public ExpNum(int num)
	{
		this.num = num;
	}
	
	public int getNum()
	{
		return this.num;
	}

	
	@Override
	public int computeVal() 
	{
		return this.num;
	}
		
}
