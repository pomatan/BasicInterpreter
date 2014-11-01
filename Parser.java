import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import Entities.*;
import Entities.ExpBinOp.BinOp;

public class Parser
{
	private BufferedReader bufReader;
	
	private StmtList prog = new StmtList();
	private int lastLabelNum = -1;
	private Set<Integer> labalesNumSet = new HashSet<Integer>();
	private List<GotoMapToLine> gotoMapArray = new ArrayList<GotoMapToLine>(); // maps goto label line and line in file
	
	// match general command. groups: 1=num, 2=cmd
	private static Pattern cmdGeneralRegex = Pattern.compile("^\\s*(0|[1-9][0-9]*)\\s*:\\s*(.+);\\s*$");
	
	// match the print command. groups: 1=exp
	private static Pattern cmdPrintRegex = Pattern.compile("^\\s*print\\s*\\((.+)\\)\\s*$");
	
	// match the goto command. groups: 1=goto line number
	private static Pattern cmdGotoRegex = Pattern.compile("^\\s*goto\\s+(0|[1-9][0-9]*)\\s*$");
	
	// match the assignment command. groups: 1=var, 2=exp
	private static Pattern cmdAssignmentRegex = Pattern.compile("^\\s*(.+)\\s*:=\\s*(.+)\\s*$");
	
	// match the if command. groups: 1=exp_1, 2=bool op, 3=exp_2, 4=inner cmd
	private static Pattern cmdIfRegex = Pattern.compile("^\\s*if\\s*\\((.+?)(<|>|<=|>=|==|!=)(.+?)\\)\\s*(.+)\\s*$");
	
	// match the variable expression. groups: 1=var
	private static Pattern expVarRegex = Pattern.compile("^\\s*([a-z])\\s*$");
	
	// match the variable expression. groups: 1=num
	private static Pattern expNumRegex = Pattern.compile("^\\s*(0|[1-9][0-9]*)\\s*$");
		
	public Parser(String filename) throws FileNotFoundException
	{
		bufReader = new BufferedReader(new FileReader(filename));
	}
	
	public StmtList parse() throws IOException
	{
		// read file line by line and parse it
		String line;
		int lineNum = 1;
		
		while ((line = bufReader.readLine()) != null)
		{
		   if(!parseLine(line, lineNum))
		   {
			   bufReader.close();
			   
			   return null;
		   }
		   
		   lineNum++;
		}
		
		bufReader.close();
		
		// validate that all the goto labels exists
		for(Iterator<GotoMapToLine> iter = gotoMapArray.iterator(); iter.hasNext(); )
		{
			GotoMapToLine item = iter.next();
			
			if (!labalesNumSet.contains(item.gotoLabelNum))
			{
				Utilities.PrintError(item.lineNum, 2); // goto command to non-existing label
			}
		}
		
		return this.prog;
	}

	private boolean parseLine(String line, int lineNum)
	{
		// parse general command
		Matcher matcher = cmdGeneralRegex.matcher(line);
		if (!matcher.find())
		{
			Utilities.PrintError(lineNum, 1); // program can not be derived from grammar
			return false;
		}
		
		// fetch data from line
		int labelNum = Integer.parseInt(matcher.group(1));
		String cmdStr = matcher.group(2);
		
		// check labels are strictly monotonically	
		if(labelNum<lastLabelNum)
		{
			Utilities.PrintError(lineNum, 3); // program can not be derived from grammar
			return false;
		}
		
		// add label to labels set
		labalesNumSet.add(labelNum);
		
		// set it as the last label number
		lastLabelNum = labelNum;
		
		// now parse the command
		Cmd cmd = parseCmd(cmdStr, lineNum);
		if (cmd==null)
		{
			Utilities.PrintError(lineNum, 1); // program can not be derived from grammar
			return false;
		}
		
		// if we got so far then the command is syntaxly correct
		// then add it to the program struct
		prog.AddStmt(new Stmt(labelNum, cmd));

		return true;
	}
	
	private Cmd parseCmd(String cmdStr, int lineNum)
	{
		Cmd cmd;
		
		// try print command
		cmd = parsePrintCmd(cmdStr, lineNum);
		if (cmd != null)
			return cmd;
		
		// try goto command
		cmd = parseGotoCmd(cmdStr, lineNum);
		if (cmd != null)
			return cmd;
		
		// try goto command
		cmd = parseAssignmentCmd(cmdStr, lineNum);
		if (cmd != null)
			return cmd;
		
		// try if command
		cmd = parseIfCmd(cmdStr, lineNum);
		if (cmd != null)
			return cmd;
		
		return null;
	}

	private CmdIf parseIfCmd(String cmdStr, int lineNum)
	{
		// parse command
		Matcher matcher = cmdIfRegex.matcher(cmdStr); 
		if (!matcher.find())
			return null;
		
		// parse expression 1 argument
		Exp expArg1 = parseExp(matcher.group(1));
		if (expArg1==null)
			return null;
		
		// parse operator
		CmdIf.BoolOp op = CmdIf.BoolOp.ST; // dummy init, should be re-init in switch
		switch (matcher.group(2))
		{
		case "<": op = CmdIf.BoolOp.ST; break;
		case ">": op = CmdIf.BoolOp.GT; break;
		case "<=": op = CmdIf.BoolOp.SEQ; break;
		case ">=": op = CmdIf.BoolOp.GEQ; break;
		case "==": op = CmdIf.BoolOp.EQ; break;
		case "!=": op = CmdIf.BoolOp.NEQ; break;
		}
	
		// parse expression 2 argument
		Exp expArg2 = parseExp(matcher.group(3));
		if (expArg2==null)
			return null;
		
		// parse inner command
		Cmd cmdInner = parseCmd(matcher.group(4), lineNum);
		if (cmdInner==null)
			return null;
		
		// create command object and populate it, and return it
		return new CmdIf(expArg1, expArg2, op, cmdInner);
	}

	private CmdAssignment parseAssignmentCmd(String cmdStr, int lineNum)
	{
		// parse command
		Matcher matcher = cmdAssignmentRegex.matcher(cmdStr); 
		if (!matcher.find())
			return null;
		
		// parse variable argument
		ExpVar expVarName = parseVariableExp(matcher.group(1));
		if (expVarName==null)
			return null;
		
		// parse expression argument
		Exp expArg = parseExp(matcher.group(2));
		if (expArg==null)
			return null;
		
		// create command object and populate it, and return it
		return new CmdAssignment(expVarName, expArg);
	}

	private CmdGoto parseGotoCmd(String cmdStr, int lineNum)
	{
		// parse command
		Matcher matcher = cmdGotoRegex.matcher(cmdStr); 
		if (!matcher.find())
			return null;
		
		int gotoNumLabel = Integer.parseInt(matcher.group(1));
		
		// add it to the goto map array
		gotoMapArray.add(new GotoMapToLine(gotoNumLabel, lineNum));
		
		// create command object and populate it, and return it
		return new CmdGoto(gotoNumLabel);
	}

	private CmdPrint parsePrintCmd(String cmdStr, int lineNum)
	{
		// parse command
		Matcher matcher = cmdPrintRegex.matcher(cmdStr); 
		if (!matcher.find())
			return null;
		
		// parse expression argument
		Exp expArg = parseExp(matcher.group(1));
		if (expArg==null)
			return null;
		
		// create command object and populate it, and return it
		return new CmdPrint(expArg);
	}

	private Exp parseExp(String expStr)
	{
		Exp exp;
		
		// try variable expression
		exp = parseVariableExp(expStr);
		if (exp != null)
			return exp;
		
		// try number expression
		exp = parseNumberExp(expStr);
		if (exp != null)
			return exp;
		
		// try binary expression
		exp = parseBinaryExp(expStr);
		if (exp != null)
			return exp;

		return null;
	}

	private ExpVar parseVariableExp(String expStr)
	{
		// parse expression
		Matcher matcher = expVarRegex.matcher(expStr); 
		if (!matcher.find())
			return null;
		
		char varName = matcher.group(1).charAt(0); // the string contains only one char
		
		return new ExpVar(varName);
	}
	
	private ExpNum parseNumberExp(String expStr)
	{
		// parse expression
		Matcher matcher = expNumRegex.matcher(expStr); 
		if (!matcher.find())
			return null;
		
		return new ExpNum(Integer.parseInt(matcher.group(1)));
	}
	
	private Exp parseBinaryExp(String expStr)
	{
		String[] tokens = expStr.split(" ");		
		
		return (parseBinaryExp(tokens));
	}
	
	private Exp parseBinaryExp(String[] expStr)
	{
		// if size is one try Number and Variable Expressions
		if (expStr.length == 1)
		{
			return (parseExp(expStr[0]));
		}
		// binary exp must have opSign and two expresions (at least three tokens)
		if (expStr.length < 2)
		{
			return null;
		}
		
		// First token must be a binOp
		BinOp op = parseBinOp(expStr[0]);
		if (op == null)
		{
			return null;
		}

		// Find the end of exp1
		int counter = 1, i=1;
		while (counter > 0)
		{
			if (parseBinOp(expStr[i]) != null)
				counter++;
			else if(parseVariableExp(expStr[i]) != null || parseNumberExp(expStr[i]) != null)
				counter--;
			else
				return null;
			
			// Make sure not to go out of range
			if (i > (expStr.length - 1))
				return (null);
			else
				i++;
		}
		
		// parse first expresion
		String exp1array[] = new String[i - 1];
		System.arraycopy(expStr, 1, exp1array, 0, i-1);
		Exp exp1 = parseBinaryExp(exp1array);
		if (exp1 == null)
			return null;
		
		// parse second expresion
		int exp2length = expStr.length - i;
		String exp2array[] = new String[exp2length];
		System.arraycopy(expStr, i, exp2array, 0, exp2length);
		Exp exp2 = parseBinaryExp(exp2array);
		if (exp2 == null)
			return null;
		
		return (new ExpBinOp(op, exp1, exp2));
	}
	
	private BinOp parseBinOp(String opString)
	{
		BinOp op = null;
		
		switch (opString)
		{
			case "/": op = BinOp.DIV; break;
			case "*": op = BinOp.MUL; break;
			case "+": op = BinOp.PLUS; break;
			case "-": op = BinOp.MIN; break;
		}
		
		return op;
	}

	private class GotoMapToLine
	{
		int gotoLabelNum;
		int lineNum;
		
		public GotoMapToLine(int gotoLabelNum, int lineNum)
		{
			this.gotoLabelNum = gotoLabelNum;
			this.lineNum = lineNum;
		}
	}
}
