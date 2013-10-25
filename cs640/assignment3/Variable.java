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

	public Variable(String strName, Type type, String strValue)
	{
		setName(strName);
		setType(type);
	}

	public Variable(Variable right)
	{
		setName(right.getName());
		setType(right.getType());
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
};
