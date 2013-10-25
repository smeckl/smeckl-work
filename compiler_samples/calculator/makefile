calc:	calc.flex calc.y calc.h calc_lib.c
	bison -d calc.y
	flex -ocalc.flex.c calc.flex
	gcc -o $@ calc_lib.c calc.tab.c calc.flex.c -lfl

clean:
	rm calc 
	rm calc.tab.c 
	rm calc.tab.h
	rm calc.flex.c
