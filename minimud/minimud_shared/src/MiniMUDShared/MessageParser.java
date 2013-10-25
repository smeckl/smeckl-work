// $ANTLR 3.4 /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g 2013-10-24 23:49:22

  package MiniMUDShared;
  import org.antlr.runtime.*;
  import org.antlr.runtime.tree.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class MessageParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CHAR", "CHARLITERAL", "CHARNAME", "DECIMAL_LITERAL", "DIGIT", "EQUALS", "ESCAPE", "EXIT", "FROM", "GO_DOWN", "GO_EAST", "GO_NORTH", "GO_NORTHEAST", "GO_NORTHWEST", "GO_SOUTH", "GO_SOUTHEAST", "GO_SOUTHWEST", "GO_UP", "GO_WEST", "HEX_DIGIT", "HEX_LITERAL", "INVALID", "LOGON_FAILED", "LOGON_SUCCESS", "LOGOUT", "MESSAGE", "NORMAL_INPUT", "PASSWD_INPUT", "PLAYER", "PRINTABLE_CHARS", "QUIT", "REQUEST_INPUT", "SAY", "SERVER", "SERVER_STATUS", "SHOUT", "STRINGLITERAL", "TELL", "TEXTMSG", "TO", "TYPE", "WHISPER", "WS_", "GO"
    };

    public static final int EOF=-1;
    public static final int CHAR=4;
    public static final int CHARLITERAL=5;
    public static final int CHARNAME=6;
    public static final int DECIMAL_LITERAL=7;
    public static final int DIGIT=8;
    public static final int EQUALS=9;
    public static final int ESCAPE=10;
    public static final int EXIT=11;
    public static final int FROM=12;
    public static final int GO_DOWN=13;
    public static final int GO_EAST=14;
    public static final int GO_NORTH=15;
    public static final int GO_NORTHEAST=16;
    public static final int GO_NORTHWEST=17;
    public static final int GO_SOUTH=18;
    public static final int GO_SOUTHEAST=19;
    public static final int GO_SOUTHWEST=20;
    public static final int GO_UP=21;
    public static final int GO_WEST=22;
    public static final int HEX_DIGIT=23;
    public static final int HEX_LITERAL=24;
    public static final int INVALID=25;
    public static final int LOGON_FAILED=26;
    public static final int LOGON_SUCCESS=27;
    public static final int LOGOUT=28;
    public static final int MESSAGE=29;
    public static final int NORMAL_INPUT=30;
    public static final int PASSWD_INPUT=31;
    public static final int PLAYER=32;
    public static final int PRINTABLE_CHARS=33;
    public static final int QUIT=34;
    public static final int REQUEST_INPUT=35;
    public static final int SAY=36;
    public static final int SERVER=37;
    public static final int SERVER_STATUS=38;
    public static final int SHOUT=39;
    public static final int STRINGLITERAL=40;
    public static final int TELL=41;
    public static final int TEXTMSG=42;
    public static final int TO=43;
    public static final int TYPE=44;
    public static final int WHISPER=45;
    public static final int WS_=46;
    public static final int GO=47;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public MessageParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public MessageParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return MessageParser.tokenNames; }
    public String getGrammarFileName() { return "/home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g"; }


      protected static String trimQuotes(String str)
      {
        if('\"' == str.charAt(0) && '\"' == str.charAt(str.length()-1))
            return str.substring(1, str.length()-1);
        
        return str;
      }



    // $ANTLR start "message"
    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:26:1: message returns [Message retVal] : (a= client_showtext |b= request_input |c= server_status |d= user_logout |e= chat_message |f= move_message |);
    public final Message message() throws RecognitionException {
        Message retVal = null;


        Message a =null;

        Message b =null;

        Message c =null;

        Message d =null;

        Message e =null;

        Message f =null;


        try {
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:27:3: (a= client_showtext |b= request_input |c= server_status |d= user_logout |e= chat_message |f= move_message |)
            int alt1=7;
            switch ( input.LA(1) ) {
            case TEXTMSG:
                {
                alt1=1;
                }
                break;
            case REQUEST_INPUT:
                {
                alt1=2;
                }
                break;
            case SERVER_STATUS:
                {
                alt1=3;
                }
                break;
            case EXIT:
            case QUIT:
                {
                alt1=4;
                }
                break;
            case SAY:
            case SHOUT:
            case TELL:
            case WHISPER:
                {
                alt1=5;
                }
                break;
            case GO_DOWN:
            case GO_EAST:
            case GO_NORTH:
            case GO_NORTHEAST:
            case GO_NORTHWEST:
            case GO_SOUTH:
            case GO_SOUTHEAST:
            case GO_SOUTHWEST:
            case GO_UP:
            case GO_WEST:
            case GO:
                {
                alt1=6;
                }
                break;
            case EOF:
                {
                alt1=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }

            switch (alt1) {
                case 1 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:27:5: a= client_showtext
                    {
                    pushFollow(FOLLOW_client_showtext_in_message45);
                    a=client_showtext();

                    state._fsp--;



                            retVal = a;
                        

                    }
                    break;
                case 2 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:31:5: b= request_input
                    {
                    pushFollow(FOLLOW_request_input_in_message59);
                    b=request_input();

                    state._fsp--;



                    	      retVal = b;
                    	  

                    }
                    break;
                case 3 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:35:4: c= server_status
                    {
                    pushFollow(FOLLOW_server_status_in_message71);
                    c=server_status();

                    state._fsp--;



                    	      retVal = c;
                    	  

                    }
                    break;
                case 4 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:39:4: d= user_logout
                    {
                    pushFollow(FOLLOW_user_logout_in_message83);
                    d=user_logout();

                    state._fsp--;



                    	      retVal = d;
                    	  

                    }
                    break;
                case 5 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:43:4: e= chat_message
                    {
                    pushFollow(FOLLOW_chat_message_in_message95);
                    e=chat_message();

                    state._fsp--;



                    		    retVal = e;
                    		

                    }
                    break;
                case 6 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:47:5: f= move_message
                    {
                    pushFollow(FOLLOW_move_message_in_message109);
                    f=move_message();

                    state._fsp--;



                            retVal = f;
                        

                    }
                    break;
                case 7 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:52:4: 
                    {

                    	      retVal = null;
                    	  

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
        return retVal;
    }
    // $ANTLR end "message"



    // $ANTLR start "client_showtext"
    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:57:1: client_showtext returns [Message msg] : TEXTMSG FROM EQUALS from= ( SERVER | CHARNAME ) MESSAGE EQUALS txtmsg= STRINGLITERAL ;
    public final Message client_showtext() throws RecognitionException {
        Message msg = null;


        Token from=null;
        Token txtmsg=null;

        try {
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:57:38: ( TEXTMSG FROM EQUALS from= ( SERVER | CHARNAME ) MESSAGE EQUALS txtmsg= STRINGLITERAL )
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:57:40: TEXTMSG FROM EQUALS from= ( SERVER | CHARNAME ) MESSAGE EQUALS txtmsg= STRINGLITERAL
            {
            match(input,TEXTMSG,FOLLOW_TEXTMSG_in_client_showtext141); 

            match(input,FROM,FOLLOW_FROM_in_client_showtext143); 

            match(input,EQUALS,FOLLOW_EQUALS_in_client_showtext145); 

            from=(Token)input.LT(1);

            if ( input.LA(1)==CHARNAME||input.LA(1)==SERVER ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            match(input,MESSAGE,FOLLOW_MESSAGE_in_client_showtext157); 

            match(input,EQUALS,FOLLOW_EQUALS_in_client_showtext159); 

            txtmsg=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_client_showtext163); 


                  String strFrom = (from!=null?from.getText():null);
                  
                  if(strFrom.length() < 31)
                      msg = (Message)new ClientShowTextMessage((from!=null?from.getText():null), trimQuotes((txtmsg!=null?txtmsg.getText():null)));
                  else
                      msg = null;
              

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return msg;
    }
    // $ANTLR end "client_showtext"



    // $ANTLR start "request_input"
    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:68:1: request_input returns [Message msg] : REQUEST_INPUT TYPE EQUALS type= ( NORMAL_INPUT | PASSWD_INPUT ) MESSAGE EQUALS txtmsg= STRINGLITERAL ;
    public final Message request_input() throws RecognitionException {
        Message msg = null;


        Token type=null;
        Token txtmsg=null;

        try {
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:68:36: ( REQUEST_INPUT TYPE EQUALS type= ( NORMAL_INPUT | PASSWD_INPUT ) MESSAGE EQUALS txtmsg= STRINGLITERAL )
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:68:38: REQUEST_INPUT TYPE EQUALS type= ( NORMAL_INPUT | PASSWD_INPUT ) MESSAGE EQUALS txtmsg= STRINGLITERAL
            {
            match(input,REQUEST_INPUT,FOLLOW_REQUEST_INPUT_in_request_input181); 

            match(input,TYPE,FOLLOW_TYPE_in_request_input183); 

            match(input,EQUALS,FOLLOW_EQUALS_in_request_input185); 

            type=(Token)input.LT(1);

            if ( (input.LA(1) >= NORMAL_INPUT && input.LA(1) <= PASSWD_INPUT) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            match(input,MESSAGE,FOLLOW_MESSAGE_in_request_input197); 

            match(input,EQUALS,FOLLOW_EQUALS_in_request_input199); 

            txtmsg=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_request_input203); 


                  ClientRequestInputMessage.Type inputType = ClientRequestInputMessage.Type.Invalid;
                  
                  if((type!=null?type.getType():0) == NORMAL_INPUT)
                      inputType = ClientRequestInputMessage.Type.Normal;
                  else if((type!=null?type.getType():0) == PASSWD_INPUT)
                      inputType = ClientRequestInputMessage.Type.Password;
                      
                  msg = (Message) new ClientRequestInputMessage(inputType, trimQuotes((txtmsg!=null?txtmsg.getText():null)));
              

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return msg;
    }
    // $ANTLR end "request_input"



    // $ANTLR start "server_status"
    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:81:1: server_status returns [Message msg] : SERVER_STATUS TYPE EQUALS type= ( LOGON_SUCCESS | LOGON_FAILED | LOGOUT ) ;
    public final Message server_status() throws RecognitionException {
        Message msg = null;


        Token type=null;
        Token TYPE1=null;

        try {
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:81:36: ( SERVER_STATUS TYPE EQUALS type= ( LOGON_SUCCESS | LOGON_FAILED | LOGOUT ) )
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:81:38: SERVER_STATUS TYPE EQUALS type= ( LOGON_SUCCESS | LOGON_FAILED | LOGOUT )
            {
            match(input,SERVER_STATUS,FOLLOW_SERVER_STATUS_in_server_status219); 

            TYPE1=(Token)match(input,TYPE,FOLLOW_TYPE_in_server_status221); 

            match(input,EQUALS,FOLLOW_EQUALS_in_server_status223); 

            type=(Token)input.LT(1);

            if ( (input.LA(1) >= LOGON_FAILED && input.LA(1) <= LOGOUT) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }



                  ServerStatusMessage.Status status = ServerStatusMessage.Status.INVALID;
                  
                  if((type!=null?type.getType():0) == LOGON_SUCCESS)
                      status = ServerStatusMessage.Status.LOGON_SUCCESS;
                  else if((type!=null?type.getType():0) == LOGON_FAILED)
                      status = ServerStatusMessage.Status.LOGON_FAILED;
                  else if((TYPE1!=null?TYPE1.getType():0) == LOGOUT)
                      status = ServerStatusMessage.Status.LOGOUT;
                    
                  msg = (Message) new ServerStatusMessage(status);
              

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return msg;
    }
    // $ANTLR end "server_status"



    // $ANTLR start "user_logout"
    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:96:1: user_logout returns [Message msg] : ( QUIT | EXIT ) ;
    public final Message user_logout() throws RecognitionException {
        Message msg = null;


        try {
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:96:34: ( ( QUIT | EXIT ) )
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:96:36: ( QUIT | EXIT )
            {
            if ( input.LA(1)==EXIT||input.LA(1)==QUIT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }



                  msg = new UserLogoutMessage();
              

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return msg;
    }
    // $ANTLR end "user_logout"



    // $ANTLR start "chat_message"
    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:102:1: chat_message returns [Message msg] : ( TELL toChar= CHARNAME txtMsg= STRINGLITERAL | SAY txtMsg= STRINGLITERAL | SHOUT txtMsg= STRINGLITERAL | WHISPER toChar= CHARNAME txtMsg= STRINGLITERAL );
    public final Message chat_message() throws RecognitionException {
        Message msg = null;


        Token toChar=null;
        Token txtMsg=null;

        try {
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:102:35: ( TELL toChar= CHARNAME txtMsg= STRINGLITERAL | SAY txtMsg= STRINGLITERAL | SHOUT txtMsg= STRINGLITERAL | WHISPER toChar= CHARNAME txtMsg= STRINGLITERAL )
            int alt2=4;
            switch ( input.LA(1) ) {
            case TELL:
                {
                alt2=1;
                }
                break;
            case SAY:
                {
                alt2=2;
                }
                break;
            case SHOUT:
                {
                alt2=3;
                }
                break;
            case WHISPER:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:102:37: TELL toChar= CHARNAME txtMsg= STRINGLITERAL
                    {
                    match(input,TELL,FOLLOW_TELL_in_chat_message275); 

                    toChar=(Token)match(input,CHARNAME,FOLLOW_CHARNAME_in_chat_message279); 

                    txtMsg=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_chat_message283); 


                    	    UserChatMessage chatMsg = new UserChatMessage();
                    	    chatMsg.setMsgType(UserChatMessage.MsgType.Tell);
                    	    chatMsg.setToUser((toChar!=null?toChar.getText():null));
                    	    chatMsg.setMessage(trimQuotes((txtMsg!=null?txtMsg.getText():null)));
                    	    
                    	    msg = chatMsg;
                    	

                    }
                    break;
                case 2 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:111:3: SAY txtMsg= STRINGLITERAL
                    {
                    match(input,SAY,FOLLOW_SAY_in_chat_message290); 

                    txtMsg=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_chat_message294); 


                    	    UserChatMessage chatMsg = new UserChatMessage();
                          chatMsg.setMsgType(UserChatMessage.MsgType.Say);
                          chatMsg.setMessage(trimQuotes((txtMsg!=null?txtMsg.getText():null)));
                          
                          msg = chatMsg;
                    	

                    }
                    break;
                case 3 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:119:3: SHOUT txtMsg= STRINGLITERAL
                    {
                    match(input,SHOUT,FOLLOW_SHOUT_in_chat_message301); 

                    txtMsg=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_chat_message305); 


                          UserChatMessage chatMsg = new UserChatMessage();
                          chatMsg.setMsgType(UserChatMessage.MsgType.Shout);
                          chatMsg.setMessage(trimQuotes((txtMsg!=null?txtMsg.getText():null)));
                          
                          msg = chatMsg;
                      

                    }
                    break;
                case 4 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:127:3: WHISPER toChar= CHARNAME txtMsg= STRINGLITERAL
                    {
                    match(input,WHISPER,FOLLOW_WHISPER_in_chat_message313); 

                    toChar=(Token)match(input,CHARNAME,FOLLOW_CHARNAME_in_chat_message317); 

                    txtMsg=(Token)match(input,STRINGLITERAL,FOLLOW_STRINGLITERAL_in_chat_message321); 


                    	    UserChatMessage chatMsg = new UserChatMessage();
                          chatMsg.setMsgType(UserChatMessage.MsgType.Tell);
                          chatMsg.setToUser((toChar!=null?toChar.getText():null));
                          chatMsg.setMessage(trimQuotes((txtMsg!=null?txtMsg.getText():null)));
                          
                          msg = chatMsg;
                    	

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
        return msg;
    }
    // $ANTLR end "chat_message"



    // $ANTLR start "move_message"
    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:138:1: move_message returns [Message msg] : ( ( GO )? GO_NORTH | GO_SOUTH | GO_EAST | GO_WEST | GO_NORTHEAST | GO_NORTHWEST | GO_SOUTHEAST | GO_SOUTHWEST | GO_UP | GO_DOWN );
    public final Message move_message() throws RecognitionException {
        Message msg = null;


        try {
            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:138:35: ( ( GO )? GO_NORTH | GO_SOUTH | GO_EAST | GO_WEST | GO_NORTHEAST | GO_NORTHWEST | GO_SOUTHEAST | GO_SOUTHWEST | GO_UP | GO_DOWN )
            int alt4=10;
            switch ( input.LA(1) ) {
            case GO_NORTH:
            case GO:
                {
                alt4=1;
                }
                break;
            case GO_SOUTH:
                {
                alt4=2;
                }
                break;
            case GO_EAST:
                {
                alt4=3;
                }
                break;
            case GO_WEST:
                {
                alt4=4;
                }
                break;
            case GO_NORTHEAST:
                {
                alt4=5;
                }
                break;
            case GO_NORTHWEST:
                {
                alt4=6;
                }
                break;
            case GO_SOUTHEAST:
                {
                alt4=7;
                }
                break;
            case GO_SOUTHWEST:
                {
                alt4=8;
                }
                break;
            case GO_UP:
                {
                alt4=9;
                }
                break;
            case GO_DOWN:
                {
                alt4=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }

            switch (alt4) {
                case 1 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:138:37: ( GO )? GO_NORTH
                    {
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:138:37: ( GO )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==GO) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:138:38: GO
                            {
                            match(input,GO,FOLLOW_GO_in_move_message337); 

                            }
                            break;

                    }


                    match(input,GO_NORTH,FOLLOW_GO_NORTH_in_move_message343); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.North);
                      

                    }
                    break;
                case 2 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:143:3: GO_SOUTH
                    {
                    match(input,GO_SOUTH,FOLLOW_GO_SOUTH_in_move_message351); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.South);
                      

                    }
                    break;
                case 3 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:147:3: GO_EAST
                    {
                    match(input,GO_EAST,FOLLOW_GO_EAST_in_move_message359); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.East);
                      

                    }
                    break;
                case 4 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:151:3: GO_WEST
                    {
                    match(input,GO_WEST,FOLLOW_GO_WEST_in_move_message367); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.West);
                      

                    }
                    break;
                case 5 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:155:3: GO_NORTHEAST
                    {
                    match(input,GO_NORTHEAST,FOLLOW_GO_NORTHEAST_in_move_message375); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Northeast);
                      

                    }
                    break;
                case 6 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:159:3: GO_NORTHWEST
                    {
                    match(input,GO_NORTHWEST,FOLLOW_GO_NORTHWEST_in_move_message383); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Northwest);
                      

                    }
                    break;
                case 7 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:163:3: GO_SOUTHEAST
                    {
                    match(input,GO_SOUTHEAST,FOLLOW_GO_SOUTHEAST_in_move_message391); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Southeast);
                      

                    }
                    break;
                case 8 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:167:3: GO_SOUTHWEST
                    {
                    match(input,GO_SOUTHWEST,FOLLOW_GO_SOUTHWEST_in_move_message399); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Southwest);
                      

                    }
                    break;
                case 9 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:171:3: GO_UP
                    {
                    match(input,GO_UP,FOLLOW_GO_UP_in_move_message407); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Up);
                      

                    }
                    break;
                case 10 :
                    // /home/steve/codemunki-gitrepo/minimud/minimud_shared/src/MiniMUDShared/MessageParser.g:175:3: GO_DOWN
                    {
                    match(input,GO_DOWN,FOLLOW_GO_DOWN_in_move_message415); 


                          msg = (Message)new PlayerMoveMessage(PlayerMoveMessage.Direction.Down);
                      

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
        return msg;
    }
    // $ANTLR end "move_message"

    // Delegated rules


 

    public static final BitSet FOLLOW_client_showtext_in_message45 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_request_input_in_message59 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_server_status_in_message71 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_user_logout_in_message83 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_chat_message_in_message95 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_move_message_in_message109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TEXTMSG_in_client_showtext141 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_FROM_in_client_showtext143 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUALS_in_client_showtext145 = new BitSet(new long[]{0x0000002000000040L});
    public static final BitSet FOLLOW_set_in_client_showtext149 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_MESSAGE_in_client_showtext157 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUALS_in_client_showtext159 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_client_showtext163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REQUEST_INPUT_in_request_input181 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_TYPE_in_request_input183 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUALS_in_request_input185 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_request_input189 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_MESSAGE_in_request_input197 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUALS_in_request_input199 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_request_input203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SERVER_STATUS_in_server_status219 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_TYPE_in_server_status221 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUALS_in_server_status223 = new BitSet(new long[]{0x000000001C000000L});
    public static final BitSet FOLLOW_set_in_server_status227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_user_logout253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TELL_in_chat_message275 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CHARNAME_in_chat_message279 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_chat_message283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SAY_in_chat_message290 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_chat_message294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SHOUT_in_chat_message301 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_chat_message305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHISPER_in_chat_message313 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CHARNAME_in_chat_message317 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_STRINGLITERAL_in_chat_message321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_in_move_message337 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_GO_NORTH_in_move_message343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_SOUTH_in_move_message351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_EAST_in_move_message359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_WEST_in_move_message367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_NORTHEAST_in_move_message375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_NORTHWEST_in_move_message383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_SOUTHEAST_in_move_message391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_SOUTHWEST_in_move_message399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_UP_in_move_message407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GO_DOWN_in_move_message415 = new BitSet(new long[]{0x0000000000000002L});

}