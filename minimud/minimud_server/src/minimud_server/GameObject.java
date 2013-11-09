package minimud_server;

import java.util.ArrayList;

import minimud_shared.RegularExpressions;

public class GameObject
{
	private int m_nID = 0;
	private int m_nRoomID = 0;
	private String m_strName = "";
	private String m_strDescription = "";

	private RegularExpressions m_regEx = new RegularExpressions();
	private ArrayList<Action> m_actions = new ArrayList<Action>();
	
	public void setID(int nID)
	{
		m_nID = nID;
	}
	
	public int getID()
	{
		return m_nID;
	}
	
	public boolean isValidID(String strID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidID(int nID)
	{
		return (getID() > 0 && getID() < 100);
	}
	
	public void setRoomID(int nID)
	{
		m_nRoomID = nID;
	}
	
	public int getRoomID()
	{
		return m_nRoomID;
	}
	
	public boolean isValidRoomID(String strRoomID)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strRoomID, RegularExpressions.RegExID.ID);
		
		return bRet;
	}
	
	public boolean isValidRoomID(int nID)
	{
		return (getRoomID() > 0 && getRoomID() < 100);
	}
	
	public void setName(String strName)
	{
		m_strName = strName;
	}
	
	public String getName()
	{
		return m_strName;
	}
	
	public boolean isValidName(String strName)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strName, RegularExpressions.RegExID.NAME);
		
		return bRet;
	}
	
	public void setDescription(String strDescription)
	{
		m_strDescription = strDescription;
	}
	
	public String getDescription()
	{
		return m_strDescription;
	}
	
	public boolean isValidDescription(String strDescription)
	{
		boolean bRet = false;
		
		bRet = m_regEx.stringMatchesRegEx(strDescription, RegularExpressions.RegExID.DESCRIPTION);
		
		return bRet;
	}
	
	public boolean isValid()
	{
		boolean bValid = false;
		
		if(isValidID(getID())
				&& isValidRoomID(getRoomID())
				&& isValidName(getName())
				&& isValidDescription(getDescription()))
		{
			bValid = true;
		}
		
		return bValid;
	}
	
	public void addAction(Action action)
	{
		m_actions.add(action);
	}
	
	public Action getAction(DatabaseConnector dbConn, String strName, String strUsername)
	{
		Action ret = null;
		Action noDepAction = null;
		Action depAction = null;
		
		// Loop through the list of actions
		for(int i = 0; i < m_actions.size(); i++)
		{
			Action action = m_actions.get(i);
			
			// Does this action match the action name?
			if(0 == action.getName().compareTo(strName))
			{
				// Now make sure the user satisfies any quest dependencies
				
				// Does this action have a quest dependency?
				if(0 == action.getQuestDependencyID()) // no dependency
				{
					noDepAction = action;
				}
				else // This action has a quest dependency
				{
					// See if the user is on that quest and step
					if(dbConn.isUserOnQuestStep(strUsername, action.getQuestDependencyID(), action.getQuestDependencyStep()))
					{
						depAction = action;
					}
				}
			}
		}
		
		if(null != depAction)
			ret = depAction;
		else if(null != noDepAction)
			ret = noDepAction;
		
		return ret;
	}
	
	protected RegularExpressions getRegEx()
	{
		return m_regEx;
	}
}
