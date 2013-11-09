package minimud_shared;

public class UserLogoutMessage extends Message
{
	public UserLogoutMessage()
	{
		super(MessageID.USER_LOGOUT);
	}
	
	@Override
	public String serialize()
	{
		String strOut = "quit";
		
		return strOut;
	}
}
