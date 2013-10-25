package MiniMUDServer;
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Logger;

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
	
	private static Logger m_logger = null;
    
	public DatabaseConnector(String strServer, int nPort, String strUser, String strPassword, Logger logger)
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
}
