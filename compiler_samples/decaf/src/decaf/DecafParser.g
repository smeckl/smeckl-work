parser grammar DecafParser;

options
{
  tokenVocab=DecafScanner;
}

@header 
{
  package decaf;
  
  import org.antlr.runtime.*;
  import org.antlr.runtime.tree.*;
}

@members
{
    private SymbolTable m_symbolTable = new SymbolTable();
    
    private boolean m_bValidMain = false;
    
    private Method m_curMethod = null;
    
    private boolean m_bInForLoop = false;
    
    private void setValidMain(boolean bValid)
    {
        m_bValidMain = true;
    }
    
    private boolean getValidMain()
    {
        return m_bValidMain;
    }
    
    private void setInForLoop(boolean bInFor)
    {
        m_bInForLoop = bInFor;
    }
    
    private boolean getInForLoop()
    {
        return m_bInForLoop;
    }
    
    private static Expression.Type stringToType(String strType)
    {
        Expression.Type type;
        
        if(0 == strType.compareTo("boolean"))
	          type = Expression.Type.TYPE_BOOL;
	      else if(0 == strType.compareTo("int"))
	          type = Expression.Type.TYPE_INT;
	      else if(0 == strType.compareTo("void"))
            type = Expression.Type.TYPE_VOID;
	      else
	          type = Expression.Type.TYPE_INVALID;
	          
	      return type;
    }
}

program options{backtrack=true;}: CLASS PROGRAM LCURLY (field_decl)* (method_decl)* RCURLY EOF
    {
        if(!getValidMain())
        {
            System.out.println($RCURLY.line + ":" + $RCURLY.pos + "  Method \"main\" not declared.");
        }
    }
  ;

block options{backtrack=true;}: LCURLY { m_symbolTable.startScope(); }(field_decl)* (statement)* RCURLY { m_symbolTable.endScope(); };

field_decl
@init {
    Expression.Type fieldType = Expression.Type.TYPE_INVALID;
    
    Token ahead = input.LT(1);
    int line = ahead.getLine();
    int pos = ahead.getCharPositionInLine();
}
: type
  {
      fieldType = stringToType($type.text);
  }
  b=field
    {     
	      if(null != m_symbolTable.lookupSymbol(b.getName(), true)) 
	      {
	          System.out.println(line + ":" + pos + " Variable " + b.getName() + " already defined.");
	      }
	      else
	      {
	          b.setType(fieldType);
	          m_symbolTable.addSymbol(b);
	      }
    }
  (COMMA c=field
    {
        if(null != m_symbolTable.lookupSymbol(c.getName(), true)) 
        {
            System.out.println(line + ":" + pos + " Variable " + c.getName() + " already defined.");
        }
        else
        {
            c.setType(fieldType);
            m_symbolTable.addSymbol(c);
        }
    })* SEMICOLON;

field returns [Symbol sym]
  : (IDENTIFIER LBRACKET) => IDENTIFIER LBRACKET INTLITERAL RBRACKET
      {        
          sym = new Symbol($IDENTIFIER.text, Expression.Type.TYPE_INVALID);
          
          if(Integer.parseInt($INTLITERAL.text) <= 0)
          {
              System.out.println($LBRACKET.line + ":" + $LBRACKET.pos + " Array size must be greater than zero.");
          }
          
          sym.setIsArray(true);
          sym.setArraySize(Integer.parseInt($INTLITERAL.text));
      }
  | IDENTIFIER
      {
          sym = new Symbol($IDENTIFIER.text, Expression.Type.TYPE_INVALID);
      }
  ;

method_decl
@init
{
    Method method = null;
    String strType = "";
    String strID = "";
    int line = 0;
    int pos = 0;
}
  : (type {strType = $type.text;}| VOID {strType = $VOID.text;}) IDENTIFIER LPAREN 
    {
        strID = $IDENTIFIER.text;
        line = $IDENTIFIER.line;
        pos = $IDENTIFIER.pos;
        
        method = (Method)m_symbolTable.addMethod(strID, stringToType(strType));    
        
        m_symbolTable.startScope();
        
        if(0 == strID.compareTo("main"))
        {
            if(0 != strType.compareTo("void"))
		        {
		            System.out.println(line + ":" + pos + "  Method \"main\" must have type void.");
		        }
		        
		        setValidMain(true);
		    }
    }
  (parm1=parameter_def 
    {
        if(0 == strID.compareTo("main"))
        {
            System.out.println(line + ":" + pos + "  Method \"main\" cannot have parameters.");
            
            setValidMain(false);
        }
        else
        {
		        method.addParameter(parm1);
		        m_symbolTable.addSymbol(parm1.getName(), parm1.getType());
		    }
    }
  (COMMA parm2=parameter_def
    {
        if(method.paramExists(parm2.getName()))
        {
            System.out.println($COMMA.line + ":" + $COMMA.pos + " Parameter " + parm2.getName() + " already defined.");
        }
        else
        {
            method.addParameter(parm2);
            m_symbolTable.addSymbol(parm2.getName(), parm2.getType());
        }
    }
  )*)? RPAREN
  {
        m_curMethod = method;
  }
  block
    {
        m_curMethod = null;
    }
    ;

parameter_def returns [Symbol sym]
  : type b=IDENTIFIER
		{
		    Expression.Type type = stringToType($type.text);
		        
		    sym = new Symbol($b.text, type);
		}
	;

type: INT | BOOLEAN;

statement
  : loc=location assign=(ASSIGN | ADD_ASSIGN | SUBTRACT_ASSIGN) ex=expr SEMICOLON  // Assignment
      {
          if(0 == $assign.text.compareTo("="))
          {
              if(loc.getType() != ex.getType())
                  System.out.println($assign.line + ":" + $assign.pos + " Type mismatch.  Assignment of invalid type.");
          }
          else
          { 
              if(loc.getType() != Expression.Type.TYPE_INT
                  || ex.getType() != Expression.Type.TYPE_INT)
              {
                  System.out.println($assign.line + ":" + $assign.pos + " Invalid assignment.  Right and left of operand must be of type int.");
              }
          }
      }
  | method_call SEMICOLON
      {
          // Emit code for method call
      }
  | IF LPAREN ex=expr RPAREN 
      {
          if(Expression.Type.TYPE_BOOL != ex.getType())
              System.out.println($LPAREN.line + ":" + $LPAREN.pos + " Invalid type.  Condition in \"if\" statement must be of type boolean.");
      }
    block (ELSE block)?
  | FOR 
      {
         m_symbolTable.startScope();
      }
    IDENTIFIER
      {
          m_symbolTable.addSymbol($IDENTIFIER.text, Expression.Type.TYPE_INT);
      }
    ASSIGN exStart=expr COMMA exEnd=expr
	    {
	        if(Expression.Type.TYPE_INT != exStart.getType())
	        {
	            System.out.println($ASSIGN.line + ":" + $ASSIGN.pos + " Start of range in for statement must be of type int.");
	        }
	        
	        if(Expression.Type.TYPE_INT != exEnd.getType())
          {
              System.out.println($COMMA.line + ":" + $COMMA.pos + " End of range in for statement must be of type int.");
          }
          
          setInForLoop(true);
	    }
    block
      {
          setInForLoop(false);
          m_symbolTable.endScope();
      }
  | RETURN ex=expr SEMICOLON
      {
          Token ahead = input.LT(1);
          int line = ahead.getLine();
          int pos = ahead.getCharPositionInLine();
          
          if(null == m_curMethod)
          {
              System.out.println(line + ":" + pos + "  Return statement must be inside of a method.");
          }
          else if(Expression.Type.TYPE_VOID == m_curMethod.getType())
          {
              System.out.println(line + ":" + pos + "  Return statement invalid inside of method with void return type.");
          }
          else if(ex.getType() != m_curMethod.getType())
          {
              System.out.println(line + ":" + pos + "  Return value type different from method type.");
          }
      }
  | BREAK SEMICOLON
      {
          if(!getInForLoop())
              System.out.println($SEMICOLON.line + ":" + $SEMICOLON.pos + " Break statement must be in the context of a for loop."); 
      }
  | CONTINUE SEMICOLON
      {
          if(!getInForLoop())
              System.out.println($SEMICOLON.line + ":" + $SEMICOLON.pos + " Continue statement must be in the context of a for loop."); 
      }
  | block;

method_call returns [MethodCall callMethod]
@init
{
    Method refMethod = null;
    int nParamIndex = 0;
}
  : IDENTIFIER LPAREN
    {
        refMethod = m_symbolTable.lookupMethod($IDENTIFIER.text);
        
        if(null == refMethod)
        {
            System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Invalid method call.  Undefined method identifier.");
        }
        else
        {
            callMethod = new MethodCall($IDENTIFIER.text, refMethod.getType());
        }
    }
    (a=expr 
        {
            Symbol symParam = refMethod.getParameterAt(nParamIndex);
            
            if(null == symParam)
            {
                System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Invalid number of parameters in method call.");
            }
            else if(symParam.getType() != a.getType())
            {
                System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Invalid method call.  Wrong type for parameter " + nParamIndex + ".");
            }
            else
            {
                callMethod.addParameter(a);
            }
            
            nParamIndex++;
        }
    (COMMA b=expr
        {
            Symbol symParam = refMethod.getParameterAt(nParamIndex);
            
            if(null == symParam)
            {
                System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Invalid number of parameters in method call.");
            }
            else if(symParam.getType() != b.getType())
            {
                System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Invalid method call.  Wrong type for parameter " + nParamIndex + ".");
            }
            else
            {
                callMethod.addParameter(b);
            }
            
            nParamIndex++;
        }
    )*)? RPAREN
  | 
  CALLOUT LPAREN str=literal
    {
        if(Literal.Type.TYPE_STRING != str.getType())
            System.out.println($LPAREN.line + ":" + $LPAREN.pos + " First parameter to callout() must be a string.");
            
        callMethod = new MethodCall(str.getValue(), Expression.Type.TYPE_INT);
    }
  (COMMA callout_arg)* RPAREN;


location returns [Symbol sym]
  : (IDENTIFIER LBRACKET) => id1=IDENTIFIER LBRACKET ex=expr RBRACKET
    {
        sym = m_symbolTable.lookupSymbol($id1.text, false);
        
        if(null == sym)
        {
            System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Undefined symbol " + $IDENTIFIER.text);
            
            sym = new Symbol($id1.text, Expression.Type.TYPE_INVALID);
        }
        
        if(!sym.getIsArray())
            System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Identifier " + $id1.text + " is not an array.");
         
        if(Expression.Type.TYPE_INT != ex.getType())
            System.out.println($LBRACKET.line + ":" + $LBRACKET.pos + " Array index must be of type int.");
    }
  | id2=IDENTIFIER
	  {
	      sym = m_symbolTable.lookupSymbol($id2.text, false);
	      
	      if(null == sym)
	      {
	          System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Undefined symbol " + $id2.text);
	          
	          sym = new Symbol($IDENTIFIER.text, Expression.Type.TYPE_INVALID);
	      }
	      else if(sym.getIsArray())
	      {
	          System.out.println($IDENTIFIER.line + ":" + $IDENTIFIER.pos + " Invalid use of array type identifier.");	          
	      }
	  }
	  ;

expr returns [Expression expr]
  :  a=andExpr (OR b=andExpr)*
    {
        if(null == b)
        {
            expr = a;
        }
        else
        {
            if(Expression.Type.TYPE_BOOL != a.getType()
              || Expression.Type.TYPE_BOOL != b.getType())
            {
                System.out.println($OR.line + ":" + $OR.pos + " Type mismatch.");
            }

            expr = new ConditionalExpression(ConditionalExpression.Operator.OP_OR, a, b);
        }
    }
  ; 

andExpr returns [Expression expr]
  : a=eqExpr (AND b=eqExpr)*
    {
        if(null == b)
        {
            expr = a;
        }
        else
        {
            if(Expression.Type.TYPE_BOOL != a.getType()
              || Expression.Type.TYPE_BOOL != b.getType())
            {
                System.out.println($AND.line + ":" + $AND.pos +" Type mismatch.");
            }
            
            expr = new ConditionalExpression(ConditionalExpression.Operator.OP_AND, a, b);
        }
    }
  ;

eqExpr returns [Expression expr]
  : a=compExpr (eq=(EQ | NEQ) b=compExpr)*
    {
        if(null == b)
        {
            expr = a;
        }
        else
        {
            if(a.getType() != b.getType())
            {
                System.out.println($eq.line + ":" + $eq.pos +" Type mismatch.  Operands must have the same type.");
            }

            expr = new EquivalenceExpression(EquivalenceExpression.parseOperator($eq.text), a, b);
        }
    }
  ;

compExpr returns [Expression expr]
  : a=addExpr (rel=(GT | LT | GTE | LTE) b=addExpr)*
    {
        if(null == b)
        {
            expr = a;
        }
        else
        {
            if(Expression.Type.TYPE_INT != a.getType()
              || Expression.Type.TYPE_INT != b.getType())
            {
                System.out.println($rel.line + ":" + $rel.pos + "  Type mismatch.  Operands must be type int.");
            }  
                     
            expr = new RelationalExpression(RelationalExpression.parseOperator($rel.text), a, b);
        }
    }
  ;

addExpr returns [Expression expr]
  : a=multExpr (add=(ADD | SUBTRACT) b=multExpr)*
    {
        if(null == b)
        {
            expr = a;
        }
        else
        {
            if(Expression.Type.TYPE_INT != a.getType()
              || Expression.Type.TYPE_INT != b.getType())
            {
                System.out.println($add.line + ":" + $add.pos + " Type mismatch.  Operands must be type int.");
            }

            expr = new MathExpression(MathExpression.parseOperator($add.text), a, b);
        }
    }
  ;

multExpr returns [Expression expr]
  : a=signedExpr (mul=(MULTIPLY | DIVIDE | MODULUS) b=signedExpr)*
    {
        if(null == b)
        {
            expr = a;
        }
        else
        {
            if(Expression.Type.TYPE_INT != a.getType()
              || Expression.Type.TYPE_INT != b.getType())
            {
                System.out.println($mul.line + ":" + $mul.pos + " Type mismatch.  Operands must be type int.");
            }

            expr = new MathExpression(MathExpression.parseOperator($mul.text), a, b);
        }
    }
  ;

signedExpr returns [Expression expr]
  : SUBTRACT a=notExpr
    {
        if(Expression.Type.TYPE_INT != a.getType())
        {
            System.out.println($SUBTRACT.line + ":" + $SUBTRACT.pos + " Invalid type for unary minus operator.  Operand must be type int.");
        }
        else
        {
		        Literal lit = new Literal(Literal.Type.TYPE_INT, "-1");
		        
		        expr = new MathExpression(MathExpression.Operator.OP_MULTIPLY, a, new LiteralExpression(lit));
		    }
    }
  | b=notExpr
	  {
	      expr = b;
	  }
  ;

notExpr returns [Expression expr]
  options {backtrack=true;}
  : NOT a=atomExpr
  {
      if(Expression.Type.TYPE_BOOL != a.getType())
        {
            System.out.println($NOT.line + ":" + $NOT.pos + " Invalid type for logical negation operator.  Operand must be type boolean.");
        }

        expr = new ConditionalExpression(ConditionalExpression.Operator.OP_NOT, a, null);
  }
  | b=atomExpr
  {
      expr = b;
  }
  ;

atomExpr returns [Expression expr]
  : LPAREN a=expr RPAREN
      {
          expr = a;
      }
  | loc=location
	    {
	        expr = new IdentifierExpression(loc);
	    }
  | methCall=method_call
      {
          Token ahead = input.LT(1);
			    int line = ahead.getLine();
			    int pos = ahead.getCharPositionInLine();
			    
          if(Expression.Type.TYPE_INT != methCall.getType()
              && Expression.Type.TYPE_BOOL != methCall.getType())
          {
              System.out.println(line + ":" + pos + " Cannot use void method " + methCall.getName() + " in an expression.");
          }
          
          expr = methCall;
      }
  | lit=literal
      {
          expr = new LiteralExpression(lit);
      }
  ;

callout_arg: expr;

unary_operator: NOT | SUBTRACT;

boolean_literal: TRUE | FALSE;

literal returns [Literal lit]
   :
     boolean_literal
      {
          lit = new Literal(Literal.Type.TYPE_BOOL, $boolean_literal.text);
      }
   | CHARLITERAL  
      {
          lit = new Literal(Literal.Type.TYPE_CHAR, $CHARLITERAL.text);
      }
   | INTLITERAL
      {
          lit = new Literal(Literal.Type.TYPE_INT, $INTLITERAL.text);
      }
   | STRINGLITERAL
      {
          lit = new Literal(Literal.Type.TYPE_STRING, $STRINGLITERAL.text);
      }
    ;