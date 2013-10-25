%option noyywrap nodefault yylineno

%{
#include "calc.tab.h"
#include "calc.h"

%}

/* Float exponent */
EXP	([Ee][-+]?[0-9]+)

%%

"+" |
"-" |
"*" |
"/" |
"|" |
"=" |
";" |
"," |
"(" |
")" { return yytext[0]; }


">"	{ yylval.fn = 1; return CMP;}
"<"	{ yylval.fn = 2; return CMP;}
"<>"	{ yylval.fn = 3; return CMP;}
"=="	{ yylval.fn = 4; return CMP;}
">="	{ yylval.fn = 5; return CMP;}
"<="	{ yylval.fn = 6; return CMP;}


"if"	{ return IF; }
"then"	{ return THEN; }
"else"	{ return ELSE; }
"while"	{ return WHILE; }
"do"	{ return DO; }
"let"	{ return LET; }


"sqrt"	{ yylval.fn = B_sqrt; return FUNC; }
"exp"	{ yylval.fn = B_exp; return FUNC; }
"log"	{ yylval.fn = B_log; return FUNC; }
"print"	{ yylval.fn = B_print; return FUNC; }


[a-xA-Z][a-zA-z0-9]*	{ yylval.s = lookup(yytext); return NAME; }

[0-9]+"."[0-9]*{EXP}? |
"."?[0-9]+{EXP}?  { yylval.d = atof(yytext); return NUMBER; }

\n	{ return EOL; }

"//".*	{ /* comments */ }

[ \t]	{ /* Ignore whitespace */ }

\\\n	{ printf("c> "); /* ignore line concatenation */ }

.	{ printf("Mystery character %c\n", *yytext); }

%%

