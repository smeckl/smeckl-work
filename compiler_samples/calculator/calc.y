%{
#include <stdio.h>
#include <stdlib.h>
#include "calc.h"
%}

%union
{
	struct ast *a;
	double d;
}

/* declare tokens */
%token <d> NUMBER 
%token EXP
%token ADD SUB MUL DIV ABS LBR RBR
%token EOL

%type <a> exp factor term

%%

calclist: /* nothing */
	| calclist exp EOL 
		{ 
			printf("= %4.4g\n", eval($2)); 
			treefree($2);
			printf("> ");
		}
	| calclist EOL
		{
			printf("> ");
		}
;

exp: factor
	| exp ADD factor 
		{ 
			$$ = newast('+', $1, $3); 
		}
	| exp SUB factor 
		{ 
			$$ = newast('-', $1, $3); 
		}
;

factor: term
	| factor MUL term 
		{ 
			$$ = newast('*', $1, $3); 
		}
	| factor DIV term
		{ 
			$$ = newast('/', $1, $3); 
		}
;

term: NUMBER
		{
			$$ = newnum($1);
		}
	| ABS term 
		{ 
			$$ = newast('|', $2, NULL); 
		}
	| LBR exp RBR
		{ 
			$$ = $2; 
		}
	| SUB term
		{ 
			$$ = newast('M', $2, NULL); 
		}
;

%%




