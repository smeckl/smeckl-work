package decaf;

public class ConditionalExpression extends Expression 
{
	enum Operator
	{
		OP_AND,
		OP_OR,
		OP_NOT
	}
	
	private Operator m_op;
	
	public ConditionalExpression(Operator op, Expression left, Expression right)
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
	
	public boolean isValid(Expression left, Expression right)
	{
		boolean bValid = true;
		
		if(Expression.Type.TYPE_BOOL != getLeft().getType())
			bValid = false;
		
		if(isUnary())
		{
			if(Operator.OP_NOT != getOperator())
				bValid = false;
		}
		else
		{
			if(Expression.Type.TYPE_BOOL != getRight().getType())
				bValid = false;
		}
		
		return bValid;
	}
}
