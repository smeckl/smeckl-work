package decaf;

public class IdentifierExpression extends Expression 
{
	private Symbol m_sym;
	
	public IdentifierExpression(Symbol sym)
	{
		super(sym.getType(), null, null);
		
		setIsAtom(true);
	}

	public void setSymbol(Symbol sym)
	{
		m_sym = sym;
	}
	
	public Symbol getSymbol()
	{
		return m_sym;
	}
	
	public boolean isValid()
	{
		return (null != m_sym);
	}
}
