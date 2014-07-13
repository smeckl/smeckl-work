package decaf;

public class Expression 
{
	enum Type
	{
		TYPE_INT,
		TYPE_BOOL,
		TYPE_VOID,
		TYPE_INVALID
	}

	private Type m_type;
	private Expression m_left;
	private Expression m_right; //optional
	private boolean m_bUnary;
	private boolean m_bAtom = false;
	
	public Expression(Type type, Expression left, Expression right)
	{
		setType(type);
		setLeft(left);
		setRight(right);
		
		if(null == right)
			setIsUnary(true);
	}
	
	public Expression(Expression right)
	{
		setType(right.getType());
		setLeft(right.getLeft());
		setRight(right.getRight());
	}
	
	public void setType(Type type)
	{
		m_type = type;
	}
	
	public Type getType()
	{
		return m_type;
	}
	
	public void setLeft(Expression left)
	{
		m_left = left;
	}
	
	public Expression getLeft()
	{
		return m_left;
	}

	public void setRight(Expression right)
	{
		m_right = right;
	}
	
	public Expression getRight()
	{
		return m_right;
	}
	
	public boolean isAtom()
	{
		return m_bAtom;
	}
	
	protected void setIsAtom(boolean bAtom)
	{
		m_bAtom = bAtom;
	}
	
	public boolean isValid()
	{
		boolean bValid = true;
		
		return bValid;
	}
	
	public boolean isUnary()
	{
		return m_bUnary;
	}
	
	private void setIsUnary(boolean bIsUnary)
	{
		m_bUnary = bIsUnary;
	}
}
