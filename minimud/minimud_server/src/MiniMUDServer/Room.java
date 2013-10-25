package MiniMUDServer;

import java.util.HashMap;

import MiniMUDShared.RegularExpressions;

public class Room
{
	private int m_nID = 0;
	private String m_strName = "";
	private String m_strDescription = "";
	
	private RegularExpressions m_regEx = new RegularExpressions();
	
	private HashMap<String, Move> m_moves = new HashMap<String, Move>();
	
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
		return (nID > 0 && nID < 100);
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
	
	public void setDescription(String str_Description)
	{
		m_strDescription = str_Description;
	}
	
	public String getDescription()
	{
		return m_strDescription;
	}
	
	public boolean isValidDescription(String strDescription)
	{
		boolean bRet = false;
		
		bRet = (m_regEx.stringMatchesRegEx(strDescription, RegularExpressions.RegExID.DESCRIPTION)
				&& strDescription.length() < 2000);
		
		return bRet;
	}
	
	public void addMove(Move move)
	{
		m_moves.put(move.getDirection(), move);
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
}
