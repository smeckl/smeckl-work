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
	
	public ErrorCode addNPC(int nRoomID, int nID, String strName, String strDescription, String strIntro)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new NPC");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into npcs values(?, ?, ?, ?, ?)");
			pstmt.setInt(1, nRoomID);
			pstmt.setInt(2,  nID);
			pstmt.setString(3, strName);
			pstmt.setString(4, strDescription);
			pstmt.setString(5, strIntro);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("Failed to insert NPC.");
			}
			else
				System.out.println("NPC creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addNPC() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode addAction(int nParentID, int nID, String strName, int nDepID, int nDepStep, int nDepCompl, int nResult)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new Action");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into actions values(?,?,?,?,?,?,?)");
			pstmt.setInt(1, nID);
			pstmt.setInt(2, nParentID);
			pstmt.setString(3,  strName);
			pstmt.setInt(4, nResult);
			pstmt.setInt(5, nDepID);
			pstmt.setInt(6, nDepStep);
            pstmt.setInt(7, nDepCompl);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("Failed to insert Action.");
			}
			else
				System.out.println("Action creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addAction() " + e);
			retVal = ErrorCode.Exception;
		}
	
		return retVal;
	}
	
	public ErrorCode addActionResult(int nID, int nParentID, String strType, String strText, int nItemID, int nValue )
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new ActionResult");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into action_results values(?,?,?,?,?,?)");
			pstmt.setInt(1, nID);
			pstmt.setInt(2, nParentID);
			pstmt.setString(3,  strType);
			pstmt.setString(4, strText);
			pstmt.setInt(5, nItemID);
			pstmt.setInt(6, nValue);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("Failed to insert ActionResult.");
			}
			else
				System.out.println("ActionResult creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addActionResult() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode addObject(int nRoomID, int nID, String strName, String strDescription)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new Object");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into objects values(?, ?, ?, ?)");
			pstmt.setInt(1, nID);
			pstmt.setInt(2,  nRoomID);
			pstmt.setString(3, strName);
			pstmt.setString(4, strDescription);

			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("Failed to insert Object.");
			}
			else
				System.out.println("Object creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addObject() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode addItem(int nID, String strName, String strDescription)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new Item");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into items values(?, ?, ?)");
			pstmt.setInt(1, nID);
			pstmt.setString(2, strName);
			pstmt.setString(3, strDescription);

			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("Failed to insert Item.");
			}
			else
				System.out.println("Item creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addItem() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode addQuest(int nID, String strName, int nRewardXP, int nFirstBonus, int nRewardGold, int nRewardItem)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new Quest");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into quests values(?,?,?,?,?,?,?)");
			pstmt.setInt(1, nID);
			pstmt.setString(2, strName);
			pstmt.setString(3, "");
			pstmt.setInt(4, nRewardGold);
			pstmt.setInt(5, nRewardXP);
			pstmt.setInt(6, nRewardItem);
			pstmt.setInt(7, nFirstBonus);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("Failed to insert Quest.");
			}
			else
				System.out.println("Quest creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addQuest() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	public ErrorCode addQuestStep(int nID, int nStepNum, String strDescription, String strHint, 
								  int nRewardXP, int nRewardGold, int nRewardItem)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new Quest");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into quest_steps values(?,?,?,?,?,?,?)");
			pstmt.setInt(1, nID);
			pstmt.setInt(2, nStepNum);
			pstmt.setString(3, strDescription);
			pstmt.setString(4, strHint);
			pstmt.setInt(5, nRewardGold);
			pstmt.setInt(6, nRewardXP);
			pstmt.setInt(7, nRewardItem);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("FAILED to insert Quest step.");
			}
			else
				System.out.println("Quest Step creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addQuestStep() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
    
    public ErrorCode addMonster(int nID, String strName, String strDescription, int nHealth,
                            int nAttackPower, int nMagicPower, int nDefense, int nMagicDefense, int nLootTableID)
    {
        ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			System.out.println("Adding new Monster");
			
			PreparedStatement pstmt = getConnection().prepareStatement("insert into monsters values(?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, nID);
			pstmt.setString(2, strName);
			pstmt.setString(3, strDescription);
			pstmt.setInt(4, nHealth);
			pstmt.setInt(5, nAttackPower);
			pstmt.setInt(6, nMagicPower);
			pstmt.setInt(7, nDefense);
            pstmt.setInt(8, nMagicDefense);
            pstmt.setInt(9, nLootTableID);
			
			if(0 == pstmt.executeUpdate())
			{
				retVal = ErrorCode.InsertFailed;
				System.out.println("FAILED to insert MONSTER.");
			}
			else
				System.out.println("Monster creation successful.");
		}
		catch(Exception e)
		{
			System.out.println("Exception in DatabaseConnector.addMonster() " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
    }
}
