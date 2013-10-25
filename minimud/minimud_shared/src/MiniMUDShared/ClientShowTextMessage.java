package MiniMUDShared;

public class ClientShowTextMessage extends Message
{
	private String m_strFrom = "";
	private String m_strMessage = "";
	
	public ClientShowTextMessage(String strFrom, String strMessage)
	{
		super(MessageID.CLIENT_SHOW_TEXT);
		
		setFrom(strFrom);
		setMessage(strMessage);
	}
	
	public void setFrom(String strFrom)
	{
		m_strFrom = strFrom;
	}
	
	public String getFrom()
	{
		return m_strFrom;
	}
	
	public void setMessage(String strMessage)
	{
		m_strMessage = strMessage;
	}
	
	public String getMessage()
	{
		return m_strMessage;
	}
	
	@Override
	public String serialize()
	{
		String strOut = "text_msg from=" + getFrom() + " message=\"" + getMessage() + "\"";
		
		return strOut;
	}
	
	@Override
	public String getClientDisplaytext()
	{
		String strOut = "";
		
		if(!getFrom().isEmpty())
			strOut += getFrom() + ": " + getMessage();
		else
			strOut += getMessage();
		
		return strOut;
	}
}
