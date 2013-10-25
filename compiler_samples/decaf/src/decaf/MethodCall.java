package decaf;

import java.util.Vector;

public class MethodCall extends Expression
{
	private Vector<Expression> m_exprList;
	private String m_strName;
	private boolean m_bIsCallout;
	
	public MethodCall(String strName, Expression.Type type)
	{
		super(type, null, null);
		
		setName(strName);
		setIsAtom(true);
		setIsCallout(false);
		
		m_exprList = new Vector<Expression>();
	}
	
	public void setName(String strName)
	{
		m_strName = strName;
	}
	
	public String getName()
	{
		return m_strName;
	}
	
	public void setIsCallout(boolean bCallout)
	{
		m_bIsCallout = bCallout;
	}
	
	public boolean getIsCallout()
	{
		return m_bIsCallout;
	}
	
	public void addParameter(Expression expr)
	{
		m_exprList.addElement(expr);
	}
	
	public int getNumberOfParameters()
	{
		return m_exprList.size();
	}
	
	public Expression getParameterAt(int loc)
	{
		Expression expr = null;
		
		if(loc < m_exprList.size())
			expr = m_exprList.get(loc);
		
		return expr;
	}
}
