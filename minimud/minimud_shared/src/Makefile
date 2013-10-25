genparser: 
	java -cp ./JFlex.jar JFlex.Main ./MiniMUDShared/MessageScanner.jflex
	./yacc.linux -Jpackage=MiniMUDShared -Jclass=MessageParser ./MiniMUDShared/MessageParser.y
	mv MessageParser*.java ./MiniMUDShared/
	javac ./MiniMUDShared/MessageParserVal.java
	javac ./MiniMUDShared/MessageParser.java 
	javac ./MiniMUDShared/MessageScanner.java
