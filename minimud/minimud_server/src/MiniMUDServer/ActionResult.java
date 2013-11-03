package MiniMUDServer;

import java.util.HashMap;

import MiniMUDShared.RegularExpressions;

public class ActionResult
{
	private int m_nID = 0;;
	private String m_strType = "";
	private String m_strDescription = "";
	private int m_nItemID = 0;
	private int m_nValue = 0;
	
	private RegularExpressions m_regEx = new RegularExpressions();
	
	private HashMap<String,String> m_validTypes = new HashMap<String,String>();
	
	public ActionResult()
	{
		m_validTypes.put("text_only", "text_only");
		m_validTypes.put("xp_reward", "xp_reward");
		m_validTypes.put("gold_reward", "gold_reward");
		m_validTypes.put("item_reward", "item_reward");
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
	
	public void setType(String strType)
	{
		m_strType = strType;
	}
	
	public String getType()
	{
		return m_strType;
	}
	
	public boolean isValidType(String strType)
	{
		boolean bRet = false;
		
		bRet = (m_regEx.stringMatchesRegEx(strType, RegularExpressions.RegExID.RESULT_TYPE)
				&& m_validTypes.containsKey(strType));
		
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
		
		bRet = (m_regEx.stringMatchesRegEx(strDescription, RegularExpressions.RegExID.DESCRIPTION)
				&& strDescription.length() < 100);
		
		return bRet;
	}
	
	public void setItemID(int nID)
	{
		m_nItemID = nID;
	}
	
	public int getItemID()
	{
		return m_nItemID;
	}
	
	public boolean isValidItemID(String strID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidItemID(int nID)
	{
		return (nID > 0 && nID < 100);
	}
	
	public void setValue(int nValue)
	{
		m_nValue = nValue;
	}
	
	public int getValue()
	{
		return m_nValue;
	}
	
	public boolean isValidValue(String strValue)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strValue, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidValue(int nValue)
	{
		return (nValue > 0 && nValue <= 65535);
	}
}
