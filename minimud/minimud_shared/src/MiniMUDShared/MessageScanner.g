lexer grammar MessageScanner;

@header
{
  package MiniMUDShared;
}

// Reserved words
TEXTMSG:          'text_msg';
MESSAGE:          'message';
FROM:             'from';
TO:               'to';
PLAYER:           'player';
SERVER:           'server';
REQUEST_INPUT:    'request_input';
TYPE:             'type';
NORMAL_INPUT:     'normal_input';
PASSWD_INPUT:     'passwd_input';
SERVER_STATUS:    'server_status';
LOGON_SUCCESS:    'logon_success';
LOGON_FAILED:     'logon_failed';
INVALID:          'invalid';
QUIT:             'quit';
EXIT:             'exit';
LOGOUT:           'logout';
TELL:             '/tell';
SAY:              '/say';
SHOUT:            '/shout';
WHISPER:          '/whisper';
GO_NORTH:         'north';
GO_SOUTH:         'south';
GO_EAST:          'east';
GO_WEST:          'west';
GO_NORTHEAST:     'northeast';
GO_NORTHWEST:     'northwest';
GO_SOUTHEAST:     'southeast';
GO_SOUTHWEST:     'southwest';
GO_UP:            'up';
GO_DOWN:          'down';

WS_ : (' ' | '\n' | '\t' | '\r') { skip(); };

PRINTABLE_CHARS: (' ' | '!' | ('#' .. '&') | ('(' .. '/') | (':' .. '<') | ('>' .. '@') | '[' | (']' .. '`') | DIGIT | CHAR);

CHARLITERAL : '\'' ( ESCAPE | PRINTABLE_CHARS ) '\'';

STRINGLITERAL : '"'
        ( ESCAPE
        | PRINTABLE_CHARS
        )*
        ('"');

CHARNAME: (CHAR)(DIGIT|CHAR)+;

fragment DIGIT: ('0' .. '9');

fragment DECIMAL_LITERAL: (DIGIT)+;

fragment HEX_DIGIT: (DIGIT | 'a' .. 'f' | 'A' .. 'F');

fragment HEX_LITERAL: '0' ('x' | 'X') (HEX_DIGIT)+;

fragment CHAR: ('a'..'z' | 'A'..'Z');

fragment
ESCAPE :  '\\' 
  ( 'n' { setText("\\n"); }
    | 'r' { setText("\\r"); }
    | 't' { setText("\\t"); }
    | '\\' { setText("\\\\"); }
    | '"' { setText("\\\""); }
    | '\'' { setText("\\\'"); }
  );
  
  EQUALS: '=';