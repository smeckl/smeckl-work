/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minimud_server;

/**
 *
 * @author steve
 */
public class Mob
{
   
	private String m_strName = "";
    private String m_strDescription = "";
    private int m_nHealth = 0;
    private int m_nAttackPower = 0;
    private int m_nMagicPower = 0;
    private int m_nDefense = 0;
    private int m_nMagicDefense = 0;
	
	public void setName(String name)
	{
		m_strName = name;
	}
	
	public String getName()
	{
		return m_strName;
	}
    
    public void setDescription(String strDescription)
    {
        m_strDescription = strDescription;
    }
    
    public String getDescription()
    {
        return m_strDescription;
    }
    
    public void setHealth(int nHealth)
    {
        m_nHealth = nHealth;
    }
    
    public int getHealth()
    {
        return m_nHealth;
    }
    
    public void setAttackPower(int nAttackPower)
    {
        m_nAttackPower = nAttackPower;
    }
    
    public int getAttackPower()
    {
        return m_nAttackPower;
    }
    
    public void setMagicPower(int nMagicPower)
    {
        m_nMagicPower = nMagicPower;
    }
    
    public int getMagicPower()
    {
        return m_nMagicPower;
    }
    
    public void setDefense(int nDefense)
    {
        m_nDefense = nDefense;
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
}
