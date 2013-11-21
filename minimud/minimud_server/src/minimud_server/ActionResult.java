package minimud_server;

import minimud_shared.RegularExpressions;
import minimud_shared.RangeChecker;

public class ActionResult
{
	private int m_nID = 0;;
	private String m_strType = "";
	private String m_strDescription = "";
	private int m_nItemID = 0;
	private int m_nValue = 0;
	
	private RegularExpressions m_regEx = new RegularExpressions();
    private RangeChecker m_rangeCheck = new RangeChecker();
	
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
		return m_rangeCheck.checkRange(RangeChecker.RangeID.ID, nID);
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
		
		bRet = (m_regEx.stringMatchesRegEx(strType, RegularExpressions.RegExID.RESULT_TYPE));
		
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
		return m_rangeCheck.checkRange(RangeChecker.RangeID.ID, nID);
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
		return m_rangeCheck.checkRange(RangeChecker.RangeID.VALUE, nValue);
	}
    
    public boolean isValid()
    {
        return (isValidID(getID())
                && isValidDescription(getDescription())
                && isValidItemID(getItemID())
                && isValidType(getType())
                && isValidValue(getValue()));
    }
}
