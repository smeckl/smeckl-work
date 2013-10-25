
%%
%class scanner 
%unicode
%line
%column
%byaccj

%{
/* store a reference to the parser object */
	private Parser yyparser;

	/* constructor taking an additional parser */
	public scanner (java.io.Reader r, Parser yyparser) {
		this (r);	
		this.yyparser = yyparser;
	}

	/* return the current line number. We need this
	   because yyline is made private and we don't have
	   a mechanism like extern in C. */
	public int getLine() {
		return yyline;
	}
%}

%%
addi		{yyparser.yylval = new ParserVal(yytext()); return Parser.ADDI_T;}
add		{yyparser.yylval = new ParserVal(yytext()); return Parser.ADD_T;}
and		{yyparser.yylval = new ParserVal(yytext()); return Parser.AND_T;}
b		{yyparser.yylval = new ParserVal(yytext()); return Parser.BRANCH_T;}
beqz		{yyparser.yylval = new ParserVal(yytext()); return Parser.CBRANCH1_T;}
bnez		{yyparser.yylval = new ParserVal(yytext()); return Parser.CBRANCH2_T;}
div		{yyparser.yylval = new ParserVal(yytext()); return Parser.DIV_T;}
la		{yyparser.yylval = new ParserVal(yytext()); return Parser.LA_T;}
li		{yyparser.yylval = new ParserVal(yytext()); return Parser.LI_T;}
lw		{yyparser.yylval = new ParserVal(yytext()); return Parser.LW_T;}
move		{yyparser.yylval = new ParserVal(yytext()); return Parser.MOVE_T;}
mul		{yyparser.yylval = new ParserVal(yytext()); return Parser.MUL_T;}
neg		{yyparser.yylval = new ParserVal(yytext()); return Parser.NEG_T;}
or		{yyparser.yylval = new ParserVal(yytext()); return Parser.OR_T;}
seq		{yyparser.yylval = new ParserVal(yytext()); return Parser.COMP5_T;}
sge		{yyparser.yylval = new ParserVal(yytext()); return Parser.COMP2_T;}
sgt		{yyparser.yylval = new ParserVal(yytext()); return Parser.COMP1_T;}
sle		{yyparser.yylval = new ParserVal(yytext()); return Parser.COMP4_T;}
slt		{yyparser.yylval = new ParserVal(yytext()); return Parser.COMP3_T;}
sne		{yyparser.yylval = new ParserVal(yytext()); return Parser.COMP6_T;}
sub		{yyparser.yylval = new ParserVal(yytext()); return Parser.SUB_T;}
subi		{yyparser.yylval = new ParserVal(yytext()); return Parser.SUBI_T;}
sw		{yyparser.yylval = new ParserVal(yytext()); return Parser.SW_T;}
syscall		{yyparser.yylval = new ParserVal(yytext()); return Parser.SYSCALL_T;}
".data"		{yyparser.yylval = new ParserVal(yytext()); return Parser.DATA_START;}
".text"		{yyparser.yylval = new ParserVal(yytext()); return Parser.TEXT_START;}
".word"		{yyparser.yylval = new ParserVal(yytext()); return Parser.IS_WORD;}
".asciiz"	{yyparser.yylval = new ParserVal(yytext()); return Parser.IS_STRING;}
".space"	{yyparser.yylval = new ParserVal(yytext()); return Parser.IS_SPACE;}
\,		{return (int)yycharat(0);}
\:		{return (int)yycharat(0);}
\(		{return (int)yycharat(0);}
\)		{return (int)yycharat(0);}
\#.*		{;}
\"[^\"]*\"	{yyparser.yylval = new ParserVal(yytext()); return Parser.STRING;}
"$a"[0-9]	{yyparser.yylval = new ParserVal(yytext()); return Parser.AREG;}
"$v"[0-9]	{yyparser.yylval = new ParserVal(yytext()); return Parser.VREG;}
"$t"[0-9]	{yyparser.yylval = new ParserVal(yytext()); return Parser.TREG;}
[a-zA-Z][a-zA-Z0-9_]*	{yyparser.yylval = new ParserVal(yytext()); return Parser.IDENT;}
[0-9]*		{yyparser.yylval = new ParserVal(yytext()); return Parser.INT;}
[\n]		{;}
[ \t]		{;}
