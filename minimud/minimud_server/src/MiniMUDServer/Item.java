package MiniMUDServer;

import java.util.HashMap;

import MiniMUDShared.RegularExpressions;

public class Item 
{
	private int m_nID = 0;
	private String m_strName = "";
	private String m_strDescription = "";

	
	private RegularExpressions m_regEx = new RegularExpressions();
	private HashMap<String, Action> m_actions = new HashMap<String, Action>();
	
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
		
		bRet = m_regEx.stringMatchesRegEx(strName, RegularExpressions.RegExID.NAME);
		
		return bRet;
	}
	
	public void setDescription(String strDescription)
	{
		m_strDescription = strDescription;
	}
	
	public String getDescription()
	{
		return m_strDescription;
	}
	
	public boolean isValidDescription(String strDescription)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strDescription, RegularExpressions.RegExID.DESCRIPTION);
		
		return bRet;
	}
	
	public boolean isValid()
	{
		boolean bValid = false;
		
		if(isValidID(getID())
				&& isValidName(getName())
				&& isValidDescription(getDescription()))
		{
			bValid = true;
		}
		
		return bValid;
	}
	
	public void addAction(Action action)
	{
		m_actions.put(action.getName(), action);
	}
	
	public Action getAction(String strName)
	{
		Action ret = null;
		
		if(m_actions.containsKey(strName))
			ret = m_actions.get(strName);
		
		return ret;
	}
	
	protected RegularExpressions getRegEx()
	{
		return m_regEx;
	}
}
