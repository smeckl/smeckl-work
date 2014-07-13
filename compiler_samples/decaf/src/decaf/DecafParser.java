// $ANTLR 3.5.2 /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g 2014-07-13 07:11:39

  package decaf;
  
  import org.antlr.runtime.*;
  import org.antlr.runtime.tree.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("all")
public class DecafParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADD", "ADD_ASSIGN", "AND", "ASSIGN", 
		"BOOLEAN", "BREAK", "CALLOUT", "CHAR", "CHARLITERAL", "CLASS", "COMMA", 
		"CONTINUE", "DECIMAL_LITERAL", "DIGIT", "DIVIDE", "ELSE", "EQ", "ESCAPE", 
		"FALSE", "FOR", "GT", "GTE", "HEX_DIGIT", "HEX_LITERAL", "IDENTIFIER", 
		"IF", "INT", "INTLITERAL", "LBRACKET", "LCURLY", "LPAREN", "LT", "LTE", 
		"MODULUS", "MULTIPLY", "NEQ", "NOT", "OR", "PRINTABLE_CHARS", "PROGRAM", 
		"RBRACKET", "RCURLY", "RETURN", "RPAREN", "SEMICOLON", "SL_COMMENT", "STRINGLITERAL", 
		"SUBTRACT", "SUBTRACT_ASSIGN", "TRUE", "VOID", "WS_"
	};
	public static final int EOF=-1;
	public static final int ADD=4;
	public static final int ADD_ASSIGN=5;
	public static final int AND=6;
	public static final int ASSIGN=7;
	public static final int BOOLEAN=8;
	public static final int BREAK=9;
	public static final int CALLOUT=10;
	public static final int CHAR=11;
	public static final int CHARLITERAL=12;
	public static final int CLASS=13;
	public static final int COMMA=14;
	public static final int CONTINUE=15;
	public static final int DECIMAL_LITERAL=16;
	public static final int DIGIT=17;
	public static final int DIVIDE=18;
	public static final int ELSE=19;
	public static final int EQ=20;
	public static final int ESCAPE=21;
	public static final int FALSE=22;
	public static final int FOR=23;
	public static final int GT=24;
	public static final int GTE=25;
	public static final int HEX_DIGIT=26;
	public static final int HEX_LITERAL=27;
	public static final int IDENTIFIER=28;
	public static final int IF=29;
	public static final int INT=30;
	public static final int INTLITERAL=31;
	public static final int LBRACKET=32;
	public static final int LCURLY=33;
	public static final int LPAREN=34;
	public static final int LT=35;
	public static final int LTE=36;
	public static final int MODULUS=37;
	public static final int MULTIPLY=38;
	public static final int NEQ=39;
	public static final int NOT=40;
	public static final int OR=41;
	public static final int PRINTABLE_CHARS=42;
	public static final int PROGRAM=43;
	public static final int RBRACKET=44;
	public static final int RCURLY=45;
	public static final int RETURN=46;
	public static final int RPAREN=47;
	public static final int SEMICOLON=48;
	public static final int SL_COMMENT=49;
	public static final int STRINGLITERAL=50;
	public static final int SUBTRACT=51;
	public static final int SUBTRACT_ASSIGN=52;
	public static final int TRUE=53;
	public static final int VOID=54;
	public static final int WS_=55;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public DecafParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public DecafParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return DecafParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g"; }


	    private SymbolTable m_symbolTable = new SymbolTable();
	    
	    private boolean m_bValidMain = false;
	    
	    private Method m_curMethod = null;
	    
	    private boolean m_bInForLoop = false;
	    
	    private void setValidMain(boolean bValid)
	    {
	        m_bValidMain = true;
	    }
	    
	    private boolean getValidMain()
	    {
	        return m_bValidMain;
	    }
	    
	    private void setInForLoop(boolean bInFor)
	    {
	        m_bInForLoop = bInFor;
	    }
	    
	    private boolean getInForLoop()
	    {
	        return m_bInForLoop;
	    }
	    
	    private static Expression.Type stringToType(String strType)
	    {
	        Expression.Type type;
	        
	        if(0 == strType.compareTo("boolean"))
		          type = Expression.Type.TYPE_BOOL;
		      else if(0 == strType.compareTo("int"))
		          type = Expression.Type.TYPE_INT;
		      else if(0 == strType.compareTo("void"))
	            type = Expression.Type.TYPE_VOID;
		      else
		          type = Expression.Type.TYPE_INVALID;
		          
		      return type;
	    }



	// $ANTLR start "program"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:63:1: program options {backtrack=true; } : CLASS PROGRAM LCURLY ( field_decl )* ( method_decl )* RCURLY EOF ;
	public final void program() throws RecognitionException {
		Token RCURLY1=null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:63:33: ( CLASS PROGRAM LCURLY ( field_decl )* ( method_decl )* RCURLY EOF )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:63:35: CLASS PROGRAM LCURLY ( field_decl )* ( method_decl )* RCURLY EOF
			{
			match(input,CLASS,FOLLOW_CLASS_in_program43); if (state.failed) return;
			match(input,PROGRAM,FOLLOW_PROGRAM_in_program45); if (state.failed) return;
			match(input,LCURLY,FOLLOW_LCURLY_in_program47); if (state.failed) return;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:63:56: ( field_decl )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==BOOLEAN||LA1_0==INT) ) {
					int LA1_1 = input.LA(2);
					if ( (LA1_1==IDENTIFIER) ) {
						int LA1_3 = input.LA(3);
						if ( (LA1_3==COMMA||LA1_3==LBRACKET||LA1_3==SEMICOLON) ) {
							alt1=1;
						}

					}

				}

				switch (alt1) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:63:57: field_decl
					{
					pushFollow(FOLLOW_field_decl_in_program50);
					field_decl();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop1;
				}
			}

			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:63:70: ( method_decl )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==BOOLEAN||LA2_0==INT||LA2_0==VOID) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:63:71: method_decl
					{
					pushFollow(FOLLOW_method_decl_in_program55);
					method_decl();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop2;
				}
			}

			RCURLY1=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_program59); if (state.failed) return;
			match(input,EOF,FOLLOW_EOF_in_program61); if (state.failed) return;
			if ( state.backtracking==0 ) {
			        if(!getValidMain())
			        {
			            System.out.println((RCURLY1!=null?RCURLY1.getLine():0) + ":" + (RCURLY1!=null?RCURLY1.getCharPositionInLine():0) + "  Method \"main\" not declared.");
			        }
			    }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "program"



	// $ANTLR start "block"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:72:1: block options {backtrack=true; } : LCURLY ( field_decl )* ( statement )* RCURLY ;
	public final void block() throws RecognitionException {
		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:72:31: ( LCURLY ( field_decl )* ( statement )* RCURLY )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:72:33: LCURLY ( field_decl )* ( statement )* RCURLY
			{
			match(input,LCURLY,FOLLOW_LCURLY_in_block84); if (state.failed) return;
			if ( state.backtracking==0 ) { m_symbolTable.startScope(); }
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:72:71: ( field_decl )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==BOOLEAN||LA3_0==INT) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:72:72: field_decl
					{
					pushFollow(FOLLOW_field_decl_in_block88);
					field_decl();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop3;
				}
			}

			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:72:85: ( statement )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= BREAK && LA4_0 <= CALLOUT)||LA4_0==CONTINUE||LA4_0==FOR||(LA4_0 >= IDENTIFIER && LA4_0 <= IF)||LA4_0==LCURLY||LA4_0==RETURN) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:72:86: statement
					{
					pushFollow(FOLLOW_statement_in_block93);
					statement();
					state._fsp--;
					if (state.failed) return;
					}
					break;

				default :
					break loop4;
				}
			}

			match(input,RCURLY,FOLLOW_RCURLY_in_block97); if (state.failed) return;
			if ( state.backtracking==0 ) { m_symbolTable.endScope(); }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "block"



	// $ANTLR start "field_decl"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:74:1: field_decl : type b= field ( COMMA c= field )* SEMICOLON ;
	public final void field_decl() throws RecognitionException {
		Symbol b =null;
		Symbol c =null;
		ParserRuleReturnScope type2 =null;


		    Expression.Type fieldType = Expression.Type.TYPE_INVALID;
		    
		    Token ahead = input.LT(1);
		    int line = ahead.getLine();
		    int pos = ahead.getCharPositionInLine();

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:82:3: ( type b= field ( COMMA c= field )* SEMICOLON )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:82:3: type b= field ( COMMA c= field )* SEMICOLON
			{
			pushFollow(FOLLOW_type_in_field_decl112);
			type2=type();
			state._fsp--;
			if (state.failed) return;
			if ( state.backtracking==0 ) {
			      fieldType = stringToType((type2!=null?input.toString(type2.start,type2.stop):null));
			  }
			pushFollow(FOLLOW_field_in_field_decl122);
			b=field();
			state._fsp--;
			if (state.failed) return;
			if ( state.backtracking==0 ) {     
				      if(null != m_symbolTable.lookupSymbol(b.getName(), true)) 
				      {
				          System.out.println(line + ":" + pos + " Variable " + b.getName() + " already defined.");
				      }
				      else
				      {
				          b.setType(fieldType);
				          m_symbolTable.addSymbol(b);
				      }
			    }
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:98:3: ( COMMA c= field )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==COMMA) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:98:4: COMMA c= field
					{
					match(input,COMMA,FOLLOW_COMMA_in_field_decl133); if (state.failed) return;
					pushFollow(FOLLOW_field_in_field_decl137);
					c=field();
					state._fsp--;
					if (state.failed) return;
					if ( state.backtracking==0 ) {
					        if(null != m_symbolTable.lookupSymbol(c.getName(), true)) 
					        {
					            System.out.println(line + ":" + pos + " Variable " + c.getName() + " already defined.");
					        }
					        else
					        {
					            c.setType(fieldType);
					            m_symbolTable.addSymbol(c);
					        }
					    }
					}
					break;

				default :
					break loop5;
				}
			}

			match(input,SEMICOLON,FOLLOW_SEMICOLON_in_field_decl147); if (state.failed) return;
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "field_decl"



	// $ANTLR start "field"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:111:1: field returns [Symbol sym] : ( ( IDENTIFIER LBRACKET )=> IDENTIFIER LBRACKET INTLITERAL RBRACKET | IDENTIFIER );
	public final Symbol field() throws RecognitionException {
		Symbol sym = null;


		Token IDENTIFIER3=null;
		Token INTLITERAL4=null;
		Token LBRACKET5=null;
		Token IDENTIFIER6=null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:112:3: ( ( IDENTIFIER LBRACKET )=> IDENTIFIER LBRACKET INTLITERAL RBRACKET | IDENTIFIER )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==IDENTIFIER) ) {
				int LA6_1 = input.LA(2);
				if ( (LA6_1==LBRACKET) && (synpred1_DecafParser())) {
					alt6=1;
				}
				else if ( (LA6_1==COMMA||LA6_1==SEMICOLON) ) {
					alt6=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return sym;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				if (state.backtracking>0) {state.failed=true; return sym;}
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:112:5: ( IDENTIFIER LBRACKET )=> IDENTIFIER LBRACKET INTLITERAL RBRACKET
					{
					IDENTIFIER3=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_field169); if (state.failed) return sym;
					LBRACKET5=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_field171); if (state.failed) return sym;
					INTLITERAL4=(Token)match(input,INTLITERAL,FOLLOW_INTLITERAL_in_field173); if (state.failed) return sym;
					match(input,RBRACKET,FOLLOW_RBRACKET_in_field175); if (state.failed) return sym;
					if ( state.backtracking==0 ) {        
					          sym = new Symbol((IDENTIFIER3!=null?IDENTIFIER3.getText():null), Expression.Type.TYPE_INVALID);
					          
					          if(Integer.parseInt((INTLITERAL4!=null?INTLITERAL4.getText():null)) <= 0)
					          {
					              System.out.println((LBRACKET5!=null?LBRACKET5.getLine():0) + ":" + (LBRACKET5!=null?LBRACKET5.getCharPositionInLine():0) + " Array size must be greater than zero.");
					          }
					          
					          sym.setIsArray(true);
					          sym.setArraySize(Integer.parseInt((INTLITERAL4!=null?INTLITERAL4.getText():null)));
					      }
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:124:5: IDENTIFIER
					{
					IDENTIFIER6=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_field189); if (state.failed) return sym;
					if ( state.backtracking==0 ) {
					          sym = new Symbol((IDENTIFIER6!=null?IDENTIFIER6.getText():null), Expression.Type.TYPE_INVALID);
					      }
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return sym;
	}
	// $ANTLR end "field"



	// $ANTLR start "method_decl"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:130:1: method_decl : ( type | VOID ) IDENTIFIER LPAREN (parm1= parameter_def ( COMMA parm2= parameter_def )* )? RPAREN block ;
	public final void method_decl() throws RecognitionException {
		Token VOID8=null;
		Token IDENTIFIER9=null;
		Token COMMA10=null;
		Symbol parm1 =null;
		Symbol parm2 =null;
		ParserRuleReturnScope type7 =null;


		    Method method = null;
		    String strType = "";
		    String strID = "";
		    int line = 0;
		    int pos = 0;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:139:3: ( ( type | VOID ) IDENTIFIER LPAREN (parm1= parameter_def ( COMMA parm2= parameter_def )* )? RPAREN block )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:139:5: ( type | VOID ) IDENTIFIER LPAREN (parm1= parameter_def ( COMMA parm2= parameter_def )* )? RPAREN block
			{
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:139:5: ( type | VOID )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==BOOLEAN||LA7_0==INT) ) {
				alt7=1;
			}
			else if ( (LA7_0==VOID) ) {
				alt7=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:139:6: type
					{
					pushFollow(FOLLOW_type_in_method_decl216);
					type7=type();
					state._fsp--;
					if (state.failed) return;
					if ( state.backtracking==0 ) {strType = (type7!=null?input.toString(type7.start,type7.stop):null);}
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:139:36: VOID
					{
					VOID8=(Token)match(input,VOID,FOLLOW_VOID_in_method_decl221); if (state.failed) return;
					if ( state.backtracking==0 ) {strType = (VOID8!=null?VOID8.getText():null);}
					}
					break;

			}

			IDENTIFIER9=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_method_decl226); if (state.failed) return;
			match(input,LPAREN,FOLLOW_LPAREN_in_method_decl228); if (state.failed) return;
			if ( state.backtracking==0 ) {
			        strID = (IDENTIFIER9!=null?IDENTIFIER9.getText():null);
			        line = (IDENTIFIER9!=null?IDENTIFIER9.getLine():0);
			        pos = (IDENTIFIER9!=null?IDENTIFIER9.getCharPositionInLine():0);
			        
			        method = (Method)m_symbolTable.addMethod(strID, stringToType(strType));    
			        
			        m_symbolTable.startScope();
			        
			        if(0 == strID.compareTo("main"))
			        {
			            if(0 != strType.compareTo("void"))
					        {
					            System.out.println(line + ":" + pos + "  Method \"main\" must have type void.");
					        }
					        
					        setValidMain(true);
					    }
			    }
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:159:3: (parm1= parameter_def ( COMMA parm2= parameter_def )* )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==BOOLEAN||LA9_0==INT) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:159:4: parm1= parameter_def ( COMMA parm2= parameter_def )*
					{
					pushFollow(FOLLOW_parameter_def_in_method_decl242);
					parm1=parameter_def();
					state._fsp--;
					if (state.failed) return;
					if ( state.backtracking==0 ) {
					        if(0 == strID.compareTo("main"))
					        {
					            System.out.println(line + ":" + pos + "  Method \"main\" cannot have parameters.");
					            
					            setValidMain(false);
					        }
					        else
					        {
							        method.addParameter(parm1);
							        m_symbolTable.addSymbol(parm1.getName(), parm1.getType());
							    }
					    }
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:173:3: ( COMMA parm2= parameter_def )*
					loop8:
					while (true) {
						int alt8=2;
						int LA8_0 = input.LA(1);
						if ( (LA8_0==COMMA) ) {
							alt8=1;
						}

						switch (alt8) {
						case 1 :
							// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:173:4: COMMA parm2= parameter_def
							{
							COMMA10=(Token)match(input,COMMA,FOLLOW_COMMA_in_method_decl254); if (state.failed) return;
							pushFollow(FOLLOW_parameter_def_in_method_decl258);
							parm2=parameter_def();
							state._fsp--;
							if (state.failed) return;
							if ( state.backtracking==0 ) {
							        if(method.paramExists(parm2.getName()))
							        {
							            System.out.println((COMMA10!=null?COMMA10.getLine():0) + ":" + (COMMA10!=null?COMMA10.getCharPositionInLine():0) + " Parameter " + parm2.getName() + " already defined.");
							        }
							        else
							        {
							            method.addParameter(parm2);
							            m_symbolTable.addSymbol(parm2.getName(), parm2.getType());
							        }
							    }
							}
							break;

						default :
							break loop8;
						}
					}

					}
					break;

			}

			match(input,RPAREN,FOLLOW_RPAREN_in_method_decl273); if (state.failed) return;
			if ( state.backtracking==0 ) {
			        m_curMethod = method;
			  }
			pushFollow(FOLLOW_block_in_method_decl281);
			block();
			state._fsp--;
			if (state.failed) return;
			if ( state.backtracking==0 ) {
			        m_curMethod = null;
			    }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "method_decl"



	// $ANTLR start "parameter_def"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:195:1: parameter_def returns [Symbol sym] : type b= IDENTIFIER ;
	public final Symbol parameter_def() throws RecognitionException {
		Symbol sym = null;


		Token b=null;
		ParserRuleReturnScope type11 =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:196:3: ( type b= IDENTIFIER )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:196:5: type b= IDENTIFIER
			{
			pushFollow(FOLLOW_type_in_parameter_def306);
			type11=type();
			state._fsp--;
			if (state.failed) return sym;
			b=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_parameter_def310); if (state.failed) return sym;
			if ( state.backtracking==0 ) {
					    Expression.Type type = stringToType((type11!=null?input.toString(type11.start,type11.stop):null));
					        
					    sym = new Symbol((b!=null?b.getText():null), type);
					}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return sym;
	}
	// $ANTLR end "parameter_def"


	public static class type_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "type"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:204:1: type : ( INT | BOOLEAN );
	public final DecafParser.type_return type() throws RecognitionException {
		DecafParser.type_return retval = new DecafParser.type_return();
		retval.start = input.LT(1);

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:204:5: ( INT | BOOLEAN )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:
			{
			if ( input.LA(1)==BOOLEAN||input.LA(1)==INT ) {
				input.consume();
				state.errorRecovery=false;
				state.failed=false;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "type"



	// $ANTLR start "statement"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:206:1: statement : (loc= location assign= ( ASSIGN | ADD_ASSIGN | SUBTRACT_ASSIGN ) ex= expr SEMICOLON | method_call SEMICOLON | IF LPAREN ex= expr RPAREN block ( ELSE block )? | FOR IDENTIFIER ASSIGN exStart= expr COMMA exEnd= expr block | RETURN ex= expr SEMICOLON | BREAK SEMICOLON | CONTINUE SEMICOLON | block );
	public final void statement() throws RecognitionException {
		Token assign=null;
		Token LPAREN12=null;
		Token IDENTIFIER13=null;
		Token ASSIGN14=null;
		Token COMMA15=null;
		Token SEMICOLON16=null;
		Token SEMICOLON17=null;
		Symbol loc =null;
		Expression ex =null;
		Expression exStart =null;
		Expression exEnd =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:207:3: (loc= location assign= ( ASSIGN | ADD_ASSIGN | SUBTRACT_ASSIGN ) ex= expr SEMICOLON | method_call SEMICOLON | IF LPAREN ex= expr RPAREN block ( ELSE block )? | FOR IDENTIFIER ASSIGN exStart= expr COMMA exEnd= expr block | RETURN ex= expr SEMICOLON | BREAK SEMICOLON | CONTINUE SEMICOLON | block )
			int alt11=8;
			switch ( input.LA(1) ) {
			case IDENTIFIER:
				{
				int LA11_1 = input.LA(2);
				if ( (LA11_1==ADD_ASSIGN||LA11_1==ASSIGN||LA11_1==LBRACKET||LA11_1==SUBTRACT_ASSIGN) ) {
					alt11=1;
				}
				else if ( (LA11_1==LPAREN) ) {
					alt11=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 11, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case CALLOUT:
				{
				alt11=2;
				}
				break;
			case IF:
				{
				alt11=3;
				}
				break;
			case FOR:
				{
				alt11=4;
				}
				break;
			case RETURN:
				{
				alt11=5;
				}
				break;
			case BREAK:
				{
				alt11=6;
				}
				break;
			case CONTINUE:
				{
				alt11=7;
				}
				break;
			case LCURLY:
				{
				alt11=8;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return;}
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}
			switch (alt11) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:207:5: loc= location assign= ( ASSIGN | ADD_ASSIGN | SUBTRACT_ASSIGN ) ex= expr SEMICOLON
					{
					pushFollow(FOLLOW_location_in_statement339);
					loc=location();
					state._fsp--;
					if (state.failed) return;
					assign=input.LT(1);
					if ( input.LA(1)==ADD_ASSIGN||input.LA(1)==ASSIGN||input.LA(1)==SUBTRACT_ASSIGN ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_expr_in_statement357);
					ex=expr();
					state._fsp--;
					if (state.failed) return;
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement359); if (state.failed) return;
					if ( state.backtracking==0 ) {
					          if(0 == (assign!=null?assign.getText():null).compareTo("="))
					          {
					              if(loc.getType() != ex.getType())
					                  System.out.println((assign!=null?assign.getLine():0) + ":" + (assign!=null?assign.getCharPositionInLine():0) + " Type mismatch.  Assignment of invalid type.");
					          }
					          else
					          { 
					              if(loc.getType() != Expression.Type.TYPE_INT
					                  || ex.getType() != Expression.Type.TYPE_INT)
					              {
					                  System.out.println((assign!=null?assign.getLine():0) + ":" + (assign!=null?assign.getCharPositionInLine():0) + " Invalid assignment.  Right and left of operand must be of type int.");
					              }
					          }
					      }
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:223:5: method_call SEMICOLON
					{
					pushFollow(FOLLOW_method_call_in_statement375);
					method_call();
					state._fsp--;
					if (state.failed) return;
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement377); if (state.failed) return;
					if ( state.backtracking==0 ) {
					          // Emit code for method call
					      }
					}
					break;
				case 3 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:227:5: IF LPAREN ex= expr RPAREN block ( ELSE block )?
					{
					match(input,IF,FOLLOW_IF_in_statement391); if (state.failed) return;
					LPAREN12=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_statement393); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_statement397);
					ex=expr();
					state._fsp--;
					if (state.failed) return;
					match(input,RPAREN,FOLLOW_RPAREN_in_statement399); if (state.failed) return;
					if ( state.backtracking==0 ) {
					          if(Expression.Type.TYPE_BOOL != ex.getType())
					              System.out.println((LPAREN12!=null?LPAREN12.getLine():0) + ":" + (LPAREN12!=null?LPAREN12.getCharPositionInLine():0) + " Invalid type.  Condition in \"if\" statement must be of type boolean.");
					      }
					pushFollow(FOLLOW_block_in_statement414);
					block();
					state._fsp--;
					if (state.failed) return;
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:232:11: ( ELSE block )?
					int alt10=2;
					int LA10_0 = input.LA(1);
					if ( (LA10_0==ELSE) ) {
						alt10=1;
					}
					switch (alt10) {
						case 1 :
							// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:232:12: ELSE block
							{
							match(input,ELSE,FOLLOW_ELSE_in_statement417); if (state.failed) return;
							pushFollow(FOLLOW_block_in_statement419);
							block();
							state._fsp--;
							if (state.failed) return;
							}
							break;

					}

					}
					break;
				case 4 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:233:5: FOR IDENTIFIER ASSIGN exStart= expr COMMA exEnd= expr block
					{
					match(input,FOR,FOLLOW_FOR_in_statement427); if (state.failed) return;
					if ( state.backtracking==0 ) {
					         m_symbolTable.startScope();
					      }
					IDENTIFIER13=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_statement442); if (state.failed) return;
					if ( state.backtracking==0 ) {
					          m_symbolTable.addSymbol((IDENTIFIER13!=null?IDENTIFIER13.getText():null), Expression.Type.TYPE_INT);
					      }
					ASSIGN14=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_statement456); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_statement460);
					exStart=expr();
					state._fsp--;
					if (state.failed) return;
					COMMA15=(Token)match(input,COMMA,FOLLOW_COMMA_in_statement462); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_statement466);
					exEnd=expr();
					state._fsp--;
					if (state.failed) return;
					if ( state.backtracking==0 ) {
						        if(Expression.Type.TYPE_INT != exStart.getType())
						        {
						            System.out.println((ASSIGN14!=null?ASSIGN14.getLine():0) + ":" + (ASSIGN14!=null?ASSIGN14.getCharPositionInLine():0) + " Start of range in for statement must be of type int.");
						        }
						        
						        if(Expression.Type.TYPE_INT != exEnd.getType())
					          {
					              System.out.println((COMMA15!=null?COMMA15.getLine():0) + ":" + (COMMA15!=null?COMMA15.getCharPositionInLine():0) + " End of range in for statement must be of type int.");
					          }
					          
					          setInForLoop(true);
						    }
					pushFollow(FOLLOW_block_in_statement479);
					block();
					state._fsp--;
					if (state.failed) return;
					if ( state.backtracking==0 ) {
					          setInForLoop(false);
					          m_symbolTable.endScope();
					      }
					}
					break;
				case 5 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:260:5: RETURN ex= expr SEMICOLON
					{
					match(input,RETURN,FOLLOW_RETURN_in_statement493); if (state.failed) return;
					pushFollow(FOLLOW_expr_in_statement497);
					ex=expr();
					state._fsp--;
					if (state.failed) return;
					match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement499); if (state.failed) return;
					if ( state.backtracking==0 ) {
					          Token ahead = input.LT(1);
					          int line = ahead.getLine();
					          int pos = ahead.getCharPositionInLine();
					          
					          if(null == m_curMethod)
					          {
					              System.out.println(line + ":" + pos + "  Return statement must be inside of a method.");
					          }
					          else if(Expression.Type.TYPE_VOID == m_curMethod.getType())
					          {
					              System.out.println(line + ":" + pos + "  Return statement invalid inside of method with void return type.");
					          }
					          else if(ex.getType() != m_curMethod.getType())
					          {
					              System.out.println(line + ":" + pos + "  Return value type different from method type.");
					          }
					      }
					}
					break;
				case 6 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:279:5: BREAK SEMICOLON
					{
					match(input,BREAK,FOLLOW_BREAK_in_statement513); if (state.failed) return;
					SEMICOLON16=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement515); if (state.failed) return;
					if ( state.backtracking==0 ) {
					          if(!getInForLoop())
					              System.out.println((SEMICOLON16!=null?SEMICOLON16.getLine():0) + ":" + (SEMICOLON16!=null?SEMICOLON16.getCharPositionInLine():0) + " Break statement must be in the context of a for loop."); 
					      }
					}
					break;
				case 7 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:284:5: CONTINUE SEMICOLON
					{
					match(input,CONTINUE,FOLLOW_CONTINUE_in_statement529); if (state.failed) return;
					SEMICOLON17=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statement531); if (state.failed) return;
					if ( state.backtracking==0 ) {
					          if(!getInForLoop())
					              System.out.println((SEMICOLON17!=null?SEMICOLON17.getLine():0) + ":" + (SEMICOLON17!=null?SEMICOLON17.getCharPositionInLine():0) + " Continue statement must be in the context of a for loop."); 
					      }
					}
					break;
				case 8 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:289:5: block
					{
					pushFollow(FOLLOW_block_in_statement545);
					block();
					state._fsp--;
					if (state.failed) return;
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "statement"



	// $ANTLR start "method_call"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:291:1: method_call returns [MethodCall callMethod] : ( IDENTIFIER LPAREN (a= expr ( COMMA b= expr )* )? RPAREN | CALLOUT LPAREN str= literal ( COMMA callout_arg )* RPAREN );
	public final MethodCall method_call() throws RecognitionException {
		MethodCall callMethod = null;


		Token IDENTIFIER18=null;
		Token LPAREN19=null;
		Expression a =null;
		Expression b =null;
		Literal str =null;


		    Method refMethod = null;
		    int nParamIndex = 0;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:297:3: ( IDENTIFIER LPAREN (a= expr ( COMMA b= expr )* )? RPAREN | CALLOUT LPAREN str= literal ( COMMA callout_arg )* RPAREN )
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==IDENTIFIER) ) {
				alt15=1;
			}
			else if ( (LA15_0==CALLOUT) ) {
				alt15=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return callMethod;}
				NoViableAltException nvae =
					new NoViableAltException("", 15, 0, input);
				throw nvae;
			}

			switch (alt15) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:297:5: IDENTIFIER LPAREN (a= expr ( COMMA b= expr )* )? RPAREN
					{
					IDENTIFIER18=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_method_call564); if (state.failed) return callMethod;
					match(input,LPAREN,FOLLOW_LPAREN_in_method_call566); if (state.failed) return callMethod;
					if ( state.backtracking==0 ) {
					        refMethod = m_symbolTable.lookupMethod((IDENTIFIER18!=null?IDENTIFIER18.getText():null));
					        
					        if(null == refMethod)
					        {
					            System.out.println((IDENTIFIER18!=null?IDENTIFIER18.getLine():0) + ":" + (IDENTIFIER18!=null?IDENTIFIER18.getCharPositionInLine():0) + " Invalid method call.  Undefined method identifier.");
					        }
					        else
					        {
					            callMethod = new MethodCall((IDENTIFIER18!=null?IDENTIFIER18.getText():null), refMethod.getType());
					        }
					    }
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:310:5: (a= expr ( COMMA b= expr )* )?
					int alt13=2;
					int LA13_0 = input.LA(1);
					if ( (LA13_0==CALLOUT||LA13_0==CHARLITERAL||LA13_0==FALSE||LA13_0==IDENTIFIER||LA13_0==INTLITERAL||LA13_0==LPAREN||LA13_0==NOT||(LA13_0 >= STRINGLITERAL && LA13_0 <= SUBTRACT)||LA13_0==TRUE) ) {
						alt13=1;
					}
					switch (alt13) {
						case 1 :
							// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:310:6: a= expr ( COMMA b= expr )*
							{
							pushFollow(FOLLOW_expr_in_method_call581);
							a=expr();
							state._fsp--;
							if (state.failed) return callMethod;
							if ( state.backtracking==0 ) {
							            Symbol symParam = refMethod.getParameterAt(nParamIndex);
							            
							            if(null == symParam)
							            {
							                System.out.println((IDENTIFIER18!=null?IDENTIFIER18.getLine():0) + ":" + (IDENTIFIER18!=null?IDENTIFIER18.getCharPositionInLine():0) + " Invalid number of parameters in method call.");
							            }
							            else if(symParam.getType() != a.getType())
							            {
							                System.out.println((IDENTIFIER18!=null?IDENTIFIER18.getLine():0) + ":" + (IDENTIFIER18!=null?IDENTIFIER18.getCharPositionInLine():0) + " Invalid method call.  Wrong type for parameter " + nParamIndex + ".");
							            }
							            else
							            {
							                callMethod.addParameter(a);
							            }
							            
							            nParamIndex++;
							        }
							// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:329:5: ( COMMA b= expr )*
							loop12:
							while (true) {
								int alt12=2;
								int LA12_0 = input.LA(1);
								if ( (LA12_0==COMMA) ) {
									alt12=1;
								}

								switch (alt12) {
								case 1 :
									// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:329:6: COMMA b= expr
									{
									match(input,COMMA,FOLLOW_COMMA_in_method_call599); if (state.failed) return callMethod;
									pushFollow(FOLLOW_expr_in_method_call603);
									b=expr();
									state._fsp--;
									if (state.failed) return callMethod;
									if ( state.backtracking==0 ) {
									            Symbol symParam = refMethod.getParameterAt(nParamIndex);
									            
									            if(null == symParam)
									            {
									                System.out.println((IDENTIFIER18!=null?IDENTIFIER18.getLine():0) + ":" + (IDENTIFIER18!=null?IDENTIFIER18.getCharPositionInLine():0) + " Invalid number of parameters in method call.");
									            }
									            else if(symParam.getType() != b.getType())
									            {
									                System.out.println((IDENTIFIER18!=null?IDENTIFIER18.getLine():0) + ":" + (IDENTIFIER18!=null?IDENTIFIER18.getCharPositionInLine():0) + " Invalid method call.  Wrong type for parameter " + nParamIndex + ".");
									            }
									            else
									            {
									                callMethod.addParameter(b);
									            }
									            
									            nParamIndex++;
									        }
									}
									break;

								default :
									break loop12;
								}
							}

							}
							break;

					}

					match(input,RPAREN,FOLLOW_RPAREN_in_method_call624); if (state.failed) return callMethod;
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:350:3: CALLOUT LPAREN str= literal ( COMMA callout_arg )* RPAREN
					{
					match(input,CALLOUT,FOLLOW_CALLOUT_in_method_call633); if (state.failed) return callMethod;
					LPAREN19=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_method_call635); if (state.failed) return callMethod;
					pushFollow(FOLLOW_literal_in_method_call639);
					str=literal();
					state._fsp--;
					if (state.failed) return callMethod;
					if ( state.backtracking==0 ) {
					        if(Literal.Type.TYPE_STRING != str.getType())
					            System.out.println((LPAREN19!=null?LPAREN19.getLine():0) + ":" + (LPAREN19!=null?LPAREN19.getCharPositionInLine():0) + " First parameter to callout() must be a string.");
					            
					        callMethod = new MethodCall(str.getValue(), Expression.Type.TYPE_INT);
					    }
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:357:3: ( COMMA callout_arg )*
					loop14:
					while (true) {
						int alt14=2;
						int LA14_0 = input.LA(1);
						if ( (LA14_0==COMMA) ) {
							alt14=1;
						}

						switch (alt14) {
						case 1 :
							// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:357:4: COMMA callout_arg
							{
							match(input,COMMA,FOLLOW_COMMA_in_method_call650); if (state.failed) return callMethod;
							pushFollow(FOLLOW_callout_arg_in_method_call652);
							callout_arg();
							state._fsp--;
							if (state.failed) return callMethod;
							}
							break;

						default :
							break loop14;
						}
					}

					match(input,RPAREN,FOLLOW_RPAREN_in_method_call656); if (state.failed) return callMethod;
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return callMethod;
	}
	// $ANTLR end "method_call"



	// $ANTLR start "location"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:360:1: location returns [Symbol sym] : ( ( IDENTIFIER LBRACKET )=>id1= IDENTIFIER LBRACKET ex= expr RBRACKET |id2= IDENTIFIER );
	public final Symbol location() throws RecognitionException {
		Symbol sym = null;


		Token id1=null;
		Token id2=null;
		Token LBRACKET20=null;
		Expression ex =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:361:3: ( ( IDENTIFIER LBRACKET )=>id1= IDENTIFIER LBRACKET ex= expr RBRACKET |id2= IDENTIFIER )
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==IDENTIFIER) ) {
				int LA16_1 = input.LA(2);
				if ( (LA16_1==LBRACKET) && (synpred2_DecafParser())) {
					alt16=1;
				}
				else if ( (LA16_1==EOF||(LA16_1 >= ADD && LA16_1 <= ASSIGN)||LA16_1==COMMA||LA16_1==DIVIDE||LA16_1==EQ||(LA16_1 >= GT && LA16_1 <= GTE)||LA16_1==LCURLY||(LA16_1 >= LT && LA16_1 <= NEQ)||LA16_1==OR||LA16_1==RBRACKET||(LA16_1 >= RPAREN && LA16_1 <= SEMICOLON)||(LA16_1 >= SUBTRACT && LA16_1 <= SUBTRACT_ASSIGN)) ) {
					alt16=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return sym;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 16, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				if (state.backtracking>0) {state.failed=true; return sym;}
				NoViableAltException nvae =
					new NoViableAltException("", 16, 0, input);
				throw nvae;
			}

			switch (alt16) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:361:5: ( IDENTIFIER LBRACKET )=>id1= IDENTIFIER LBRACKET ex= expr RBRACKET
					{
					id1=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_location681); if (state.failed) return sym;
					LBRACKET20=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_location683); if (state.failed) return sym;
					pushFollow(FOLLOW_expr_in_location687);
					ex=expr();
					state._fsp--;
					if (state.failed) return sym;
					match(input,RBRACKET,FOLLOW_RBRACKET_in_location689); if (state.failed) return sym;
					if ( state.backtracking==0 ) {
					        sym = m_symbolTable.lookupSymbol((id1!=null?id1.getText():null), false);
					        
					        if(null == sym)
					        {
					            System.out.println((id1!=null?id1.getLine():0) + ":" + (id1!=null?id1.getCharPositionInLine():0) + " Undefined symbol " + (id1!=null?id1.getText():null));
					            
					            sym = new Symbol((id1!=null?id1.getText():null), Expression.Type.TYPE_INVALID);
					        }
					        
					        if(!sym.getIsArray())
					            System.out.println((id1!=null?id1.getLine():0) + ":" + (id1!=null?id1.getCharPositionInLine():0) + " Identifier " + (id1!=null?id1.getText():null) + " is not an array.");
					         
					        if(Expression.Type.TYPE_INT != ex.getType())
					            System.out.println((LBRACKET20!=null?LBRACKET20.getLine():0) + ":" + (LBRACKET20!=null?LBRACKET20.getCharPositionInLine():0) + " Array index must be of type int.");
					    }
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:378:5: id2= IDENTIFIER
					{
					id2=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_location703); if (state.failed) return sym;
					if ( state.backtracking==0 ) {
						      sym = m_symbolTable.lookupSymbol((id2!=null?id2.getText():null), false);
						      
						      if(null == sym)
						      {
						          System.out.println((id2!=null?id2.getLine():0) + ":" + (id2!=null?id2.getCharPositionInLine():0) + " Undefined symbol " + (id2!=null?id2.getText():null));
						          
						          sym = new Symbol((id2!=null?id2.getText():null), Expression.Type.TYPE_INVALID);
						      }
						      else if(sym.getIsArray())
						      {
						          System.out.println((id2!=null?id2.getLine():0) + ":" + (id2!=null?id2.getCharPositionInLine():0) + " Invalid use of array type identifier.");	          
						      }
						  }
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return sym;
	}
	// $ANTLR end "location"



	// $ANTLR start "expr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:395:1: expr returns [Expression expr] : a= andExpr ( OR b= andExpr )* ;
	public final Expression expr() throws RecognitionException {
		Expression expr = null;


		Token OR21=null;
		Expression a =null;
		Expression b =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:396:3: (a= andExpr ( OR b= andExpr )* )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:396:6: a= andExpr ( OR b= andExpr )*
			{
			pushFollow(FOLLOW_andExpr_in_expr729);
			a=andExpr();
			state._fsp--;
			if (state.failed) return expr;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:396:16: ( OR b= andExpr )*
			loop17:
			while (true) {
				int alt17=2;
				int LA17_0 = input.LA(1);
				if ( (LA17_0==OR) ) {
					alt17=1;
				}

				switch (alt17) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:396:17: OR b= andExpr
					{
					OR21=(Token)match(input,OR,FOLLOW_OR_in_expr732); if (state.failed) return expr;
					pushFollow(FOLLOW_andExpr_in_expr736);
					b=andExpr();
					state._fsp--;
					if (state.failed) return expr;
					}
					break;

				default :
					break loop17;
				}
			}

			if ( state.backtracking==0 ) {
			        if(null == b)
			        {
			            expr = a;
			        }
			        else
			        {
			            if(Expression.Type.TYPE_BOOL != a.getType()
			              || Expression.Type.TYPE_BOOL != b.getType())
			            {
			                System.out.println((OR21!=null?OR21.getLine():0) + ":" + (OR21!=null?OR21.getCharPositionInLine():0) + " Type mismatch.");
			            }

			            expr = new ConditionalExpression(ConditionalExpression.Operator.OP_OR, a, b);
			        }
			    }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "expr"



	// $ANTLR start "andExpr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:415:1: andExpr returns [Expression expr] : a= eqExpr ( AND b= eqExpr )* ;
	public final Expression andExpr() throws RecognitionException {
		Expression expr = null;


		Token AND22=null;
		Expression a =null;
		Expression b =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:416:3: (a= eqExpr ( AND b= eqExpr )* )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:416:5: a= eqExpr ( AND b= eqExpr )*
			{
			pushFollow(FOLLOW_eqExpr_in_andExpr764);
			a=eqExpr();
			state._fsp--;
			if (state.failed) return expr;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:416:14: ( AND b= eqExpr )*
			loop18:
			while (true) {
				int alt18=2;
				int LA18_0 = input.LA(1);
				if ( (LA18_0==AND) ) {
					alt18=1;
				}

				switch (alt18) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:416:15: AND b= eqExpr
					{
					AND22=(Token)match(input,AND,FOLLOW_AND_in_andExpr767); if (state.failed) return expr;
					pushFollow(FOLLOW_eqExpr_in_andExpr771);
					b=eqExpr();
					state._fsp--;
					if (state.failed) return expr;
					}
					break;

				default :
					break loop18;
				}
			}

			if ( state.backtracking==0 ) {
			        if(null == b)
			        {
			            expr = a;
			        }
			        else
			        {
			            if(Expression.Type.TYPE_BOOL != a.getType()
			              || Expression.Type.TYPE_BOOL != b.getType())
			            {
			                System.out.println((AND22!=null?AND22.getLine():0) + ":" + (AND22!=null?AND22.getCharPositionInLine():0) +" Type mismatch.");
			            }
			            
			            expr = new ConditionalExpression(ConditionalExpression.Operator.OP_AND, a, b);
			        }
			    }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "andExpr"



	// $ANTLR start "eqExpr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:435:1: eqExpr returns [Expression expr] : a= compExpr (eq= ( EQ | NEQ ) b= compExpr )* ;
	public final Expression eqExpr() throws RecognitionException {
		Expression expr = null;


		Token eq=null;
		Expression a =null;
		Expression b =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:436:3: (a= compExpr (eq= ( EQ | NEQ ) b= compExpr )* )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:436:5: a= compExpr (eq= ( EQ | NEQ ) b= compExpr )*
			{
			pushFollow(FOLLOW_compExpr_in_eqExpr798);
			a=compExpr();
			state._fsp--;
			if (state.failed) return expr;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:436:16: (eq= ( EQ | NEQ ) b= compExpr )*
			loop19:
			while (true) {
				int alt19=2;
				int LA19_0 = input.LA(1);
				if ( (LA19_0==EQ||LA19_0==NEQ) ) {
					alt19=1;
				}

				switch (alt19) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:436:17: eq= ( EQ | NEQ ) b= compExpr
					{
					eq=input.LT(1);
					if ( input.LA(1)==EQ||input.LA(1)==NEQ ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return expr;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_compExpr_in_eqExpr813);
					b=compExpr();
					state._fsp--;
					if (state.failed) return expr;
					}
					break;

				default :
					break loop19;
				}
			}

			if ( state.backtracking==0 ) {
			        if(null == b)
			        {
			            expr = a;
			        }
			        else
			        {
			            if(a.getType() != b.getType())
			            {
			                System.out.println((eq!=null?eq.getLine():0) + ":" + (eq!=null?eq.getCharPositionInLine():0) +" Type mismatch.  Operands must have the same type.");
			            }

			            expr = new EquivalenceExpression(EquivalenceExpression.parseOperator((eq!=null?eq.getText():null)), a, b);
			        }
			    }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "eqExpr"



	// $ANTLR start "compExpr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:454:1: compExpr returns [Expression expr] : a= addExpr (rel= ( GT | LT | GTE | LTE ) b= addExpr )* ;
	public final Expression compExpr() throws RecognitionException {
		Expression expr = null;


		Token rel=null;
		Expression a =null;
		Expression b =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:455:3: (a= addExpr (rel= ( GT | LT | GTE | LTE ) b= addExpr )* )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:455:5: a= addExpr (rel= ( GT | LT | GTE | LTE ) b= addExpr )*
			{
			pushFollow(FOLLOW_addExpr_in_compExpr840);
			a=addExpr();
			state._fsp--;
			if (state.failed) return expr;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:455:15: (rel= ( GT | LT | GTE | LTE ) b= addExpr )*
			loop20:
			while (true) {
				int alt20=2;
				int LA20_0 = input.LA(1);
				if ( ((LA20_0 >= GT && LA20_0 <= GTE)||(LA20_0 >= LT && LA20_0 <= LTE)) ) {
					alt20=1;
				}

				switch (alt20) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:455:16: rel= ( GT | LT | GTE | LTE ) b= addExpr
					{
					rel=input.LT(1);
					if ( (input.LA(1) >= GT && input.LA(1) <= GTE)||(input.LA(1) >= LT && input.LA(1) <= LTE) ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return expr;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_addExpr_in_compExpr863);
					b=addExpr();
					state._fsp--;
					if (state.failed) return expr;
					}
					break;

				default :
					break loop20;
				}
			}

			if ( state.backtracking==0 ) {
			        if(null == b)
			        {
			            expr = a;
			        }
			        else
			        {
			            if(Expression.Type.TYPE_INT != a.getType()
			              || Expression.Type.TYPE_INT != b.getType())
			            {
			                System.out.println((rel!=null?rel.getLine():0) + ":" + (rel!=null?rel.getCharPositionInLine():0) + "  Type mismatch.  Operands must be type int.");
			            }  
			                     
			            expr = new RelationalExpression(RelationalExpression.parseOperator((rel!=null?rel.getText():null)), a, b);
			        }
			    }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "compExpr"



	// $ANTLR start "addExpr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:474:1: addExpr returns [Expression expr] : a= multExpr (add= ( ADD | SUBTRACT ) b= multExpr )* ;
	public final Expression addExpr() throws RecognitionException {
		Expression expr = null;


		Token add=null;
		Expression a =null;
		Expression b =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:475:3: (a= multExpr (add= ( ADD | SUBTRACT ) b= multExpr )* )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:475:5: a= multExpr (add= ( ADD | SUBTRACT ) b= multExpr )*
			{
			pushFollow(FOLLOW_multExpr_in_addExpr890);
			a=multExpr();
			state._fsp--;
			if (state.failed) return expr;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:475:16: (add= ( ADD | SUBTRACT ) b= multExpr )*
			loop21:
			while (true) {
				int alt21=2;
				int LA21_0 = input.LA(1);
				if ( (LA21_0==ADD||LA21_0==SUBTRACT) ) {
					alt21=1;
				}

				switch (alt21) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:475:17: add= ( ADD | SUBTRACT ) b= multExpr
					{
					add=input.LT(1);
					if ( input.LA(1)==ADD||input.LA(1)==SUBTRACT ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return expr;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_multExpr_in_addExpr905);
					b=multExpr();
					state._fsp--;
					if (state.failed) return expr;
					}
					break;

				default :
					break loop21;
				}
			}

			if ( state.backtracking==0 ) {
			        if(null == b)
			        {
			            expr = a;
			        }
			        else
			        {
			            if(Expression.Type.TYPE_INT != a.getType()
			              || Expression.Type.TYPE_INT != b.getType())
			            {
			                System.out.println((add!=null?add.getLine():0) + ":" + (add!=null?add.getCharPositionInLine():0) + " Type mismatch.  Operands must be type int.");
			            }

			            expr = new MathExpression(MathExpression.parseOperator((add!=null?add.getText():null)), a, b);
			        }
			    }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "addExpr"



	// $ANTLR start "multExpr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:494:1: multExpr returns [Expression expr] : a= signedExpr (mul= ( MULTIPLY | DIVIDE | MODULUS ) b= signedExpr )* ;
	public final Expression multExpr() throws RecognitionException {
		Expression expr = null;


		Token mul=null;
		Expression a =null;
		Expression b =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:495:3: (a= signedExpr (mul= ( MULTIPLY | DIVIDE | MODULUS ) b= signedExpr )* )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:495:5: a= signedExpr (mul= ( MULTIPLY | DIVIDE | MODULUS ) b= signedExpr )*
			{
			pushFollow(FOLLOW_signedExpr_in_multExpr932);
			a=signedExpr();
			state._fsp--;
			if (state.failed) return expr;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:495:18: (mul= ( MULTIPLY | DIVIDE | MODULUS ) b= signedExpr )*
			loop22:
			while (true) {
				int alt22=2;
				int LA22_0 = input.LA(1);
				if ( (LA22_0==DIVIDE||(LA22_0 >= MODULUS && LA22_0 <= MULTIPLY)) ) {
					alt22=1;
				}

				switch (alt22) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:495:19: mul= ( MULTIPLY | DIVIDE | MODULUS ) b= signedExpr
					{
					mul=input.LT(1);
					if ( input.LA(1)==DIVIDE||(input.LA(1) >= MODULUS && input.LA(1) <= MULTIPLY) ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return expr;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_signedExpr_in_multExpr951);
					b=signedExpr();
					state._fsp--;
					if (state.failed) return expr;
					}
					break;

				default :
					break loop22;
				}
			}

			if ( state.backtracking==0 ) {
			        if(null == b)
			        {
			            expr = a;
			        }
			        else
			        {
			            if(Expression.Type.TYPE_INT != a.getType()
			              || Expression.Type.TYPE_INT != b.getType())
			            {
			                System.out.println((mul!=null?mul.getLine():0) + ":" + (mul!=null?mul.getCharPositionInLine():0) + " Type mismatch.  Operands must be type int.");
			            }

			            expr = new MathExpression(MathExpression.parseOperator((mul!=null?mul.getText():null)), a, b);
			        }
			    }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "multExpr"



	// $ANTLR start "signedExpr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:514:1: signedExpr returns [Expression expr] : ( SUBTRACT a= notExpr |b= notExpr );
	public final Expression signedExpr() throws RecognitionException {
		Expression expr = null;


		Token SUBTRACT23=null;
		Expression a =null;
		Expression b =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:515:3: ( SUBTRACT a= notExpr |b= notExpr )
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( (LA23_0==SUBTRACT) ) {
				alt23=1;
			}
			else if ( (LA23_0==CALLOUT||LA23_0==CHARLITERAL||LA23_0==FALSE||LA23_0==IDENTIFIER||LA23_0==INTLITERAL||LA23_0==LPAREN||LA23_0==NOT||LA23_0==STRINGLITERAL||LA23_0==TRUE) ) {
				alt23=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return expr;}
				NoViableAltException nvae =
					new NoViableAltException("", 23, 0, input);
				throw nvae;
			}

			switch (alt23) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:515:5: SUBTRACT a= notExpr
					{
					SUBTRACT23=(Token)match(input,SUBTRACT,FOLLOW_SUBTRACT_in_signedExpr976); if (state.failed) return expr;
					pushFollow(FOLLOW_notExpr_in_signedExpr980);
					a=notExpr();
					state._fsp--;
					if (state.failed) return expr;
					if ( state.backtracking==0 ) {
					        if(Expression.Type.TYPE_INT != a.getType())
					        {
					            System.out.println((SUBTRACT23!=null?SUBTRACT23.getLine():0) + ":" + (SUBTRACT23!=null?SUBTRACT23.getCharPositionInLine():0) + " Invalid type for unary minus operator.  Operand must be type int.");
					        }
					        else
					        {
							        Literal lit = new Literal(Literal.Type.TYPE_INT, "-1");
							        
							        expr = new MathExpression(MathExpression.Operator.OP_MULTIPLY, a, new LiteralExpression(lit));
							    }
					    }
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:528:5: b= notExpr
					{
					pushFollow(FOLLOW_notExpr_in_signedExpr994);
					b=notExpr();
					state._fsp--;
					if (state.failed) return expr;
					if ( state.backtracking==0 ) {
						      expr = b;
						  }
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "signedExpr"



	// $ANTLR start "notExpr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:534:1: notExpr returns [Expression expr] options {backtrack=true; } : ( NOT a= atomExpr |b= atomExpr );
	public final Expression notExpr() throws RecognitionException {
		Expression expr = null;


		Token NOT24=null;
		Expression a =null;
		Expression b =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:536:3: ( NOT a= atomExpr |b= atomExpr )
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( (LA24_0==NOT) ) {
				alt24=1;
			}
			else if ( (LA24_0==CALLOUT||LA24_0==CHARLITERAL||LA24_0==FALSE||LA24_0==IDENTIFIER||LA24_0==INTLITERAL||LA24_0==LPAREN||LA24_0==STRINGLITERAL||LA24_0==TRUE) ) {
				alt24=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return expr;}
				NoViableAltException nvae =
					new NoViableAltException("", 24, 0, input);
				throw nvae;
			}

			switch (alt24) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:536:5: NOT a= atomExpr
					{
					NOT24=(Token)match(input,NOT,FOLLOW_NOT_in_notExpr1025); if (state.failed) return expr;
					pushFollow(FOLLOW_atomExpr_in_notExpr1029);
					a=atomExpr();
					state._fsp--;
					if (state.failed) return expr;
					if ( state.backtracking==0 ) {
					      if(Expression.Type.TYPE_BOOL != a.getType())
					        {
					            System.out.println((NOT24!=null?NOT24.getLine():0) + ":" + (NOT24!=null?NOT24.getCharPositionInLine():0) + " Invalid type for logical negation operator.  Operand must be type boolean.");
					        }

					        expr = new ConditionalExpression(ConditionalExpression.Operator.OP_NOT, a, null);
					  }
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:545:5: b= atomExpr
					{
					pushFollow(FOLLOW_atomExpr_in_notExpr1041);
					b=atomExpr();
					state._fsp--;
					if (state.failed) return expr;
					if ( state.backtracking==0 ) {
					      expr = b;
					  }
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "notExpr"



	// $ANTLR start "atomExpr"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:551:1: atomExpr returns [Expression expr] : ( LPAREN a= expr RPAREN |loc= location |methCall= method_call |lit= literal );
	public final Expression atomExpr() throws RecognitionException {
		Expression expr = null;


		Expression a =null;
		Symbol loc =null;
		MethodCall methCall =null;
		Literal lit =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:552:3: ( LPAREN a= expr RPAREN |loc= location |methCall= method_call |lit= literal )
			int alt25=4;
			switch ( input.LA(1) ) {
			case LPAREN:
				{
				alt25=1;
				}
				break;
			case IDENTIFIER:
				{
				int LA25_2 = input.LA(2);
				if ( (LA25_2==EOF||LA25_2==ADD||LA25_2==AND||LA25_2==COMMA||LA25_2==DIVIDE||LA25_2==EQ||(LA25_2 >= GT && LA25_2 <= GTE)||(LA25_2 >= LBRACKET && LA25_2 <= LCURLY)||(LA25_2 >= LT && LA25_2 <= NEQ)||LA25_2==OR||LA25_2==RBRACKET||(LA25_2 >= RPAREN && LA25_2 <= SEMICOLON)||LA25_2==SUBTRACT) ) {
					alt25=2;
				}
				else if ( (LA25_2==LPAREN) ) {
					alt25=3;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return expr;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 25, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case CALLOUT:
				{
				alt25=3;
				}
				break;
			case CHARLITERAL:
			case FALSE:
			case INTLITERAL:
			case STRINGLITERAL:
			case TRUE:
				{
				alt25=4;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return expr;}
				NoViableAltException nvae =
					new NoViableAltException("", 25, 0, input);
				throw nvae;
			}
			switch (alt25) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:552:5: LPAREN a= expr RPAREN
					{
					match(input,LPAREN,FOLLOW_LPAREN_in_atomExpr1062); if (state.failed) return expr;
					pushFollow(FOLLOW_expr_in_atomExpr1066);
					a=expr();
					state._fsp--;
					if (state.failed) return expr;
					match(input,RPAREN,FOLLOW_RPAREN_in_atomExpr1068); if (state.failed) return expr;
					if ( state.backtracking==0 ) {
					          expr = a;
					      }
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:556:5: loc= location
					{
					pushFollow(FOLLOW_location_in_atomExpr1084);
					loc=location();
					state._fsp--;
					if (state.failed) return expr;
					if ( state.backtracking==0 ) {
						        expr = new IdentifierExpression(loc);
						    }
					}
					break;
				case 3 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:560:5: methCall= method_call
					{
					pushFollow(FOLLOW_method_call_in_atomExpr1099);
					methCall=method_call();
					state._fsp--;
					if (state.failed) return expr;
					if ( state.backtracking==0 ) {
					          Token ahead = input.LT(1);
								    int line = ahead.getLine();
								    int pos = ahead.getCharPositionInLine();
								    
					          if(Expression.Type.TYPE_INT != methCall.getType()
					              && Expression.Type.TYPE_BOOL != methCall.getType())
					          {
					              System.out.println(line + ":" + pos + " Cannot use void method " + methCall.getName() + " in an expression.");
					          }
					          
					          expr = methCall;
					      }
					}
					break;
				case 4 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:574:5: lit= literal
					{
					pushFollow(FOLLOW_literal_in_atomExpr1115);
					lit=literal();
					state._fsp--;
					if (state.failed) return expr;
					if ( state.backtracking==0 ) {
					          expr = new LiteralExpression(lit);
					      }
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "atomExpr"



	// $ANTLR start "callout_arg"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:580:1: callout_arg : expr ;
	public final void callout_arg() throws RecognitionException {
		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:580:12: ( expr )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:580:14: expr
			{
			pushFollow(FOLLOW_expr_in_callout_arg1133);
			expr();
			state._fsp--;
			if (state.failed) return;
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "callout_arg"



	// $ANTLR start "unary_operator"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:582:1: unary_operator : ( NOT | SUBTRACT );
	public final void unary_operator() throws RecognitionException {
		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:582:15: ( NOT | SUBTRACT )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:
			{
			if ( input.LA(1)==NOT||input.LA(1)==SUBTRACT ) {
				input.consume();
				state.errorRecovery=false;
				state.failed=false;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return;}
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "unary_operator"


	public static class boolean_literal_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "boolean_literal"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:584:1: boolean_literal : ( TRUE | FALSE );
	public final DecafParser.boolean_literal_return boolean_literal() throws RecognitionException {
		DecafParser.boolean_literal_return retval = new DecafParser.boolean_literal_return();
		retval.start = input.LT(1);

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:584:16: ( TRUE | FALSE )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:
			{
			if ( input.LA(1)==FALSE||input.LA(1)==TRUE ) {
				input.consume();
				state.errorRecovery=false;
				state.failed=false;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "boolean_literal"



	// $ANTLR start "literal"
	// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:586:1: literal returns [Literal lit] : ( boolean_literal | CHARLITERAL | INTLITERAL | STRINGLITERAL );
	public final Literal literal() throws RecognitionException {
		Literal lit = null;


		Token CHARLITERAL26=null;
		Token INTLITERAL27=null;
		Token STRINGLITERAL28=null;
		ParserRuleReturnScope boolean_literal25 =null;

		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:587:4: ( boolean_literal | CHARLITERAL | INTLITERAL | STRINGLITERAL )
			int alt26=4;
			switch ( input.LA(1) ) {
			case FALSE:
			case TRUE:
				{
				alt26=1;
				}
				break;
			case CHARLITERAL:
				{
				alt26=2;
				}
				break;
			case INTLITERAL:
				{
				alt26=3;
				}
				break;
			case STRINGLITERAL:
				{
				alt26=4;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return lit;}
				NoViableAltException nvae =
					new NoViableAltException("", 26, 0, input);
				throw nvae;
			}
			switch (alt26) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:588:6: boolean_literal
					{
					pushFollow(FOLLOW_boolean_literal_in_literal1175);
					boolean_literal25=boolean_literal();
					state._fsp--;
					if (state.failed) return lit;
					if ( state.backtracking==0 ) {
					          lit = new Literal(Literal.Type.TYPE_BOOL, (boolean_literal25!=null?input.toString(boolean_literal25.start,boolean_literal25.stop):null));
					      }
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:592:6: CHARLITERAL
					{
					CHARLITERAL26=(Token)match(input,CHARLITERAL,FOLLOW_CHARLITERAL_in_literal1190); if (state.failed) return lit;
					if ( state.backtracking==0 ) {
					          lit = new Literal(Literal.Type.TYPE_CHAR, (CHARLITERAL26!=null?CHARLITERAL26.getText():null));
					      }
					}
					break;
				case 3 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:596:6: INTLITERAL
					{
					INTLITERAL27=(Token)match(input,INTLITERAL,FOLLOW_INTLITERAL_in_literal1207); if (state.failed) return lit;
					if ( state.backtracking==0 ) {
					          lit = new Literal(Literal.Type.TYPE_INT, (INTLITERAL27!=null?INTLITERAL27.getText():null));
					      }
					}
					break;
				case 4 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:600:6: STRINGLITERAL
					{
					STRINGLITERAL28=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_literal1222); if (state.failed) return lit;
					if ( state.backtracking==0 ) {
					          lit = new Literal(Literal.Type.TYPE_STRING, (STRINGLITERAL28!=null?STRINGLITERAL28.getText():null));
					      }
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return lit;
	}
	// $ANTLR end "literal"

	// $ANTLR start synpred1_DecafParser
	public final void synpred1_DecafParser_fragment() throws RecognitionException {
		// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:112:5: ( IDENTIFIER LBRACKET )
		// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:112:6: IDENTIFIER LBRACKET
		{
		match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_synpred1_DecafParser162); if (state.failed) return;
		match(input,LBRACKET,FOLLOW_LBRACKET_in_synpred1_DecafParser164); if (state.failed) return;
		}

	}
	// $ANTLR end synpred1_DecafParser

	// $ANTLR start synpred2_DecafParser
	public final void synpred2_DecafParser_fragment() throws RecognitionException {
		// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:361:5: ( IDENTIFIER LBRACKET )
		// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafParser.g:361:6: IDENTIFIER LBRACKET
		{
		match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_synpred2_DecafParser672); if (state.failed) return;
		match(input,LBRACKET,FOLLOW_LBRACKET_in_synpred2_DecafParser674); if (state.failed) return;
		}

	}
	// $ANTLR end synpred2_DecafParser

	// Delegated rules

	public final boolean synpred2_DecafParser() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred2_DecafParser_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred1_DecafParser() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred1_DecafParser_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}



	public static final BitSet FOLLOW_CLASS_in_program43 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_PROGRAM_in_program45 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_LCURLY_in_program47 = new BitSet(new long[]{0x0040200040000100L});
	public static final BitSet FOLLOW_field_decl_in_program50 = new BitSet(new long[]{0x0040200040000100L});
	public static final BitSet FOLLOW_method_decl_in_program55 = new BitSet(new long[]{0x0040200040000100L});
	public static final BitSet FOLLOW_RCURLY_in_program59 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_program61 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LCURLY_in_block84 = new BitSet(new long[]{0x0000600270808700L});
	public static final BitSet FOLLOW_field_decl_in_block88 = new BitSet(new long[]{0x0000600270808700L});
	public static final BitSet FOLLOW_statement_in_block93 = new BitSet(new long[]{0x0000600230808600L});
	public static final BitSet FOLLOW_RCURLY_in_block97 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_field_decl112 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_field_in_field_decl122 = new BitSet(new long[]{0x0001000000004000L});
	public static final BitSet FOLLOW_COMMA_in_field_decl133 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_field_in_field_decl137 = new BitSet(new long[]{0x0001000000004000L});
	public static final BitSet FOLLOW_SEMICOLON_in_field_decl147 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENTIFIER_in_field169 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_LBRACKET_in_field171 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_INTLITERAL_in_field173 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_RBRACKET_in_field175 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENTIFIER_in_field189 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_method_decl216 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_VOID_in_method_decl221 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_IDENTIFIER_in_method_decl226 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_LPAREN_in_method_decl228 = new BitSet(new long[]{0x0000800040000100L});
	public static final BitSet FOLLOW_parameter_def_in_method_decl242 = new BitSet(new long[]{0x0000800000004000L});
	public static final BitSet FOLLOW_COMMA_in_method_decl254 = new BitSet(new long[]{0x0000000040000100L});
	public static final BitSet FOLLOW_parameter_def_in_method_decl258 = new BitSet(new long[]{0x0000800000004000L});
	public static final BitSet FOLLOW_RPAREN_in_method_decl273 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_block_in_method_decl281 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_parameter_def306 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_IDENTIFIER_in_parameter_def310 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_location_in_statement339 = new BitSet(new long[]{0x00100000000000A0L});
	public static final BitSet FOLLOW_set_in_statement343 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_expr_in_statement357 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement359 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_method_call_in_statement375 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement377 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IF_in_statement391 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_LPAREN_in_statement393 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_expr_in_statement397 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_RPAREN_in_statement399 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_block_in_statement414 = new BitSet(new long[]{0x0000000000080002L});
	public static final BitSet FOLLOW_ELSE_in_statement417 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_block_in_statement419 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FOR_in_statement427 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_IDENTIFIER_in_statement442 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_ASSIGN_in_statement456 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_expr_in_statement460 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_COMMA_in_statement462 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_expr_in_statement466 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_block_in_statement479 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_RETURN_in_statement493 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_expr_in_statement497 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement499 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BREAK_in_statement513 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement515 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CONTINUE_in_statement529 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_SEMICOLON_in_statement531 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_block_in_statement545 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENTIFIER_in_method_call564 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_LPAREN_in_method_call566 = new BitSet(new long[]{0x002C810490401400L});
	public static final BitSet FOLLOW_expr_in_method_call581 = new BitSet(new long[]{0x0000800000004000L});
	public static final BitSet FOLLOW_COMMA_in_method_call599 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_expr_in_method_call603 = new BitSet(new long[]{0x0000800000004000L});
	public static final BitSet FOLLOW_RPAREN_in_method_call624 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CALLOUT_in_method_call633 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_LPAREN_in_method_call635 = new BitSet(new long[]{0x0024000080401000L});
	public static final BitSet FOLLOW_literal_in_method_call639 = new BitSet(new long[]{0x0000800000004000L});
	public static final BitSet FOLLOW_COMMA_in_method_call650 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_callout_arg_in_method_call652 = new BitSet(new long[]{0x0000800000004000L});
	public static final BitSet FOLLOW_RPAREN_in_method_call656 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENTIFIER_in_location681 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_LBRACKET_in_location683 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_expr_in_location687 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_RBRACKET_in_location689 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENTIFIER_in_location703 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_andExpr_in_expr729 = new BitSet(new long[]{0x0000020000000002L});
	public static final BitSet FOLLOW_OR_in_expr732 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_andExpr_in_expr736 = new BitSet(new long[]{0x0000020000000002L});
	public static final BitSet FOLLOW_eqExpr_in_andExpr764 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_AND_in_andExpr767 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_eqExpr_in_andExpr771 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_compExpr_in_eqExpr798 = new BitSet(new long[]{0x0000008000100002L});
	public static final BitSet FOLLOW_set_in_eqExpr803 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_compExpr_in_eqExpr813 = new BitSet(new long[]{0x0000008000100002L});
	public static final BitSet FOLLOW_addExpr_in_compExpr840 = new BitSet(new long[]{0x0000001803000002L});
	public static final BitSet FOLLOW_set_in_compExpr845 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_addExpr_in_compExpr863 = new BitSet(new long[]{0x0000001803000002L});
	public static final BitSet FOLLOW_multExpr_in_addExpr890 = new BitSet(new long[]{0x0008000000000012L});
	public static final BitSet FOLLOW_set_in_addExpr895 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_multExpr_in_addExpr905 = new BitSet(new long[]{0x0008000000000012L});
	public static final BitSet FOLLOW_signedExpr_in_multExpr932 = new BitSet(new long[]{0x0000006000040002L});
	public static final BitSet FOLLOW_set_in_multExpr937 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_signedExpr_in_multExpr951 = new BitSet(new long[]{0x0000006000040002L});
	public static final BitSet FOLLOW_SUBTRACT_in_signedExpr976 = new BitSet(new long[]{0x0024010490401400L});
	public static final BitSet FOLLOW_notExpr_in_signedExpr980 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_notExpr_in_signedExpr994 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOT_in_notExpr1025 = new BitSet(new long[]{0x0024000490401400L});
	public static final BitSet FOLLOW_atomExpr_in_notExpr1029 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_atomExpr_in_notExpr1041 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_atomExpr1062 = new BitSet(new long[]{0x002C010490401400L});
	public static final BitSet FOLLOW_expr_in_atomExpr1066 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_RPAREN_in_atomExpr1068 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_location_in_atomExpr1084 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_method_call_in_atomExpr1099 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_literal_in_atomExpr1115 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_callout_arg1133 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_boolean_literal_in_literal1175 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CHARLITERAL_in_literal1190 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTLITERAL_in_literal1207 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRINGLITERAL_in_literal1222 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENTIFIER_in_synpred1_DecafParser162 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_LBRACKET_in_synpred1_DecafParser164 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENTIFIER_in_synpred2_DecafParser672 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_LBRACKET_in_synpred2_DecafParser674 = new BitSet(new long[]{0x0000000000000002L});
}
