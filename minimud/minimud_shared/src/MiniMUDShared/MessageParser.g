parser grammar MessageParser;

options
{
  tokenVocab=MessageScanner;
}

@header 
{
  package MiniMUDShared;
  import org.antlr.runtime.*;
  import org.antlr.runtime.tree.*;
}

@members
{
  protected static String trimQuotes(String str)
  {
    if('\"' == str.charAt(0) && '\"' == str.charAt(str.length()-1))
        return str.substring(1, str.length()-1);
    
    return str;
  }
}

message returns [Message retVal]
  : a=client_showtext
    {
        retVal = a;
    }
  | b=request_input
	  {
	      retVal = b;
	  }
	| c=server_status
	  {
	      retVal = c;
	  }
	| d=user_logout
	  {
	      retVal = d;
	  }
	| e=chat_message
		{
		    retVal = e;
		}
  | f = move_message
    {
        retVal = f;
    }
	|  
	  {
	      retVal = null;
	  }
  ;
  
client_showtext returns [Message msg]: TEXTMSG FROM EQUALS from=(SERVER | CHARNAME) MESSAGE EQUALS txtmsg=STRINGLITERAL
  {
      String strFrom = $from.text;
      
      if(strFrom.length() < 31)
          msg = (Message)new ClientShowTextMessage($from.text, trimQuotes($txtmsg.text));
      else
          msg = null;
  }
;
  
request_input returns [Message msg]: REQUEST_INPUT TYPE EQUALS type=(NORMAL_INPUT | PASSWD_INPUT) MESSAGE EQUALS txtmsg=STRINGLITERAL
  {
      ClientRequestInputMessage.Type inputType = ClientRequestInputMessage.Type.Invalid;
      
      if($type.type == NORMAL_INPUT)
          inputType = ClientRequestInputMessage.Type.Normal;
      else if($type.type == PASSWD_INPUT)
          inputType = ClientRequestInputMessage.Type.Password;
          
      msg = (Message) new ClientRequestInputMessage(inputType, trimQuotes($txtmsg.text));
  }
;

server_status returns [Message msg]: SERVER_STATUS TYPE EQUALS type=(LOGON_SUCCESS | LOGON_FAILED | LOGOUT)
  {
      ServerStatusMessage.Status status = ServerStatusMessage.Status.INVALID;
      
      if($type.type == LOGON_SUCCESS)
          status = ServerStatusMessage.Status.LOGON_SUCCESS;
      else if($type.type == LOGON_FAILED)
          status = ServerStatusMessage.Status.LOGON_FAILED;
      else if($TYPE.type == LOGOUT)
          status = ServerStatusMessage.Status.LOGOUT;
        
      msg = (Message) new ServerStatusMessage(status);
  }
;

user_logout returns [Message msg]: (QUIT | EXIT)
  {
      msg = new UserLogoutMessage();
  }
;

chat_message returns [Message msg]: TELL toChar=CHARNAME txtMsg=STRINGLITERAL
	{
	    UserChatMessage chatMsg = new UserChatMessage();
	    chatMsg.setMsgType(UserChatMessage.MsgType.Tell);
	    chatMsg.setToUser($toChar.text);
	    chatMsg.setMessage(trimQuotes($txtMsg.text));
	    
	    msg = chatMsg;
	}
| SAY txtMsg=STRINGLITERAL
	{
	    UserChatMessage chatMsg = new UserChatMessage();
      chatMsg.setMsgType(UserChatMessage.MsgType.Say);
      chatMsg.setMessage(trimQuotes($txtMsg.text));
      
      msg = chatMsg;
	}
| SHOUT txtMsg=STRINGLITERAL
  {
      UserChatMessage chatMsg = new UserChatMessage();
      chatMsg.setMsgType(UserChatMessage.MsgType.Shout);
      chatMsg.setMessage(trimQuotes($txtMsg.text));
      
      msg = chatMsg;
  }
| WHISPER toChar=CHARNAME txtMsg=STRINGLITERAL
	{
	    UserChatMessage chatMsg = new UserChatMessage();
      chatMsg.setMsgType(UserChatMessage.MsgType.Tell);
      chatMsg.setToUser($toChar.text);
      chatMsg.setMessage(trimQuotes($txtMsg.text));
      
      msg = chatMsg;
	}
;

move_message returns [Message msg]: (GO)?
  GO_NORTH
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.North);
  }
| GO_SOUTH
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.South);
  }
| GO_EAST
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.East);
  }
| GO_WEST
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.West);
  }
| GO_NORTHEAST
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Northeast);
  }
| GO_NORTHWEST
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Northwest);
  }
| GO_SOUTHEAST
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Southeast);
  }
| GO_SOUTHWEST
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Southwest);
  }
| GO_UP
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Up);
  }
| GO_DOWN
  {
      msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Down);
  }
;
