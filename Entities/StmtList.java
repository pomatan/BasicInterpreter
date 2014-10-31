package Entities;

import java.util.ArrayList;
import java.util.List;

public class StmtList 
{
	private List<Stmt> stmts;
	
	public StmtList()
	{
		stmts = new ArrayList<>(); 
		Utilities.initVarsMem(); //initialize variables memory
	}
	
	public void AddStmt(Stmt s)
	{
		this.stmts.add(s);
	}
	
	public void run()
	{
		Utilities.result runResult;
		
		for(int i = 0; i < stmts.size();)
		{
			runResult = stmts.get(i).run();
			if(runResult == Utilities.result.FAIL) //if stmt contains a non initialized var
			{
				Utilities.PrintError(Utilities.currentLine, 4);
				return;
			}
		
			if(Utilities.nextLine == null) //last stmt wasn't a "goto" stmt
				i++;
			else //in case of a "goto" command
				i = getIndexByLine(Utilities.nextLine);
		}
	}

	/* For a given line number, returns it's stmt index */
	private int getIndexByLine(Integer nextLine)
	{
		for(int i = 0; i < stmts.size(); i++)
		{
			if(stmts.get(i).GetNum() == nextLine)
				return i;
		}
		return -1;
	} 
}
