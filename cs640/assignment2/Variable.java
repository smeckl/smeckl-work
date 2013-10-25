class Variable
{
	enum Type
	{
		BINARY,
		INTEGER,
		STRING
	};

	private String m_strName;
	private Type m_type;
	private String m_strValue;

	public Variable(String strName, Type type, String strValue)
	{
		setName(strName);
		setType(type);
		setValue(strValue);
	}

	public Variable(Variable right)
	{
		setName(right.getName());
		setType(right.getType());
		setValue(right.getValue());
	}

	public void setName(String strName)
	{
		m_strName = strName;
	}

	public String getName()
	{
		return m_strName;
	}

	public void setType(Type type)
	{
		m_type = type;
	}

	public Type getType()
	{
		return m_type;
	}

	public void setValue(String strValue)
	{
		m_strValue = strValue;
	}

	public String getValue()
	{
		return m_strValue;
	}
};
