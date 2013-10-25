calc:	calc.l calc.y calc.h calc_lib.c
	bison -d calc.y
	flex --outfile=calc.l.c calc.l
	gcc -o calc calc_lib.c calc.tab.c calc.l.c -lfl -lm -g

clean:
	rm calc 
	rm calc.tab.c 
	rm calc.tab.h
	rm calc.l.c
