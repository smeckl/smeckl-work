package minimud_server;

import java.util.concurrent.Semaphore;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
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
	
    private static String m_strDBPassword = "";
    private static String m_strCertPwd = "";
    
    private static RegularExpressions regEx = new RegularExpressions();
    
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{    
		System.out.println("Starting Mini MUD server.");

		int nPort = 0;
		String strUser = "";		
		String strLogFile = "";
		String strDBServer = "";
        String strCertFile = "";
        String strPasswordFile = "";
		
		while(true)
		{
			if(args.length < 5)
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
            
            // The only invalid file paths in linux are ones that can't load
            strCertFile = args[3];
            
            strLogFile = args[4];
            
            strPasswordFile = args[5];
            
            if(!loadPasswords(strPasswordFile))
            {
                // If a password was specified, then use it
                if(args.length > 5)
                {
                    m_strDBPassword = args[5];
                }
                else
                {
                    // Have the user enter the password
                    System.out.print("Enter the password to the database: ");

                    char szPwd[] = System.console().readPassword();

                    if (null != szPwd)
                    {
                        m_strDBPassword = new String(szPwd);
                    }
                    else
                    {
                        System.out.println("Invalid database login credentials.");
                        break;
                    }
                }

                if(args.length > 6)
                {
                    m_strCertPwd = args[6];
                }
                else
                {
                    // Have the user enter the password
                    System.out.print("Enter the password to the certificate store: ");

                    char szPwd[] = System.console().readPassword();

                    if (null != szPwd)
                    {
                        m_strCertPwd = new String(szPwd);
                    }
                    else
                    {
                        System.out.println("Invalid certificate store login credentials.");
                        break;
                    }
                }
            }
			
			try
			{		
                System.out.close();
                System.err.close();
                
				logger = new MMLogger(strLogFile);
						
			    // Connect to the database
				m_dbConn = new DatabaseConnector(strDBServer, nPort, strUser, m_strDBPassword, logger);
				m_dbConn.connect();
				
				// Create the game server and load data into memory
				m_gameServer = new GameServer(m_dbConn, logger);
				
				GameServer.ErrorCode retVal = m_gameServer.loadGameData();
				
				if(GameServer.ErrorCode.Success == retVal)
				{
					// Start listening to new connections
					// This call blocks until the server is shut down
					startListener(SSL_PORT, strCertFile, m_strCertPwd);
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
		//Specifying the Keystore detailsg
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
    
    private static boolean loadPasswords(String strPasswordFile)
    {
        boolean bRet = true;
        
        try
        {
            FileInputStream in = new FileInputStream(strPasswordFile);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            // Try to read the database password
            String line = reader.readLine();

            if(null != line && regEx.stringMatchesRegEx(line, RegularExpressions.RegExID.PASSWORD))
            {
                m_strDBPassword = line;
            }
            else
            {
                bRet = false;
            }
            
            if(bRet)
            {
                line = reader.readLine();
                
                if(null != line && regEx.stringMatchesRegEx(line, RegularExpressions.RegExID.PASSWORD))
                {
                    m_strCertPwd = line;
                }
                else
                {
                    bRet = false;
                }
            }
            
            reader.close();
            in.close();
        } 
        catch (IOException e) 
        {
            bRet = false;
        }
        
        return bRet;
    }

}
