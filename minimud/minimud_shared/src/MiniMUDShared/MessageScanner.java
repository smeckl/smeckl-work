// $ANTLR 3.4 /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g 2013-10-20 17:30:47

  package MiniMUDShared;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class MessageScanner extends Lexer {
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

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public MessageScanner() {} 
    public MessageScanner(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public MessageScanner(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g"; }

    // $ANTLR start "TEXTMSG"
    public final void mTEXTMSG() throws RecognitionException {
        try {
            int _type = TEXTMSG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:9:8: ( 'text_msg' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:9:19: 'text_msg'
            {
            match("text_msg"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TEXTMSG"

    // $ANTLR start "MESSAGE"
    public final void mMESSAGE() throws RecognitionException {
        try {
            int _type = MESSAGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:10:8: ( 'message' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:10:19: 'message'
            {
            match("message"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MESSAGE"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:11:5: ( 'from' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:11:19: 'from'
            {
            match("from"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "TO"
    public final void mTO() throws RecognitionException {
        try {
            int _type = TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:12:3: ( 'to' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:12:19: 'to'
            {
            match("to"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TO"

    // $ANTLR start "PLAYER"
    public final void mPLAYER() throws RecognitionException {
        try {
            int _type = PLAYER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:13:7: ( 'player' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:13:19: 'player'
            {
            match("player"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PLAYER"

    // $ANTLR start "SERVER"
    public final void mSERVER() throws RecognitionException {
        try {
            int _type = SERVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:14:7: ( 'server' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:14:19: 'server'
            {
            match("server"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SERVER"

    // $ANTLR start "REQUEST_INPUT"
    public final void mREQUEST_INPUT() throws RecognitionException {
        try {
            int _type = REQUEST_INPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:15:14: ( 'request_input' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:15:19: 'request_input'
            {
            match("request_input"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "REQUEST_INPUT"

    // $ANTLR start "TYPE"
    public final void mTYPE() throws RecognitionException {
        try {
            int _type = TYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:16:5: ( 'type' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:16:19: 'type'
            {
            match("type"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TYPE"

    // $ANTLR start "NORMAL_INPUT"
    public final void mNORMAL_INPUT() throws RecognitionException {
        try {
            int _type = NORMAL_INPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:17:13: ( 'normal_input' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:17:19: 'normal_input'
            {
            match("normal_input"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NORMAL_INPUT"

    // $ANTLR start "PASSWD_INPUT"
    public final void mPASSWD_INPUT() throws RecognitionException {
        try {
            int _type = PASSWD_INPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:18:13: ( 'passwd_input' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:18:19: 'passwd_input'
            {
            match("passwd_input"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PASSWD_INPUT"

    // $ANTLR start "SERVER_STATUS"
    public final void mSERVER_STATUS() throws RecognitionException {
        try {
            int _type = SERVER_STATUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:19:14: ( 'server_status' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:19:19: 'server_status'
            {
            match("server_status"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SERVER_STATUS"

    // $ANTLR start "LOGON_SUCCESS"
    public final void mLOGON_SUCCESS() throws RecognitionException {
        try {
            int _type = LOGON_SUCCESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:20:14: ( 'logon_success' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:20:19: 'logon_success'
            {
            match("logon_success"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LOGON_SUCCESS"

    // $ANTLR start "LOGON_FAILED"
    public final void mLOGON_FAILED() throws RecognitionException {
        try {
            int _type = LOGON_FAILED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:21:13: ( 'logon_failed' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:21:19: 'logon_failed'
            {
            match("logon_failed"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LOGON_FAILED"

    // $ANTLR start "INVALID"
    public final void mINVALID() throws RecognitionException {
        try {
            int _type = INVALID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:22:8: ( 'invalid' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:22:19: 'invalid'
            {
            match("invalid"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INVALID"

    // $ANTLR start "QUIT"
    public final void mQUIT() throws RecognitionException {
        try {
            int _type = QUIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:23:5: ( 'quit' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:23:19: 'quit'
            {
            match("quit"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUIT"

    // $ANTLR start "EXIT"
    public final void mEXIT() throws RecognitionException {
        try {
            int _type = EXIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:24:5: ( 'exit' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:24:19: 'exit'
            {
            match("exit"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXIT"

    // $ANTLR start "LOGOUT"
    public final void mLOGOUT() throws RecognitionException {
        try {
            int _type = LOGOUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:25:7: ( 'logout' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:25:19: 'logout'
            {
            match("logout"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LOGOUT"

    // $ANTLR start "TELL"
    public final void mTELL() throws RecognitionException {
        try {
            int _type = TELL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:26:5: ( '/tell' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:26:19: '/tell'
            {
            match("/tell"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TELL"

    // $ANTLR start "SAY"
    public final void mSAY() throws RecognitionException {
        try {
            int _type = SAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:27:4: ( '/say' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:27:19: '/say'
            {
            match("/say"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SAY"

    // $ANTLR start "SHOUT"
    public final void mSHOUT() throws RecognitionException {
        try {
            int _type = SHOUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:28:6: ( '/shout' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:28:19: '/shout'
            {
            match("/shout"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SHOUT"

    // $ANTLR start "WHISPER"
    public final void mWHISPER() throws RecognitionException {
        try {
            int _type = WHISPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:29:8: ( '/whisper' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:29:19: '/whisper'
            {
            match("/whisper"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHISPER"

    // $ANTLR start "GO_NORTH"
    public final void mGO_NORTH() throws RecognitionException {
        try {
            int _type = GO_NORTH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:30:9: ( 'north' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:30:19: 'north'
            {
            match("north"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_NORTH"

    // $ANTLR start "GO_SOUTH"
    public final void mGO_SOUTH() throws RecognitionException {
        try {
            int _type = GO_SOUTH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:31:9: ( 'south' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:31:19: 'south'
            {
            match("south"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_SOUTH"

    // $ANTLR start "GO_EAST"
    public final void mGO_EAST() throws RecognitionException {
        try {
            int _type = GO_EAST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:32:8: ( 'east' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:32:19: 'east'
            {
            match("east"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_EAST"

    // $ANTLR start "GO_WEST"
    public final void mGO_WEST() throws RecognitionException {
        try {
            int _type = GO_WEST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:33:8: ( 'west' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:33:19: 'west'
            {
            match("west"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_WEST"

    // $ANTLR start "GO_NORTHEAST"
    public final void mGO_NORTHEAST() throws RecognitionException {
        try {
            int _type = GO_NORTHEAST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:34:13: ( 'northeast' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:34:19: 'northeast'
            {
            match("northeast"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_NORTHEAST"

    // $ANTLR start "GO_NORTHWEST"
    public final void mGO_NORTHWEST() throws RecognitionException {
        try {
            int _type = GO_NORTHWEST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:35:13: ( 'northwest' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:35:19: 'northwest'
            {
            match("northwest"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_NORTHWEST"

    // $ANTLR start "GO_SOUTHEAST"
    public final void mGO_SOUTHEAST() throws RecognitionException {
        try {
            int _type = GO_SOUTHEAST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:36:13: ( 'southeast' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:36:19: 'southeast'
            {
            match("southeast"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_SOUTHEAST"

    // $ANTLR start "GO_SOUTHWEST"
    public final void mGO_SOUTHWEST() throws RecognitionException {
        try {
            int _type = GO_SOUTHWEST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:37:13: ( 'southwest' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:37:19: 'southwest'
            {
            match("southwest"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_SOUTHWEST"

    // $ANTLR start "GO_UP"
    public final void mGO_UP() throws RecognitionException {
        try {
            int _type = GO_UP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:38:6: ( 'up' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:38:19: 'up'
            {
            match("up"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_UP"

    // $ANTLR start "GO_DOWN"
    public final void mGO_DOWN() throws RecognitionException {
        try {
            int _type = GO_DOWN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:39:8: ( 'down' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:39:19: 'down'
            {
            match("down"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GO_DOWN"

    // $ANTLR start "WS_"
    public final void mWS_() throws RecognitionException {
        try {
            int _type = WS_;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:41:5: ( ( ' ' | '\\n' | '\\t' | '\\r' ) )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:41:7: ( ' ' | '\\n' | '\\t' | '\\r' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS_"

    // $ANTLR start "PRINTABLE_CHARS"
    public final void mPRINTABLE_CHARS() throws RecognitionException {
        try {
            int _type = PRINTABLE_CHARS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:43:16: ( ( ' ' | '!' | ( '#' .. '&' ) | ( '(' .. '/' ) | ( ':' .. '<' ) | ( '>' .. '@' ) | '[' | ( ']' .. '`' ) | DIGIT | CHAR ) )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:
            {
            if ( (input.LA(1) >= ' ' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '<')||(input.LA(1) >= '>' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PRINTABLE_CHARS"

    // $ANTLR start "CHARLITERAL"
    public final void mCHARLITERAL() throws RecognitionException {
        try {
            int _type = CHARLITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:45:13: ( '\\'' ( ESCAPE | PRINTABLE_CHARS ) '\\'' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:45:15: '\\'' ( ESCAPE | PRINTABLE_CHARS ) '\\''
            {
            match('\''); 

            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:45:20: ( ESCAPE | PRINTABLE_CHARS )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='\\') ) {
                alt1=1;
            }
            else if ( ((LA1_0 >= ' ' && LA1_0 <= '!')||(LA1_0 >= '#' && LA1_0 <= '&')||(LA1_0 >= '(' && LA1_0 <= '<')||(LA1_0 >= '>' && LA1_0 <= '[')||(LA1_0 >= ']' && LA1_0 <= 'z')) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }
            switch (alt1) {
                case 1 :
                    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:45:22: ESCAPE
                    {
                    mESCAPE(); 


                    }
                    break;
                case 2 :
                    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:45:31: PRINTABLE_CHARS
                    {
                    mPRINTABLE_CHARS(); 


                    }
                    break;

            }


            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHARLITERAL"

    // $ANTLR start "STRINGLITERAL"
    public final void mSTRINGLITERAL() throws RecognitionException {
        try {
            int _type = STRINGLITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:47:15: ( '\"' ( ESCAPE | PRINTABLE_CHARS )* ( '\"' ) )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:47:17: '\"' ( ESCAPE | PRINTABLE_CHARS )* ( '\"' )
            {
            match('\"'); 

            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:48:9: ( ESCAPE | PRINTABLE_CHARS )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\\') ) {
                    alt2=1;
                }
                else if ( ((LA2_0 >= ' ' && LA2_0 <= '!')||(LA2_0 >= '#' && LA2_0 <= '&')||(LA2_0 >= '(' && LA2_0 <= '<')||(LA2_0 >= '>' && LA2_0 <= '[')||(LA2_0 >= ']' && LA2_0 <= 'z')) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:48:11: ESCAPE
            	    {
            	    mESCAPE(); 


            	    }
            	    break;
            	case 2 :
            	    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:49:11: PRINTABLE_CHARS
            	    {
            	    mPRINTABLE_CHARS(); 


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:51:9: ( '\"' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:51:10: '\"'
            {
            match('\"'); 

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRINGLITERAL"

    // $ANTLR start "CHARNAME"
    public final void mCHARNAME() throws RecognitionException {
        try {
            int _type = CHARNAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:53:9: ( ( CHAR ) ( DIGIT | CHAR )+ )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:53:11: ( CHAR ) ( DIGIT | CHAR )+
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:53:17: ( DIGIT | CHAR )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '0' && LA3_0 <= '9')||(LA3_0 >= 'A' && LA3_0 <= 'Z')||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHARNAME"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:55:15: ( ( '0' .. '9' ) )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "DECIMAL_LITERAL"
    public final void mDECIMAL_LITERAL() throws RecognitionException {
        try {
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:57:25: ( ( DIGIT )+ )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:57:27: ( DIGIT )+
            {
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:57:27: ( DIGIT )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DECIMAL_LITERAL"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:59:19: ( ( DIGIT | 'a' .. 'f' | 'A' .. 'F' ) )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "HEX_LITERAL"
    public final void mHEX_LITERAL() throws RecognitionException {
        try {
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:61:21: ( '0' ( 'x' | 'X' ) ( HEX_DIGIT )+ )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:61:23: '0' ( 'x' | 'X' ) ( HEX_DIGIT )+
            {
            match('0'); 

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:61:39: ( HEX_DIGIT )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= '0' && LA5_0 <= '9')||(LA5_0 >= 'A' && LA5_0 <= 'F')||(LA5_0 >= 'a' && LA5_0 <= 'f')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HEX_LITERAL"

    // $ANTLR start "CHAR"
    public final void mCHAR() throws RecognitionException {
        try {
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:63:14: ( ( 'a' .. 'z' | 'A' .. 'Z' ) )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHAR"

    // $ANTLR start "ESCAPE"
    public final void mESCAPE() throws RecognitionException {
        try {
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:66:8: ( '\\\\' ( 'n' | 'r' | 't' | '\\\\' | '\"' | '\\'' ) )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:66:11: '\\\\' ( 'n' | 'r' | 't' | '\\\\' | '\"' | '\\'' )
            {
            match('\\'); 

            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:67:3: ( 'n' | 'r' | 't' | '\\\\' | '\"' | '\\'' )
            int alt6=6;
            switch ( input.LA(1) ) {
            case 'n':
                {
                alt6=1;
                }
                break;
            case 'r':
                {
                alt6=2;
                }
                break;
            case 't':
                {
                alt6=3;
                }
                break;
            case '\\':
                {
                alt6=4;
                }
                break;
            case '\"':
                {
                alt6=5;
                }
                break;
            case '\'':
                {
                alt6=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }

            switch (alt6) {
                case 1 :
                    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:67:5: 'n'
                    {
                    match('n'); 

                     setText("\\n"); 

                    }
                    break;
                case 2 :
                    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:68:7: 'r'
                    {
                    match('r'); 

                     setText("\\r"); 

                    }
                    break;
                case 3 :
                    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:69:7: 't'
                    {
                    match('t'); 

                     setText("\\t"); 

                    }
                    break;
                case 4 :
                    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:70:7: '\\\\'
                    {
                    match('\\'); 

                     setText("\\\\"); 

                    }
                    break;
                case 5 :
                    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:71:7: '\"'
                    {
                    match('\"'); 

                     setText("\\\""); 

                    }
                    break;
                case 6 :
                    // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:72:7: '\\''
                    {
                    match('\''); 

                     setText("\\\'"); 

                    }
                    break;

            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ESCAPE"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:75:9: ( '=' )
            // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:75:11: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQUALS"

    public void mTokens() throws RecognitionException {
        // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:8: ( TEXTMSG | MESSAGE | FROM | TO | PLAYER | SERVER | REQUEST_INPUT | TYPE | NORMAL_INPUT | PASSWD_INPUT | SERVER_STATUS | LOGON_SUCCESS | LOGON_FAILED | INVALID | QUIT | EXIT | LOGOUT | TELL | SAY | SHOUT | WHISPER | GO_NORTH | GO_SOUTH | GO_EAST | GO_WEST | GO_NORTHEAST | GO_NORTHWEST | GO_SOUTHEAST | GO_SOUTHWEST | GO_UP | GO_DOWN | WS_ | PRINTABLE_CHARS | CHARLITERAL | STRINGLITERAL | CHARNAME | EQUALS )
        int alt7=37;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:10: TEXTMSG
                {
                mTEXTMSG(); 


                }
                break;
            case 2 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:18: MESSAGE
                {
                mMESSAGE(); 


                }
                break;
            case 3 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:26: FROM
                {
                mFROM(); 


                }
                break;
            case 4 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:31: TO
                {
                mTO(); 


                }
                break;
            case 5 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:34: PLAYER
                {
                mPLAYER(); 


                }
                break;
            case 6 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:41: SERVER
                {
                mSERVER(); 


                }
                break;
            case 7 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:48: REQUEST_INPUT
                {
                mREQUEST_INPUT(); 


                }
                break;
            case 8 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:62: TYPE
                {
                mTYPE(); 


                }
                break;
            case 9 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:67: NORMAL_INPUT
                {
                mNORMAL_INPUT(); 


                }
                break;
            case 10 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:80: PASSWD_INPUT
                {
                mPASSWD_INPUT(); 


                }
                break;
            case 11 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:93: SERVER_STATUS
                {
                mSERVER_STATUS(); 


                }
                break;
            case 12 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:107: LOGON_SUCCESS
                {
                mLOGON_SUCCESS(); 


                }
                break;
            case 13 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:121: LOGON_FAILED
                {
                mLOGON_FAILED(); 


                }
                break;
            case 14 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:134: INVALID
                {
                mINVALID(); 


                }
                break;
            case 15 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:142: QUIT
                {
                mQUIT(); 


                }
                break;
            case 16 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:147: EXIT
                {
                mEXIT(); 


                }
                break;
            case 17 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:152: LOGOUT
                {
                mLOGOUT(); 


                }
                break;
            case 18 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:159: TELL
                {
                mTELL(); 


                }
                break;
            case 19 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:164: SAY
                {
                mSAY(); 


                }
                break;
            case 20 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:168: SHOUT
                {
                mSHOUT(); 


                }
                break;
            case 21 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:174: WHISPER
                {
                mWHISPER(); 


                }
                break;
            case 22 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:182: GO_NORTH
                {
                mGO_NORTH(); 


                }
                break;
            case 23 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:191: GO_SOUTH
                {
                mGO_SOUTH(); 


                }
                break;
            case 24 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:200: GO_EAST
                {
                mGO_EAST(); 


                }
                break;
            case 25 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:208: GO_WEST
                {
                mGO_WEST(); 


                }
                break;
            case 26 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:216: GO_NORTHEAST
                {
                mGO_NORTHEAST(); 


                }
                break;
            case 27 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:229: GO_NORTHWEST
                {
                mGO_NORTHWEST(); 


                }
                break;
            case 28 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:242: GO_SOUTHEAST
                {
                mGO_SOUTHEAST(); 


                }
                break;
            case 29 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:255: GO_SOUTHWEST
                {
                mGO_SOUTHWEST(); 


                }
                break;
            case 30 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:268: GO_UP
                {
                mGO_UP(); 


                }
                break;
            case 31 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:274: GO_DOWN
                {
                mGO_DOWN(); 


                }
                break;
            case 32 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:282: WS_
                {
                mWS_(); 


                }
                break;
            case 33 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:286: PRINTABLE_CHARS
                {
                mPRINTABLE_CHARS(); 


                }
                break;
            case 34 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:302: CHARLITERAL
                {
                mCHARLITERAL(); 


                }
                break;
            case 35 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:314: STRINGLITERAL
                {
                mSTRINGLITERAL(); 


                }
                break;
            case 36 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:328: CHARNAME
                {
                mCHARNAME(); 


                }
                break;
            case 37 :
                // /home/steve/codemunki-work/minimud/minimud_shared/src/MiniMUDShared/MessageScanner.g:1:337: EQUALS
                {
                mEQUALS(); 


                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\1\uffff\17\25\2\uffff\1\25\4\uffff\1\32\1\57\1\32\1\uffff\15\32"+
        "\3\uffff\1\32\1\101\2\32\1\uffff\16\32\2\uffff\1\32\1\uffff\2\32"+
        "\1\126\1\32\1\130\11\32\1\143\1\144\1\145\1\146\1\147\2\uffff\1"+
        "\32\1\uffff\3\32\1\156\2\32\1\163\3\32\5\uffff\1\32\1\170\1\32\1"+
        "\173\2\32\1\uffff\4\32\2\uffff\1\u0084\1\32\1\u0086\4\uffff\3\32"+
        "\1\uffff\2\32\3\uffff\1\u008c\1\uffff\2\32\1\uffff\2\32\1\uffff"+
        "\1\u0091\1\u0092\1\u0093\1\u0094\4\uffff";
    static final String DFA7_eofS =
        "\u0095\uffff";
    static final String DFA7_minS =
        "\1\11\13\60\1\163\3\60\2\uffff\1\60\4\uffff\1\170\1\60\1\160\1\uffff"+
        "\1\163\1\157\1\141\1\163\1\162\1\165\1\161\1\162\1\147\1\166\2\151"+
        "\1\163\1\uffff\1\141\1\uffff\1\163\1\60\1\167\1\164\1\uffff\1\145"+
        "\1\163\1\155\1\171\1\163\1\166\1\164\1\165\1\155\1\157\1\141\3\164"+
        "\2\uffff\1\164\1\uffff\1\156\1\137\1\60\1\141\1\60\1\145\1\167\1"+
        "\145\1\150\1\145\1\141\1\150\1\156\1\154\5\60\2\uffff\1\147\1\uffff"+
        "\1\162\1\144\1\162\1\60\1\163\1\154\1\60\1\137\1\164\1\151\5\uffff"+
        "\1\145\1\60\1\137\1\60\1\141\1\145\1\uffff\1\164\1\137\1\141\1\145"+
        "\1\uffff\1\146\1\60\1\144\1\60\4\uffff\2\163\1\137\1\uffff\2\163"+
        "\3\uffff\1\60\1\uffff\2\164\1\uffff\2\164\1\uffff\4\60\4\uffff";
    static final String DFA7_maxS =
        "\14\172\1\167\3\172\2\uffff\1\172\4\uffff\1\170\1\172\1\160\1\uffff"+
        "\1\163\1\157\1\141\1\163\1\162\1\165\1\161\1\162\1\147\1\166\2\151"+
        "\1\163\1\uffff\1\150\1\uffff\1\163\1\172\1\167\1\164\1\uffff\1\145"+
        "\1\163\1\155\1\171\1\163\1\166\1\164\1\165\1\164\1\157\1\141\3\164"+
        "\2\uffff\1\164\1\uffff\1\156\1\137\1\172\1\141\1\172\1\145\1\167"+
        "\1\145\1\150\1\145\1\141\1\150\1\165\1\154\5\172\2\uffff\1\147\1"+
        "\uffff\1\162\1\144\1\162\1\172\1\163\1\154\1\172\1\137\1\164\1\151"+
        "\5\uffff\1\145\1\172\1\137\1\172\1\141\1\145\1\uffff\1\164\1\137"+
        "\1\141\1\145\1\uffff\1\163\1\172\1\144\1\172\4\uffff\2\163\1\137"+
        "\1\uffff\2\163\3\uffff\1\172\1\uffff\2\164\1\uffff\2\164\1\uffff"+
        "\4\172\4\uffff";
    static final String DFA7_acceptS =
        "\20\uffff\2\40\1\uffff\1\42\1\43\1\41\1\45\3\uffff\1\44\15\uffff"+
        "\1\22\1\uffff\1\25\4\uffff\1\4\16\uffff\1\23\1\24\1\uffff\1\36\23"+
        "\uffff\1\1\1\10\1\uffff\1\3\12\uffff\1\17\1\20\1\30\1\31\1\37\6"+
        "\uffff\1\27\4\uffff\1\26\4\uffff\1\5\1\12\1\13\1\6\3\uffff\1\11"+
        "\2\uffff\1\14\1\15\1\21\1\uffff\1\2\2\uffff\1\7\2\uffff\1\16\4\uffff"+
        "\1\34\1\35\1\32\1\33";
    static final String DFA7_specialS =
        "\u0095\uffff}>";
    static final String[] DFA7_transitionS = {
            "\2\21\2\uffff\1\21\22\uffff\1\20\1\25\1\24\4\25\1\23\7\25\1"+
            "\14\15\25\1\26\3\25\32\22\1\25\1\uffff\4\25\3\22\1\17\1\13\1"+
            "\3\2\22\1\11\2\22\1\10\1\2\1\7\1\22\1\4\1\12\1\6\1\5\1\1\1\16"+
            "\1\22\1\15\3\22",
            "\12\32\7\uffff\32\32\6\uffff\4\32\1\27\11\32\1\30\11\32\1\31"+
            "\1\32",
            "\12\32\7\uffff\32\32\6\uffff\4\32\1\33\25\32",
            "\12\32\7\uffff\32\32\6\uffff\21\32\1\34\10\32",
            "\12\32\7\uffff\32\32\6\uffff\1\36\12\32\1\35\16\32",
            "\12\32\7\uffff\32\32\6\uffff\4\32\1\37\11\32\1\40\13\32",
            "\12\32\7\uffff\32\32\6\uffff\4\32\1\41\25\32",
            "\12\32\7\uffff\32\32\6\uffff\16\32\1\42\13\32",
            "\12\32\7\uffff\32\32\6\uffff\16\32\1\43\13\32",
            "\12\32\7\uffff\32\32\6\uffff\15\32\1\44\14\32",
            "\12\32\7\uffff\32\32\6\uffff\24\32\1\45\5\32",
            "\12\32\7\uffff\32\32\6\uffff\1\47\26\32\1\46\2\32",
            "\1\51\1\50\2\uffff\1\52",
            "\12\32\7\uffff\32\32\6\uffff\4\32\1\53\25\32",
            "\12\32\7\uffff\32\32\6\uffff\17\32\1\54\12\32",
            "\12\32\7\uffff\32\32\6\uffff\16\32\1\55\13\32",
            "",
            "",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "",
            "",
            "",
            "",
            "\1\56",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\1\60",
            "",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\75",
            "",
            "\1\76\6\uffff\1\77",
            "",
            "\1\100",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\1\102",
            "\1\103",
            "",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114\6\uffff\1\115",
            "\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "",
            "",
            "\1\123",
            "",
            "\1\124",
            "\1\125",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\1\127",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140\6\uffff\1\141",
            "\1\142",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "",
            "",
            "\1\150",
            "",
            "\1\151",
            "\1\152",
            "\1\153",
            "\12\32\7\uffff\32\32\6\uffff\4\32\1\154\21\32\1\155\3\32",
            "\1\157",
            "\1\160",
            "\12\32\7\uffff\32\32\6\uffff\4\32\1\161\21\32\1\162\3\32",
            "\1\164",
            "\1\165",
            "\1\166",
            "",
            "",
            "",
            "",
            "",
            "\1\167",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\1\171",
            "\12\32\7\uffff\32\32\4\uffff\1\172\1\uffff\32\32",
            "\1\174",
            "\1\175",
            "",
            "\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "",
            "\1\u0083\14\uffff\1\u0082",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\1\u0085",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "",
            "",
            "",
            "",
            "\1\u0087",
            "\1\u0088",
            "\1\u0089",
            "",
            "\1\u008a",
            "\1\u008b",
            "",
            "",
            "",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "",
            "\1\u008d",
            "\1\u008e",
            "",
            "\1\u008f",
            "\1\u0090",
            "",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "\12\32\7\uffff\32\32\6\uffff\32\32",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( TEXTMSG | MESSAGE | FROM | TO | PLAYER | SERVER | REQUEST_INPUT | TYPE | NORMAL_INPUT | PASSWD_INPUT | SERVER_STATUS | LOGON_SUCCESS | LOGON_FAILED | INVALID | QUIT | EXIT | LOGOUT | TELL | SAY | SHOUT | WHISPER | GO_NORTH | GO_SOUTH | GO_EAST | GO_WEST | GO_NORTHEAST | GO_NORTHWEST | GO_SOUTHEAST | GO_SOUTHWEST | GO_UP | GO_DOWN | WS_ | PRINTABLE_CHARS | CHARLITERAL | STRINGLITERAL | CHARNAME | EQUALS );";
        }
    }
 

}