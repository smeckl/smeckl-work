package decaf;

public class MathExpression extends Expression 
{
	enum Operator
	{
		OP_ADD,
		OP_SUBTRACT,
		OP_MULTIPLY,
		OP_DIVIDE,
		OP_MODULUS,
		OP_INVALID
	}
	
	private Operator m_op;
	
	public MathExpression(Operator op, Expression left, Expression right)
	{
		super(Expression.Type.TYPE_INT, left, right);
		
		setOperator(op);
	}
	
	public void setOperator(Operator op)
	{
		m_op = op;
	}
	
	public Operator getOperator()
	{
		return m_op;
	}
	
	public boolean isValid(Expression left, Expression right)
	{
		return (Expression.Type.TYPE_INT == left.getType() && Expression.Type.TYPE_INT == right.getType());
	}
	
	public static Operator parseOperator(String strOp)
	{
		Operator op = MathExpression.Operator.OP_INVALID;
		
		if(0 == strOp.compareTo("+"))
			op = MathExpression.Operator.OP_ADD;
		else if(0 == strOp.compareTo("-"))			
			op = MathExpression.Operator.OP_SUBTRACT;
		else if(0 == strOp.compareTo("*"))
			op = MathExpression.Operator.OP_MULTIPLY;
		else if(0 == strOp.compareTo("/"))
			op = MathExpression.Operator.OP_DIVIDE;
		else if(0 == strOp.compareTo("%"))
			op = MathExpression.Operator.OP_MODULUS;
				
		return op;	
	}
}
