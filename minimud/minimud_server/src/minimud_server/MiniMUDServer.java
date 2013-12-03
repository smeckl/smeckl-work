package minimud_server;

import java.util.concurrent.Semaphore;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import minimud_shared.*;
/**
 * @author Steve Meckl
 *
 */

public class MiniMUDServer
{
	private static MMLogger logger = null;
	
	public static int SSL_PORT = 1443;
	private static GameServer m_gameServer = null;
	private static DatabaseConnector m_dbConn = null;
	private static Semaphore m_stopSem = new Semaphore(1);
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{    
		System.out.println("Starting Mini MUD server.");

		int nPort = 0;
		String strUser = "";
		String strDBPassword = "";
		String strLogFile = "./log.txt";
		String strDBServer = "";
        String strCertFile = "";
        String strCertPwd = "";
		
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
            
            strCertFile = args[3];
            
            // If a password was specified, then use it
            if(args.length > 4)
            {
                strDBPassword = args[4];
            }
            else
            {
                // Have the user enter the password
                System.out.print("Enter the password to the database: ");

                char szPwd[] = System.console().readPassword();

                if (null != szPwd)
                {
                    strDBPassword = new String(szPwd);
                }
                else
                {
                    System.out.println("Invalid database login credentials.");
                    break;
                }
            }
            
            if(args.length == 6)
            {
                strCertPwd = args[5];
            }
            else
            {
                // Have the user enter the password
                System.out.print("Enter the password to the certificate store: ");

                char szPwd[] = System.console().readPassword();

                if (null != szPwd)
                {
                    strCertPwd = new String(szPwd);
                }
                else
                {
                    System.out.println("Invalid certificate store login credentials.");
                    break;
                }
            }
			
			try
			{				
				logger = new MMLogger(null);
						
			    // Connect to the database
				m_dbConn = new DatabaseConnector(strDBServer, nPort, strUser, strDBPassword, logger);
				m_dbConn.connect();
				
				// Create the game server and load data into memory
				m_gameServer = new GameServer(m_dbConn, logger);
				
				GameServer.ErrorCode retVal = m_gameServer.loadGameData();
				
				if(GameServer.ErrorCode.Success == retVal)
				{
					// Start listening to new connections
					// This call blocks until the server is shut down
					startListener(SSL_PORT, strCertFile, strCertPwd);
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
	
	public static void startListener(int nPort, String strKeyStoreFile, String strKeyStorePwd)
	{	
		//Specifying the Keystore details
		System.setProperty("javax.net.ssl.keyStore", strKeyStoreFile);
		System.setProperty("javax.net.ssl.keyStorePassword", strKeyStorePwd);
		
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
