package MiniMUDClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class UserInputThread extends Thread
{
	public enum ErrorCode
	{
		Success,
		Exception,
		InvalidNetworkConnection,
		InvalidCommand,
		AccessDenied,
		ObjectCreationFailed
	}
	
	private Socket m_socket;
	private PrintWriter m_serverOut = null;
	private BufferedReader m_stdIn = null;
	private Semaphore m_stopSem = null;
	
	public UserInputThread(Socket socket, Semaphore stopSem)
	{
		setSocket(socket);
		m_stopSem = stopSem;
	}
	
	public void setSocket(Socket socket)
	{
		m_socket = socket;
	}
	
	private Socket getSocket()
	{
		return m_socket;
	}
	
	public void run()
	{
		ErrorCode retVal = ErrorCode.Success;
			
		try
		{
			while(true)
			{
				retVal = initializeThread();
				
				if(ErrorCode.Success != retVal)
				{
					System.out.println("Failed to initialize game session.");
					break;
				}
				
				String userInput = "";
		
				showPrompt();
				
				while(m_socket.isConnected() 
						&& 1 == m_stopSem.availablePermits())
				{
					if(m_stdIn.ready() 
						&& (userInput = m_stdIn.readLine()) != null)
					{
						if(!userInput.isEmpty())
							m_serverOut.println(userInput);
						
						//showPrompt();
					}
				}
				
				retVal = teardownThread();
				
				if(ErrorCode.Success != retVal)
				{
					System.out.println("Failed to teardown game session.");
					break;
				}
				
				break;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserConnectionThread::run() - " + e);
			retVal = ErrorCode.Exception;
		}
	}
	
	private void showPrompt()
	{
		System.out.print(">> ");
	}
	
	private ErrorCode initializeThread()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			while(true)
			{
				// Initializing the streams for Communication with the Server
		     	m_serverOut = new PrintWriter(getSocket().getOutputStream(), true);
		     	
		     	if(null == m_serverOut)
		     	{
		     		retVal = ErrorCode.ObjectCreationFailed;
		     		break;
		     	}

				m_stdIn = new BufferedReader(new InputStreamReader(System.in));
				if(null == m_stdIn)
				{
		     		retVal = ErrorCode.ObjectCreationFailed;
		     		break;
				}
		        
		        break;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserConnectionThread::initializeGameSession() - " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
	
	private ErrorCode teardownThread()
	{
		ErrorCode retVal = ErrorCode.Success;
		
		try
		{
			// Closing the Streams
			if(null != m_serverOut)
				m_serverOut.close();
			
			if(null != m_stdIn)
				m_stdIn.close();
			
			if(!m_socket.isClosed())
				m_socket.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in InputThread::teardownThread() - " + e);
			retVal = ErrorCode.Exception;
		}
		
		return retVal;
	}
}
