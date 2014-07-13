package decaf;

public class EquivalenceExpression extends Expression 
{
	enum Operator
	{
		OP_EQ,
		OP_NEQ,
		OP_INVALID
	}
	
	private Operator m_op;
	
	public EquivalenceExpression(Operator op, Expression left, Expression right)
	{
		super(Expression.Type.TYPE_BOOL, left, right);
		
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
	
	// Using Expression.isValid()
	
	public static Operator parseOperator(String strOp)
	{
		Operator op = EquivalenceExpression.Operator.OP_INVALID;
		
		if(0 == strOp.compareTo("=="))
			op = EquivalenceExpression.Operator.OP_EQ;
		else if(0 == strOp.compareTo("!="))
			op = EquivalenceExpression.Operator.OP_NEQ;
				
		return op;	
	}
}
