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
    
	private int m_nID = 0;
	private String m_strName = "";
	private String m_strDescription = "";
    private boolean m_bIsWeapon = false;
    private DamageType m_damageType = DamageType.NotSet;
    private int m_nDamage = -1;
	
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
    
    public void setDamageType(String strDmgType)
    {
        if(m_regEx.stringMatchesRegEx(strDmgType, RegularExpressions.RegExID.POSITIVE_INT))
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
	
	protected RegularExpressions getRegEx()
	{
		return m_regEx;
	}
}
