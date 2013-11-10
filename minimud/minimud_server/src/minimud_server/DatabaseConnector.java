package minimud_server;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseConnector
{
	public enum ErrorCode
	{
		Success,
		Exception,
		InsertFailed
	}
    
    public enum SortBy
    {
        Gold,
        XP
    }
	
	private Connection m_conn;

	private static String m_strUrl = "jdbc:mysql://";
	private static String m_strDbName = "minimud";
	private static String m_strDriver = "com.mysql.jdbc.Driver";
	private static String m_strUserName = "";
	private static String m_strPassword = "";
	
	private static MMLogger m_logger = null;
    
	public DatabaseConnector(String strServer, int nPort, String strUser, String strPassword, MMLogger logger)
	{
		m_strUrl += strServer + ":";
		m_strUrl += nPort + "/";
		m_strUserName = strUser;
		m_strPassword = strPassword;
		
		m_logger = logger;
	}
	
	protected synchronized Connection getConnection()
	{
		return m_conn;
	}
	
	public synchronized ErrorCode connect()
	{     
		ErrorCode retVal = ErrorCode.Success;
		
        try 
        {
        	m_logger.info("Connecting to database: " + m_strUrl + m_strDbName);
        	
	        Class.forName(m_strDriver).newInstance();
	        m_conn = DriverManager.getConnection(m_strUrl + m_strDbName, m_strUserName, m_strPassword);
	        
	        m_logger.info("Connection successful!");
        } 
        catch (Exception e) 
        {
        	m_logger.severe("Exception connecting to the database " + e);
        	retVal = ErrorCode.Exception;
        }
        
        return retVal;
	}
	
	public synchronized ErrorCode disconnect()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			m_logger.info("Disconnecting from database.");
					
			if(null != m_conn && m_conn.isClosed())
				m_conn.close();
			
			m_logger.info("Disconnection successful!");
		}
		catch(Exception e)
		{
			m_logger.severe("Exception disconnecting from the database " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public synchronized boolean checkDuplicateUser(String strUserName)
	{
		boolean retVal = false;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select username from characters where username = ?");
			pstmt.setString(1, strUserName);
			
			ResultSet results = pstmt.executeQuery();
			
			if(results.first())
			{
				retVal = true;
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::checkDuplicateUser() " + e);
			retVal = true;
		}
		
		return retVal;
	}
	
	public synchronized UserRecord lookupUserRecord(String strUserName)
	{
		UserRecord userRec = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from characters where username = ?");
			pstmt.setString(1, strUserName);
			
			ResultSet results = pstmt.executeQuery();
			
			if(results.first())
			{
				userRec = new UserRecord();
				
				userRec.setPasswordHash(results.getBytes("pwd_hash"));
				userRec.setPasswordSalt(results.getBytes("pwd_salt"));
				userRec.setUsername(strUserName);
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::lookupUserRecord() " + e);
			userRec = null;
		}
		
		return userRec;
	}
	
	public synchronized ErrorCode createNewUser(String strUserName, byte pwdHash[], byte pwdSalt[])
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("insert into characters values(?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, strUserName);
			pstmt.setBytes(2,  pwdHash);
			pstmt.setBytes(3, pwdSalt);
			
			Calendar cal = Calendar.getInstance();
			java.util.Date date = cal.getTime();
			
	    	java.sql.Date curDate = new java.sql.Date(date.getTime());
	    	
			pstmt.setDate(4, curDate);
			
			pstmt.setString(5,  "");
			pstmt.setInt(6,  0);
			pstmt.setInt(7, 0);
			pstmt.setInt(8,  0);
	
			if(0 == pstmt.executeUpdate())
				retVal = ErrorCode.InsertFailed;
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::checkDuplicateUser() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
    
    public synchronized UserInfo loadUserInfo(String strUsername)
    {
        UserInfo userInfo = null;
        
        try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from characters where username = ?");
			pstmt.setString(1, strUsername);
			
			ResultSet results = pstmt.executeQuery();
			
			if(results.first())
			{
				userInfo = new UserInfo();
                
                userInfo.setUserName(results.getString("username"));
                userInfo.setDescription(results.getString("description"));
                userInfo.setGold(results.getInt("health"));
                userInfo.setXP(results.getInt("xp"));
                userInfo.setHealth(results.getInt("health"));
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::loadUserInfo() " + e);
			userInfo = null;
		}
        
        return userInfo;
    }
    
    public synchronized ErrorCode saveUserState(UserConnectionThread user)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("update characters set gold=?, xp=?, health=?"
                                                                    + " where username=?");
			
            pstmt.setInt(1, user.getUserInfo().getGold());
            pstmt.setInt(2, user.getUserInfo().getXP());
            pstmt.setInt(3, user.getUserInfo().getHealth());
            pstmt.setString(4, user.getUserInfo().getUserName());
	
			if(0 == pstmt.executeUpdate())
				retVal = ErrorCode.InsertFailed;
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::saveUserState() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	// Retrieves list of rooms.  Return value is null if there is an error.
	public synchronized ResultSet getRooms()
	{
		ResultSet results = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from rooms");
			
			results = pstmt.executeQuery();
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getRooms() " + e);
			results = null;
		}
		
		return results;
	}
	
	// Retrieves list of moves.  Return value is null if there is an error.
	public synchronized ResultSet getMovesForRoom(int nRoomID)
	{
		ResultSet results = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from moves where RoomID = ?");
			pstmt.setInt(1, nRoomID);
			
			results = pstmt.executeQuery();
			
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getMoves() " + e);
			results = null;
		}
		
		return results;
	}
	
	// Retrieves list of NPCs.  Return value is null if there is an error.
	public synchronized ResultSet getNPCsForRoom(int nRoomID)
	{
		ResultSet results = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from npcs where room = ?");
			pstmt.setInt(1, nRoomID);
			
			results = pstmt.executeQuery();
			
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getNPCsForRoom() " + e);
			results = null;
		}
		
		return results;
	}
	
	// Retrieves list of Objects.  Return value is null if there is an error.
	public synchronized ResultSet getObjectsForRoom(int nRoomID)
	{
		ResultSet results = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from objects where room = ?");
			pstmt.setInt(1, nRoomID);
			
			results = pstmt.executeQuery();
			
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getObjectsForRoom() " + e);
			results = null;
		}
		
		return results;
	}
	
	// Retrieves list of Actions.  Return value is null if there is an error.
	public synchronized ResultSet getActionsForObject(GameObject obj)
	{
		ResultSet results = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from actions where parent = ?");
			pstmt.setInt(1, obj.getID());
			
			results = pstmt.executeQuery();
			
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getActionsForNPC() " + e);
			results = null;
		}
		
		return results;
	}
	
	// Retrieves the result of an action.  Return value is null if there is an error.
	public synchronized ArrayList<ActionResult> getActionResults(int parentID)
	{
		ArrayList<ActionResult> actionResults = new ArrayList<ActionResult>();
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from action_results where parent = ?");
			pstmt.setInt(1, parentID);
			
			ResultSet results = pstmt.executeQuery();
			
            if(null != results)
            {
                while(results.next())
                {
                    ActionResult actionRes = new ActionResult();

                    actionRes.setID(results.getInt("ID"));
                    actionRes.setType(results.getString("type"));
                    actionRes.setDescription(results.getString("description"));
                    actionRes.setItemID(results.getInt("ItemID"));
                    actionRes.setValue(results.getInt("Value"));

                    actionResults.add(actionRes);
                }
            }
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getActionResults() " + e);
			actionResults = null;
		}
		
		return actionResults;
	}
	
	// Retrieves an item.  Return value is null if there is an error.
	public synchronized Item getItem(int nID)
	{
		Item item = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from items where ID = ?");
			pstmt.setInt(1, nID);
			
			ResultSet results = pstmt.executeQuery();
			
			if(null != results && results.next())
			{
				item = new Item();
				
				item.setID(results.getInt("ID"));
				item.setName(results.getString("name"));
				item.setDescription(results.getString("description"));
			}
			
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getActionResult() " + e);
			item = null;
		}
		
		return item;
	}
	
	// Retrieves list of Items for a user.  Return value is null if there is an error.
	public synchronized ResultSet getItemsForUser(String strUser)
	{
		ResultSet results = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from inventory where username = ?");
			pstmt.setString(1, strUser);
			
			results = pstmt.executeQuery();
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getItemsForUser() " + e);
			results = null;
		}
		
		return results;
	}
	
	public synchronized ErrorCode addItemToInventory(int nID, String strUserName)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into inventory values(?, ?)");
			pstmt.setInt(1, nID);
			pstmt.setString(2, strUserName);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				m_logger.severe("Failed to insert item into inventory.");
			}
			else
				m_logger.info("Item " + nID + "added to the inventory of " + strUserName + ".");
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector.addItemToInventory() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public synchronized ErrorCode addQuestToUser(int nQuestID, String strUserName)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into quest_status values(?,?,?,?)");
			pstmt.setString(1, strUserName);
			pstmt.setInt(2, nQuestID);
			pstmt.setInt(3, 1);
			pstmt.setInt(4, 0);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				m_logger.severe("Failed to insert quest into quest_status.");
			}
			else
				m_logger.info("Quest" + nQuestID + "added to the quest log of " + strUserName + ".");
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector.addQuestToUser() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public synchronized boolean isUserOnQuestStep(String strUser, int nQuestID, int nQuestStep)
	{
		boolean bRet = false;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from quest_status where " +
																"username = ? && quest_id = ? and step = ?");
			pstmt.setString(1, strUser);
			pstmt.setInt(2, nQuestID);
			pstmt.setInt(3, nQuestStep);
			
			ResultSet results = pstmt.executeQuery();
			
			if(null != results && results.next())
			{
				bRet = true;
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::isUserOnQuestStep() " + e);
			bRet = false;
		}
		
		return bRet;
	}
	
	public synchronized ErrorCode updateUserQuestStep(int nQuestID, int nNewStep, String strUserName)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{			
			PreparedStatement pstmt = getConnection().prepareStatement("update quest_status set step=?" +
									" where quest_id=? and username=?");
			
			pstmt.setInt(1, nNewStep);
			pstmt.setInt(2, nQuestID);
			pstmt.setString(3, strUserName);			
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				m_logger.severe("Failed to update quest_status.");
			}
			else
				m_logger.info("Quest " + nQuestID + " updated to step " + 
								nNewStep + " for user " + strUserName + ".");
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector.updateUserQuestStep() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	// Retrieves Quest object.  Return value is null if there is an error.
	public synchronized Quest getQuest(int nID)
	{
		Quest quest = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from quests where ID = ?");
			pstmt.setInt(1, nID);
			
			ResultSet results = pstmt.executeQuery();
			
			if(null != results && results.next())
			{
				quest = new Quest();
				
				quest.setID(results.getInt("ID"));
				quest.setName(results.getString("name"));
				
				String strFC = results.getString("first_completion_user");
				
				if(null != strFC)
					quest.setFirstCompleteUser(strFC);
				
				quest.setRewardGold(results.getInt("reward_gold"));
				quest.setRewardXP(results.getInt("reward_xp"));
				quest.setRewardItemID(results.getInt("reward_item"));
				quest.setFirstBonus(results.getInt("first_bonus"));
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getQuest() " + e);
			quest = null;
		}
		
		return quest;
	}
	
	public synchronized ErrorCode setQuestFirstCompletion(int nQuestID, String strUserName)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{			
			PreparedStatement pstmt = getConnection().prepareStatement("update quests set first_completion_user=?" +
									" where ID=?");
			
			pstmt.setString(1, strUserName);
			pstmt.setInt(2, nQuestID);		
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				m_logger.severe("Failed to set quest first completion.");
			}
			else
				m_logger.info("Quest " + nQuestID + " first completed by " + strUserName + ".");
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector.setQuestFirstCompletion() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public synchronized ErrorCode setUserQuestCompleted(int nQuestID, String strUserName)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{			
			PreparedStatement pstmt = getConnection().prepareStatement("update quest_status set completed=1, step=0" +
									" where quest_id=? and username=?");
			
			pstmt.setInt(1, nQuestID);
			pstmt.setString(2, strUserName);		
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				m_logger.severe("Failed to set quest completed.");
			}
			else
				m_logger.info("Quest " + nQuestID + " completed by " + strUserName + ".");
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector.setUserQuestCompleted() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
    
    public synchronized boolean userCompletedQuest(int nQuestID, String strUserName)
	{
		boolean bRet = false;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from quest_status where " +
																"username = ? && quest_id = ? and completed = 1");
			pstmt.setString(1, strUserName);
			pstmt.setInt(2, nQuestID);
			
			ResultSet results = pstmt.executeQuery();
			
			if(null != results && results.next())
			{
				bRet = true;
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::userCompletedQuest() " + e);
			bRet = false;
		}
		
		return bRet;
	}
    
    public synchronized ArrayList<UserInfo> getSortedUserList(SortBy sortBy)
	{
		ArrayList<UserInfo> actionResults = new ArrayList<UserInfo>();
		
		try
		{
            String sqlString = "";
            
            if(SortBy.Gold == sortBy)
            {
                sqlString = "select * from characters order by gold desc";
            }
            else if(SortBy.XP == sortBy)
            {
                sqlString = "select * from characters order by xp desc";
            }
            
			PreparedStatement pstmt = getConnection().prepareStatement(sqlString);
			
			ResultSet results = pstmt.executeQuery();
			
            if(null != results)
            {
                while(results.next())
                {
                    UserInfo info = new UserInfo();
                    
                    info.setUserName(results.getString("username"));
                    info.setGold(results.getInt("gold"));
                    info.setXP(results.getInt("xp"));
                    
                    actionResults.add(info); 
                }
            }
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getSortedUserList() " + e);
			actionResults = null;
		}
		
		return actionResults;
	}
}
