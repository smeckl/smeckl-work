dragon:	dragon.tab.c lex.yy.c
	gcc -o dragon dragon.tab.c lex.yy.c -ly -ll

dragon.tab.c:	dragon.y
	bison -vd dragon.y

lex.yy.c:	dragon.l
	flex dragon.l
