package decaf;

public class RelationalExpression extends Expression 
{
	enum Operator
	{
		OP_LT,
		OP_GT,
		OP_LTE,
		OP_GTE,
		OP_INVALID
	}
	
	private Operator m_op;
	
	public RelationalExpression(Operator op, Expression left, Expression right)
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
		return (Expression.Type.TYPE_INT == left.getType() && Expression.Type.TYPE_INT == right.getType());
	}
	
	public static Operator parseOperator(String strOp)
	{
		Operator op = RelationalExpression.Operator.OP_INVALID;
		
		if(0 == strOp.compareTo("<"))
			op = RelationalExpression.Operator.OP_LT;
		else if(0 == strOp.compareTo(">"))			
			op = RelationalExpression.Operator.OP_GT;
		else if(0 == strOp.compareTo("<="))
			op = RelationalExpression.Operator.OP_LTE;
		else if(0 == strOp.compareTo(">="))
			op = RelationalExpression.Operator.OP_GTE;
				
		return op;	
	}
}
