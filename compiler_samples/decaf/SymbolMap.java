package decaf;

import java.util.Hashtable;

public class SymbolMap 
{
	private Hashtable<String, Symbol> m_symMap;
	
	public SymbolMap()
	{
		m_symMap = new Hashtable<String, Symbol>();
	}
	
	public void addSymbol(Symbol sym)
	{
		if(!m_symMap.containsKey(sym.getName()))
			m_symMap.put(sym.getName(), sym);
	}
	
	public boolean symExists(String strName)
	{
		return m_symMap.containsKey(strName);
	}
	
	public Symbol getSymbol(String strName)
	{
		Symbol sym = null;
		
		if(m_symMap.containsKey(strName))
		{
			sym = m_symMap.get(strName);
		}
		
		return sym;
	}
	
	public int getSize()
	{
		return m_symMap.size();
	}
}
