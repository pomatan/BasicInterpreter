import java.io.FileNotFoundException;
import java.io.IOException;

import Entities.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Parser parser;
		StmtList program;
		
		try
		{
			// parse the program written in BASIC language
			parser = new Parser("prog.basic");
			program = parser.parse();
			
			// if errors was encountered while parsing then do nothing.
			// error message was already printed during the parsing.
			if (program==null)
				return;
			
			program.run();	

		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (IOException e) {
			System.out.println("error while reading file");
		}
	}

}
