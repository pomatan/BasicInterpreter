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
	public Integer computeVal() 
	{
		return this.num;
	}
		
}
