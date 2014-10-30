package Entities;

import java.util.ArrayList;
import java.util.List;

public class StmtList 
{
	private List<Stmt> stmts;
	
	public StmtList()
	{
		stmts = new ArrayList<>();
	}
	
	public void AddStmt(Stmt s)
	{
		//Todo: Check if legal stmt   
		
		this.stmts.add(s);
	}
	
	public void run()
	{
		// Todo, by Itzik: Make the List of trees run
	} 
}
