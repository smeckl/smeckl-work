package minimud_shared;

public class ClientRequestInputMessage extends Message
{
	private Type m_reqType;
	private String m_strMessage = "";
	
	public enum Type
	{
		Invalid,
		Normal,
		Password
	}
	
	public ClientRequestInputMessage(Type type, String strMessage)
	{
		super(MessageID.CLIENT_REQUEST_INPUT);
		
		setInputRequestType(type);
		setMessage(strMessage);
	}
	
	public void setInputRequestType(Type type)
	{
		m_reqType = type;
	}
	
	public Type getInputRequestType()
	{
		return m_reqType;
	}
	
	public String getInputRequestTypeString()
	{
		String str = "invalid";
		
		if(Type.Normal == getInputRequestType())
			str = "normal_input";
		else if(Type.Password == getInputRequestType())
			str = "passwd_input";
		
		return str;
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
		String strOut = "request_input type = " + getInputRequestTypeString() + " message=\"" + getMessage() + "\"";
		
		return strOut;
	}
}
