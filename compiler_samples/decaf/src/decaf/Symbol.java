package decaf;

public class Symbol 
{
	private String m_strName;
	private Expression.Type m_type;
	private boolean m_bIsArray;
	private int m_nArraySize;
	
	public Symbol(String strName, Expression.Type type)
	{
		setName(strName);
		setType(type);
		setIsArray(false);
		setArraySize(0);
	}
	
	public void setName(String strName)
	{
		m_strName = strName;
	}
	
	public String getName()
	{
		return m_strName;
	}
	
	public void setType(Expression.Type type)
	{
		m_type = type;
	}
	
	public Expression.Type getType()
	{
		return m_type;
	}
	
	public void setIsArray(boolean bArray)
	{
		m_bIsArray = bArray;
	}
	
	public boolean getIsArray()
	{
		return m_bIsArray;
	}
	
	public void setArraySize(int nSize)
	{
		m_nArraySize = nSize;
	}
	
	public int getArraySize()
	{
		return m_nArraySize;
	}
}
