package decaf;

import java.util.Vector;

public class Method extends Symbol 
{
	private Vector<Symbol> m_symList;
	
	public Method(String strName, Expression.Type type)
	{
		super(strName, type);
		
		m_symList = new Vector<Symbol>();
	}
	
	public boolean paramExists(String strName)
	{
		boolean bExists = false;
		for(int i = 0; i < m_symList.size(); i++)
		{
			Symbol sym = m_symList.get(i);
			
			if(0 == strName.compareTo(sym.getName()))
			{
				bExists = true;
				break;
			}
		}
			
		return bExists;
	}
	
	public void addParameter(Symbol sym)
	{
		m_symList.addElement(sym);
	}
	
	public int getNumberOfParameters()
	{
		return m_symList.size();
	}
	
	public Symbol getParameterAt(int loc)
	{
		Symbol sym = null;
		
		if(loc < m_symList.size())
			sym = m_symList.get(loc);
		
		return sym;
	}
}
