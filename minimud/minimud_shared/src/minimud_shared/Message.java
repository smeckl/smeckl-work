package minimud_shared;
public class Message
{
	private int m_nMessageId;
	
	public Message(int nId)
	{
		setMessageId(nId);
	}
	
	protected void setMessageId(int nId)
	{
		m_nMessageId = nId;
	}
	
	public int getMessageId()
	{
		return m_nMessageId;
	}
	
	public String serialize()
	{
		return "";
	}
	
	public String getClientDisplaytext()
	{
		return "";
	}
	
	protected static String trimQuotes(String str)
	{
		if('\"' == str.charAt(0) && '\"' == str.charAt(str.length()-1))
				return str.substring(1, str.length()-1);
		
		return str;
	}
}
