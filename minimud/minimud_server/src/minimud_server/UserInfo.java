package minimud_server;

public class UserInfo
{
	private String m_strUserName = "";
    private String m_strDescription = "";
    private int m_nGold = 0;
    private int m_nXP = 0;
    private int m_nHealth = 0;
	
	public void setUserName(String userName)
	{
		m_strUserName = userName;
	}
	
	public String getUserName()
	{
		return m_strUserName;
	}
    
    public void setDescription(String strDescription)
    {
        m_strDescription = strDescription;
    }
    
    public void setGold(int nGold)
    {
        m_nGold = nGold;
    }
    
    public int getGold()
    {
        return m_nGold;
    }
    
    public void setXP(int nXP)
    {
        m_nXP = nXP;
    }
    
    public int getXP()
    {
        return m_nXP;
    }
    
    public void setHealth(int nHealth)
    {
        m_nHealth = nHealth;
    }
    
    public int getHealth()
    {
        return m_nHealth;
    }
}
