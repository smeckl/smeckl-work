package minimud_shared;

public class PlayerActionMessage extends Message
{
	public enum Action
	{
		Look,
		Talk,
		Punch,
		Kick,
		Stab,
		Slash,
		Push,
		Shoot,
        Attack,
		Take,
		Drop,
		Give,
        Leaders
	}
	
	private Action m_action;
	private String m_strObject;
	private String m_strSubject;
	
	public PlayerActionMessage(Action action, String strObject, String strSubject)
	{
		super(MessageID.ACTION);
		
		setAction(action);
		setObject(strObject);
		setSubject(strSubject);
	}
	
	public void setAction(Action action)
	{
		m_action = action;
	}
	
	public Action getAction()
	{
		return m_action;
	}
	
	public void setObject(String strObject)
	{
		m_strObject = strObject;
	}
	
	public String getObject()
	{
		return m_strObject;
	}
	
	public void setSubject(String strSubject)
	{
		m_strSubject = strSubject;
	}
	
	public String getSubject()
	{
		return m_strSubject;
	}
	
	public String getActionString()
	{
		String strOut = "";
		
		switch(getAction())
		{
		case Look:
			strOut = "look";
			break;
			
		case Talk:
			strOut = "talk";
			break;
			
		case Punch:
			strOut = "punch";
			break;
			
		case Kick:
			strOut = "kick";
			break;
			
		case Stab:
			strOut = "stab";
			break;
			
		case Slash:
			strOut = "slash";
			break;
			
		case Push:
			strOut = "push";
			break;
			
		case Shoot:
			strOut = "shoot";
			break;
		
        case Attack:
            strOut = "attack";
            break;
            
		case Take:
			strOut = "take";
			break;
			
		case Drop:
			strOut = "drop";
			break;
			
		case Give:
			strOut = "give";
            break;
            
        case Leaders:
            strOut = "leaders";
            break;
		}
		
		return strOut;
	}
	
	public String serialize()
	{
		String strOut = "";
		
		strOut += getActionString();
		
		if(!getObject().isEmpty())
			strOut += " " + getObject();
		
		return strOut;
	}
}
