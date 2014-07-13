lexer grammar DecafScanner;

@header {package decaf;}

// Reserved words
CLASS:      'class';
PROGRAM:    'Program';
INT:        'int';
BOOLEAN:    'boolean';
IF:         'if';
ELSE:       'else';
FOR:        'for';
RETURN:     'return';
BREAK:      'break';
CONTINUE:   'continue';
CALLOUT:    'callout';
VOID:       'void';
TRUE:       'true';
FALSE:      'false';

WS_ : (' ' | '\n' | '\t' | '\r') { skip(); };

SL_COMMENT : '//' (~'\n')* '\n' { skip(); };

// Need to be inclusive with char literal.  Trying to exclude specific characters
// did not generate errors in the same way as the sample output
fragment PRINTABLE_CHARS: (' ' | '!' | ('#' .. '&') | ('(' .. '/') | (':' .. '@') | '[' | (']' .. '`') | DIGIT | CHAR);

CHARLITERAL : '\'' ( ESCAPE | PRINTABLE_CHARS ) '\'';

STRINGLITERAL : '"'
				( ESCAPE
				| PRINTABLE_CHARS
				)*
				('"');

fragment DIGIT: ('0' .. '9');

fragment DECIMAL_LITERAL: (DIGIT)+;

fragment HEX_DIGIT: (DIGIT | 'a' .. 'f' | 'A' .. 'F');

fragment HEX_LITERAL: '0' ('x' | 'X') (HEX_DIGIT)+;

fragment CHAR: ('a'..'z' | 'A'..'Z');

INTLITERAL: DECIMAL_LITERAL | HEX_LITERAL;

LCURLY: '{';
RCURLY: '}';

LPAREN: '(';
RPAREN: ')';

LBRACKET: '[';
RBRACKET: ']';

ADD: '+';
SUBTRACT: '-';
MULTIPLY: '*';
DIVIDE: '/';
MODULUS: '%';

GT: '>';
LT: '<';
GTE: '>=';
LTE: '<=';

EQ: '==';
NEQ: '!=';

AND: '&&';
OR: '||';

NOT: '!';

ASSIGN: '=';
ADD_ASSIGN: '+=';
SUBTRACT_ASSIGN: '-=';

COMMA: ',';

SEMICOLON: ';';

IDENTIFIER: 
  (CHAR | '_') ( CHAR | '_' | '0' .. '9')*;

fragment
ESCAPE :  '\\' 
	(	'n' { setText("\\n"); }
	  | 'r' { setText("\\r"); }
	  | 't' { setText("\\t"); }
	  | '\\' { setText("\\\\"); }
	  | '"' { setText("\\\""); }
	  | '\'' { setText("\\\'"); }
	);
