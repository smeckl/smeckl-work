package decaf;

public class Literal 
{
	enum Type
	{
		TYPE_CHAR,
		TYPE_INT,
		TYPE_BOOL,
		TYPE_STRING
	}
	
	private String m_strVal;
	private Type m_type;
	
	public Literal(Type type, String value)
	{
		setValue(value);
		setType(type);
	}
	
	public void setType(Type type)
	{
		m_type = type;
	}
	
	public Type getType()
	{
		return m_type;
	}
	
	public void setValue(String value)
	{
		m_strVal = value;
	}
	
	public String getValue()
	{
		return m_strVal;
	}
}
