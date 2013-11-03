package MiniMUDServer;

import java.util.HashMap;

import MiniMUDShared.RegularExpressions;

public class Action
{
	private int m_nID = 0;;
	private String m_strName = "";
	private int m_nResultID = 0;
	
	private RegularExpressions m_regEx = new RegularExpressions();
	
	private HashMap<String,String> m_validActions = new HashMap<String,String>();
	
	public Action()
	{
		m_validActions.put("look", "look");
		m_validActions.put("talk", "talk");
		m_validActions.put("punch", "punch");
		m_validActions.put("kick", "kick");
		m_validActions.put("stab", "stab");
		m_validActions.put("slash", "slash");
		m_validActions.put("push", "push");
		m_validActions.put("shoot", "shoot");
	}
	
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
		
		bRet = (m_regEx.stringMatchesRegEx(strName, RegularExpressions.RegExID.NAME)
				&& m_validActions.containsKey(strName));
		
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
	
	public boolean isValid()
	{
		boolean bValid = false;
		
		if(isValidID(getID())
				&& isValidName(getName())
				&& isValidResultID(getResultID()))
		{
			bValid = true;
		}
		
		return bValid;
	}
}
