%option noyywrap nodefault yylineno

%{
#include "calc.tab.h"
#include "calc.h"

%}

EXP	([Ee][-+]?[0-9]+)

%%

"+"	{ return ADD; }
"-"	{ return SUB; }
"*"	{ return MUL; }
"/"	{ return DIV; }
"|"	{ return ABS; }
"("	{ return LBR; }
")"	{ return RBR; }
[0-9]+"."[0-9]*{EXP}? |
"."?[0-9]+{EXP}?  { yylval.d = atof(yytext); return NUMBER; }
\n	{ return EOL; }
[ \t]	{ /* Ignore whitespace */ }
.	{ printf("Mystery character %c\n", *yytext); }

%%

