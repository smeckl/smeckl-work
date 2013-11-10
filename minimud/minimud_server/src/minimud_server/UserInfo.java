package minimud_server;

public class UserInfo extends Mob
{
    enum CharType
    {
        NotSet,
        Fighter,
        Mage
    }
    
    private CharType m_charType;
    private int m_nGold = 0;
    private int m_nXP;
    
    public UserInfo()
    {
        super();
    }
    
    public void setCharType(CharType type)
    {
        m_charType = type;
    }
    
    public void setCharType(int nType)
    {
        switch(nType)
        {
            case 0:
                m_charType = CharType.NotSet;
                break;
                
            case 1:
                m_charType = CharType.Fighter;
                break;
                
            case 2:
                m_charType = CharType.Mage;
                break;
                
            default:
                m_charType = CharType.NotSet;
                break;
        }
    }
    
    public CharType getCharType()
    {
        return m_charType;
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
}
