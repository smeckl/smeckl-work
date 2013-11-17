package minimud_server;

import minimud_shared.RegularExpressions;

public class Action
{
	private int m_nID = 0;;
	private String m_strName = "";
	private int m_nResultID = 0;
	private int m_nQuestDependencyID = 0;
	private int m_nQuestDependencyStep = 0;
    private int m_nQuestDependencyCompletion = 0;
	
	private RegularExpressions m_regEx = new RegularExpressions();
	
	public void setID(int nID)
	{
		m_nID = nID;
	}
	
	public int getID()
	{
		return m_nID;
	}
	
	public boolean isValidID(String strID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidID(int nID)
	{
		return (getID() > 0 && getID() < 100);
	}
	
	public void setName(String strName)
	{
		m_strName = strName;
	}
	
	public String getName()
	{
		return m_strName;
	}
	
	public boolean isValidName(String strName)
	{
		boolean bRet = false;
		
		bRet = (m_regEx.stringMatchesRegEx(strName, RegularExpressions.RegExID.ACTION_TYPE));
		
		return bRet;
	}
	
	public void setResult(int nResultID)
	{
		m_nResultID = nResultID;
	}
	
	public int getResultID()
	{
		return m_nResultID;
	}
	
	public boolean isValidResultID(String strResultID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strResultID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidResultID(int nID)
	{
		return (getID() > 0 && getID() < 100);
	}
	
	public void setQuestDependencyID(int nQuestDependencyID)
	{
		m_nQuestDependencyID = nQuestDependencyID;
	}
	
	public int getQuestDependencyID()
	{
		return m_nQuestDependencyID;
	}
	
	public boolean isValidQuestDependencyID(String strQuestDependencyID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strQuestDependencyID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidQuestDependencyID(int nQuestDependencyID)
	{
		return (getQuestDependencyID() >= 0 && getQuestDependencyID() < 100);
	}
	
	public void setQuestDependencyStep(int nQuestDependencyStep)
	{
		m_nQuestDependencyStep = nQuestDependencyStep;
	}
	
	public int getQuestDependencyStep()
	{
		return m_nQuestDependencyStep;
	}
	
	public boolean isValidQuestDependencyStep(String strQuestDependencyStep)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strQuestDependencyStep, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidQuestDependencyStep(int nQuestDependencyStep)
	{
		return (getQuestDependencyID() >= 0 && getQuestDependencyID() < 100);
	}
	
	public void setQuestDependencyCompletion(int nQuestDependencyCompletion)
	{
		m_nQuestDependencyCompletion = nQuestDependencyCompletion;
	}
	
	public int getQuestDependencyCompletion()
	{
		return m_nQuestDependencyCompletion;
	}
	
	public boolean isValidQuestDependencyCompletion(String strQuestDependencyCompletion)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strQuestDependencyCompletion, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidQuestDependencyCompletion(int nQuestDependencyCompletion)
	{
		return (getQuestDependencyCompletion() > 0 || getQuestDependencyCompletion() < 100);
	}
	
	public boolean isValid()
	{
		boolean bValid = false;
		
		if(isValidID(getID())
				&& isValidName(getName())
				&& isValidResultID(getResultID())
				&& isValidQuestDependencyID(getQuestDependencyID())
				&& isValidQuestDependencyStep(getQuestDependencyStep())
                && isValidQuestDependencyCompletion(getQuestDependencyCompletion()))
		{
			bValid = true;
		}
		
		return bValid;
	}
}
