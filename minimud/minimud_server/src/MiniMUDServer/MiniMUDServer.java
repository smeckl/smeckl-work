package MiniMUDServer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import MiniMUDShared.*;
/**
 * @author Steve Meckl
 *
 */

public class MiniMUDServer
{
	private static final Logger logger = Logger.getLogger(MiniMUDServer.class.getName());
	
	public static int SSL_PORT = 1443;
	private static GameServer m_gameServer = null;
	private static DatabaseConnector m_dbConn = null;
	private static Semaphore m_stopSem = new Semaphore(1);
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Handler fh = null;
	    
		System.out.println("Starting Mini MUD server.");

		int nPort = 0;
		String strUser = "";
		String strPassword = "";
		String strLogFile = "./log.txt";
		String strDBServer = "";
		
		RegularExpressions regEx = new RegularExpressions();
		
		while(true)
		{
			if(args.length < 4)
			{
				System.out.println("Invalid number of arguments.");
				break;
			}
			
			// Validate server format
			if(regEx.stringMatchesRegEx(args[0], RegularExpressions.RegExID.IP)
					|| regEx.stringMatchesRegEx(args[0], RegularExpressions.RegExID.DOMAIN))
			{
				strDBServer = args[0];
			}
			else
			{
				System.out.println("Invalid server name or IP address specified.");
				break;
			}
			
			// Validate port number format
			if(regEx.stringMatchesRegEx(args[1], RegularExpressions.RegExID.PORT))
			{
				nPort = Integer.parseInt(args[1]);
			}
			else
			{
				System.out.println("Invalid port number specified.");
				break;
			}
			
			// Validate user name format
			if(regEx.stringMatchesRegEx(args[2], RegularExpressions.RegExID.USERNAME))
			{
				strUser = args[2];
			}
			else
			{
				System.out.println("Invalid user name.");
				break;
			}
			
			// Validate password format
			if(regEx.stringMatchesRegEx(args[3], RegularExpressions.RegExID.PASSWORD))
			{
				strPassword = args[3];
			}
			else
			{
				System.out.println("Invalid password format.");
				break;
			}
			
			// If a log file was specified, then use it
			if(args.length == 5)
			{
				strLogFile = args[4];
			}
			
			try
			{
				// Set up the logger object
				fh = new FileHandler(strLogFile, true);
				
				logger.addHandler(fh);
			    logger.setLevel(Level.FINE);
				
			    // Connect to the database
				m_dbConn = new DatabaseConnector(strDBServer, nPort, strUser, strPassword, logger);
				m_dbConn.connect();
				
				// Create the game server and load data into memory
				m_gameServer = new GameServer(m_dbConn, logger);
				
				GameServer.ErrorCode retVal = m_gameServer.loadGameData();
				
				if(GameServer.ErrorCode.Success == retVal)
				{
					// Start listening to new connections
					// This call blocks until the server is shut down
					startListener(SSL_PORT);
				}
				else
					logger.severe("Failed to load game data.");
				
				// We're done.  Close the connection to the database
				m_dbConn.disconnect();
			}
			catch(Exception e)
			{
				
			}
			
			break;
		}
	}
	
	public static void startListener(int nPort)
	{	
		//Specifying the Keystore details
		System.setProperty("javax.net.ssl.keyStore","/home/steve/.keystore");
		System.setProperty("javax.net.ssl.keyStorePassword","%t0rmP34ks");
		
		try
		{
			logger.info("Creating SSLServerSocketFactory object");
			SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
			
			logger.info("Creating SSLServerSocket object on port " + nPort);
			SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(nPort);
			
			logger.info("Starting to listen on port " + nPort);
			
			boolean bListening = true;
			
			while(true == bListening)
			{
				SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
				
				logger.info("Accepted user connection");
				
				UserConnectionThread userThread = new UserConnectionThread(sslSocket, m_gameServer, m_dbConn, m_stopSem, logger);
				userThread.start();
			}		
			
			logger.info("Closing SSLServerSocket object");
	        sslServerSocket.close();
		}
		catch (Exception e)
		{
			logger.severe("Exception caught: " + e);
		}
	}

}
