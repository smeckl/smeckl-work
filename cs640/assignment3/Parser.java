//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "spim.by"
import java.io.*;
import java.util.ArrayList;
import java.io.FileInputStream;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short LW_T=257;
public final static short LA_T=258;
public final static short LI_T=259;
public final static short SW_T=260;
public final static short MOVE_T=261;
public final static short ADD_T=262;
public final static short ADDI_T=263;
public final static short AND_T=264;
public final static short OR_T=265;
public final static short SUB_T=266;
public final static short SUBI_T=267;
public final static short MUL_T=268;
public final static short DIV_T=269;
public final static short NEG_T=270;
public final static short COMP1_T=271;
public final static short COMP2_T=272;
public final static short COMP3_T=273;
public final static short COMP4_T=274;
public final static short COMP5_T=275;
public final static short COMP6_T=276;
public final static short BRANCH_T=277;
public final static short CBRANCH1_T=278;
public final static short CBRANCH2_T=279;
public final static short SYSCALL_T=280;
public final static short AREG=281;
public final static short TREG=282;
public final static short VREG=283;
public final static short INT=284;
public final static short IDENT=285;
public final static short DATA_START=286;
public final static short TEXT_START=287;
public final static short IS_WORD=288;
public final static short IS_SPACE=289;
public final static short IS_STRING=290;
public final static short STRING=291;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    1,    2,    2,    3,    3,
    3,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    5,    5,    5,    6,    6,    6,    6,
};
final static short yylen[] = {                            2,
    2,    0,    2,    1,    1,    1,    1,    1,    4,    4,
    4,    4,    4,    4,    4,    6,    6,    6,    6,    6,
    4,    4,    1,    6,    6,    6,    6,    6,    6,    2,
    4,    4,    1,    1,    1,    1,    1,    3,    4,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   23,    0,    7,    8,    1,    4,    5,    6,   33,
   34,   35,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   30,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   36,    0,   37,   12,
   14,   13,   15,   22,    0,    0,    0,    0,    0,   21,
    0,    0,    0,    0,    0,    0,   31,   32,    9,   10,
   11,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   38,   16,   17,   18,   19,
   20,   24,   25,   26,   27,   28,   29,   39,
};
final static short yydgoto[] = {                          1,
   26,   27,   28,   29,   79,   80,
};
final static short yysindex[] = {                         0,
 -233, -232, -232, -232, -232, -232, -232, -232, -232, -232,
 -232, -232, -232, -232, -232, -232, -232, -232, -283, -232,
 -232,    0,  -55,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -13,    4,   11,   12,   29,   30,   31,   32,
   33,   34,   35,   37,   38,   39,   40,   41,   42,    0,
   43,   44, -231,  -40,  -40, -263,  -40, -232, -232, -232,
 -232, -232, -232, -232, -232, -232, -232, -232, -232, -232,
 -196, -195, -193, -192, -198,   54,    0, -232,    0,    0,
    0,    0,    0,    0,   51,   52,   53,   55,   56,    0,
   57,   58,   59,   61,   63,   73,    0,    0,    0,    0,
    0, -232,   77, -232, -186, -232, -232, -232, -232, -232,
 -232, -232, -232, -232,   78,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    2,  -23,
};
final static int YYTABLESIZE=288;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         78,
    3,   50,   53,   33,   34,   35,   36,   37,   38,   39,
   40,   41,   42,   43,   44,   45,   46,   47,   48,   49,
   82,   51,   52,    2,    3,    4,    5,    6,    7,    8,
   54,   81,    9,   83,   10,   11,   12,   13,   14,   15,
   16,   17,   18,   19,   20,   21,   22,   55,   30,   31,
   32,   23,   24,   25,   56,   57,   73,   74,   75,   84,
   85,   86,   87,   88,   89,   90,   91,   92,   93,   94,
   95,   96,   58,   59,   60,   61,   62,   63,   64,  103,
   65,   66,   67,   68,   69,   70,   71,   72,   97,   98,
   99,  100,  101,  102,  104,  105,  106,  118,  107,  108,
  109,  110,  111,  115,  112,  117,  113,  119,  120,  121,
  122,  123,  124,  125,  126,  127,  114,  116,  128,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   30,   31,   32,   76,   77,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,    3,    3,
    3,    3,    3,    3,    0,    0,    3,    0,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    0,    0,    0,    0,    3,    3,    3,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,  285,   58,    2,    3,    4,    5,    6,    7,    8,
    9,   10,   11,   12,   13,   14,   15,   16,   17,   18,
  284,   20,   21,  257,  258,  259,  260,  261,  262,  263,
   44,   55,  266,   57,  268,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,   44,  281,  282,
  283,  285,  286,  287,   44,   44,  288,  289,  290,   58,
   59,   60,   61,   62,   63,   64,   65,   66,   67,   68,
   69,   70,   44,   44,   44,   44,   44,   44,   44,   78,
   44,   44,   44,   44,   44,   44,   44,   44,  285,  285,
  284,  284,  291,   40,   44,   44,   44,  284,   44,   44,
   44,   44,   44,  102,   44,  104,   44,  106,  107,  108,
  109,  110,  111,  112,  113,  114,   44,   41,   41,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  281,  282,  283,  284,  285,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,   -1,  266,   -1,  268,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,   -1,   -1,   -1,   -1,  285,  286,  287,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=291;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'",null,null,"','",
null,null,null,null,null,null,null,null,null,null,null,null,null,"':'",null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"LW_T","LA_T","LI_T","SW_T","MOVE_T","ADD_T","ADDI_T",
"AND_T","OR_T","SUB_T","SUBI_T","MUL_T","DIV_T","NEG_T","COMP1_T","COMP2_T",
"COMP3_T","COMP4_T","COMP5_T","COMP6_T","BRANCH_T","CBRANCH1_T","CBRANCH2_T",
"SYSCALL_T","AREG","TREG","VREG","INT","IDENT","DATA_START","TEXT_START",
"IS_WORD","IS_SPACE","IS_STRING","STRING",
};
final static String yyrule[] = {
"$accept : program",
"program : program spimline",
"program :",
"spimline : IDENT ':'",
"spimline : directive",
"spimline : var_decl",
"spimline : instruction",
"directive : DATA_START",
"directive : TEXT_START",
"var_decl : IDENT ':' IS_WORD INT",
"var_decl : IDENT ':' IS_SPACE INT",
"var_decl : IDENT ':' IS_STRING STRING",
"instruction : LW_T register ',' location",
"instruction : LI_T register ',' INT",
"instruction : LA_T register ',' location",
"instruction : SW_T register ',' location",
"instruction : ADD_T register ',' register ',' register",
"instruction : ADDI_T register ',' register ',' INT",
"instruction : SUB_T register ',' register ',' register",
"instruction : MUL_T register ',' register ',' register",
"instruction : DIV_T register ',' register ',' register",
"instruction : NEG_T register ',' register",
"instruction : MOVE_T register ',' register",
"instruction : SYSCALL_T",
"instruction : COMP1_T register ',' register ',' register",
"instruction : COMP2_T register ',' register ',' register",
"instruction : COMP3_T register ',' register ',' register",
"instruction : COMP4_T register ',' register ',' register",
"instruction : COMP5_T register ',' register ',' register",
"instruction : COMP6_T register ',' register ',' register",
"instruction : BRANCH_T IDENT",
"instruction : CBRANCH1_T register ',' IDENT",
"instruction : CBRANCH2_T register ',' IDENT",
"register : AREG",
"register : TREG",
"register : VREG",
"location : IDENT",
"location : register",
"location : '(' register ')'",
"location : INT '(' register ')'",
};

//#line 218 "spim.by"

/* Byacc/J expects a member method int yylex(). We need to provide one
   through this mechanism. See the jflex manual for more information. */

	private LineList lineList = new LineList();
	private ArrayList<Variable> variableList = new ArrayList<Variable>();

	public LineList getLineList()
	{
		return lineList;
	}

	public ArrayList<Variable> getVariableList()
	{
		return variableList;
	}

	private Instruction newInstruction(Line.Type type, String strInstruction, 
					Object argument1, Object argument2, Object argument3)
	{
		return new Instruction(type, strInstruction, (Argument)argument1, (Argument)argument2, (Argument)argument3);
	}		

	private Line newLine(Line.Type type, String strLineText)
	{
		return new Line(type, strLineText);
	}

	/* reference to the lexer object */
	private scanner lexer;

	/* interface to the lexer */
	private int yylex() {
		int retVal = -1;
		try {
			retVal = lexer.yylex();
		} catch (IOException e) {
			System.err.println("IO Error:" + e);
		}
		return retVal;
	}
	
	/* error reporting */
	public void yyerror (String error) {
		System.err.println("Error : " + error + " at line " + lexer.getLine());
		System.err.println("String rejected");
	}

	/* constructor taking in File Input */
	public Parser (Reader r) {
		lexer = new scanner (r, this);
	}


	public static void main (String [] args) throws IOException 
	{
		FileInputStream inStream = new FileInputStream(args[0]);
		
		boolean bDiag = false;
		if(args.length == 2 && 0 == args[1].compareTo("diag"))
			bDiag = true;
		
        Parser yyparser = new Parser(new InputStreamReader(inStream));
        yyparser.yyparse();

		LineList instrList = yyparser.getLineList();

		// Perform code analysis
		CodeAnalyzer analyzer = new CodeAnalyzer(instrList, yyparser.getVariableList());

		if(!bDiag)
			analyzer.doWork();
		else
			analyzer.runDiagnostics();
    }

//#line 405 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 3:
//#line 15 "spim.by"
{
			Line line = newLine(Line.Type.LABEL, val_peek(1).sval + ":");
			line.setLabel(val_peek(1).sval);
			lineList.addItem(line);
		}
break;
case 7:
//#line 26 "spim.by"
{
			Line line = newLine(Line.Type.SECTION, "\t" + val_peek(0).sval);
			lineList.addItem(line);
		}
break;
case 8:
//#line 31 "spim.by"
{
			Line line = newLine(Line.Type.SECTION, "\t" + val_peek(0).sval);
			lineList.addItem(line);
		}
break;
case 9:
//#line 38 "spim.by"
{
			Line line = newLine(Line.Type.VAR_DECL, val_peek(3).sval + ":\t" + val_peek(1).sval + "\t" + val_peek(0).sval);
			lineList.addItem(line);

			Variable var = new Variable(val_peek(3).sval, Variable.Type.INTEGER, "");
			variableList.add(var);
		}
break;
case 10:
//#line 46 "spim.by"
{
			Line line = newLine(Line.Type.VAR_DECL, val_peek(3).sval + ":\t" + val_peek(1).sval + "\t" + val_peek(0).sval);
			lineList.addItem(line);

			Variable var = new Variable(val_peek(3).sval, Variable.Type.BINARY, "");
			variableList.add(var);
		}
break;
case 11:
//#line 54 "spim.by"
{
			Line line = newLine(Line.Type.VAR_DECL, val_peek(3).sval + ":\t" + val_peek(1).sval + "\t" + val_peek(0).sval);
			lineList.addItem(line);

			Variable var = new Variable(val_peek(3).sval, Variable.Type.STRING, "");
			variableList.add(var);
		}
break;
case 12:
//#line 64 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(3).sval, val_peek(2).obj, val_peek(0).obj, null);
			lineList.addItem(instr);
		}
break;
case 13:
//#line 69 "spim.by"
{
			Argument arg = new Argument(val_peek(0).sval, Argument.Type.LITERAL);
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(3).sval, val_peek(2).obj, arg, null);
			lineList.addItem(instr);
		}
break;
case 14:
//#line 75 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(3).sval, val_peek(2).obj, val_peek(0).obj, null);
			lineList.addItem(instr);
		}
break;
case 15:
//#line 80 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(3).sval, val_peek(2).obj, val_peek(0).obj, null);
			lineList.addItem(instr);
		}
break;
case 16:
//#line 85 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 17:
//#line 90 "spim.by"
{
			Argument arg = new Argument(val_peek(0).sval, Argument.Type.LITERAL);

			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, arg);
			lineList.addItem(instr);
		}
break;
case 18:
//#line 97 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 19:
//#line 102 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 20:
//#line 107 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 21:
//#line 112 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(3).sval, val_peek(2).obj, val_peek(0).obj, null);
			lineList.addItem(instr);
		}
break;
case 22:
//#line 117 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(3).sval, val_peek(2).obj, val_peek(0).obj, null);
			lineList.addItem(instr);
		}
break;
case 23:
//#line 122 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(0).sval, null, null, null);
			lineList.addItem(instr);
		}
break;
case 24:
//#line 128 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 25:
//#line 133 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 26:
//#line 138 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 27:
//#line 143 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 28:
//#line 148 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 29:
//#line 153 "spim.by"
{
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(5).sval, val_peek(4).obj, val_peek(2).obj, val_peek(0).obj);
			lineList.addItem(instr);
		}
break;
case 30:
//#line 158 "spim.by"
{
			Argument arg = new Argument(val_peek(0).sval, Argument.Type.IDENTIFIER);
			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(1).sval, arg, null, null);
			instr.setIsJump(true);
			instr.setBranchType(Instruction.BranchType.JUMP);
			lineList.addItem(instr);
		}
break;
case 31:
//#line 166 "spim.by"
{
			Argument arg = new Argument(val_peek(0).sval, Argument.Type.IDENTIFIER);

			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(3).sval, val_peek(2).obj, arg, null);
			instr.setIsJump(true);
			instr.setBranchType(Instruction.BranchType.JUMP_EQ);

			lineList.addItem(instr);
		}
break;
case 32:
//#line 176 "spim.by"
{
			Argument arg = new Argument(val_peek(0).sval, Argument.Type.IDENTIFIER);

			Instruction instr = newInstruction(Line.Type.INSTRUCTION, val_peek(3).sval, val_peek(2).obj, arg, null);
			instr.setIsJump(true);
			instr.setBranchType(Instruction.BranchType.JUMP_NEQ);

			lineList.addItem(instr);
		}
break;
case 33:
//#line 188 "spim.by"
{
			yyval = new ParserVal(new Argument(val_peek(0).sval, Argument.Type.REGISTER));
		}
break;
case 34:
//#line 192 "spim.by"
{
			yyval = new ParserVal(new Argument(val_peek(0).sval, Argument.Type.REGISTER));
		}
break;
case 35:
//#line 196 "spim.by"
{
			yyval = new ParserVal(new Argument(val_peek(0).sval, Argument.Type.REGISTER));
		}
break;
case 36:
//#line 201 "spim.by"
{
			yyval = new ParserVal(new Argument(val_peek(0).sval, Argument.Type.IDENTIFIER));
		}
break;
case 37:
//#line 205 "spim.by"
{
			yyval = new ParserVal(new Argument(val_peek(0).sval, Argument.Type.REGISTER));
		}
break;
case 38:
//#line 209 "spim.by"
{
			yyval = new ParserVal(new Argument(val_peek(1).obj.toString(), Argument.Type.REGISTER_CONTENTS));
		}
break;
case 39:
//#line 213 "spim.by"
{
			yyval = new ParserVal(new Argument(val_peek(3).sval + "(" + val_peek(1).obj + ")", Argument.Type.IMMEDIATE_PLUS_REGISTER_CONTENTS));
		}
break;
//#line 811 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
