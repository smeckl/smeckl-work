package MiniMUDServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import MiniMUDShared.*;

@SuppressWarnings("deprecation")
public class UserConnectionThread extends Thread
{
	private Socket m_socket;
	private PrintWriter m_textOut;
	private BufferedReader m_textIn;
	private GameServer m_game = null;
	private DatabaseConnector m_dbConn = null;
	private UserSessionState m_state = UserSessionState.Unauthenticated;
	private UserInfo m_userInfo = new UserInfo();
	
	private Semaphore m_stopSem = null;
	
	private static Logger m_logger = null;
	
	private Room m_curRoom = null;
	
	private DisplayHelper m_display = null;
	
	public enum ErrorCode
	{
		Success,
		Exception,
		InvalidNetworkConnection,
		InvalidCommand,
		AccessDenied,
		ObjectCreationFailed,
		CreateUserFailed,
		CommandProcessingFailed,
		DisplayError
	}
	
	public enum UserSessionState
	{
		Unauthenticated,
		Playing,
		LoggingOut
	};
	
	public UserConnectionThread(Socket socket, GameServer game, DatabaseConnector dbConn, Semaphore stopSem, Logger logger)
	{
		setSocket(socket);
		setGameServer(game);
		setDatabaseConnector(dbConn);
		m_stopSem = stopSem;
		m_logger = logger;
	}
	
	public void setSocket(Socket socket)
	{
		m_socket = socket;
	}
	
	private Socket getSocket()
	{
		return m_socket;
	}
	
	private void setGameServer(GameServer game)
	{
		m_game = game;
	}
	
	private GameServer getGameServer()
	{
		return m_game;
	}
	
	private void setDatabaseConnector(DatabaseConnector dbConn)
	{
		m_dbConn = dbConn;
	}
	
	private DatabaseConnector getDatabaseConnector()
	{
		return m_dbConn;
	}
	
	public void setUserState(UserSessionState newState)
	{
		m_state = newState;
	}
	
	public UserSessionState getUserState()
	{
		return m_state;
	}
	
	public UserInfo getUserInfo()
	{
		return m_userInfo;
	}
	
	public void setCurrentRoom(Room curRoom)
	{
		m_curRoom = curRoom;
	}
	
	public Room getCurrentRoom()
	{
		return m_curRoom;
	}
	
	// Determines if there is a message ready to be read
    public boolean MessageReady()
    {
        boolean bRetVal = true;
        
        try
        {
            bRetVal = m_textIn.ready();
        }
        catch(IOException e)
        {
            m_logger.severe("Encountered exception in UserConnectionThread::MessageReady(): " + e);
            bRetVal = false;
        }
        
        return bRetVal;
    }
    
    // Reads one line of text from the TCP socket
    public String ReceiveMessage()
    {
        String out = null;
        
        try
        {                        
            out = m_textIn.readLine();
        }
        // If receive() call timed out, then return null
        catch(SocketTimeoutException e)
        {
            out = null;
        }
        // An IO exception was caught.  Return null
        catch(IOException e)
        {
            out = null;
        }
        
        return out;
    }
	
	public void run()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			while(true)
			{
				retVal = initializeGameSession();
				
				setUserState(UserSessionState.Unauthenticated);
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("UserConnectionThread::run() - Initialization failed.");
					break;
				}
				
				retVal = printWelcomeMessage(); 
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("UserConnectionThread::run() - User logon failed.");
					break;
				}
				
				retVal = loginUser(); 
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("UserConnectionThread::run() - User logon failed.");
					break;
				}
				
				setUserState(UserSessionState.Playing);
				
				getCurrentRoom().displayRoom(m_display);
					
				m_socket.setSoTimeout(1000); // Don't want reads to block forever
				
				while(ErrorCode.Success == retVal 
					  && 1 == m_stopSem.availablePermits()
					  && UserSessionState.Playing == getUserState()
					  && getSocket().isConnected())
				{	
			        String inputLine;
			
			        if((inputLine = ReceiveMessage()) != null)
			        {
			        	Message msg = parseClientCommand(inputLine);
					    
					    if(null != msg)
					    {
					    	retVal = executeClientCommand(msg);	    		
					    }
					    else
					    	m_display.sendText("Invalid command.");
			        }		           
				}
				
				retVal = teardownGameSession();
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("UserConnectionThread::run() - Session teardown failed.");
					break;
				}
				
				break;
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in UserConnectionThread::run() - " + e);
			retVal = ErrorCode.Exception;
		}
	}
	
	private ErrorCode initializeGameSession()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			while(true)
			{
				m_textOut = new PrintWriter(getSocket().getOutputStream(), true);
				
				if(null == m_textOut)
				{
					m_logger.severe("Error in UserConnectionThread::initializeGameSession() - Failed to create PrintWriter()");
					retVal = ErrorCode.ObjectCreationFailed;
					break;
				}
				
		        m_textIn = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
		        
		        if(null == m_textIn)
		        {
		        	m_logger.severe("Error in UserConnectionThread::initializeGameSession() - Failed to create BufferedReader()");
		        	retVal = ErrorCode.ObjectCreationFailed;
					break;
		        }
		        
		        m_display = new DisplayHelper(m_textOut, m_textIn, m_logger);
		        
		        if(null == m_display)
		        {
		        	m_logger.severe("Error in UserConnectionThread::initializeGameSession() - Failed to create DisplayHelper()");
		        	retVal = ErrorCode.ObjectCreationFailed;
					break;
		        }	
		        
		        break;
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in UserConnectionThread::initializeGameSession() - " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode teardownGameSession()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			// Close the streams and the socket
	        m_logger.info("Closing PrintWriter");
	        if(null != m_textOut)
	        	m_textOut.close();
	        
	        m_logger.info("Closing BufferedReader");
	        if(null != m_textIn)
	        	m_textIn.close();
	        
	        m_logger.info("Closing SSL socket");
	        
	        if(!getSocket().isClosed())
	        	getSocket().close();
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in UserConnectionThread::teardownGameSession() - " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	
	
	private ErrorCode printWelcomeMessage()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			m_display.sendText("Welcome to MiniMUD!");
			m_display.sendText("MiniMUD is a fledgeling text-based adventure.  Please obey all of the rules");
			m_display.sendText("and be curteous to your fellow players");
			m_display.sendText("");
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in UserConnectionThread::teardownGameSession() - " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode loginUser()
	{
		ErrorCode retVal = ErrorCode.Success;
		RegularExpressions regExprs = new RegularExpressions();
		
		try
		{	
			while(ErrorCode.Success == retVal)
			{	
				m_display.sendText("Do you wish to create an account or log in with an existing one?");
				m_display.sendText("1) Create new");
				m_display.sendText("2) Use existing");
				m_display.sendText("3) Quit");
				
		        String inputLine = m_display.requestClientInput(ClientRequestInputMessage.Type.Normal, "Selection: ");
		        
		        if(0 == inputLine.compareTo("1"))
		        {
		        	String strUserName = "";
		        	String strPwd1 = "";
		        	String strPwd2 = "";
		        	
		        	boolean bGoodUserName = false;
		        	
		        	while(!bGoodUserName)
		        	{
		        		inputLine = m_display.requestClientInput(ClientRequestInputMessage.Type.Normal, 
		        										"Enter your new character name: ");
			        	
		        		if(!regExprs.stringMatchesRegEx(inputLine, RegularExpressions.RegExID.USERNAME))
		        		{
		        			m_display.sendText("Username cannot exceed 30 characters.  Try another.");
		        		}
		        		else
		        		{
				        	if(!getDatabaseConnector().checkDuplicateUser(inputLine))
				        	{
				        		bGoodUserName = true;
				        		strUserName = inputLine;
				        	}
				        	else
				        	{
				        		// input already validated against regular expression.  It is safe
				        		// to send back to the client.
				        		m_display.sendText("Username " + inputLine + " is already in use.  Try another.");
				        	}
		        		}
		        	}
		        	
		        	boolean bGoodPwd = false;
		        	
		        	while(!bGoodPwd)
		        	{        	
			        	strPwd1 = m_display.requestClientInput(ClientRequestInputMessage.Type.Password, 
			        			 						"Enter a new password: ");			        				   
			        	
			        	strPwd2 = m_display.requestClientInput(ClientRequestInputMessage.Type.Password, 
			        									"Re-enter your new password: ");
			        	
			        	if(0 != strPwd1.compareTo(strPwd2))			        		
			        		m_display.sendText("The passwords to not match.");
			        	else if(!regExprs.stringMatchesRegEx(strPwd1, RegularExpressions.RegExID.PASSWORD))
			        	{
			        		m_display.sendText("The password does not comply with the MiniMud password policy.");
			        		m_display.sendText("Password must be 8-20 characters and may contain letters, numbers, and");
			        		m_display.sendText("the following characters:  !@#$%,.;:?<>");
			        	}
			        	else
			        		bGoodPwd = true;
		        	}
		        	
		        	byte salt[] = SecurityHelper.getPwdSalt();
		        	
		        	byte hash[] = SecurityHelper.hashPassword(strPwd1, salt);
		        	
		        	if(DatabaseConnector.ErrorCode.Success !=
		        				getDatabaseConnector().createNewUser(strUserName, hash, salt))
    				{
		        		m_logger.info("Failed to create new user");
		        		retVal = ErrorCode.CreateUserFailed;
    				}
		        	else
		        	{
		        		m_logger.info("User created correctly.");
		        		m_display.sendCommand(new ServerStatusMessage(ServerStatusMessage.Status.LOGON_SUCCESS));
		        		
		        		// Authenticated.  Add this user thread to the game server.
		        		m_userInfo.setUserName(strUserName);
	        			getGameServer().addUser(this);
		        	}
			        	
		        	break;
		        }
		        else if(0 == inputLine.compareTo("2"))
		        {
		        	String strUserName = "";
		        	String strPwd = "";
		        	
		        	boolean bLoggedIn = false;
		        	
		        	while(!bLoggedIn)
		        	{
		        		strUserName = m_display.requestClientInput(ClientRequestInputMessage.Type.Normal, 
		        										"Enter your character name: ");
			        		        	
			        	strPwd = m_display.requestClientInput(ClientRequestInputMessage.Type.Password, 
			        			 						"Enter your password: ");
			        	
			        	UserRecord userRec = m_dbConn.lookupUserRecord(strUserName);
			        	
			        	if(null == userRec)
			        	{
			        		m_display.sendText("Login failed.  Please try again.");
			        	}
			        	else
			        	{
			        		bLoggedIn = SecurityHelper.verifyPassword(strPwd, 
			        												userRec.getPasswordHash(), 
			        												userRec.getPasswordSalt());
			        		
			        		if(bLoggedIn)
			        		{
			        			m_logger.info("User " + strUserName + " logged in successfully.");
			        			m_display.sendCommand(new ServerStatusMessage(ServerStatusMessage.Status.LOGON_SUCCESS));
			        			
			        			// Authenticated.  Add this user thread to the game server.
			        			m_userInfo.setUserName(strUserName);
			        			getGameServer().addUser(this);
			        		}
			        		else
			        			m_display.sendText("Login failed.  Please try again.");
			        	}
		        	}
		        	
		        	break;
		        }
		        else if(0 == inputLine.compareTo("3"))
		        {
		        	m_display.sendCommand(new UserLogoutMessage());
		        	
		        	break;
		        }
		        else
		        {
		        	m_display.sendText("Invalid selection.");
		        }
			}
		}
		catch(Exception e)
		{
			m_logger.severe("Exception in UserConnectionThread::loginUser() - " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	@SuppressWarnings("deprecation")
	private static Message parseClientCommand(String strMsg)
	{
		Message msg = null;
		
		try
		{
			MessageParser parser = new MessageParser(new StringReader(strMsg));
		    
			parser.parse();
			
			msg = parser.getLastMessage();
		}
		catch(Exception e)
		{
			m_logger.severe("Invalid Server Message.");
			msg = null;
		}
		
		return msg;
	}
	
	private ErrorCode executeClientCommand(Message msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			if(MessageID.USER_LOGOUT == msg.getMessageId())
			{
				setUserState(UserSessionState.LoggingOut);
				m_display.sendCommand(msg);
			}
			else
			{
				GameServer.ErrorCode error = m_game.processUserMessage(getUserInfo(), msg);
				
				if(GameServer.ErrorCode.Success != error)
				{
					retVal = ErrorCode.CommandProcessingFailed;
				}
			}
		}
		catch(Exception e)
		{
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private String escapeOutput(String strIn)
	{
		String strOut = "";
		
		
		
		return strOut;
	}
	
	public ErrorCode processGameServerCommand(Message msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		DisplayHelper.ErrorCode err = m_display.sendCommand(msg);
		
		if(DisplayHelper.ErrorCode.Success != err)
			retVal = ErrorCode.DisplayError;
		
		return retVal;
	}
}
