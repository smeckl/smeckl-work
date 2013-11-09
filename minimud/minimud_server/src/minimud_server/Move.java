package minimud_server;

import minimud_shared.RegularExpressions;

public class Move
{
	private int m_nRoomID = 0;
	private String m_strDirection = "";
	private int m_nNextRoomID = 0;
	private String m_strDescription = "";
	
	private RegularExpressions m_regEx = new RegularExpressions();
	
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
		return (nID > 0 && nID < 100);
	}

	public void setDirection(String strDirection)
	{
		m_strDirection = strDirection;
	}
	
	public String getDirection()
	{
		return m_strDirection;
	}
	
	public boolean isValidDirection(String strDirection)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strDirection, RegularExpressions.RegExID.DIRECTION);
		
		return bRet;
	}
	
	public void setNextRoomID(int nID)
	{
		m_nNextRoomID = nID;
	}
	
	public int getNextRoomID()
	{
		return m_nNextRoomID;
	}
	
	public boolean isValidNextRoomID(String strNextRoomID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strNextRoomID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidNextRoomID(int nID)
	{
		return (nID > 0 && nID < 100);
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
	
	public boolean isValid()
	{
		boolean bValid = false;
		
		if(isValidRoomID(getRoomID())
				&& isValidDirection(getDirection())
				&& isValidNextRoomID(getNextRoomID())
				&& isValidDescription(getDescription()))
		{
			bValid = true;
		}
			
		
		return bValid;
	}
}
