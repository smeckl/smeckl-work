package minimud_server;

import java.util.HashMap;
import java.util.Iterator;

import minimud_shared.*;


public class Room
{
	public enum ErrorCode
	{
		Success,
		Exception,
		Invalid
	}
	
	private int m_nID = 0;
	private String m_strName = "";
	private String m_strDescription = "";
	
	private RegularExpressions m_regEx = new RegularExpressions();
	
	private HashMap<String, Move> m_moves = new HashMap<String, Move>();
	private HashMap<String, UserConnectionThread> m_users = new HashMap<String, UserConnectionThread>();
	private HashMap<String, NPC> m_npcs = new HashMap<String, NPC>();
	private HashMap<String, GameObject> m_objects = new HashMap<String, GameObject>();
    private HashMap<String, Monster> m_Monsters = new HashMap<String, Monster>();
	
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
		return (nID > 0 && nID < 100);
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
	
	public void setDescription(String str_Description)
	{
		m_strDescription = str_Description;
	}
	
	public String getDescription()
	{
		return m_strDescription;
	}
	
	public boolean isValidDescription(String strDescription)
	{
		boolean bRet = false;
		
		bRet = (m_regEx.stringMatchesRegEx(strDescription, RegularExpressions.RegExID.DESCRIPTION)
				&& strDescription.length() < 2000);
		
		return bRet;
	}
	
	public void addMove(Move move)
	{
		m_moves.put(move.getDirection(), move);
	}
	
	// Returns ID of the next room.  -1 if none exists
	public int getNextRoomID(PlayerMoveMessage move)
	{
		int nNextRoom = -1;
		
		if(m_moves.containsKey(move.getDirectionString()))
			nNextRoom  = m_moves.get(move.getDirectionString()).getNextRoomID();
		
		return nNextRoom;
	}
	
	public void addUser(UserConnectionThread user, String strName)
	{
		m_users.put(strName, user);
	}
	
	public void removeUser(UserConnectionThread user)
	{
		m_users.remove(user.getUserInfo().getName());
	}
    
    public Iterator<UserConnectionThread> getUserList()    
    {
        return m_users.values().iterator();
    }
	
	public void addNPC(NPC npc)
	{
		m_npcs.put(npc.getName(), npc);
	}
	
	public NPC getNPC(String strName)
	{
		NPC npc = null;
				
		if(m_npcs.containsKey(strName))	
			npc = m_npcs.get(strName);
		
		return npc;
	}
	
	public void addObject(GameObject obj)
	{
		m_objects.put(obj.getName(), obj);
	}
	
	public GameObject getObject(String strObject)
	{
		GameObject obj = null;
				
		if(m_objects.containsKey(strObject))	
			obj = m_objects.get(strObject);
		
		return obj;
	}
    
    public void addMonster(Monster monster)
    {
        m_Monsters.put(monster.getName(), monster);
    }
	
	public boolean isValid()
	{
		boolean bValid = false;
		
		if(isValidID(getID())
				&& isValidName(getName())
				&& isValidDescription(getDescription()))
		{
			bValid = true;
		}
		
		return bValid;
	}
    
    public Monster getMonster(String strName)
	{
		Monster monster = null;
				
		if(m_Monsters.containsKey(strName))	
			monster = m_Monsters.get(strName);
		
		return monster;
	}
	
	public ErrorCode displayRoom(DisplayHelper display)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		if(!isValid())
			retVal = ErrorCode.Invalid;
		else
		{
			display.sendText("");
			display.sendText(getName());
			display.sendText("");
			display.sendText(getDescription());
			display.sendText("");
			
			// Show NPCs
			Iterator<NPC> userIter = m_npcs.values().iterator();
			
			while(userIter.hasNext())
			{
				NPC npc = userIter.next();
				
				display.sendText("You see " + npc.getName());
			}
            
            // Show Monsters
			Iterator<Monster> monsterIter = m_Monsters.values().iterator();
			
			while(monsterIter.hasNext())
			{
				Monster monster = monsterIter.next();
				
                if(Monster.State.Open == monster.getState())
                    display.sendText("A " + monster.getName() + " stalks around the room.");
			}
		}
		
		return retVal;
	}
    
    public ErrorCode refreshState()
    {
        ErrorCode retVal = ErrorCode.Success;
        
        // Refresh Monsters
        Iterator<Monster> monsterIter = m_Monsters.values().iterator();
        boolean bMonsterChanges = false;
        
        while(monsterIter.hasNext())
        {
            Monster monster = monsterIter.next();

            bMonsterChanges = monster.refreshState();
            
            if(bMonsterChanges)
            {
                Iterator<UserConnectionThread> users = m_users.values().iterator();

                while(users.hasNext())
                {
                    UserConnectionThread user = users.next();

                    if(Monster.State.Open == monster.getState())
                        GameServer.sendUserText(user, "A " + monster.getName() + " stalks around the room.");
                }
            }
        }
        
        return retVal;
    }
}
