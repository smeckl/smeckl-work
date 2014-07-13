package decaf;

import java.util.Stack;

public class SymbolTable 
{
	private Stack<SymbolMap> m_symStack;
	private SymbolMap m_globalScope;
	
	public SymbolTable()
	{
		// Create symbol map stack
		m_symStack = new Stack<SymbolMap>();
		
		// Create symbol map for global scope
		m_globalScope = new SymbolMap();
		
		// and push it onto the stack
		m_symStack.push(m_globalScope);		
	}
	
	public void startScope()
	{
		m_symStack.push(new SymbolMap());
	}
	
	public void endScope()
	{
		m_symStack.pop();
	}
	
	public Symbol lookupSymbol(String strName, boolean bLimitScope)
	{
		Symbol sym = null;
		
		// If we are limiting the search to the current scope, then
		// just look at top of the stack
		if(bLimitScope)
		{
			SymbolMap symMap = m_symStack.peek();
			
			sym = symMap.getSymbol(strName);
		}
		else
		{
			// Look through stack of scopes starting with the top
			// but don't pop any off
			for(int i = (m_symStack.size() - 1); i >= 0; i--)
			{
				SymbolMap symMap = m_symStack.elementAt(i);
				
				if(symMap.symExists(strName))
				{
					sym = symMap.getSymbol(strName);
					break;
				}
			}
		}
		
		return sym;
	}
	
	public void addSymbol(String strName, Expression.Type type)
	{
		SymbolMap curScope = m_symStack.peek();
		
		curScope.addSymbol(new Symbol(strName, type));
	}
	
	public void addSymbol(Symbol sym)
	{
		SymbolMap curScope = m_symStack.peek();
		curScope.addSymbol(sym);
	}
	
	public Method addMethod(String strName, Expression.Type type)
	{
		Method sym = null;
		
		if(!m_globalScope.symExists(strName))
		{
			sym = new Method(strName, type);
			m_globalScope.addSymbol(sym);
		}
		
		return sym;
	}
	
	public Method lookupMethod(String strName)
	{
		return (Method)m_globalScope.getSymbol(strName);
	}
}
