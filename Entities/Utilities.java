package Entities;

import java.util.HashMap;
import java.util.Map;

public abstract class Utilities 
{
	public enum result{FAIL, SUCCESS};
	public static Map<Character, Integer> vars; //Vars Memory
	public static Integer nextLine;
	
	public static void PrintError(int line, int code)
	{
		System.out.println("Error! Line:" +line + " Code:"+code);
	}
	
	public static void Print(int val)
	{
		System.out.println("" + val);
	}
	
	/* checking if c is a non initialized var */
	public static boolean IsInitVar(Character c)
	{
		if(vars.get(c) == null)
			return false;
		return true;
	}
	
	public static Integer getVarContent(Character c)
	{
		return vars.get(c);
	}

	public static void initVarsMem()
	{
		vars = new HashMap<Character, Integer>();
		for(char c = 'a'; c < 'z'; c++)
		{
			Utilities.vars.put(c, null);
		}
	}
}
