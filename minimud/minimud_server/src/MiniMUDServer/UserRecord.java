package MiniMUDServer;

public class UserRecord
{
	private String m_strUsername = "";
	private byte m_pwdHash[];
	private byte m_salt[];
	
	public void setUsername(String strUsername)
	{
		m_strUsername = strUsername;
	}
	
	public String getUsername()
	{
		return m_strUsername;
	}
	
	public void setPasswordHash(byte pwdHash[])
	{
		m_pwdHash = pwdHash.clone();
	}
	
	public byte[] getPasswordHash()
	{
		return m_pwdHash.clone();
	}
	
	public void setPasswordSalt(byte pwdSalt[])
	{
		m_salt = pwdSalt.clone();
	}
	
	public byte[] getPasswordSalt()
	{
		return m_salt.clone();
	}
}
