package MiniMUDClient;
import java.io.*;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.util.concurrent.Semaphore;
import MiniMUDShared.*;

public class MiniMUDClient
{
	public static String m_serverName = "localhost"; // SSL Server Name
	public static int SSL_PORT = 1443; 							// Port where the SSL Server is listening
	
	private static SSLSocket m_sslSocket = null;
	private static PrintWriter m_serverOut = null;
	private static BufferedReader m_serverIn = null;
	private static BufferedReader m_stdIn = null;
	private static Semaphore m_stopSem = new Semaphore(1);
	
	public enum ErrorCode
	{
		Success,
		Exception, 
		ObjectCreationFailed,
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
	    try
	    {
	    	while(true)
	    	{
		    	ErrorCode retVal = ErrorCode.Success;
		    	
		    	retVal = initializeClient();
		    	
		    	if(ErrorCode.Success != retVal)
		    	{
		    		System.out.println("Failed to connect to game server.");
		    		break;
		    	}
		    	
				String serverInput = "";
	
				while(!m_sslSocket.isClosed() && 1 == m_stopSem.availablePermits())
				{
					try
					{
						if((serverInput = m_serverIn.readLine()) != null)
						{
							//System.out.println("Raw server string: " + serverInput);
						    Message msg = parseServerCommand(serverInput);
						    
						    if(null != msg)
						    {
						    	executeServerCommand(msg);	    		
						    }
						}
					}
					catch(SocketTimeoutException e)
					{
						// Do nothing
					}
				}
				
				retVal = teardownClient();
		    	
		    	if(ErrorCode.Success != retVal)
		    	{
		    		System.out.println("Error disconnecting from game server.");
		    		break;
		    	}
				
				break;
	    	}
		}
	    catch(SocketException e)
	    {
	    	System.out.println("Disconnected from server");
	    	teardownClient();
	    }
	    catch(SSLException e)
	    {
	    	System.out.println("Disconnected from server");
	    	teardownClient();
	    }
		catch(Exception e)
		{
			System.out.println("Exception caught: " + e);
		}
	}
	
	private static ErrorCode initializeClient()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try 
	    {
	    	// Method for overriding certificate chain validation found at:  
	    	// 		https://code.google.com/p/misc-utils/wiki/JavaHttpsUrl
	    	
	    	 // Create a trust manager that does not validate certificate chains
	        final TrustManager[] trustAllCerts = new TrustManager[] 
	        { 
	        		new X509TrustManager() 
			        {
			            @Override
			            public void checkClientTrusted( final X509Certificate[] chain, final String authType ) 
			            {
			            }
			            
			            @Override
			            public void checkServerTrusted( final X509Certificate[] chain, final String authType ) 
			            {
			            }
			            
			            @Override
			            public X509Certificate[] getAcceptedIssuers() 
			            {
			                return null;
			            }
			        } 
	        };
	        
	        // Install the all-trusting trust manager
	        final SSLContext sslContext = SSLContext.getInstance("SSL");
	        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
	        
	        // Create an ssl socket factory with our all-trusting manager
	        final SSLSocketFactory socketFactory = sslContext.getSocketFactory();
	    	
			// Creating Client Sockets
			m_sslSocket = (SSLSocket)socketFactory.createSocket(m_serverName, SSL_PORT);
			m_sslSocket.setSoTimeout(1000);
			
         	// Initializing the streams for Communication with the Server
         	m_serverIn = new BufferedReader(new InputStreamReader(m_sslSocket.getInputStream()));
         	
         	// Initializing the streams for Communication with the Server
	     	m_serverOut = new PrintWriter(m_sslSocket.getOutputStream(), true);
         	
         	m_stdIn = new BufferedReader(new InputStreamReader(System.in));
			if(null == m_stdIn)
	     		retVal = ErrorCode.ObjectCreationFailed;
	    }
		catch(Exception e)
		{
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private static ErrorCode teardownClient()
	{
		ErrorCode retVal = ErrorCode.Success;
		try
		{
			// Closing the Streams and the Socket
			m_serverIn.close();
			
			if(!m_sslSocket.isClosed())
				m_sslSocket.close();
		}
		catch(Exception e)
		{
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	@SuppressWarnings("deprecation")
	private static Message parseServerCommand(String strMsg)
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
			System.out.println("Invalid Server Message.");
			msg = null;
		}
		
		return msg;
	}
	
	private static ErrorCode executeServerCommand(Message msg)
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			if(MessageID.CLIENT_SHOW_TEXT == msg.getMessageId())
			{
				System.out.println(msg.getClientDisplaytext());
			}
			else if(MessageID.CLIENT_REQUEST_INPUT == msg.getMessageId())
			{
				ClientRequestInputMessage clMsg = (ClientRequestInputMessage)msg;
				
				System.out.print(clMsg.getMessage());
				
				if(ClientRequestInputMessage.Type.Normal == clMsg.getInputRequestType())
				{
					String userInput = m_stdIn.readLine();
					
					if(null != userInput)
					{
					    m_serverOut.println(userInput);
					}
				}
				else if(ClientRequestInputMessage.Type.Password == clMsg.getInputRequestType())
				{
					char szPwd[] = System.console().readPassword();
					
					if(null != szPwd)
					{
						String strPwd = new String(szPwd);
						
						m_serverOut.println(strPwd);
					}
				}
			}
			else if(MessageID.SERVER_STATUS == msg.getMessageId())
			{
				ServerStatusMessage srvMsg = (ServerStatusMessage)msg;
				
				// If interactive logon is complete, then begin bi-directional communication
				if(ServerStatusMessage.Status.LOGON_SUCCESS == srvMsg.getStatus())
				{
					System.out.println("Login successful.");
					
					// Create and start thread to read user input and send it to the server
					UserInputThread userInThread = new UserInputThread(m_sslSocket, m_stopSem);
					userInThread.start();
				}
				else if(ServerStatusMessage.Status.LOGOUT == srvMsg.getStatus())
				{
					// acquire the semaphore to stop the client
					m_stopSem.acquire();
				}
			}
			else if(MessageID.USER_LOGOUT == msg.getMessageId())
			{
				// acquire the semaphore to stop the client
				System.out.println("Acquired sem");
				m_stopSem.acquire();
			}
		}
		catch(Exception e)
		{
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
}
