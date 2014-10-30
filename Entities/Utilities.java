package Entities;

public abstract class Utilities 
{
	public enum result{FAIL, SUCCESS};
	
	public static void PrintError(int line, int code)
	{
		System.out.println("Error! Line:" +line + "Code:"+code);
	}
	
	public static void Print(int val)
	{
		System.out.println("" + val);
	}
}
