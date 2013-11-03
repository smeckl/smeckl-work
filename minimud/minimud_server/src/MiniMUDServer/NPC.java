package MiniMUDServer;

import java.util.HashMap;

import MiniMUDShared.RegularExpressions;

public class NPC
{
	private int m_nID = 0;
	private int m_nRoomID = 0;
	private String m_strName = "";
	private String m_strDescription = "";
	private String m_strIntro = "";
	
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
	
	public void setRoomID(int nID)
	{
		m_nRoomID = nID;
	}
	
	public int getRoomID()
	{
		return m_nRoomID;
	}
	
	public boolean isValidRoomID(String strRoomID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strRoomID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidRoomID(int nID)
	{
		return (getRoomID() > 0 && getRoomID() < 100);
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
	
	public void setIntro(String strIntro)
	{
		m_strIntro = strIntro;
	}
	
	public String getIntro()
	{
		return m_strIntro;
	}
	
	public boolean isValidIntro(String strIntro)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strIntro, RegularExpressions.RegExID.NPCTEXT);
		
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
				&& isValidRoomID(getRoomID())
				&& isValidName(getName())
				&& isValidDescription(getDescription())
				&& isValidIntro(getIntro()))
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
}
