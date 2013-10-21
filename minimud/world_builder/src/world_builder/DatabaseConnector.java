package world_builder;

import java.sql.*;

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
    
	public DatabaseConnector(String strServer, int nPort, String strUser, String strPassword)
	{
		m_strUrl += strServer + ":";
		m_strUrl += nPort + "/";
		m_strUserName = strUser;
		m_strPassword = strPassword;
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
        	System.out.println("Connecting to database: " + m_strUrl + m_strDbName);
        	
	        Class.forName(m_strDriver).newInstance();
	        m_conn = DriverManager.getConnection(m_strUrl + m_strDbName, m_strUserName, m_strPassword);
	        
	        System.out.println("Connection successful!");
        } 
        catch (Exception e) 
        {
        	System.out.println("Exception connecting to the database " + e);
        	retVal = ErrorCode.Exception;
        }
        
        return retVal;
	}
	
	public synchronized ErrorCode disconnect()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Disconnecting from database.");
					
			if(null != m_conn && m_conn.isClosed())
				m_conn.close();
			
			System.out.println("Disconnection successful!");
		}
		catch(Exception e)
		{
			System.out.println("Exception disconnecting from the database " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode dropTable(String strTableName)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Dropping table: " + strTableName);
			
			// This looks like an insecure use of prepared statements.  It is not for a couple of reasons:
			// 1) It does not use input from the user.  strTableName is hard coded in the calling function.
			// 2) Table names are not escaped, so using pstmt.setString() will cause the statement to fail
			//    due to a syntax error.
			PreparedStatement pstmt = getConnection().prepareStatement("drop table " + strTableName);
			
			pstmt.executeUpdate();
			
			System.out.println("Table drop successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.dropTable() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	// DO NOT CALL THIS FUNCTION WITH USER INPUT --- EVER!
	// It is essentially just runs an update with a raw query.
	public ErrorCode addTable(String strQuery)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new table: " + strQuery);
			
			PreparedStatement pstmt = getConnection().prepareStatement(strQuery);
			
			pstmt.executeUpdate();
			
			System.out.println("Table creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addTable() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode addRoom(int nID, String strName, String strDescription)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new room");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into rooms values(?, ?, ?)");
			pstmt.setInt(1, nID);
			pstmt.setString(2, strName);
			pstmt.setString(3, strDescription);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("Failed to insert room.");
			}
			else
				System.out.println("Room creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addRoom() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode addMove(int nRoomID, String strDirection, int nNextRoomID, String strDescription)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new move");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into moves values(?, ?, ?, ?)");
			pstmt.setInt(1, nRoomID);
			pstmt.setString(2, strDirection);
			pstmt.setInt(3, nNextRoomID);
			pstmt.setString(4, strDescription);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("Failed to insert move.");
			}
			else
				System.out.println("Move creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addMove() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
}
