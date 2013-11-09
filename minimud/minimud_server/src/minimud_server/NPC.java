package minimud_server;

import minimud_shared.RegularExpressions;


public class NPC extends GameObject
{
	private String m_strIntro = "";
	
	public NPC()
	{
		super();
	}
	
	public void setIntro(String strIntro)
	{
		m_strIntro = strIntro;
	}
	
	public String getIntro()
	{
		return m_strIntro;
	}
	
	public boolean isValidIntro(String strIntro)
	{
		boolean bRet = false;
		
		bRet = getRegEx().stringMatchesRegEx(strIntro, RegularExpressions.RegExID.NPCTEXT);
		
		return bRet;
	}
	
	public boolean isValid()
	{
		boolean bValid = false;
		
		if(super.isValid() && isValidIntro(getIntro()))
		{
			bValid = true;
		}
		
		return bValid;
	}
	
}
