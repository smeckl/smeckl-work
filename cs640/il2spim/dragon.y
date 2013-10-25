%{
#include <stdio.h>
#include <stdlib.h>
extern int lineno;
%}
%token INT IF GOTO PUT GET
%token <sval> ID LABEL
%token <ival> INT_LITERAL
%token LT LE GT GE EQ NE
%type <ival> simple_op
%union{
  int ival;
  char *sval;
}

%%
program	:	{printf("\t.data\n"); } decls {printf("\t.text\nmain:\n"); } code 
		{printf("\tli $v0,10\n\tsyscall\n"); }
	;
decls	:	decls decl
	|
	;
decl	:	INT name_list
	;
name_list:	name_list ',' ID
		{printf("%s_:\t.word\t0\n",$3);}
	|	name_list  ',' ID '[' INT_LITERAL ']'
		{printf("%s_:\t.space\t%d\n",$3,$5*4);}
	|	ID
		{printf("%s_:\t.word\t0\n",$1);}
	|	ID '[' INT_LITERAL ']'
		{printf("%s_:\t.space\t%d\n",$1,$3*4);}
	;
code	:	code opt_labels stmt 
	|	opt_labels stmt	
	;
opt_labels:	opt_labels LABEL
		{printf("%s\n",$2);}
	|	
	;
stmt	:	assignment
	|	branch
	|	PUT simple_op
		{printf("\tli $v0, 1\n\tmove $a0,$t%d\n\tsyscall\n",$2); free_reg($2); }
	|	PUT ID '[' simple_op ']'
	{int r; r = get_reg();
	 printf("\tla $t%d,%s_\n",r,$2); 
	 printf("\tmul $t%d,$t%d,4\n",$4,$4); 
	 printf("\tadd $t%d,$t%d,$t%d\n",r,r,$4); 
	 printf("\tlw $a0,($t%d)\n",r);free_reg(r);free_reg($4);
	 printf("\tli $v0,1\n\tsyscall\n");
	}
	|	ID '=' GET
		{printf("\tli $v0,5\n\tsyscall\n\tsw $v0,%s_\n",$1); }
	;

branch	:	GOTO ID
		{printf("\tb %s\n",$2); }
	|	IF simple_op LT simple_op GOTO ID
		{printf("\tslt $t%d,$t%d,$t%d\n",$2,$2,$4);
   		 printf("\tbnez $t%d,%s\n",$2,$6); free_reg($2); free_reg($4);
		}
	|	IF simple_op LE simple_op GOTO ID
		{printf("\tsle $t%d,$t%d,$t%d\n",$2,$2,$4);
   		 printf("\tbnez $t%d,%s\n",$2,$6); free_reg($2); free_reg($4);
		}
	|	IF simple_op GT simple_op GOTO ID
		{printf("\tsgt $t%d,$t%d,$t%d\n",$2,$2,$4);
   		 printf("\tbnez $t%d,%s\n",$2,$6); free_reg($2); free_reg($4);
		}
	|	IF simple_op GE simple_op GOTO ID
		{printf("\tsge $t%d,$t%d,$t%d\n",$2,$2,$4);
   		 printf("\tbnez $t%d,%s\n",$2,$6); free_reg($2); free_reg($4);
		}
	|	IF simple_op NE simple_op GOTO ID
		{printf("\tsne $t%d,$t%d,$t%d\n",$2,$2,$4);
   		 printf("\tbnez $t%d,%s\n",$2,$6); free_reg($2); free_reg($4);
		}
	|	IF simple_op EQ simple_op GOTO ID
		{printf("\tseq $t%d,$t%d,$t%d\n",$2,$2,$4);
   		 printf("\tbnez $t%d,%s\n",$2,$6); free_reg($2); free_reg($4);
		}
	;

assignment: 	ID '=' simple_op	
	{printf("\tsw $t%d,%s_\n",$3,$1);free_reg($3);}
	|	ID '=' simple_op '+' simple_op
	{printf("\tadd $t%d,$t%d,$t%d\n",$3,$3,$5);free_reg($5); 
	 printf("\tsw $t%d,%s_\n",$3,$1);free_reg($3);}
	|	ID '=' simple_op '-' simple_op
	{printf("\tsub $t%d,$t%d,$t%d\n",$3,$3,$5);free_reg($5); 
	 printf("\tsw $t%d,%s_\n",$3,$1);free_reg($3);}
	|	ID '=' simple_op '*' simple_op
	{printf("\tmul $t%d,$t%d,$t%d\n",$3,$3,$5);free_reg($5); 
	 printf("\tsw $t%d,%s_\n",$3,$1);free_reg($3);}
	|	ID '=' simple_op '/' simple_op
	{printf("\tdiv $t%d,$t%d,$t%d\n",$3,$3,$5);free_reg($5); 
	 printf("\tsw $t%d,%s_\n",$3,$1);free_reg($3);}
	|	ID '=' ID '[' simple_op ']'
	{int r; r = get_reg();
	 printf("\tla $t%d,%s_\n",r,$3); 
	 printf("\tmul $t%d,$t%d,4\n",$5,$5); 
	 printf("\tadd $t%d,$t%d,$t%d\n",r,r,$5); 
	 printf("\tlw $t%d,($t%d)\n",$5,r);free_reg(r);free_reg($5);
	 printf("\tsw $t%d,%s_\n",$5,$1);
	}
	|	ID '[' simple_op ']' '=' simple_op
	{int r; r = get_reg();
	 printf("\tla $t%d,%s_\n",r,$1); 
	 printf("\tmul $t%d,$t%d,4\n",$3,$3); 
	 printf("\tadd $t%d,$t%d,$t%d\n",r,r,$3); 
	 printf("\tsw $t%d,($t%d)\n",$6,r); free_reg($3);free_reg($6);
         free_reg(r);
	}
	;

simple_op	
	:	ID	
	{$$=get_reg(); printf("\tlw $t%d,%s_\n",$$,$1);}
	|	INT_LITERAL
	{$$=get_reg(); printf("\tli $t%d,%d\n",$$,$1);}
	;
%%

yyerror() {
   printf("Syntax error line %d\n",lineno);
}

int reg[8] = {0,0,0,0,0,0,0,0};

int get_reg() {
  int i;
  for (i=0;i<8;i++)
    if (reg[i] == 0) {reg[i]=1; return i;}
  return 0;
}
free_reg(int i) {
  reg[i] = 0;
}



