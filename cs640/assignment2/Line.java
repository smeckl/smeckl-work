class Line
{
	enum Type
	{
		LABEL,
		SECTION,
		VAR_DECL,
		INSTRUCTION
	};

	private String m_strOrigText;
	private Type m_type;
	private String m_strLabel = "";
	private int m_nLineNum;

	private boolean m_bLeader;

	public Line(Type type, String strOrigText)
	{
		setType(type);
		setOrigText(strOrigText);
		setIsLeader(false);
	}

	public Line(Line right)
	{
		setType(right.getType());
		setOrigText(right.getOrigText());
		setIsLeader(false);
	}

	public void setOrigText(String strOrigText)
	{
		m_strOrigText = strOrigText;
	}

	public String getOrigText()
	{
		return m_strOrigText;
	}

	public void setType(Line.Type type)
	{
		m_type = type;
	}

	public Line.Type getType()
	{
		return m_type;
	}

	public void setLabel(String strLabel)
	{
		m_strLabel = strLabel;
	}

	public String getLabel()
	{
		return m_strLabel;
	}

	public void setLineNum(int nLineNum)
	{
		m_nLineNum = nLineNum;
	}

	public int getLineNum()
	{
		return m_nLineNum;
	}

	public void setIsLeader(boolean bLeader)
	{
		m_bLeader = bLeader;
	}

	public boolean getIsLeader()
	{
		return m_bLeader;
	}

	public String toString()
	{
		return getOrigText();
	}

	public String printDebug()
	{
		return toString();
	}
}
