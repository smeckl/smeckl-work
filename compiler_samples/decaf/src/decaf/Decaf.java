/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package decaf;

import java.io.*;
import org.antlr.runtime.Token;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

/**
 *
 * @author steve
 */
public class Decaf
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try 
        {        	
        	if (args.length < 2) throw new Exception("Invalid args.");

        	String target = args[0];
        	String filename = args[1];
        	
        	FileInputStream fileStream = new FileInputStream(filename);
        	
        	ANTLRInputStream inputStream = new ANTLRInputStream(fileStream);
        	
        	if (0 == target.compareToIgnoreCase("scan"))
        	{        		
        		DecafScanner lexer = new DecafScanner(inputStream);
        		Token token;
        		boolean done = false;
        		while (!done)
        		{
        			try
        			{
		        		for (token=lexer.nextToken(); token.getType()!=DecafScanner.EOF; token=lexer.nextToken())
		        		{
		        			String type = "";
		        			String text = token.getText();
		
		        			switch (token.getType())
		        			{
		        			case DecafScanner.IDENTIFIER:
		        				type = " IDENTIFIER";
		        				break;
		        				
		        			case DecafScanner.STRINGLITERAL:
		        				type = " STRINGLITERAL";
		        				break;
		        				
		        			case DecafScanner.INTLITERAL:
		        				type = " INTLITERAL";
		        				break;
		        				
		        			case DecafScanner.CHARLITERAL:
		        				type = " CHARLITERAL";
		        				break;
		        				
		        			case DecafScanner.TRUE:
		        			case DecafScanner.FALSE:
		        				type = " BOOLEANLITERAL";
		        				break;
		        			}
		        			System.out.println (token.getLine() + type + " " + text);
		        		}
		        		done = true;
        			} 
                                catch(Exception e) 
                                {
                                    // print the error:
                                    System.out.println(filename + " " + e);
                                }
        		}
        	}
        	else if(0 == target.compareToIgnoreCase("parse"))
        	{	
        		DecafScanner lexer = new DecafScanner(inputStream);
        		CommonTokenStream tokStream = new CommonTokenStream(lexer);
        		
        		DecafParser parser = new DecafParser (tokStream);
                        parser.program();
        	}
        	else
        		System.out.println("Invalid target type.");
        	
        } 
        catch(Exception e) 
        {
        	// print the error:
            System.out.println("Exception "+" "+e);
        }
    }
    
}
