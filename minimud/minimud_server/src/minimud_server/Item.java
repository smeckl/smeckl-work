package minimud_server;

import java.util.HashMap;

import minimud_shared.RangeChecker;
import minimud_shared.RegularExpressions;

public class Item 
{
    public enum DamageType
    {
        NotSet,
        Piercing,
        Slashing,
        Bashing,
        Magic
    }
    
    public enum Effect
    {
        NotSet,
        GiveHealth,
        Teleport,
        UpdateQuest
    }
    
	private int m_nID = 0;
	private String m_strName = "";
	private String m_strDescription = "";
    private boolean m_bIsWeapon = false;
    private DamageType m_damageType = DamageType.NotSet;
    private int m_nDamage = -1;
    private Effect m_effect = Effect.NotSet;
    private boolean m_bIsStackable = false;
    private int m_nReqRoomID = 0;
    private int m_nValue = 0;
    private boolean m_bDeleteOnUse = false;
    private String m_strEffectText = "";
    private int m_nQuestDependencyID = 0;
	private int m_nQuestDependencyStep = 0;
    private int m_nUpdateQuestStep = 0;
	
	private RegularExpressions m_regEx = new RegularExpressions();
    private RangeChecker m_rangeCheck = new RangeChecker();
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
		
        if(!m_bIsWeapon)
        {
            if(isValidID(getID())
                    && isValidName(getName())
                    && isValidDescription(getDescription()))
            {
                bValid = true;
            }
        }
        else
        {
            if(isValidID(getID())
                    && isValidName(getName())
                    && isValidDescription(getDescription())
                    && DamageType.NotSet != m_damageType
                    && -1 != m_nDamage)
            {
                bValid = true;
            }
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
    
    public void setIsWeapon(boolean bIsWeapon)
    {
        m_bIsWeapon = bIsWeapon;
    }
    
    public boolean getIsWeapon()
    {
        return m_bIsWeapon;
    }
    
    public void setIsStackable(boolean bIsStackable)
    {
        m_bIsStackable = bIsStackable;
    }
    
    public boolean getIsStackable()
    {
        return m_bIsStackable;
    }
    
    public void setDeleteOnUse(boolean bDeleteOnUse)
    {
        m_bDeleteOnUse = bDeleteOnUse;
    }
    
    public boolean getDeleteOnUse()
    {
        return m_bDeleteOnUse;
    }
    
    public void setDamageType(String strDmgType)
    {
        if(m_regEx.stringMatchesRegEx(strDmgType, RegularExpressions.RegExID.DAMAGE_TYPE))
        {
            if(0 == strDmgType.compareTo("piercing"))
                setDamageType(DamageType.Piercing);
            else if(0 == strDmgType.compareTo("slashing"))
                setDamageType(DamageType.Slashing);
            else if(0 == strDmgType.compareTo("bashing"))
                setDamageType(DamageType.Bashing);
            else if(0 == strDmgType.compareTo("magic"))
                setDamageType(DamageType.Magic);
        }
    }
    
    public void setDamageType(DamageType dmgType)
    {
        m_damageType = dmgType;
    }
    
    public DamageType getDamageType()
    {
        return m_damageType;
    }
    
    public void setEffect(String strEffect)
    {
        if(m_regEx.stringMatchesRegEx(strEffect, RegularExpressions.RegExID.EFFECT))
        {
            if(0 == strEffect.compareTo("give_health"))
            {
                setEffect(Effect.GiveHealth);
            }
            else if(0 == strEffect.compareTo("teleport"))
            {
                setEffect(Effect.Teleport);
            }
            else if(0 == strEffect.compareTo("update_quest"))
            {
                setEffect(Effect.UpdateQuest);
            }
        }
    }
    
    public void setEffect(Effect effect)
    {
        m_effect = effect;
    }
    
    public Effect getEffect()
    {
        return m_effect;
    }
    
    public int getDamage()
    {
        return m_nDamage;
    }
    
    public void setDamage(String strDamage)
    {
        if(m_regEx.stringMatchesRegEx(strDamage, RegularExpressions.RegExID.POSITIVE_INT))
        {
            int nDamage = Integer.parseInt(strDamage);
            
            if(m_rangeCheck.checkRange(RangeChecker.RangeID.DAMAGE, nDamage))
                m_nDamage = nDamage;
        }
    }
    
    public void setDamage(int nDamage)
    {
        m_nDamage = nDamage;
    }
    
    public int getValue()
    {
        return m_nValue;
    }
    
    public void setValue(String strValue)
    {
        if(m_regEx.stringMatchesRegEx(strValue, RegularExpressions.RegExID.POSITIVE_INT))
        {
            int nValue = Integer.parseInt(strValue);
            
            if(m_rangeCheck.checkRange(RangeChecker.RangeID.VALUE, nValue))
                setValue(nValue);
        }
    }
    
    public void setValue(int nValue)
    {
        m_nValue = nValue;
    }
    
    public void setReqRoomID(int nID)
	{
		m_nReqRoomID = nID;
	}
	
	public int getReqRoomID()
	{
		return m_nReqRoomID;
	}
	
	public boolean isValidReqRoomID(String strReqRoomID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strReqRoomID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidReqRoomID(int nID)
	{
		return m_rangeCheck.checkRange(RangeChecker.RangeID.ID, nID);
	}
	
	protected RegularExpressions getRegEx()
	{
		return m_regEx;
	}
    
    public void setEffectText(String strEffectText)
    {
        m_strEffectText = strEffectText;
    }
    
    public String getEffectText()
    {
        return m_strEffectText;
    }
    
    public boolean isValidEffectText()
    {
        return getRegEx().stringMatchesRegEx(getEffectText(), RegularExpressions.RegExID.DESCRIPTION);
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
    
    public void setUpdateQuestStep(int nUpdateQuestStep)
	{
		m_nUpdateQuestStep = nUpdateQuestStep;
	}
	
	public int getUpdateQuestStep()
	{
		return m_nUpdateQuestStep;
	}
	
	public boolean isValidUpdateQuestStep(String strUpdateQuestStep)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strUpdateQuestStep, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidUpdateQuestStep(int nUpdateQuestStep)
	{
		return (nUpdateQuestStep >= 0 && nUpdateQuestStep < 100);
    }
}
