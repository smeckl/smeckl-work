/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minimud_server;

import minimud_shared.RegularExpressions;

/**
 *
 * @author steve
 */
public class Mob
{
    RegularExpressions regEx = new RegularExpressions();
    
    private int m_nID = 0;
	private String m_strName = "";
    private String m_strDescription = "";
    private int m_nHealth = 0;
    private int m_nAttackPower = 0;
    private int m_nMagicPower = 0;
    private int m_nDefense = 0;
    private int m_nMagicDefense = 0;
	
    public void setID(int nID)
    {
        m_nID = nID;
    }
    
    public int getID()
    {
        return m_nID;
    }
    
    public boolean isValidID(int nID)
    {
        return (nID > 0 && nID < 100);
    }
    
	public void setName(String name)
	{
		m_strName = name;
	}
	
	public String getName()
	{
		return m_strName;
	}
    
    public boolean isValidName(String strName)
    {
        return regEx.stringMatchesRegEx(strName, RegularExpressions.RegExID.NAME);
    }
    
    public void setDescription(String strDescription)
    {
        m_strDescription = strDescription;
    }
    
    public String getDescription()
    {
        return m_strDescription;
    }
    
    public boolean isValidDescription(String strDesc)
    {
        return regEx.stringMatchesRegEx(strDesc, RegularExpressions.RegExID.DESCRIPTION);
    }
    
    public void setHealth(int nHealth)
    {
        m_nHealth = nHealth;
    }
    
    public int getHealth()
    {
        return m_nHealth;
    }
    
    public boolean isValidHealth(String strHealth)
    {
        return (regEx.stringMatchesRegEx(strHealth, RegularExpressions.RegExID.POSITIVE_INT))
                && isValidHealth(Integer.parseInt(strHealth));
    }
    
    public boolean isValidHealth(int nHealth)
    {
        return (nHealth >= 0 && nHealth < 1000);
    }
    
    public void setAttackPower(int nAttackPower)
    {
        m_nAttackPower = nAttackPower;
    }
    
    public int getAttackPower()
    {
        return m_nAttackPower;
    }
    
    public boolean isValidAttackPower(String strAttackPower)
    {
        return (regEx.stringMatchesRegEx(strAttackPower, RegularExpressions.RegExID.POSITIVE_INT))
                && isValidAttackPower(Integer.parseInt(strAttackPower));
    }
    
    public boolean isValidAttackPower(int nAttackPower)
    {
        return (nAttackPower >= 0 && nAttackPower < 100);
    }
    
    public void setMagicPower(int nMagicPower)
    {
        m_nMagicPower = nMagicPower;
    }
    
    public int getMagicPower()
    {
        return m_nMagicPower;
    }
    
    public boolean isValidMagicPower(String strMagicPower)
    {
        return (regEx.stringMatchesRegEx(strMagicPower, RegularExpressions.RegExID.POSITIVE_INT))
                && isValidMagicPower(Integer.parseInt(strMagicPower));
    }
    
    public boolean isValidMagicPower(int nMagicPower)
    {
        return (nMagicPower >= 0 && nMagicPower < 100);
    }
    
    public void setDefense(int nDefense)
    {
        m_nDefense = nDefense;
    }
    
    public boolean isValidDefense(String strDefense)
    {
        return (regEx.stringMatchesRegEx(strDefense, RegularExpressions.RegExID.POSITIVE_INT))
                && isValidDefense(Integer.parseInt(strDefense));
    }
    
    public boolean isValidDefense(int nDefense)
    {
        return (nDefense >= 0 && nDefense < 100);
    }
    
    public int getDefense()
    {
        return m_nDefense;
    }
    
    public void setMagicDefense(int nMagicDefense)
    {
        m_nMagicDefense = nMagicDefense;
    }
    
    public int getMagicDefense()
    {
        return m_nMagicDefense;
    }
    
    public boolean isValidMagicDefense(String strMagicDefense)
    {
        return (regEx.stringMatchesRegEx(strMagicDefense, RegularExpressions.RegExID.POSITIVE_INT))
                && isValidMagicDefense(Integer.parseInt(strMagicDefense));
    }
    
    public boolean isValidMagicDefense(int nMagicDefense)
    {
        return (nMagicDefense >= 0 && nMagicDefense < 100);
    }
    
    public boolean isValid()
    {
        return (isValidID(getID())
                && isValidName(getName())
                && isValidDescription(getDescription())
                && isValidHealth(getHealth())
                && isValidAttackPower(getAttackPower())
                && isValidMagicPower(getMagicPower())
                && isValidDefense(getDefense())
                && isValidMagicDefense(getMagicDefense()));
    }
}
