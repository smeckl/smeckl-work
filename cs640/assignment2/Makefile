assignment1: genparser javabuild

genparser: spim.jflex spim.by
	java -cp ./JFlex.jar JFlex.Main spim.jflex
	./yacc.linux -Jclass=Parser spim.by

javabuild: ParserVal.java Parser.java scanner.java Instruction.java LineList.java Argument.java Line.java CodeAnalyzer.java BasicBlock.java Variable.java Symbol.java
	javac Variable.java
	javac Symbol.java
	javac PhiFunction.java
	javac Line.java
	javac Argument.java
	javac Instruction.java
	javac LineList.java
	javac BasicBlock.java
	javac CodeAnalyzer.java
	javac ParserVal.java
	javac Parser.java
	javac scanner.java

clean:
	rm ParserVal.*
	rm Parser.*
	rm scanner.*
	rm *.class
	rm *~
