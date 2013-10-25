class Argument
{

	enum Type
	{
		LITERAL,
		REGISTER,
		REGISTER_CONTENTS,
		IDENTIFIER,
		IMMEDIATE_PLUS_REGISTER_CONTENTS
	}

	private String m_strOrigValue;
	private Type m_type;

	public Argument(String strOrigValue, Type type)
	{
		setOrigValue(strOrigValue);
		setType(type);
	}

	public void setOrigValue(String strOrigValue)
	{
		m_strOrigValue = strOrigValue;
	}

	public String getOrigValue()
	{
		return m_strOrigValue;
	}

	public void setType(Type type)
	{
		m_type = type;
	}

	public Type getType()
	{
		return m_type;
	}

	public String toString()
	{
		return m_strOrigValue;
	}
};
