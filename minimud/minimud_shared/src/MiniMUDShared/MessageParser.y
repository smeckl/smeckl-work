%{
import java.io.*;
%}

%token TEXTMSG MESSAGE FROM TO PLAYER SERVER REQUEST_INPUT TYPE NORMAL_INPUT PASSWD_INPUT SERVER_STATUS
%token LOGON_SUCCESS LOGON_FAILED INVALID QUIT EXIT LOGOUT TELL SAY SHOUT WHISPER 
%token GO_NORTH GO_SOUTH GO_EAST GO_WEST GO_NORTHEAST GO_NORTHWEST GO_SOUTHEAST GO_SOUTHWEST GO_UP GO_DOWN
%token CHARNAME CHARLITERAL STRINGLITERAL INT LOOK
%token KICK PUNCH TALK STAB PUSH SLASH SHOOT
%%

message:
client_showtext
	{
		m_lastMsg = (Message)$$.obj;
	}
| request_input
	{
		m_lastMsg = (Message)$$.obj;
	}
| server_status
	{
		m_lastMsg = (Message)$$.obj;
	}
| user_logout
	{
		m_lastMsg = (Message)$$.obj;
	}
| chat_message
	{
		m_lastMsg = (Message)$$.obj;
	}
| move_message
	{
		m_lastMsg = (Message)$$.obj;
	}
| action_message
	{
		m_lastMsg = (Message)$$.obj;
	}
;

client_showtext: 
  TEXTMSG FROM '=' SERVER MESSAGE '=' STRINGLITERAL
  {
  	  $$ = new MessageParserVal(new ClientShowTextMessage("", trimQuotes($7.sval)));
  }
| TEXTMSG FROM '=' CHARNAME MESSAGE '=' STRINGLITERAL
  {
      $$ = new MessageParserVal(new ClientShowTextMessage($4.sval, trimQuotes($7.sval)));
  }
;

request_input: 
  REQUEST_INPUT TYPE '=' NORMAL_INPUT MESSAGE '=' STRINGLITERAL
  {
      $$ = new MessageParserVal(new ClientRequestInputMessage(ClientRequestInputMessage.Type.Normal, 
      					trimQuotes($7.sval)));
  }
| REQUEST_INPUT TYPE '=' PASSWD_INPUT MESSAGE '=' STRINGLITERAL
  {
  	  $$ = new MessageParserVal(new ClientRequestInputMessage(ClientRequestInputMessage.Type.Password, 
      					trimQuotes($7.sval)));
  }
;

server_status: 
  SERVER_STATUS TYPE '=' LOGON_SUCCESS
	{
		$$ = new MessageParserVal(new ServerStatusMessage(ServerStatusMessage.Status.LOGON_SUCCESS));
	}
| SERVER_STATUS TYPE '=' LOGON_FAILED
	{
		$$ = new MessageParserVal(new ServerStatusMessage(ServerStatusMessage.Status.LOGON_FAILED));
	}
| SERVER_STATUS TYPE '=' LOGOUT
	{
		$$ = new MessageParserVal(new ServerStatusMessage(ServerStatusMessage.Status.LOGOUT));
	}
  ;

user_logout: 
  QUIT 
	{
		$$ = new MessageParserVal(new UserLogoutMessage());
	}
| EXIT
	{
		$$ = new MessageParserVal(new UserLogoutMessage());
	};

chat_message: 
  TELL CHARNAME STRINGLITERAL
  	{
  		UserChatMessage chatMsg = new UserChatMessage();
	    chatMsg.setMsgType(UserChatMessage.MsgType.Tell);
	    chatMsg.setToUser($2.sval);
	    chatMsg.setMessage(trimQuotes($3.sval));
	    
	    $$ = new MessageParserVal(chatMsg);
  	}
| SAY STRINGLITERAL
	{
		UserChatMessage chatMsg = new UserChatMessage();
  		chatMsg.setMsgType(UserChatMessage.MsgType.Say);
  		chatMsg.setMessage(trimQuotes($2.sval));
  
  		$$ = new MessageParserVal(chatMsg);
	}
| SHOUT STRINGLITERAL
	{
		UserChatMessage chatMsg = new UserChatMessage();
      	chatMsg.setMsgType(UserChatMessage.MsgType.Shout);
      	chatMsg.setMessage(trimQuotes($2.sval));
      
      	$$ = new MessageParserVal(chatMsg);
	}
| WHISPER CHARNAME STRINGLITERAL
	{
		UserChatMessage chatMsg = new UserChatMessage();
      	chatMsg.setMsgType(UserChatMessage.MsgType.Tell);
      	chatMsg.setToUser($2.sval);
      	chatMsg.setMessage(trimQuotes($3.sval));
      
      	$$ = new MessageParserVal(chatMsg);
	}
;

move_message:
  GO_NORTH
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.North));
	}
| GO_SOUTH
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.South));
	}
| GO_EAST
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.East));
	}
| GO_WEST 
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.West));
	}
| GO_NORTHEAST
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Northeast));
	}
| GO_NORTHWEST
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Northwest));
	}
| GO_SOUTHEAST
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Southeast));
	}
| GO_SOUTHWEST
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Southwest));
	}
| GO_UP
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Up));
	}
| GO_DOWN
	{
		$$ = new MessageParserVal(new PlayerMoveMessage(PlayerMoveMessage.Direction.Down));
	}
;

action_message:
  LOOK
  	{
  		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Look, ""));
  	}
| TALK TO CHARNAME
	{
		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Talk, $3.sval));
	}
| TALK CHARNAME
	{
		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Talk, $2.sval));
	}
| PUNCH CHARNAME
	{
		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Punch, $2.sval));
	}
| KICK CHARNAME
	{
		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Kick, $2.sval));
	}
| STAB CHARNAME
	{
		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Stab, $2.sval));
	}
| SLASH CHARNAME
	{
		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Slash, $2.sval));
	}
| PUSH CHARNAME
	{
		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Push, $2.sval));
	}
| SHOOT CHARNAME
	{
		$$ = new MessageParserVal(new PlayerActionMessage(PlayerActionMessage.Action.Shoot, $2.sval));
	}
;

%%

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
    
    