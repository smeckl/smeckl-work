%{
#include <stdio.h>
#include <stdlib.h>
#include "dragon.tab.h"
int lineno=1;
%}
%%
get		{return(GET);}
goto		{return(GOTO);}
if		{return(IF);}
int		{return(INT);}
put		{return(PUT);}
\+		{return('+');}
\-		{return('-');}
\*		{return('*');}
\/		{return('/');}
\,		{return(',');}
\:\=		{return('=');}
\<\>		{return(NE);}
\>		{return(GT);}
\>\=		{return(GE);}
\=		{return(EQ);}
\<		{return(LT);}
\<\=		{return(LE);}
\[		{return('[');}
\]		{return(']');}
[a-zA-Z][a-zA-Z0-9_]*\:	{yylval.sval=(char*)strdup(yytext); return(LABEL);}
[a-zA-Z][a-zA-Z0-9_]*	{yylval.sval=(char*)strdup(yytext); return(ID);}
[0-9]*		{yylval.ival=atoi(yytext); return(INT_LITERAL);}
[ \t]		;
[\n]		lineno++;
%%
