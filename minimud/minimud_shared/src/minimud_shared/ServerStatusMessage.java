package minimud_shared;

public class ServerStatusMessage extends Message
{
	public enum Status
	{
		INVALID,
		LOGON_SUCCESS,
		LOGON_FAILED,
		SHUTTING_DOWN,
		LOGOUT
	}
	
	private Status m_status = Status.INVALID;
	
	public ServerStatusMessage(Status status)
	{
		super(MessageID.SERVER_STATUS);
		
		setStatus(status);
	}
	
	public void setStatus(Status status)
	{
		m_status = status;
	}
	
	public Status getStatus()
	{
		return m_status;
	}
	
	public String getStatusString()
	{
		String str = "invalid";
		
		if(Status.LOGON_SUCCESS == getStatus())
			str = "logon_success";
		else if(Status.LOGON_FAILED == getStatus())
			str = "logon_failed";
		else if(Status.LOGOUT == getStatus())
			str = "logout";
		
		return str;
	}
	
	@Override
	public String serialize()
	{
		String strOut = "server_status type=" + getStatusString();
		
		return strOut;
	}
}
