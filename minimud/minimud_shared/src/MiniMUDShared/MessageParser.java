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



package MiniMUDShared;



//#line 2 "./MiniMUDShared/MessageParser.y"
import java.io.*;
//#line 19 "MessageParser.java"




public class MessageParser
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
//public class MessageParserVal is defined in MessageParserVal.java


String   yytext;//user variable to return contextual strings
MessageParserVal yyval; //used to return semantic vals from action routines
MessageParserVal yylval;//the 'lval' (result) I got from yylex()
MessageParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new MessageParserVal[YYSTACKSIZE];
  yyval=new MessageParserVal();
  yylval=new MessageParserVal();
  valptr=-1;
}
void val_push(MessageParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
MessageParserVal val_pop()
{
  if (valptr<0)
    return new MessageParserVal();
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
MessageParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new MessageParserVal();
  return valstk[ptr];
}
final MessageParserVal dup_yyval(MessageParserVal val)
{
  MessageParserVal dup = new MessageParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short TEXTMSG=257;
public final static short MESSAGE=258;
public final static short FROM=259;
public final static short TO=260;
public final static short PLAYER=261;
public final static short SERVER=262;
public final static short REQUEST_INPUT=263;
public final static short TYPE=264;
public final static short NORMAL_INPUT=265;
public final static short PASSWD_INPUT=266;
public final static short SERVER_STATUS=267;
public final static short LOGON_SUCCESS=268;
public final static short LOGON_FAILED=269;
public final static short INVALID=270;
public final static short QUIT=271;
public final static short EXIT=272;
public final static short LOGOUT=273;
public final static short TELL=274;
public final static short SAY=275;
public final static short SHOUT=276;
public final static short WHISPER=277;
public final static short GO_NORTH=278;
public final static short GO_SOUTH=279;
public final static short GO_EAST=280;
public final static short GO_WEST=281;
public final static short GO_NORTHEAST=282;
public final static short GO_NORTHWEST=283;
public final static short GO_SOUTHEAST=284;
public final static short GO_SOUTHWEST=285;
public final static short GO_UP=286;
public final static short GO_DOWN=287;
public final static short CHARNAME=288;
public final static short CHARLITERAL=289;
public final static short STRINGLITERAL=290;
public final static short INT=291;
public final static short LOOK=292;
public final static short KICK=293;
public final static short PUNCH=294;
public final static short TALK=295;
public final static short STAB=296;
public final static short PUSH=297;
public final static short SLASH=298;
public final static short SHOOT=299;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    1,    1,    2,
    2,    3,    3,    3,    4,    4,    5,    5,    5,    5,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    7,    7,    7,    7,    7,    7,    7,    7,    7,
};
final static short yylen[] = {                            2,
    1,    1,    1,    1,    1,    1,    1,    7,    7,    7,
    7,    4,    4,    4,    1,    1,    3,    2,    2,    3,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    2,    2,    2,    2,    2,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   15,   16,    0,    0,    0,    0,   21,
   22,   23,   24,   25,   26,   27,   28,   29,   30,   31,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    2,
    3,    4,    5,    6,    7,    0,    0,    0,    0,   18,
   19,    0,   35,   34,    0,   33,   36,   38,   37,   39,
    0,    0,    0,   17,   20,   32,    0,    0,    0,    0,
   12,   13,   14,    0,    0,    0,    0,    0,    0,    0,
    0,    8,    9,   10,   11,
};
final static short yydgoto[] = {                         28,
   29,   30,   31,   32,   33,   34,   35,
};
final static short yysindex[] = {                      -257,
 -254, -261, -253,    0,    0, -276, -274, -259, -245,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -244, -243, -256, -242, -241, -240, -239,    0,    0,    0,
    0,    0,    0,    0,    0,  -27,  -11,  -10, -238,    0,
    0, -237,    0,    0, -234,    0,    0,    0,    0,    0,
 -255, -264, -260,    0,    0,    0, -203, -202, -201, -200,
    0,    0,    0,   -2,   -1,    1,    2, -229, -226, -225,
 -224,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=66;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          1,
   59,   60,   37,   45,   36,    2,   57,   61,   62,    3,
   38,   39,   63,    4,    5,   40,    6,    7,    8,    9,
   10,   11,   12,   13,   14,   15,   16,   17,   18,   19,
   41,   46,   58,   51,   20,   21,   22,   23,   24,   25,
   26,   27,   42,   43,   44,   47,   48,   49,   50,   52,
   53,   54,   55,   56,   64,   65,   66,   67,   68,   69,
   72,   70,   71,   73,   74,   75,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        257,
  265,  266,  264,  260,  259,  263,  262,  268,  269,  267,
  264,  288,  273,  271,  272,  290,  274,  275,  276,  277,
  278,  279,  280,  281,  282,  283,  284,  285,  286,  287,
  290,  288,  288,   61,  292,  293,  294,  295,  296,  297,
  298,  299,  288,  288,  288,  288,  288,  288,  288,   61,
   61,  290,  290,  288,  258,  258,  258,  258,   61,   61,
  290,   61,   61,  290,  290,  290,
};
}
final static short YYFINAL=28;
final static short YYMAXTOKEN=299;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'='",null,null,null,null,null,null,null,null,null,null,null,null,null,null,
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
null,null,null,null,null,"TEXTMSG","MESSAGE","FROM","TO","PLAYER","SERVER",
"REQUEST_INPUT","TYPE","NORMAL_INPUT","PASSWD_INPUT","SERVER_STATUS",
"LOGON_SUCCESS","LOGON_FAILED","INVALID","QUIT","EXIT","LOGOUT","TELL","SAY",
"SHOUT","WHISPER","GO_NORTH","GO_SOUTH","GO_EAST","GO_WEST","GO_NORTHEAST",
"GO_NORTHWEST","GO_SOUTHEAST","GO_SOUTHWEST","GO_UP","GO_DOWN","CHARNAME",
"CHARLITERAL","STRINGLITERAL","INT","LOOK","KICK","PUNCH","TALK","STAB","PUSH",
"SLASH","SHOOT",
};
final static String yyrule[] = {
"$accept : message",
"message : client_showtext",
"message : request_input",
"message : server_status",
"message : user_logout",
"message : chat_message",
"message : move_message",
"message : action_message",
"client_showtext : TEXTMSG FROM '=' SERVER MESSAGE '=' STRINGLITERAL",
"client_showtext : TEXTMSG FROM '=' CHARNAME MESSAGE '=' STRINGLITERAL",
"request_input : REQUEST_INPUT TYPE '=' NORMAL_INPUT MESSAGE '=' STRINGLITERAL",
"request_input : REQUEST_INPUT TYPE '=' PASSWD_INPUT MESSAGE '=' STRINGLITERAL",
"server_status : SERVER_STATUS TYPE '=' LOGON_SUCCESS",
"server_status : SERVER_STATUS TYPE '=' LOGON_FAILED",
"server_status : SERVER_STATUS TYPE '=' LOGOUT",
"user_logout : QUIT",
"user_logout : EXIT",
"chat_message : TELL CHARNAME STRINGLITERAL",
"chat_message : SAY STRINGLITERAL",
"chat_message : SHOUT STRINGLITERAL",
"chat_message : WHISPER CHARNAME STRINGLITERAL",
"move_message : GO_NORTH",
"move_message : GO_SOUTH",
"move_message : GO_EAST",
"move_message : GO_WEST",
"move_message : GO_NORTHEAST",
"move_message : GO_NORTHWEST",
"move_message : GO_SOUTHEAST",
"move_message : GO_SOUTHWEST",
"move_message : GO_UP",
"move_message : GO_DOWN",
"action_message : LOOK",
"action_message : TALK TO CHARNAME",
"action_message : TALK CHARNAME",
"action_message : PUNCH CHARNAME",
"action_message : KICK CHARNAME",
"action_message : STAB CHARNAME",
"action_message : SLASH CHARNAME",
"action_message : PUSH CHARNAME",
"action_message : SHOOT CHARNAME",
};

//#line 212 "./MiniMUDShared/MessageParser.y"

/* Byacc/J expects a member method int yylex(). We need to provide one
   through this mechanism. See the jflex manual for more information. */

	/* reference to the lexer object */
	private MessageScanner lexer;
	
	private Message m_lastMsg = null;
	
	public Message getLastMessage()
	{
		return m_lastMsg;
	}
	
	public void parse()
	{
		yyparse();
	}

	  protected static String trimQuotes(String str)
	  {
	    if('\"' == str.charAt(0) && '\"' == str.charAt(str.length()-1))
	        return str.substring(1, str.length()-1);
	    
	    return str;
	  }

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
	public MessageParser (Reader r) {
		lexer = new MessageScanner (r, this);
	}
	
	public void print(String str)
	{
		System.out.println(str);
	}


	public static void main (String [] args) 
	{
		try
		{
		    MessageParser yyparser = new MessageParser(new InputStreamReader(System.in));
		    
		    System.out.println("parsing...");
		    yyparser.yyparse();
		}
		catch (Exception e)
		{
			System.out.println("parse error: " + e);
		}
    }
    
    
//#line 349 "MessageParser.java"
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
case 1:
//#line 14 "./MiniMUDShared/MessageParser.y"
{
		m_lastMsg = (Message)yyval.obj;
	}
break;
case 2:
//#line 18 "./MiniMUDShared/MessageParser.y"
{
		m_lastMsg = (Message)yyval.obj;
	}
break;
case 3:
//#line 22 "./MiniMUDShared/MessageParser.y"
{
		m_lastMsg = (Message)yyval.obj;
	}
break;
case 4:
//#line 26 "./MiniMUDShared/MessageParser.y"
{
		m_lastMsg = (Message)yyval.obj;
	}
break;
case 5:
//#line 30 "./MiniMUDShared/MessageParser.y"
{
		m_lastMsg = (Message)yyval.obj;
	}
break;
case 6:
//#line 34 "./MiniMUDShared/MessageParser.y"
{
		m_lastMsg = (Message)yyval.obj;
	}
break;
case 7:
//#line 38 "./MiniMUDShared/MessageParser.y"
{
		m_lastMsg = (Message)yyval.obj;
	}
break;
case 8:
//#line 45 "./MiniMUDShared/MessageParser.y"
{
  	  yyval = new MessageParserVal(new ClientShowTextMessage("", trimQuotes(val_peek(0).sval)));
  }
break;
case 9:
//#line 49 "./MiniMUDShared/MessageParser.y"
{
      yyval = new MessageParserVal(new ClientShowTextMessage(val_peek(3).sval, trimQuotes(val_peek(0).sval)));
  }
break;
case 10:
//#line 56 "./MiniMUDShared/MessageParser.y"
{
      yyval = new MessageParserVal(new ClientRequestInputMessage(ClientRequestInputMessage.Type.Normal, 
      					trimQuotes(val_peek(0).sval)));
  }
break;
case 11:
//#line 61 "./MiniMUDShared/MessageParser.y"
{
  	  yyval = new MessageParserVal(new ClientRequestInputMessage(ClientRequestInputMessage.Type.Password, 
      					trimQuotes(val_peek(0).sval)));
  }
break;
case 12:
//#line 69 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new ServerStatusMessage(ServerStatusMessage.Status.LOGON_SUCCESS));
	}
break;
case 13:
//#line 73 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new ServerStatusMessage(ServerStatusMessage.Status.LOGON_FAILED));
	}
break;
case 14:
//#line 77 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new ServerStatusMessage(ServerStatusMessage.Status.LOGOUT));
	}
break;
case 15:
//#line 84 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new UserLogoutMessage());
	}
break;
case 16:
//#line 88 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new UserLogoutMessage());
	}
break;
case 17:
//#line 94 "./MiniMUDShared/MessageParser.y"
{
  		UserChatMessage chatMsg = new UserChatMessage();
	    chatMsg.setMsgType(UserChatMessage.MsgType.Tell);
	    chatMsg.setToUser(val_peek(1).sval);
	    chatMsg.setMessage(trimQuotes(val_peek(0).sval));
	    
	    yyval = new MessageParserVal(chatMsg);
  	}
break;
case 18:
//#line 103 "./MiniMUDShared/MessageParser.y"
{
		UserChatMessage chatMsg = new UserChatMessage();
  		chatMsg.setMsgType(UserChatMessage.MsgType.Say);
  		chatMsg.setMessage(trimQuotes(val_peek(0).sval));
  
  		yyval = new MessageParserVal(chatMsg);
	}
break;
case 19:
//#line 111 "./MiniMUDShared/MessageParser.y"
{
		UserChatMessage chatMsg = new UserChatMessage();
      	chatMsg.setMsgType(UserChatMessage.MsgType.Shout);
      	chatMsg.setMessage(trimQuotes(val_peek(0).sval));
      
      	yyval = new MessageParserVal(chatMsg);
	}
break;
case 20:
//#line 119 "./MiniMUDShared/MessageParser.y"
{
		UserChatMessage chatMsg = new UserChatMessage();
      	chatMsg.setMsgType(UserChatMessage.MsgType.Tell);
      	chatMsg.setToUser(val_peek(1).sval);
      	chatMsg.setMessage(trimQuotes(val_peek(0).sval));
      
      	yyval = new MessageParserVal(chatMsg);
	}
break;
case 21:
//#line 131 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.North));
	}
break;
case 22:
//#line 135 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.South));
	}
break;
case 23:
//#line 139 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.East));
	}
break;
case 24:
//#line 143 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.West));
	}
break;
case 25:
//#line 147 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Northeast));
	}
break;
case 26:
//#line 151 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Northwest));
	}
break;
case 27:
//#line 155 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Southeast));
	}
break;
case 28:
//#line 159 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Southwest));
	}
break;
case 29:
//#line 163 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Up));
	}
break;
case 30:
//#line 167 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Down));
	}
break;
case 31:
//#line 174 "./MiniMUDShared/MessageParser.y"
{
  		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Look, ""));
  	}
break;
case 32:
//#line 178 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Talk, val_peek(0).sval));
	}
break;
case 33:
//#line 182 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Talk, val_peek(0).sval));
	}
break;
case 34:
//#line 186 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Punch, val_peek(0).sval));
	}
break;
case 35:
//#line 190 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Kick, val_peek(0).sval));
	}
break;
case 36:
//#line 194 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Stab, val_peek(0).sval));
	}
break;
case 37:
//#line 198 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Slash, val_peek(0).sval));
	}
break;
case 38:
//#line 202 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Push, val_peek(0).sval));
	}
break;
case 39:
//#line 206 "./MiniMUDShared/MessageParser.y"
{
		yyval = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Shoot, val_peek(0).sval));
	}
break;
//#line 752 "MessageParser.java"
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
public MessageParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public MessageParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
