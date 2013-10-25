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

	private String m_strOrigName;
	private String m_strCurName;
	private int m_nCurValue;
	private Type m_type;

	private boolean m_bIsConstant;

	public Argument(String strOrigName, Type type)
	{
		setOrigName(strOrigName);
		setName(strOrigName);
		setType(type);
		setIsConstant(Type.LITERAL == type);

		if(getIsConstant())
		{
			setValue(Integer.parseInt(m_strOrigName));
		}
	}

	public void setOrigName(String strOrigName)
	{
		m_strOrigName = strOrigName;
	}

	public String getOrigName()
	{
		return m_strOrigName;
	}

	public void setName(String strName)
	{
		m_strCurName = strName;
	}

	public String getName()
	{
		return m_strCurName;
	}

	public void setType(Type type)
	{
		m_type = type;
	}

	public Type getType()
	{
		return m_type;
	}

	public void setIsConstant(boolean bIsConstant)
	{
		m_bIsConstant = bIsConstant;
	}

	public boolean getIsConstant()
	{
		return m_bIsConstant;
	}

	public void setValue(int nValue)
	{
		m_nCurValue = nValue;
	}

	public int getValue()
	{
		return m_nCurValue;
	}

	public String toString()
	{
		String strOut = "";
			
		switch(getType())
		{
		case LITERAL:
		case IDENTIFIER:
		case REGISTER:
		case IMMEDIATE_PLUS_REGISTER_CONTENTS:
			strOut = getName();
			break;

		case REGISTER_CONTENTS:
			strOut = "(" + getName() + ")";
			break;
		}
		return strOut;
	}
};
