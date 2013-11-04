package MiniMUDServer;
import java.sql.*;
import java.util.Calendar;

public class DatabaseConnector
{
	public enum ErrorCode
	{
		Success,
		Exception,
		InsertFailed
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
	
	// Retrieves list of rooms.  Return value is null if there is an error.
	public ResultSet getRooms()
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
	public ResultSet getMovesForRoom(int nRoomID)
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
	public ResultSet getNPCsForRoom(int nRoomID)
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
	public ResultSet getObjectsForRoom(int nRoomID)
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
	public ResultSet getActionsForObject(GameObject obj)
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
	public ActionResult getActionResult(int parentID, int resultID)
	{
		ActionResult actionRes = null;
		
		try
		{
			PreparedStatement pstmt = getConnection().prepareStatement("select * from action_results where ID = ? and parent = ?");
			pstmt.setInt(1, resultID);
			pstmt.setInt(2, parentID);
			
			ResultSet results = pstmt.executeQuery();
			
			if(null != results && results.next())
			{
				actionRes = new ActionResult();
				
				actionRes.setID(results.getInt("ID"));
				actionRes.setType(results.getString("type"));
				actionRes.setDescription(results.getString("description"));
				actionRes.setItemID(results.getInt("ItemID"));
				actionRes.setValue(results.getInt("Value"));
			}
			
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in DatabaseConnector::getActionResult() " + e);
			actionRes = null;
		}
		
		return actionRes;
	}
	
	// Retrieves an item.  Return value is null if there is an error.
	public Item getItem(int nID)
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
	public ResultSet getItemsForUser(String strUser)
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
	
	public ErrorCode addItemToInventory(int nID, String strUserName)
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
			m_logger.severe("Exception in DatabaseConnector.addRoom() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
}
