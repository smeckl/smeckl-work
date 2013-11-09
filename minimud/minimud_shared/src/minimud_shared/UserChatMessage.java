package minimud_shared;

public class UserChatMessage extends Message
{
	private String m_strFromUser = "";
	private String m_strToUser = "";
	private String m_strMessage = "";

	public enum MsgType
	{
		Tell,
		Say,
		Shout,
		Whisper
	}
	
	private MsgType m_msgType;
	
	public UserChatMessage()
	{
		super(MessageID.USER_CHAT);
	}
	
	public void setFromUser(String fromUser)
	{
		m_strFromUser = fromUser;
	}
	
	public String getFromUser()
	{
		return m_strFromUser;
	}
	
	public void setToUser(String toUser)
	{
		m_strToUser = toUser;
	}
	
	public String getToUser()
	{
		return m_strToUser;
	}
	
	public void setMessage(String strMessage)
	{
		m_strMessage = strMessage;
	}
	
	public String getMessage()
	{
		return m_strMessage;
	}
	
	public void setMsgType(MsgType type)
	{
		m_msgType = type;
	}
	
	public MsgType getMsgType()
	{
		return m_msgType;
	}
	
	public String serialize()
	{
		String strOut = "";
		
		if(MsgType.Say == getMsgType())
		{
			strOut = "/say \"" + getMessage() + "\"";
		}
		else if(MsgType.Shout == getMsgType())
		{
			strOut = "/shout \"" + getMessage() + "\"";
		}
		else if(MsgType.Tell == getMsgType())
		{
			strOut = "/tell " + getToUser() + " \"" + getMessage() + "\"";
		}
		else if(MsgType.Whisper == getMsgType())
		{
			strOut = "/whisper " + getToUser() + " \"" + getMessage() + "\"";
		}
		
		return strOut;
	}
}
