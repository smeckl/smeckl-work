package minimud_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Semaphore;

import minimud_shared.*;

public class UserConnectionThread extends Thread
{
	// Objects for IO with clients
	private Socket m_socket;
	private PrintWriter m_textOut;
	private BufferedReader m_textIn;
	
	// Main object for keeping track of the game state
	private GameServer m_game = null;
	
	// For connecting to the database
	private DatabaseConnector m_dbConn = null;
	
	// State of this user's session
	private UserSessionState m_state = UserSessionState.Unauthenticated;
	private UserInfo m_userInfo = null;;
	
	// If this is acquired, then we should stop
	private Semaphore m_stopSem = null;
	
	// For system logs
	private static MMLogger m_logger = null;
	
	// The room the current user is in
	private Room m_curRoom = null;
	
	// Helper object for sending commands to the user
	private DisplayHelper m_display = null;
    
    private String m_strUsername = "";
    
    private static int SOCKET_WAIT_MS = 1000;
	
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
	
	public UserConnectionThread(Socket socket, GameServer game, DatabaseConnector dbConn, 
								Semaphore stopSem, MMLogger logger)
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
	
	public void displayCurrentRoom()
	{
		Room cur = getCurrentRoom();
		
		if(null != cur)
			cur.displayRoom(m_display);
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
				// Initialize the user's game session
				retVal = initializeGameSession();
				
				// Start the user in the unauthenticated state
				setUserState(UserSessionState.Unauthenticated);
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("UserConnectionThread::run() - Initialization failed.");
					break;
				}
				
				// Send the welcome message
				retVal = printWelcomeMessage(); 
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("UserConnectionThread::run() - User logon failed.");
					break;
				}
				
				// Loop until the user is logged in
				retVal = loginUser(); 
				
				if(ErrorCode.Success != retVal)
				{
					m_logger.severe("UserConnectionThread::run() - User logon failed.");
					break;
				}
				
                // Load user info from database into memory.
               m_userInfo = getDatabaseConnector().loadUserInfo(m_strUsername);
               
               if(null == m_userInfo)
               {
                   m_logger.severe("UserConnectionThread::run() - Failed to load user record.");
					break;
               }
                
				// If we got this far, the user is logged in
				setUserState(UserSessionState.Playing);
				
				// Display the current room's text (set by the GameServer object
				getCurrentRoom().displayRoom(m_display);
					
				m_socket.setSoTimeout(SOCKET_WAIT_MS); // Don't want reads to block forever
				
				// Loop until the user logs out or the socket is disconnected
				while(ErrorCode.Success == retVal 
					  && 1 == m_stopSem.availablePermits()
					  && UserSessionState.Playing == getUserState()
					  && getSocket().isConnected())
				{	
			        String inputLine;
			
			        // Try to read a message from the client
			        if((inputLine = ReceiveMessage()) != null)
			        {
			        	// If a message is recieved, parse it into a Message object
			        	Message msg = parseClientCommand(inputLine);
					    
					    if(null != msg)
					    {
					    	// We have a valid Message object, process it
					    	retVal = executeClientCommand(msg);	    		
					    }
					    else
					    {
					    	// Failed to parse command, let the user know
					    	m_display.sendText("Invalid command.");
					    	
					    	m_logger.info("Received invalid command from user (" + getUserInfo().getUserName() 
										  + "): " + inputLine);
					    }
			        }		 
				}
				
				// The user has logged out or disconnected, stop the session
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
	        			getGameServer().addUser(this, strUserName);
                        
                        m_strUsername = strUserName;
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
			        			getGameServer().addUser(this, strUserName);
                                
                                m_strUsername = strUserName;
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
	
	private static Message parseClientCommand(String strMsg)
	{
		Message msg = null;
		
		try
		{
			MessageParser parser = new MessageParser(new StringReader(strMsg));
		    
			parser.parse();
			
			msg = parser.getLastMessage();
		}
		catch(java.lang.Error e)
		{
			m_logger.severe("Invalid Server Message.");
			msg = null;
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
				// Set user state to Logging Out
				setUserState(UserSessionState.LoggingOut);
				
				// Remove the user from the GameServer object
				m_game.removeUser(this);
			
				// Send the logout message to the user
				m_display.sendCommand(msg);
			}
			else
			{
				GameServer.ErrorCode error = m_game.processUserMessage(this, msg);
				
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
	
	public ErrorCode processGameServerCommand(Message msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		DisplayHelper.ErrorCode err = m_display.sendCommand(msg);
		
		if(DisplayHelper.ErrorCode.Success != err)
			retVal = ErrorCode.DisplayError;
		
		return retVal;
	}
}
