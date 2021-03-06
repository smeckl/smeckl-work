package minimud_server;

import minimud_shared.RegularExpressions;
import minimud_shared.RangeChecker;

public class Quest
{
	private int m_nID = 0;
	private String m_strName = "";
	private String m_strFirstCompleteUser = "";
	private int m_nRewardItemID = 0;
	private int m_nRewardGold = 0;
	private int m_nRewardXP = 0;
	private int m_nFirstBonus = 0;
	
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
		
		bRet = (m_regEx.stringMatchesRegEx(strName, RegularExpressions.RegExID.NAME));
		
		return bRet;
	}
	
	public void setFirstCompleteUser(String strFirstCompleteUser)
	{
		m_strFirstCompleteUser = strFirstCompleteUser;
	}
	
	public String getFirstCompleteUser()
	{
		return m_strFirstCompleteUser;
	}
	
	public boolean isValidFirstCompleteUser(String strFirstCompleteUser)
	{
		boolean bRet = false;
		
		bRet = (m_regEx.stringMatchesRegEx(strFirstCompleteUser, RegularExpressions.RegExID.USERNAME)
                || 0 == strFirstCompleteUser.length());
		
		return bRet;
	}
	
	public void setRewardItemID(int nID)
	{
		m_nRewardItemID = nID;
	}
	
	public int getRewardItemID()
	{
		return m_nRewardItemID;
	}
	
	public boolean isValidRewardItemID(String strID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidRewardItemID(int nID)
	{
		return m_rangeCheck.checkRange(RangeChecker.RangeID.ID, nID);
	}
	
	public void setRewardGold(int nRewardGold)
	{
		m_nRewardGold = nRewardGold;
	}
	
	public int getRewardGold()
	{
		return m_nRewardGold;
	}
	
	public boolean isValidRewardGold(String strRewardGold)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strRewardGold, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidRewardGold(int nRewardGold)
	{
		return m_rangeCheck.checkRange(RangeChecker.RangeID.REWARD_GOLD, nRewardGold);
	}
	
	public void setRewardXP(int nRewardXP)
	{
		m_nRewardXP = nRewardXP;
	}
	
	public int getRewardXP()
	{
		return m_nRewardXP;
	}
	
	public boolean isValidRewardXP(String strRewardXP)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strRewardXP, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidRewardXP(int nRewardXP)
	{
		return m_rangeCheck.checkRange(RangeChecker.RangeID.REWARD_XP, nRewardXP);
	}
	
	public void setFirstBonus(int nFirstBonus)
	{
		m_nFirstBonus = nFirstBonus;
	}
	
	public int getFirstBonus()
	{
		return m_nFirstBonus;
	}
	
	public boolean isValidFirstBonus(String strFirstBonus)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strFirstBonus, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidFirstBonus(int nFirstBonus)
	{
		return m_rangeCheck.checkRange(RangeChecker.RangeID.FIRST_BONUS, nFirstBonus);
	}
    
    public boolean isValid()
    {
        return (isValidID(getID())
                && isValidName(getName())
                && isValidRewardGold(getRewardGold())
                && isValidRewardXP(getRewardXP())
                && isValidRewardItemID(getRewardItemID())
                && isValidFirstBonus(getFirstBonus())
                && isValidFirstCompleteUser(getFirstCompleteUser()));
    }
}
