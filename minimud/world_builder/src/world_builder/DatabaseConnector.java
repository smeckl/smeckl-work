package world_builder;

import java.sql.*;
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

}
