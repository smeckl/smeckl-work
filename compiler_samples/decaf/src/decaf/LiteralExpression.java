package decaf;

public class LiteralExpression extends Expression 
{
	private Literal m_lit;
	
	public LiteralExpression(Literal lit)
	{
		super(convertType(lit.getType()), null, null);		
		
		setIsAtom(true);
	}
	
	private static Expression.Type convertType(Literal.Type litType)
	{
		Expression.Type type;
		
		if(Literal.Type.TYPE_INT == litType)
			type = Expression.Type.TYPE_INT;
		else if(Literal.Type.TYPE_BOOL == litType)
			type = Expression.Type.TYPE_BOOL;
		else
			type = Expression.Type.TYPE_INVALID;
		
		return type;
	}
	
	public void setLiteral(Literal lit)
	{
		m_lit = new Literal(lit.getType(), lit.getValue());
	}
	
	public Literal getLiteral()
	{
		return m_lit;
	}
	
	public boolean isValid()
	{
		return (null != m_lit);
	}
}
