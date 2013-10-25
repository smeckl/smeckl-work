#!/bin/bash

export CLASSPATH=/home/steve/codemunki-work/compiler_samples/decaf/bin:/home/steve/eclipse/plugins/org.antlr_2.7.6/antlr.jar

java decaf.Main scan char1 > myoutput/char1.out
java decaf.Main scan char2 > myoutput/char2.out
java decaf.Main scan char3 > myoutput/char3.out
java decaf.Main scan char4 > myoutput/char4.out
java decaf.Main scan char5 > myoutput/char5.out
java decaf.Main scan char6 > myoutput/char6.out
java decaf.Main scan char7 > myoutput/char7.out
java decaf.Main scan char8 > myoutput/char8.out
java decaf.Main scan char9 > myoutput/char9.out

java decaf.Main scan hexlit1 > myoutput/hexlit1.out
java decaf.Main scan hexlit2 > myoutput/hexlit2.out
java decaf.Main scan hexlit3 > myoutput/hexlit3.out

java decaf.Main scan id1 > myoutput/id1.out
java decaf.Main scan id2 > myoutput/id2.out
java decaf.Main scan id3 > myoutput/id3.out

java decaf.Main scan number1 > myoutput/number1.out
java decaf.Main scan number2 > myoutput/number2.out

java decaf.Main scan op1 > myoutput/op1.out
java decaf.Main scan op2 > myoutput/op2.out

java decaf.Main scan string1 > myoutput/string1.out
java decaf.Main scan string2 > myoutput/string2.out
java decaf.Main scan string3 > myoutput/string3.out

java decaf.Main scan tokens1 > myoutput/tokens1.out
java decaf.Main scan tokens2 > myoutput/tokens2.out
java decaf.Main scan tokens3 > myoutput/tokens3.out
java decaf.Main scan tokens4 > myoutput/tokens4.out

java decaf.Main scan ws1 > myoutput/ws1.out
