package MiniMUDShared;

public class PlayerActionMessage extends Message
{
	public enum Action
	{
		Look
	}
	
	private Action m_action;
	
	public PlayerActionMessage(Action action)
	{
		super(MessageID.ACTION);
		
		setAction(action);
	}
	
	public void setAction(Action action)
	{
		m_action = action;
	}
	
	public Action getAction()
	{
		return m_action;
	}
	
	public String getActionString()
	{
		String strOut = "";
		
		switch(getAction())
		{
		case Look:
			strOut = "look";
			break;
		}
		
		return strOut;
	}
	
	public String serialize()
	{
		String strOut = "";
		
		switch(getAction())
		{
		case Look:
			strOut = "look";
			break;
		}
		
		return strOut;
	}
}
