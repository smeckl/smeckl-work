package MiniMUDShared;
import java.util.HashMap;
import java.util.Map;

public class RegularExpressions
{
	public enum RegExID
	{
		USERNAME,
		PASSWORD,
		PORT,
		IP,
		DOMAIN,
		ID,
		DESCRIPTION,
		NAME,
		DIRECTION,
		NPCTEXT,
		ACTION_TYPE,
		RESULT_TYPE
	}
	
	private Map<RegExID, String> m_regExMap = new HashMap<RegExID, String>();
	
	public RegularExpressions()
	{
		m_regExMap.put(RegExID.USERNAME, "^[A-Za-z][A-Za-z0-9]{1,29}$");
		m_regExMap.put(RegExID.PASSWORD, "^[A-Za-z0-9!@#\\$%,\\.;:\\?<>]{8,20}$");
		m_regExMap.put(RegExID.PORT, "^[1-9][0-9]{1,4}$");
		m_regExMap.put(RegExID.IP, "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
		m_regExMap.put(RegExID.DOMAIN, "^[a-z0-9_-]+(\\.[a-z0-9_-]+)*$");
		m_regExMap.put(RegExID.ID, "^[1-9][0-9]*$");
		m_regExMap.put(RegExID.DESCRIPTION, "^[a-zA-Z0-9\\-\\.\\,\\:\\?\\!\\'\\\"\\$\\\\ ]{1,2000}$");
		m_regExMap.put(RegExID.NAME, "^[a-zA-Z ]{1,50}$");
		m_regExMap.put(RegExID.DIRECTION, "^(north|south|east|west|northeast|northwest|southeast|southwest|up|down)$");
		m_regExMap.put(RegExID.NPCTEXT, "^[a-zA-Z0-9\\-\\.\\,\\:\\?\\!\\'\\\"\\$\\\\ ]{1,1000}$");
		m_regExMap.put(RegExID.ACTION_TYPE, "^(talk|kick|punch|stab|slash|push|shoot)$");
		m_regExMap.put(RegExID.RESULT_TYPE, "^(text_only|xp_reward|gold_reward|item_reward)$");
	}
	
	public boolean stringMatchesRegEx(String str, RegExID id)
	{
		boolean bRet = false;
		
		String strRegEx = m_regExMap.get(id);
		
		if(null != strRegEx)
		{
			bRet = str.matches(strRegEx);
		}
		
		return bRet;
	}
}
