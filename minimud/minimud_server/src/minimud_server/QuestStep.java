/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minimud_server;

import minimud_shared.RangeChecker;
import minimud_shared.RegularExpressions;

/**
 *
 * @author steve
 */
public class QuestStep
{
    private int m_nID = 0;
    private int m_nStep = 0;
    private String m_strDescription = "";
    private String m_strHint = "";
	private int m_nRewardItemID = 0;
	private int m_nRewardGold = 0;
	private int m_nRewardXP = 0;
	
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
    
    public void setStep(int nStep)
    {
        m_nStep = nStep;
    }
    
    public int getStep()
    {
        return m_nStep;
    }
    
    public boolean isValidStep(String strStep)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strStep, RegularExpressions.RegExID.POSITIVE_INT);
		
		return bRet;
	}
	
	public boolean isValidStep(int nStep)
	{
		return m_rangeCheck.checkRange(RangeChecker.RangeID.QUEST_STEP_NUM, nStep);
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
        return m_regEx.stringMatchesRegEx(strDescription, RegularExpressions.RegExID.DESCRIPTION);
    }
    
    public void setHint(String strHint)
    {
        m_strHint = strHint;
    }
    
    public String getHint()
    {
        return m_strHint;
    }
    
    public boolean isValidHint(String strHint)
    {
        return m_regEx.stringMatchesRegEx(strHint, RegularExpressions.RegExID.HINT);
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
    
    public boolean isValid()
    {
        return (isValidID(getID())
                && isValidStep(getStep())
                && isValidDescription(getDescription())
                && isValidHint(getHint())
                && isValidRewardItemID(getRewardItemID())
                && isValidRewardGold(getRewardGold())
                && isValidRewardXP(getRewardXP()));
    }
}
