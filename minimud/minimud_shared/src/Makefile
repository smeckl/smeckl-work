genparser: 
	java -cp ./JFlex.jar JFlex.Main ./minimud_shared/MessageScanner.jflex
	./yacc.linux -Jpackage=minimud_shared -Jclass=MessageParser ./minimud_shared/MessageParser.y
	mv MessageParser*.java ./minimud_shared/
	javac ./minimud_shared/MessageParserVal.java
	javac ./minimud_shared/MessageParser.java 
	javac ./minimud_shared/MessageScanner.java
