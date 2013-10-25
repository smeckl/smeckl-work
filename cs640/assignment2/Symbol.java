public class Symbol 
{
	private String m_strName;
	private SymbolType m_symbolType;
	private DataType m_dataType;
	private int m_nIntVal;
	private boolean m_boolVal;
	
	enum SymbolType
	{
		REGISTER,
		VARIABLE,
		CONSTANT
	};

	enum DataType
	{
		INTEGER,
		BOOLEAN
	};

	public Symbol(String strName, SymbolType symbolType, DataType dataType)
	{
		setName(strName);
		setSymbolType(symbolType);
		setDataType(dataType);
	}

	public Symbol(Symbol right)
	{
		setName(right.getName());
		setSymbolType(right.getSymbolType());
		setDataType(right.getDataType());
		setIntegerValue(right.getIntegerValue());
		setBoolValue(right.getBoolValue());
	}
	
	public void setName(String strName)
	{
		m_strName = strName;
	}
	
	public String getName()
	{
		return m_strName;
	}
	
	public void setSymbolType(SymbolType type)
	{
		m_symbolType = type;
	}
	
	public SymbolType getSymbolType()
	{
		return m_symbolType;
	}

	public void setDataType(DataType type)
	{
		m_dataType = type;
	}
	
	public DataType getDataType()
	{
		return m_dataType;
	}
	
	public void setIntegerValue(int nVal)
	{
		m_nIntVal = nVal;
	}

	public int getIntegerValue()
	{
		return m_nIntVal;
	}

	public void setBoolValue(boolean bVal)
	{
		m_boolVal = bVal;
	}

	public boolean getBoolValue()
	{
		return m_boolVal;
	}

	public String toString()
	{
		String strVal = "";

		switch(getDataType())
		{
			case INTEGER:
				strVal = "" + getIntegerValue();
				break;

			case BOOLEAN:
				strVal = getBoolValue() ? "1" : "0";
		}

		return strVal;
	}
}
