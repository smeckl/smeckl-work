// $ANTLR 3.5.2 /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g 2014-07-13 06:56:52
package decaf;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class DecafScanner extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public DecafScanner() {} 
	public DecafScanner(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public DecafScanner(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g"; }

	// $ANTLR start "CLASS"
	public final void mCLASS() throws RecognitionException {
		try {
			int _type = CLASS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:6:6: ( 'class' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:6:13: 'class'
			{
			match("class"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CLASS"

	// $ANTLR start "PROGRAM"
	public final void mPROGRAM() throws RecognitionException {
		try {
			int _type = PROGRAM;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:7:8: ( 'Program' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:7:13: 'Program'
			{
			match("Program"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PROGRAM"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:8:4: ( 'int' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:8:13: 'int'
			{
			match("int"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "BOOLEAN"
	public final void mBOOLEAN() throws RecognitionException {
		try {
			int _type = BOOLEAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:9:8: ( 'boolean' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:9:13: 'boolean'
			{
			match("boolean"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BOOLEAN"

	// $ANTLR start "IF"
	public final void mIF() throws RecognitionException {
		try {
			int _type = IF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:10:3: ( 'if' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:10:13: 'if'
			{
			match("if"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IF"

	// $ANTLR start "ELSE"
	public final void mELSE() throws RecognitionException {
		try {
			int _type = ELSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:11:5: ( 'else' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:11:13: 'else'
			{
			match("else"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ELSE"

	// $ANTLR start "FOR"
	public final void mFOR() throws RecognitionException {
		try {
			int _type = FOR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:12:4: ( 'for' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:12:13: 'for'
			{
			match("for"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FOR"

	// $ANTLR start "RETURN"
	public final void mRETURN() throws RecognitionException {
		try {
			int _type = RETURN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:13:7: ( 'return' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:13:13: 'return'
			{
			match("return"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RETURN"

	// $ANTLR start "BREAK"
	public final void mBREAK() throws RecognitionException {
		try {
			int _type = BREAK;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:14:6: ( 'break' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:14:13: 'break'
			{
			match("break"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BREAK"

	// $ANTLR start "CONTINUE"
	public final void mCONTINUE() throws RecognitionException {
		try {
			int _type = CONTINUE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:15:9: ( 'continue' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:15:13: 'continue'
			{
			match("continue"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CONTINUE"

	// $ANTLR start "CALLOUT"
	public final void mCALLOUT() throws RecognitionException {
		try {
			int _type = CALLOUT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:16:8: ( 'callout' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:16:13: 'callout'
			{
			match("callout"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CALLOUT"

	// $ANTLR start "VOID"
	public final void mVOID() throws RecognitionException {
		try {
			int _type = VOID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:17:5: ( 'void' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:17:13: 'void'
			{
			match("void"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "VOID"

	// $ANTLR start "TRUE"
	public final void mTRUE() throws RecognitionException {
		try {
			int _type = TRUE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:18:5: ( 'true' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:18:13: 'true'
			{
			match("true"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TRUE"

	// $ANTLR start "FALSE"
	public final void mFALSE() throws RecognitionException {
		try {
			int _type = FALSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:19:6: ( 'false' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:19:13: 'false'
			{
			match("false"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FALSE"

	// $ANTLR start "WS_"
	public final void mWS_() throws RecognitionException {
		try {
			int _type = WS_;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:21:5: ( ( ' ' | '\\n' | '\\t' | '\\r' ) )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:21:7: ( ' ' | '\\n' | '\\t' | '\\r' )
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

	// $ANTLR start "SL_COMMENT"
	public final void mSL_COMMENT() throws RecognitionException {
		try {
			int _type = SL_COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:23:12: ( '//' (~ '\\n' )* '\\n' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:23:14: '//' (~ '\\n' )* '\\n'
			{
			match("//"); 

			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:23:19: (~ '\\n' )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\uFFFF')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\uFFFF') ) {
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
					break loop1;
				}
			}

			match('\n'); 
			 skip(); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SL_COMMENT"

	// $ANTLR start "PRINTABLE_CHARS"
	public final void mPRINTABLE_CHARS() throws RecognitionException {
		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:27:25: ( ( ' ' | '!' | ( '#' .. '&' ) | ( '(' .. '/' ) | ( ':' .. '@' ) | '[' | ( ']' .. '`' ) | DIGIT | CHAR ) )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:
			{
			if ( (input.LA(1) >= ' ' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= 'z') ) {
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
	// $ANTLR end "PRINTABLE_CHARS"

	// $ANTLR start "CHARLITERAL"
	public final void mCHARLITERAL() throws RecognitionException {
		try {
			int _type = CHARLITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:29:13: ( '\\'' ( ESCAPE | PRINTABLE_CHARS ) '\\'' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:29:15: '\\'' ( ESCAPE | PRINTABLE_CHARS ) '\\''
			{
			match('\''); 
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:29:20: ( ESCAPE | PRINTABLE_CHARS )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0=='\\') ) {
				alt2=1;
			}
			else if ( ((LA2_0 >= ' ' && LA2_0 <= '!')||(LA2_0 >= '#' && LA2_0 <= '&')||(LA2_0 >= '(' && LA2_0 <= '[')||(LA2_0 >= ']' && LA2_0 <= 'z')) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:29:22: ESCAPE
					{
					mESCAPE(); 

					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:29:31: PRINTABLE_CHARS
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
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:31:15: ( '\"' ( ESCAPE | PRINTABLE_CHARS )* ( '\"' ) )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:31:17: '\"' ( ESCAPE | PRINTABLE_CHARS )* ( '\"' )
			{
			match('\"'); 
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:32:5: ( ESCAPE | PRINTABLE_CHARS )*
			loop3:
			while (true) {
				int alt3=3;
				int LA3_0 = input.LA(1);
				if ( (LA3_0=='\\') ) {
					alt3=1;
				}
				else if ( ((LA3_0 >= ' ' && LA3_0 <= '!')||(LA3_0 >= '#' && LA3_0 <= '&')||(LA3_0 >= '(' && LA3_0 <= '[')||(LA3_0 >= ']' && LA3_0 <= 'z')) ) {
					alt3=2;
				}

				switch (alt3) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:32:7: ESCAPE
					{
					mESCAPE(); 

					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:33:7: PRINTABLE_CHARS
					{
					mPRINTABLE_CHARS(); 

					}
					break;

				default :
					break loop3;
				}
			}

			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:35:5: ( '\"' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:35:6: '\"'
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

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:37:15: ( ( '0' .. '9' ) )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:
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
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:39:25: ( ( DIGIT )+ )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:39:27: ( DIGIT )+
			{
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:39:27: ( DIGIT )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:
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
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

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
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:41:19: ( ( DIGIT | 'a' .. 'f' | 'A' .. 'F' ) )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:
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
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:43:21: ( '0' ( 'x' | 'X' ) ( HEX_DIGIT )+ )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:43:23: '0' ( 'x' | 'X' ) ( HEX_DIGIT )+
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
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:43:39: ( HEX_DIGIT )+
			int cnt5=0;
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( ((LA5_0 >= '0' && LA5_0 <= '9')||(LA5_0 >= 'A' && LA5_0 <= 'F')||(LA5_0 >= 'a' && LA5_0 <= 'f')) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:
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
					EarlyExitException eee = new EarlyExitException(5, input);
					throw eee;
				}
				cnt5++;
			}

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
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:45:14: ( ( 'a' .. 'z' | 'A' .. 'Z' ) )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:
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

	// $ANTLR start "INTLITERAL"
	public final void mINTLITERAL() throws RecognitionException {
		try {
			int _type = INTLITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:47:11: ( DECIMAL_LITERAL | HEX_LITERAL )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0=='0') ) {
				int LA6_1 = input.LA(2);
				if ( (LA6_1=='X'||LA6_1=='x') ) {
					alt6=2;
				}

				else {
					alt6=1;
				}

			}
			else if ( ((LA6_0 >= '1' && LA6_0 <= '9')) ) {
				alt6=1;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:47:13: DECIMAL_LITERAL
					{
					mDECIMAL_LITERAL(); 

					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:47:31: HEX_LITERAL
					{
					mHEX_LITERAL(); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTLITERAL"

	// $ANTLR start "LCURLY"
	public final void mLCURLY() throws RecognitionException {
		try {
			int _type = LCURLY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:49:7: ( '{' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:49:9: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LCURLY"

	// $ANTLR start "RCURLY"
	public final void mRCURLY() throws RecognitionException {
		try {
			int _type = RCURLY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:50:7: ( '}' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:50:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RCURLY"

	// $ANTLR start "LPAREN"
	public final void mLPAREN() throws RecognitionException {
		try {
			int _type = LPAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:52:7: ( '(' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:52:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LPAREN"

	// $ANTLR start "RPAREN"
	public final void mRPAREN() throws RecognitionException {
		try {
			int _type = RPAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:53:7: ( ')' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:53:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RPAREN"

	// $ANTLR start "LBRACKET"
	public final void mLBRACKET() throws RecognitionException {
		try {
			int _type = LBRACKET;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:55:9: ( '[' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:55:11: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LBRACKET"

	// $ANTLR start "RBRACKET"
	public final void mRBRACKET() throws RecognitionException {
		try {
			int _type = RBRACKET;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:56:9: ( ']' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:56:11: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RBRACKET"

	// $ANTLR start "ADD"
	public final void mADD() throws RecognitionException {
		try {
			int _type = ADD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:58:4: ( '+' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:58:6: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ADD"

	// $ANTLR start "SUBTRACT"
	public final void mSUBTRACT() throws RecognitionException {
		try {
			int _type = SUBTRACT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:59:9: ( '-' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:59:11: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SUBTRACT"

	// $ANTLR start "MULTIPLY"
	public final void mMULTIPLY() throws RecognitionException {
		try {
			int _type = MULTIPLY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:60:9: ( '*' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:60:11: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MULTIPLY"

	// $ANTLR start "DIVIDE"
	public final void mDIVIDE() throws RecognitionException {
		try {
			int _type = DIVIDE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:61:7: ( '/' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:61:9: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIVIDE"

	// $ANTLR start "MODULUS"
	public final void mMODULUS() throws RecognitionException {
		try {
			int _type = MODULUS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:62:8: ( '%' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:62:10: '%'
			{
			match('%'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MODULUS"

	// $ANTLR start "GT"
	public final void mGT() throws RecognitionException {
		try {
			int _type = GT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:64:3: ( '>' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:64:5: '>'
			{
			match('>'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "GT"

	// $ANTLR start "LT"
	public final void mLT() throws RecognitionException {
		try {
			int _type = LT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:65:3: ( '<' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:65:5: '<'
			{
			match('<'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LT"

	// $ANTLR start "GTE"
	public final void mGTE() throws RecognitionException {
		try {
			int _type = GTE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:66:4: ( '>=' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:66:6: '>='
			{
			match(">="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "GTE"

	// $ANTLR start "LTE"
	public final void mLTE() throws RecognitionException {
		try {
			int _type = LTE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:67:4: ( '<=' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:67:6: '<='
			{
			match("<="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LTE"

	// $ANTLR start "EQ"
	public final void mEQ() throws RecognitionException {
		try {
			int _type = EQ;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:69:3: ( '==' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:69:5: '=='
			{
			match("=="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EQ"

	// $ANTLR start "NEQ"
	public final void mNEQ() throws RecognitionException {
		try {
			int _type = NEQ;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:70:4: ( '!=' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:70:6: '!='
			{
			match("!="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NEQ"

	// $ANTLR start "AND"
	public final void mAND() throws RecognitionException {
		try {
			int _type = AND;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:72:4: ( '&&' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:72:6: '&&'
			{
			match("&&"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "AND"

	// $ANTLR start "OR"
	public final void mOR() throws RecognitionException {
		try {
			int _type = OR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:73:3: ( '||' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:73:5: '||'
			{
			match("||"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OR"

	// $ANTLR start "NOT"
	public final void mNOT() throws RecognitionException {
		try {
			int _type = NOT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:75:4: ( '!' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:75:6: '!'
			{
			match('!'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NOT"

	// $ANTLR start "ASSIGN"
	public final void mASSIGN() throws RecognitionException {
		try {
			int _type = ASSIGN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:77:7: ( '=' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:77:9: '='
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
	// $ANTLR end "ASSIGN"

	// $ANTLR start "ADD_ASSIGN"
	public final void mADD_ASSIGN() throws RecognitionException {
		try {
			int _type = ADD_ASSIGN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:78:11: ( '+=' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:78:13: '+='
			{
			match("+="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ADD_ASSIGN"

	// $ANTLR start "SUBTRACT_ASSIGN"
	public final void mSUBTRACT_ASSIGN() throws RecognitionException {
		try {
			int _type = SUBTRACT_ASSIGN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:79:16: ( '-=' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:79:18: '-='
			{
			match("-="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SUBTRACT_ASSIGN"

	// $ANTLR start "COMMA"
	public final void mCOMMA() throws RecognitionException {
		try {
			int _type = COMMA;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:81:6: ( ',' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:81:8: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMA"

	// $ANTLR start "SEMICOLON"
	public final void mSEMICOLON() throws RecognitionException {
		try {
			int _type = SEMICOLON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:83:10: ( ';' )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:83:12: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SEMICOLON"

	// $ANTLR start "IDENTIFIER"
	public final void mIDENTIFIER() throws RecognitionException {
		try {
			int _type = IDENTIFIER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:85:11: ( ( CHAR | '_' ) ( CHAR | '_' | '0' .. '9' )* )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:86:3: ( CHAR | '_' ) ( CHAR | '_' | '0' .. '9' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:86:16: ( CHAR | '_' | '0' .. '9' )*
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( ((LA7_0 >= '0' && LA7_0 <= '9')||(LA7_0 >= 'A' && LA7_0 <= 'Z')||LA7_0=='_'||(LA7_0 >= 'a' && LA7_0 <= 'z')) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
					break loop7;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IDENTIFIER"

	// $ANTLR start "ESCAPE"
	public final void mESCAPE() throws RecognitionException {
		try {
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:89:8: ( '\\\\' ( 'n' | 'r' | 't' | '\\\\' | '\"' | '\\'' ) )
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:89:11: '\\\\' ( 'n' | 'r' | 't' | '\\\\' | '\"' | '\\'' )
			{
			match('\\'); 
			// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:90:2: ( 'n' | 'r' | 't' | '\\\\' | '\"' | '\\'' )
			int alt8=6;
			switch ( input.LA(1) ) {
			case 'n':
				{
				alt8=1;
				}
				break;
			case 'r':
				{
				alt8=2;
				}
				break;
			case 't':
				{
				alt8=3;
				}
				break;
			case '\\':
				{
				alt8=4;
				}
				break;
			case '\"':
				{
				alt8=5;
				}
				break;
			case '\'':
				{
				alt8=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 8, 0, input);
				throw nvae;
			}
			switch (alt8) {
				case 1 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:90:4: 'n'
					{
					match('n'); 
					 setText("\\n"); 
					}
					break;
				case 2 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:91:6: 'r'
					{
					match('r'); 
					 setText("\\r"); 
					}
					break;
				case 3 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:92:6: 't'
					{
					match('t'); 
					 setText("\\t"); 
					}
					break;
				case 4 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:93:6: '\\\\'
					{
					match('\\'); 
					 setText("\\\\"); 
					}
					break;
				case 5 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:94:6: '\"'
					{
					match('\"'); 
					 setText("\\\""); 
					}
					break;
				case 6 :
					// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:95:6: '\\''
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

	@Override
	public void mTokens() throws RecognitionException {
		// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:8: ( CLASS | PROGRAM | INT | BOOLEAN | IF | ELSE | FOR | RETURN | BREAK | CONTINUE | CALLOUT | VOID | TRUE | FALSE | WS_ | SL_COMMENT | CHARLITERAL | STRINGLITERAL | INTLITERAL | LCURLY | RCURLY | LPAREN | RPAREN | LBRACKET | RBRACKET | ADD | SUBTRACT | MULTIPLY | DIVIDE | MODULUS | GT | LT | GTE | LTE | EQ | NEQ | AND | OR | NOT | ASSIGN | ADD_ASSIGN | SUBTRACT_ASSIGN | COMMA | SEMICOLON | IDENTIFIER )
		int alt9=45;
		alt9 = dfa9.predict(input);
		switch (alt9) {
			case 1 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:10: CLASS
				{
				mCLASS(); 

				}
				break;
			case 2 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:16: PROGRAM
				{
				mPROGRAM(); 

				}
				break;
			case 3 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:24: INT
				{
				mINT(); 

				}
				break;
			case 4 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:28: BOOLEAN
				{
				mBOOLEAN(); 

				}
				break;
			case 5 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:36: IF
				{
				mIF(); 

				}
				break;
			case 6 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:39: ELSE
				{
				mELSE(); 

				}
				break;
			case 7 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:44: FOR
				{
				mFOR(); 

				}
				break;
			case 8 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:48: RETURN
				{
				mRETURN(); 

				}
				break;
			case 9 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:55: BREAK
				{
				mBREAK(); 

				}
				break;
			case 10 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:61: CONTINUE
				{
				mCONTINUE(); 

				}
				break;
			case 11 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:70: CALLOUT
				{
				mCALLOUT(); 

				}
				break;
			case 12 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:78: VOID
				{
				mVOID(); 

				}
				break;
			case 13 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:83: TRUE
				{
				mTRUE(); 

				}
				break;
			case 14 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:88: FALSE
				{
				mFALSE(); 

				}
				break;
			case 15 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:94: WS_
				{
				mWS_(); 

				}
				break;
			case 16 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:98: SL_COMMENT
				{
				mSL_COMMENT(); 

				}
				break;
			case 17 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:109: CHARLITERAL
				{
				mCHARLITERAL(); 

				}
				break;
			case 18 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:121: STRINGLITERAL
				{
				mSTRINGLITERAL(); 

				}
				break;
			case 19 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:135: INTLITERAL
				{
				mINTLITERAL(); 

				}
				break;
			case 20 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:146: LCURLY
				{
				mLCURLY(); 

				}
				break;
			case 21 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:153: RCURLY
				{
				mRCURLY(); 

				}
				break;
			case 22 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:160: LPAREN
				{
				mLPAREN(); 

				}
				break;
			case 23 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:167: RPAREN
				{
				mRPAREN(); 

				}
				break;
			case 24 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:174: LBRACKET
				{
				mLBRACKET(); 

				}
				break;
			case 25 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:183: RBRACKET
				{
				mRBRACKET(); 

				}
				break;
			case 26 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:192: ADD
				{
				mADD(); 

				}
				break;
			case 27 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:196: SUBTRACT
				{
				mSUBTRACT(); 

				}
				break;
			case 28 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:205: MULTIPLY
				{
				mMULTIPLY(); 

				}
				break;
			case 29 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:214: DIVIDE
				{
				mDIVIDE(); 

				}
				break;
			case 30 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:221: MODULUS
				{
				mMODULUS(); 

				}
				break;
			case 31 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:229: GT
				{
				mGT(); 

				}
				break;
			case 32 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:232: LT
				{
				mLT(); 

				}
				break;
			case 33 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:235: GTE
				{
				mGTE(); 

				}
				break;
			case 34 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:239: LTE
				{
				mLTE(); 

				}
				break;
			case 35 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:243: EQ
				{
				mEQ(); 

				}
				break;
			case 36 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:246: NEQ
				{
				mNEQ(); 

				}
				break;
			case 37 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:250: AND
				{
				mAND(); 

				}
				break;
			case 38 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:254: OR
				{
				mOR(); 

				}
				break;
			case 39 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:257: NOT
				{
				mNOT(); 

				}
				break;
			case 40 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:261: ASSIGN
				{
				mASSIGN(); 

				}
				break;
			case 41 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:268: ADD_ASSIGN
				{
				mADD_ASSIGN(); 

				}
				break;
			case 42 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:279: SUBTRACT_ASSIGN
				{
				mSUBTRACT_ASSIGN(); 

				}
				break;
			case 43 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:295: COMMA
				{
				mCOMMA(); 

				}
				break;
			case 44 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:301: SEMICOLON
				{
				mSEMICOLON(); 

				}
				break;
			case 45 :
				// /home/steve/smeckl-work/compiler_samples/decaf/src/decaf/DecafScanner.g:1:311: IDENTIFIER
				{
				mIDENTIFIER(); 

				}
				break;

		}
	}


	protected DFA9 dfa9 = new DFA9(this);
	static final String DFA9_eotS =
		"\1\uffff\11\41\1\uffff\1\61\11\uffff\1\63\1\65\2\uffff\1\67\1\71\1\73"+
		"\1\75\5\uffff\5\41\1\103\10\41\16\uffff\4\41\1\120\1\uffff\3\41\1\124"+
		"\10\41\1\uffff\2\41\1\137\1\uffff\2\41\1\142\1\143\1\144\4\41\1\151\1"+
		"\uffff\1\152\1\41\3\uffff\4\41\2\uffff\1\160\1\41\1\162\1\163\1\164\1"+
		"\uffff\1\165\4\uffff";
	static final String DFA9_eofS =
		"\166\uffff";
	static final String DFA9_minS =
		"\1\11\1\141\1\162\1\146\1\157\1\154\1\141\1\145\1\157\1\162\1\uffff\1"+
		"\57\11\uffff\2\75\2\uffff\4\75\5\uffff\1\141\1\156\1\154\1\157\1\164\1"+
		"\60\1\157\1\145\1\163\1\162\1\154\1\164\1\151\1\165\16\uffff\1\163\1\164"+
		"\1\154\1\147\1\60\1\uffff\1\154\1\141\1\145\1\60\1\163\1\165\1\144\1\145"+
		"\1\163\1\151\1\157\1\162\1\uffff\1\145\1\153\1\60\1\uffff\1\145\1\162"+
		"\3\60\1\156\1\165\2\141\1\60\1\uffff\1\60\1\156\3\uffff\1\165\1\164\1"+
		"\155\1\156\2\uffff\1\60\1\145\3\60\1\uffff\1\60\4\uffff";
	static final String DFA9_maxS =
		"\1\175\1\157\1\162\1\156\1\162\1\154\1\157\1\145\1\157\1\162\1\uffff\1"+
		"\57\11\uffff\2\75\2\uffff\4\75\5\uffff\1\141\1\156\1\154\1\157\1\164\1"+
		"\172\1\157\1\145\1\163\1\162\1\154\1\164\1\151\1\165\16\uffff\1\163\1"+
		"\164\1\154\1\147\1\172\1\uffff\1\154\1\141\1\145\1\172\1\163\1\165\1\144"+
		"\1\145\1\163\1\151\1\157\1\162\1\uffff\1\145\1\153\1\172\1\uffff\1\145"+
		"\1\162\3\172\1\156\1\165\2\141\1\172\1\uffff\1\172\1\156\3\uffff\1\165"+
		"\1\164\1\155\1\156\2\uffff\1\172\1\145\3\172\1\uffff\1\172\4\uffff";
	static final String DFA9_acceptS =
		"\12\uffff\1\17\1\uffff\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\2"+
		"\uffff\1\34\1\36\4\uffff\1\45\1\46\1\53\1\54\1\55\16\uffff\1\20\1\35\1"+
		"\51\1\32\1\52\1\33\1\41\1\37\1\42\1\40\1\43\1\50\1\44\1\47\5\uffff\1\5"+
		"\14\uffff\1\3\3\uffff\1\7\12\uffff\1\6\2\uffff\1\14\1\15\1\1\4\uffff\1"+
		"\11\1\16\5\uffff\1\10\1\uffff\1\13\1\2\1\4\1\12";
	static final String DFA9_specialS =
		"\166\uffff}>";
	static final String[] DFA9_transitionS = {
			"\2\12\2\uffff\1\12\22\uffff\1\12\1\34\1\15\2\uffff\1\30\1\35\1\14\1\21"+
			"\1\22\1\27\1\25\1\37\1\26\1\uffff\1\13\12\16\1\uffff\1\40\1\32\1\33\1"+
			"\31\2\uffff\17\41\1\2\12\41\1\23\1\uffff\1\24\1\uffff\1\41\1\uffff\1"+
			"\41\1\4\1\1\1\41\1\5\1\6\2\41\1\3\10\41\1\7\1\41\1\11\1\41\1\10\4\41"+
			"\1\17\1\36\1\20",
			"\1\44\12\uffff\1\42\2\uffff\1\43",
			"\1\45",
			"\1\47\7\uffff\1\46",
			"\1\50\2\uffff\1\51",
			"\1\52",
			"\1\54\15\uffff\1\53",
			"\1\55",
			"\1\56",
			"\1\57",
			"",
			"\1\60",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\62",
			"\1\64",
			"",
			"",
			"\1\66",
			"\1\70",
			"\1\72",
			"\1\74",
			"",
			"",
			"",
			"",
			"",
			"\1\76",
			"\1\77",
			"\1\100",
			"\1\101",
			"\1\102",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\1\104",
			"\1\105",
			"\1\106",
			"\1\107",
			"\1\110",
			"\1\111",
			"\1\112",
			"\1\113",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\114",
			"\1\115",
			"\1\116",
			"\1\117",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"",
			"\1\121",
			"\1\122",
			"\1\123",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\1\125",
			"\1\126",
			"\1\127",
			"\1\130",
			"\1\131",
			"\1\132",
			"\1\133",
			"\1\134",
			"",
			"\1\135",
			"\1\136",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"",
			"\1\140",
			"\1\141",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\1\145",
			"\1\146",
			"\1\147",
			"\1\150",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\1\153",
			"",
			"",
			"",
			"\1\154",
			"\1\155",
			"\1\156",
			"\1\157",
			"",
			"",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\1\161",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"",
			"\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
			"",
			"",
			"",
			""
	};

	static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
	static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
	static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
	static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
	static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
	static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
	static final short[][] DFA9_transition;

	static {
		int numStates = DFA9_transitionS.length;
		DFA9_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
		}
	}

	protected class DFA9 extends DFA {

		public DFA9(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 9;
			this.eot = DFA9_eot;
			this.eof = DFA9_eof;
			this.min = DFA9_min;
			this.max = DFA9_max;
			this.accept = DFA9_accept;
			this.special = DFA9_special;
			this.transition = DFA9_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( CLASS | PROGRAM | INT | BOOLEAN | IF | ELSE | FOR | RETURN | BREAK | CONTINUE | CALLOUT | VOID | TRUE | FALSE | WS_ | SL_COMMENT | CHARLITERAL | STRINGLITERAL | INTLITERAL | LCURLY | RCURLY | LPAREN | RPAREN | LBRACKET | RBRACKET | ADD | SUBTRACT | MULTIPLY | DIVIDE | MODULUS | GT | LT | GTE | LTE | EQ | NEQ | AND | OR | NOT | ASSIGN | ADD_ASSIGN | SUBTRACT_ASSIGN | COMMA | SEMICOLON | IDENTIFIER );";
		}
	}

}
