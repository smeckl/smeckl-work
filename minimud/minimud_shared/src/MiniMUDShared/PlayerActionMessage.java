package MiniMUDShared;

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
		Take,
		Drop
	}
	
	private Action m_action;
	private String m_strObject;
	
	public PlayerActionMessage(Action action, String strObject)
	{
		super(MessageID.ACTION);
		
		setAction(action);
		setObject(strObject);
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
		
		case Take:
			strOut = "take";
			break;
			
		case Drop:
			strOut = "drop";
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
