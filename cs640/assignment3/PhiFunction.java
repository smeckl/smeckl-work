import java.util.Hashtable;
import java.util.Enumeration;

class PhiFunction
{
	private static boolean bDebug = false;

	public static void __debugPrint(Object str)
	{
		if(bDebug)
			System.out.print(str);
	}

	public static void __debugPrintln(Object str)
	{
		if(bDebug)
			System.out.println(str);
	}

	private Variable m_variable;
	private int m_version;

	private Hashtable<String,Symbol> m_mergeList = new Hashtable<String,Symbol>();

	public PhiFunction(Variable variable)
	{
		setVariable(variable);
	}

	public void setVariable(Variable variable)
	{
		// Make a copy of the variable
		m_variable = new Variable(variable);
	}

	public Variable getVariable()
	{
		return m_variable;
	}

	public void setVersion(int version)
	{
		m_version = version;
	}

	public String getName()
	{
		return getVariable().getName() + "_" + getVersion();
	}

	public int getVersion()
	{
		return m_version;
	}

	public boolean varInMergeList(String strVar)
	{
		return (null != m_mergeList.get(strVar));
	}

	public void addVariable(String strNewVar, Symbol value)
	{
		__debugPrintln("Phi: adding " + strNewVar + "=" + value.getIntegerValue());
		m_mergeList.put(strNewVar, value);
	}

	public void deleteVariable(String strVarName)
	{
		__debugPrintln("Phi: removing " + strVarName);
		m_mergeList.remove(strVarName);
	}

	public void updateVariable(String strVar, Symbol value)
	{
		__debugPrintln("Phi: updating " + strVar + "=" + value.getIntegerValue());
		if(m_mergeList.containsKey(strVar))
			m_mergeList.put(strVar, value);
	}

	public boolean isConst()
	{
		boolean bRet = true;
		int nValue = 0;
		int i = 0;

		__debugPrint("Phi(" + getVariable().getName() + "): ");
		for(Enumeration<String> e = m_mergeList.keys(); bRet && e.hasMoreElements();)
		{
			String strName = e.nextElement();
			Symbol sym = m_mergeList.get(strName);
			
			__debugPrint(strName + "=" + sym.getIntegerValue() + "  ");

			if(Symbol.SymbolType.CONSTANT == sym.getSymbolType())
			{
				if(0 == i)
					nValue = sym.getIntegerValue();
				else
				{
					int nNewVal = sym.getIntegerValue();
				
					if(nNewVal != nValue)
						bRet = false;
				}
			}
			else
				bRet = false;

			i++;
		}
		__debugPrintln("");
		return bRet;
	}

	public int getConstValue()
	{
		Enumeration<String> e = m_mergeList.keys();

		return m_mergeList.get(e.nextElement()).getIntegerValue();
	}

	public Hashtable<String,Symbol> getVariableList()
	{
		return m_mergeList;
	}
};
